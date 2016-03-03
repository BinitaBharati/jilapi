/**
 * 
 * @author binita.bharati@gmail.com
 * Entry point to Jilapi.
 * 
 * Jilapi is a CLI command line parser.It parses the CLI command output line wise.
 *
 *
 */

package com.github.binitabharati.jilapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binitabharati.jilapi.util.PositionalFieldHandler;
import com.github.binitabharati.jilapi.util.PrefixFieldHandler;
import com.github.binitabharati.jilapi.util.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Jilapi {
    
    public static final Logger logger = LoggerFactory.getLogger(Jilapi.class);

    private  Properties prop;
    private  String commandKey;
    private  JsonElement data;   
    private  Map<ProcessingEntity, Boolean> headerMap;
    private  Map<ProcessingEntity, Boolean> fieldPositonalMap;
    private  Map<ProcessingEntity, Boolean> fieldPrefixMap;
    private  Map<ProcessingEntity, Boolean> fieldMixMap;
    private  Map<ProcessingEntity, Boolean> footerMap;
    private  Map<ProcessingEntity, Boolean> sectionMap;
    private  Map<ProcessingEntity, Boolean> ignoreMap;
    private  JsonArray sectionData;
    private  String fieldDelimterRegex;
    private  String entityDelimterRegex;
    private  boolean startFound;
    private  Map<ProcessingEntity, Boolean> fieldMapper;
    
    public Jilapi(Properties prop, String commandKey) throws Exception {
        this.prop = prop;
        this.commandKey = commandKey;
        init(commandKey);
    }
    
    private void init(String commandKey) throws Exception {           
        List<String> tmpList = null;
        String  tmpProp = null;
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_HEADER);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.headerMap = constructUnprocessedStatusMap(tmpList);
            }
               
        }
       
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_ENTITY_POSITIONAL_MAP);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.fieldPositonalMap = constructUnprocessedStatusMap(tmpList);
            }
        }
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_ENTITY_PREFIX_MAP);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.fieldPrefixMap = constructUnprocessedStatusMap(tmpList);
            }
        }
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_ENTITY_MIX_MAP);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.fieldMixMap = constructUnprocessedStatusMap(tmpList);
            }
        }
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_FOOTER);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.footerMap = constructUnprocessedStatusMap(tmpList);
            }
        }
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_SECTIONS);   
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.sectionMap = constructUnprocessedStatusMap(tmpList);
            }
        }
       
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_IGNORE); 
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.ignoreMap = constructUnprocessedStatusMap(tmpList);
            }
        }
              
        if (sectionMap != null && sectionMap.size() != 0) {
            data = new JsonObject();
        } else {
            data = new JsonArray();
        }
        
        String cmdFieldDelimit = prop.getProperty(commandKey + Utils.CMND_FIELD_DELIMITER_KEY);
        logger.info("cmdFieldDelimit = " + cmdFieldDelimit);
        
        if (cmdFieldDelimit == null || cmdFieldDelimit.equals("")) {
            fieldDelimterRegex = Utils.CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX;
           
        } else {
            fieldDelimterRegex = cmdFieldDelimit;
        }
             
        logger.info("fieldDelimterRegex = " + fieldDelimterRegex);
        
        if (headerMap == null || headerMap.size() == 0) {
            sectionData = new JsonArray();
            startFound = true;
        }
        
        String entityDelimit = prop.getProperty(commandKey + Utils.CMND_ENTITY_DELIMITER);
        if (entityDelimit == null || entityDelimit.equals("")) {
            entityDelimterRegex = Utils.CMND_ENTITY_DELIMITER_VAL_DEFAULT;
        } else {
            if (entityDelimit.equals("EMPTY_LINE")) {
                entityDelimterRegex = Utils.CMND_ENTITY_DELIMITER_EMPTY_LINE;
            } else {
                throw new Exception("Unknown entity delimiter provided." + entityDelimterRegex + " has not been yet handled");
            }
           
        }
        
        if (fieldPositonalMap == null && fieldPrefixMap == null && fieldMixMap == null) {
            throw new Exception("Either of the result.entity.field.positional.map or result.entity.field.prefix.map or result.entity.field.mix.map should be set");
        }
        
        if (fieldPositonalMap != null && fieldPrefixMap != null && fieldMixMap != null) {
            throw new Exception("All of result.entity.field.positional.map and result.entity.field.prefix.map and result.entity.field.mix.map are set.Only 1 of them should be set.");
        } else if (fieldPositonalMap != null && fieldPrefixMap != null) {
            throw new Exception("Both result.entity.field.positional.map and result.entity.field.prefix.map are set.Only 1 of them should be set.");
        } else if (fieldPrefixMap != null && fieldMixMap != null) {
            throw new Exception("Both result.entity.field.prefix.map and result.entity.field.mix.map are set.Only 1 of them should be set.");
        } else if (fieldMixMap != null && fieldPositonalMap != null) {
            throw new Exception("Both result.entity.field.mix.map and result.entity.field.positional.mapare set.Only 1 of them should be set.");
        }
        
        if (fieldPositonalMap != null) {
            fieldMapper = fieldPositonalMap;
        } else if (fieldPrefixMap != null) {
            fieldMapper = fieldPrefixMap;
        } else {
            fieldMapper = fieldMixMap;
        }
            
         
    }
    
    public String parseCommand(InputStream is) throws Exception {
        
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return parseCommand(br);
    }
    
    public  String parseCommand(String multiLineString) throws Exception {
        StringReader sr = new StringReader(multiLineString);
        BufferedReader br = new BufferedReader(sr);
        return parseCommand(br);
    }
    
    private String parseCommand(BufferedReader br) throws Exception {
        String line = null;
        
        if (entityDelimterRegex.equals(Utils.CMND_ENTITY_DELIMITER_VAL_DEFAULT)) {
            while ((line = br.readLine()) != null) {
                parseLine(line);
            } 
        } else { //non default entity delimiter
            StringBuffer tmp = new StringBuffer();
            while ((line = br.readLine()) != null) {
                
                if (line.trim().equals(entityDelimterRegex)) {
                    parseLine(tmp.toString());
                    tmp = new StringBuffer();

                } else {
                    tmp.append(line);
                }
            } 
            
        }
              
        //Happens when there is no footer.
        if (data.isJsonArray()) {
            JsonArray tmp = (JsonArray)data;
            logger.info("iteration over, tmp size = " + tmp.size());
            if (tmp.size() == 0) {
                data = sectionData;
               
            }
        }
        logger.info("parseCommand: finally data = " + Utils.preetyPrintJson(data.toString()));
        return data.toString();
    }
    
    private void parseLine(String line) throws Exception {
        
        logger.info("parseLine: entered with line = " + line + ", startFound = " + startFound);
        if (!startFound) {
            startFound = isStartFound(line);
            return;
        } else if (isEndFound(line)) {
            logger.info("endFound @ line " + line);
            logger.info("sectionStatus = " + sectionMap);
            if (sectionMap != null && sectionMap.size() != 0) { //section and footer present.                       
                ProcessingEntity unprocessedSection = allDataProcessed(sectionMap);  
                logger.info("endFound , unprocessedSection = " + unprocessedSection);
                logger.info("end found, data = " + data);
                if (unprocessedSection != null) {
                    String key = unprocessedSection.getKey();
                    if (!key.equals("")) {
                        ((JsonObject)data).add(key, sectionData);
                       
                    }
                    logger.info("end found , settng section sttaus to true for " + unprocessedSection);
                    sectionMap.put(unprocessedSection, true);
                }
                
            } else {
                //section absent, footer present 
                data = sectionData;
            }                    
            startFound = false;  
            ProcessingEntity procStatus = allDataProcessed(fieldMapper);
            if (procStatus != null) {
                String lineMapperStr = procStatus.getKey();
                if (lineMapperStr != null && !lineMapperStr.equals("")) {
                    fieldMapper.put(procStatus, true);
                }
                
            }
           
            logger.info("end found2, data = " + data);
        }
        if (startFound && !isLineIgnored(line)) {
            JsonObject ret = mapDelimitedOp(line);
            if (ret != null) {
                sectionData.add(ret);    
            }
                           
        } 
        
    }
    
    private Map<ProcessingEntity, Boolean> constructUnprocessedStatusMap(List<String> input) {
        Map<ProcessingEntity, Boolean> out = null;
        if (input != null && input.size() != 0) {
            out = new LinkedHashMap<ProcessingEntity, Boolean>();
            int index = 0;
            for (String each : input) {
                ProcessingEntity status = new ProcessingEntity(index, each);
                out.put(status, false); //unprocessed
                index++;
            }
        }   
        return out;
    }
    
    private boolean isStartFound(String line) {
        if (!startFound) {
            logger.info("Searching for headers");
            if (headerMap != null && headerMap.size() != 0) {
                //Implies headers present   
                ProcessingEntity procStatus = allDataProcessed(headerMap);
                logger.info("procStatus = " + procStatus);
                if (procStatus != null) {
                    String unprocessedHeader = procStatus.getKey();
                    if (unprocessedHeader != null) { //unprocessed header present                        
                        String temp1 = unprocessedHeader.trim().replaceAll(",", "").replaceAll(" +", "").toLowerCase();                     
                        String temp2 = line.trim().replaceAll(" +", "").toLowerCase();
                        if (temp1.equals(temp2)) {                               
                            startFound = true;                          
                            headerMap.put(procStatus, true);  //processed
                            sectionData = new JsonArray();                                   
                        } 
                    } else { //no header at all | all headers exhausted.
                        if (headerMap != null && headerMap.size() == 0) { //no header at all
                            startFound = true;
                            sectionData = new JsonArray();
                        }
                        
                    }
                }              
                             
            } else { //no headers
                startFound = true;
                sectionData = new JsonArray();
            }
           
        } 
        return startFound;
    }
    
    //This can not be called till header is found.
    private boolean isEndFound(String line) {
        logger.info("isEndFound : footerStatusMap = " + footerMap + "line = " + line);
        boolean stopProcessing = false;
        if (footerMap != null && footerMap.size() != 0) {
            ProcessingEntity procStatus = allDataProcessed(footerMap);
            if (procStatus != null) {
                String unprocessedFooter = procStatus.getKey();  
                logger.info("unproc footer = " + unprocessedFooter + ", line = " + line);
                if (unprocessedFooter != null) {
                    if (!unprocessedFooter.equals("")) {
                        if (line.matches(unprocessedFooter)) {
                            logger.info("line = " + line + " MATCHES " + unprocessedFooter);
                            stopProcessing = true;
                            footerMap.put(procStatus, true);                                   
                        }                      
                    }                       
                }
            }          
        }
        //this means no footer present at all.   
        return stopProcessing;
    }
    
    private boolean isLineIgnored(String line) {
        ProcessingEntity status = allDataProcessed(ignoreMap);
        if (status != null) {
            String ignoredString = status.getKey();           
            if (ignoredString != null) {
                if (!ignoredString.equals("")) {
                    if (line.startsWith(ignoredString)) {
                        ignoreMap.put(status, true);
                        return true;                            
                    }                           
                }                   
            }
        }      
        return false;
    }
       
    
    private ProcessingEntity allDataProcessed(Map<ProcessingEntity, Boolean> statusMap) {
        if (statusMap != null) {
            Iterator<ProcessingEntity> statusMapItr = statusMap.keySet().iterator();
            while (statusMapItr.hasNext()) {
                ProcessingEntity status = statusMapItr.next();
                if (!statusMap.get(status)) {
                    return status;
                }                 
            }
        }       
        return null;
    }
    
    private JsonObject mapDelimitedOp(String delimitedLine) {      
        logger.info("delimitedLine: " + delimitedLine + ", fieldMap = " + fieldMapper);   
        logger.info("mapDelimitedOp: split regex = " + fieldDelimterRegex);       
        JsonObject ret = null;    
        if (fieldDelimterRegex.equals(Utils.CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX)) {
            //Remove consecutive spaces 
            delimitedLine = delimitedLine.trim().replaceAll(fieldDelimterRegex, " ");
        }
        String[] opLineTmp = delimitedLine.trim().split(fieldDelimterRegex);       
        logger.info("opLineTmp = " + Arrays.asList(opLineTmp)); 
        PositionalFieldHandler posHandler = new PositionalFieldHandler();
        PrefixFieldHandler preHandler = new PrefixFieldHandler(fieldDelimterRegex);
        int lastPrefixMatchIdx = 0;
        ProcessingEntity status = allDataProcessed(fieldMapper);
        if (status != null) {
            String lineMapperStr = status.getKey();
            if (lineMapperStr != null) {
                if (!lineMapperStr.equals("")) {
                    String[] temp = lineMapperStr.split(",");
                    //Check if all the fields as given by lineMapper are present in the input delimitedLine 
                    if (opLineTmp.length >= temp.length) {
                        ret = new JsonObject();
                        for (String x : temp) {
                            logger.info("x -> " + x);
                            String fieldMapVal = x.trim().substring(x.lastIndexOf(":") + 1); 
                            String fieldMapKey = x.trim().substring(0, x.lastIndexOf(":")).trim();
                            String fieldVal = null;
                           
                            if (fieldPositonalMap != null) {                             
                                
                                fieldVal = posHandler.handlePositionalField(fieldMapKey, opLineTmp);
                            
                            } else if (fieldPrefixMap != null) { //prefix map
                                fieldVal = preHandler.handlePrefixMap(fieldMapKey, delimitedLine);
                            } else { //mix map
                                if (fieldMapKey.matches("[0-9]+")) { //positional field
                                    posHandler.handlePositionalField(fieldMapKey, opLineTmp);
                                } else { //prefix field
                                    fieldVal = preHandler.handlePrefixMap(fieldMapKey, delimitedLine);
                            
                                }                            
                            } 
                            ret.addProperty(fieldMapVal, fieldVal);         
                    
                        }              
                    }           
                }
            }
        }
        
        logger.info("jsonObj = " + ret);       
        return ret;
    }
    
    
    
}
