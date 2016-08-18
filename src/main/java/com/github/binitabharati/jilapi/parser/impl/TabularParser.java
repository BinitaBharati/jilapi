/**
 * 
 * @author binita.bharati@gmail.com
 * Tabular Parser handles tabular data.This reads data line-wise.
 * See com.github.binitabharati.jilapi.SampleTest:testSample1,testSample2,testSample3,testSample4() test case. * 
 *
 *
 */

package com.github.binitabharati.jilapi.parser.impl;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binitabharati.jilapi.entity.parser.impl.TabularEntityParser;
import com.github.binitabharati.jilapi.parser.CommandParser;
import com.github.binitabharati.jilapi.parser.ds.ProcessingEntity;
import com.github.binitabharati.jilapi.util.Utils;
import com.google.gson.Gson;


public class TabularParser extends CommandParser {
    
    public static final Logger logger = LoggerFactory.getLogger(TabularParser.class);
    
    private  Map<ProcessingEntity, Boolean> fieldPositonalMap;
   
    public void init(String commandKey, Properties prop) throws Exception {
        String  tmpProp = null;
        List<String> tmpList = null;
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_PARSING_STOP);
        if (tmpProp != null) {
            stop = tmpProp;
        }

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
               
        String cmdFieldDelimit = prop.getProperty(commandKey + Utils.CMND_FIELD_DELIMITER_KEY);
        logger.info("cmdFieldDelimit = " + cmdFieldDelimit);
        
        if (cmdFieldDelimit == null || cmdFieldDelimit.equals("")) {
            fieldDelimterRegex = Utils.CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX;
           
        } else {
            fieldDelimterRegex = cmdFieldDelimit;
        }
             
        logger.info("fieldDelimterRegex = " + fieldDelimterRegex);
        
        if (headerMap == null || headerMap.size() == 0) {
            
            startFound = true;
        }
       
        entityDelimter = Utils.CMND_ENTITY_DELIMITER_VAL_DEFAULT;
        String entityDelimit = prop.getProperty(commandKey + Utils.CMND_ENTITY_DELIMITER);
        if (entityDelimit != null && !entityDelimit.equals("\n")) {
            throw new Exception("Tabular parser only handles new line as entity delimiter!. But, you specified it as " + entityDelimit);
        } 
       
    }
    
    public String parseCommand(BufferedReader br) throws Exception {        
        Gson gson = new Gson();
        Object ret = parseCommand2(br);
        return gson.toJson(ret);     
    }
    
    @Override
    public Object parseCommand2(BufferedReader br) throws Exception {
        // TODO Auto-generated method stub
        String line = null;
        List<Map<String, Object>> entityDataList = new ArrayList<Map<String, Object>>();
        //Below structure only used when there are sections.
        Map<String, List<Map<String, Object>>> tmp2 = new LinkedHashMap<String, List<Map<String, Object>>>();
        while ((line = br.readLine()) != null) {
            if (stop!= null && line.matches(stop)) {
                break;
            }
            logger.info("parseLine: entered with line = " + line + ", startFound = " + startFound);
            if (!startFound) {
                startFound = isStartFound(line);
                continue;
            } else if (isEndFound(line)) {
                logger.info("endFound @ line " + line);
                logger.info("sectionStatus = " + sectionMap);
                if (sectionMap != null && sectionMap.size() != 0) { //section and footer present.                       
                    ProcessingEntity unprocessedSection = allDataProcessed(sectionMap);  
                    logger.info("endFound , unprocessedSection = " + unprocessedSection);
                    if (unprocessedSection != null) {
                        String key = unprocessedSection.getKey();
                        if (!key.equals("")) {
                            //((JsonObject)data).add(key, sectionData);
                            tmp2.put(key, entityDataList);
                           
                        }
                        logger.info("end found , settng section status to true for " + unprocessedSection);
                        sectionMap.put(unprocessedSection, true);
                    }
                    
                }                
                startFound = false;  
                ProcessingEntity procStatus = allDataProcessed(fieldPositonalMap);
                if (procStatus != null) {
                    String lineMapperStr = procStatus.getKey();
                    if (lineMapperStr != null && !lineMapperStr.equals("")) {
                        fieldPositonalMap.put(procStatus, true);
                    }
                    
                }
                //Check if there is any more data to be processed, else quit.
                procStatus = allDataProcessed(fieldPositonalMap);
                if (procStatus != null) {
                    entityDataList = new ArrayList<Map<String, Object>>();
                    continue;
                } else {
                    break;
                }
                
            }
            if (startFound && !isLineIgnored(line)) {
                //Map<String, String> entityData = mapDelimitedOp(line);
                ProcessingEntity status = allDataProcessed(fieldPositonalMap);
                TabularEntityParser tb = new TabularEntityParser(status, fieldDelimterRegex);
                Map<String, Object> entityData = tb.parse(line);
                if (entityData != null) {
                    entityDataList.add(entityData);
                }
                           
            } 
        }
        if (tmp2 != null && tmp2.size() != 0) { //sections present 
            return tmp2;
        } else {
            return entityDataList;
        }
    }

}
