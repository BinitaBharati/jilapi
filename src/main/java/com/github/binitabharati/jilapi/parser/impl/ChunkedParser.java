/**
 * 
 * @author binita.bharati@gmail.com
 * Chunked Parser handles data where-in a entity spans across multiple lines.
 * ie entity data is available in chunks.
 * 
 * See com.github.binitabharati.jilapi.SampleTest:testSample5() test case.
 * 
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

import com.github.binitabharati.jilapi.entity.parser.EntityParser;
import com.github.binitabharati.jilapi.parser.CommandParser;
import com.github.binitabharati.jilapi.parser.ds.ProcessingEntity;
import com.github.binitabharati.jilapi.util.Utils;
import com.google.gson.Gson;


public class ChunkedParser extends CommandParser {
    
    public static final Logger logger = LoggerFactory.getLogger(ChunkedParser.class);

    private Map<String, String> PROP_UNWRITABLE_DELIMITS;
    private EntityParser entityParser;
  

    public void init(String commandKey, Properties prop) throws Exception {
        // TODO Auto-generated method stub
        PROP_UNWRITABLE_DELIMITS = new LinkedHashMap<String, String>();
        PROP_UNWRITABLE_DELIMITS.put("EMPTY_LINE", "");
        PROP_UNWRITABLE_DELIMITS.put("BLANK_LINE", " ");
        
        String  tmpProp = null;
        List<String> tmpList = null;
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_PARSING_STOP);
        if (tmpProp != null) {
            stop = tmpProp;
        }
        
        entityDelimter = prop.getProperty(commandKey + Utils.CMND_ENTITY_DELIMITER);
        if (PROP_UNWRITABLE_DELIMITS.containsKey(entityDelimter)) {
            entityDelimter = PROP_UNWRITABLE_DELIMITS.get(entityDelimter);
        }
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_RESULT_HEADER);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                this.headerMap = constructUnprocessedStatusMap(tmpList);
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
               
        if (headerMap == null || headerMap.size() == 0) {
            startFound = true;
        }
        
        String chunkedEntityParserClassName = prop.getProperty(commandKey + Utils.CMND_CHUNKED_ENTITY_PARSER);
        if (chunkedEntityParserClassName == null || chunkedEntityParserClassName.equals("")) {
            
        }
        
        Class<?> clazzEntityParser = Class.forName(chunkedEntityParserClassName);
        entityParser = (EntityParser)clazzEntityParser.newInstance();
        
        
    }


    @Override
    public String parseCommand(BufferedReader br) throws Exception {
        // TODO Auto-generated method stub
        Object ret = parseCommand2(br);
        Gson gson = new Gson();
        return gson.toJson(ret);
    }
    
    public Object parseCommand2(BufferedReader br) throws Exception {
        
        StringBuffer tmp = new StringBuffer();        
        List<Map<String, ?>> entityList = new ArrayList<Map<String, ?>>();
        //Below structure only used when there are sections.
        Map<String, List<Map<String, ?>>> sectionData = new LinkedHashMap<String, List<Map<String, ?>>>();  
        String line = null;
        while ((line = br.readLine()) != null) {
            if (stop!= null && line.startsWith(stop)) {
                break;
            }
            if (startFound) {
                if (line.trim().equals(entityDelimter)) {
                    Map<String, ?> entityData = parseEntity(tmp.toString());
                    if (entityData != null) {
                        
                        processToRenderJsonOut(entityList, entityData, sectionData);
                        tmp = new StringBuffer();
                    }
                   
                } else {
                    ProcessingEntity unprocessedIgnore = allDataProcessed(ignoreMap); 
                    if (unprocessedIgnore != null) {
                        String ignore = unprocessedIgnore.getKey();
                        if (line.startsWith(ignore)) {
                            ignoreMap.put(unprocessedIgnore, true);
                            continue;
                            
                        }
                    }
                    tmp.append(line);
                }
            } else  {
                startFound = isStartFound(line);
            }
           
        } 
        //check if BufferedReader reading has finished and still some data is there in the tmp entity bufffer.
        //This can happen only when the last entity doesn't have the entity delimiter at all. But, the last entity
        //is at the end of the command output itself with no delimiter.
        if (tmp.toString().length() != 0) {
            //some unprocessed entity remaining
            Map<String, ?> entityData = parseEntity(tmp.toString());
            processToRenderJsonOut(entityList, entityData, sectionData);
        }
        Gson gson = new Gson();
        if (sectionMap != null && sectionMap.size() != 0) {
            return sectionData;
        } else {
            return entityList;
        }
    }
    
    
    private Map<String, ?> parseEntity(String line) throws Exception {
        Map<String, ?> ret = null;
        if (line != null && !line.equals("") && !line.equals(" ")) {
            ret = entityParser.parse(line);
        }
        return ret;       
    }
    
    private void processToRenderJsonOut(List<Map<String, ?>> entityList, Map<String, ?> entityData,
            Map<String, List<Map<String, ?>>> sectionData) {
        if (entityData != null) {
            entityList.add(entityData);
            if (sectionMap != null && sectionMap.size() != 0) {
                ProcessingEntity unprocessedSection = allDataProcessed(sectionMap);  
                logger.info("parseCommand: unprocessedSection = " + unprocessedSection);
                if (unprocessedSection != null) {
                    String key = unprocessedSection.getKey();
                    if (!key.equals("")) {
                        sectionData.put(key, entityList);
                        sectionMap.put(unprocessedSection, true);
                    }
                }
            } 
            if (headerMap != null && headerMap.size() != 0) {
                startFound = false;
            }
        }
    }


}
