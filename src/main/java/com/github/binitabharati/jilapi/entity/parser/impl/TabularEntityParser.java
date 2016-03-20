/**
 * 
 * @author binita.bharati@gmail.com
 * Tabular entity parser. This is the OOB implementation, and user is NOT required
 * to write his own entity parser impl, for tabular data.
 * 
 */
package com.github.binitabharati.jilapi.entity.parser.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binitabharati.jilapi.entity.parser.EntityParser;
import com.github.binitabharati.jilapi.parser.ds.ProcessingEntity;
import com.github.binitabharati.jilapi.util.Utils;

public class TabularEntityParser implements EntityParser {
    public static final Logger logger = LoggerFactory.getLogger(TabularEntityParser.class);

    private ProcessingEntity status;
    private String fieldDelimterRegex;
    //private  Map<ProcessingEntity, Boolean> fieldPositonalMap;
    
    public TabularEntityParser(ProcessingEntity pe, String fieldDelimterRegex) {
        this.status = pe;
        this.fieldDelimterRegex = fieldDelimterRegex;
        //this.fieldPositonalMap = fieldPositonalMap;
    }

    public Map<String, Object> parse(String delimitedLine) {
        // TODO Auto-generated method stub
        logger.info("delimitedLine: " + delimitedLine);   
        logger.info("mapDelimitedOp: split regex = " + fieldDelimterRegex);       
        Map<String, Object> ret = null;
        
        if (fieldDelimterRegex.equals(Utils.CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX)) {
            //Remove consecutive spaces 
            delimitedLine = delimitedLine.trim().replaceAll(fieldDelimterRegex, " ");
        }
        String[] opLineTmp = delimitedLine.trim().split(fieldDelimterRegex);       
        logger.info("opLineTmp = " + Arrays.asList(opLineTmp)); 
        if (status != null) {
            String lineMapperStr = status.getKey();
            if (lineMapperStr != null) {
                if (!lineMapperStr.equals("")) {
                    String[] temp = lineMapperStr.split(",");
                    //Check if all the fields as given by lineMapper are present in the input delimitedLine 
                    if (opLineTmp.length >= temp.length) {
                        ret = new LinkedHashMap<String, Object>();
                        for (String x : temp) {
                            logger.info("x -> " + x);
                            String fieldMapVal = x.trim().substring(x.lastIndexOf(":") + 1); 
                            String fieldMapKey = x.trim().substring(0, x.lastIndexOf(":")).trim();
                            Object fieldVal = handlePositionalField(fieldMapKey, opLineTmp);                                                    
                            ret.put(fieldMapVal, fieldVal);         
                    
                        }              
                    }           
                }
            }
        }
        logger.info("jsonObj = " + ret);       
        return ret;
    
    }
    
    public Object handlePositionalField(String fieldMapKey, String[] opLine) {
        int fieldPosInOpLine = -1; 
        String fieldVal = null;
        if (fieldMapKey != null && fieldMapKey.indexOf("-") != -1) { //A multi-columnar field 
            StringBuffer tmp = new StringBuffer();
            int startIdx = Integer.parseInt(fieldMapKey.substring(0, fieldMapKey.indexOf("-")));
            int endIdx = Integer.parseInt(fieldMapKey.substring(fieldMapKey.indexOf("-") + 1));
            for (int k = (startIdx - 1); k < endIdx; k++) {
                if (Utils.isElementExists(opLine, k)) {
                    tmp.append(opLine[k]);
                    tmp.append(" ");
                }
               
            }
            fieldVal = tmp.toString().trim();
           
            
        } else { //A normal field
            fieldPosInOpLine = Integer.parseInt(fieldMapKey); 
            if (Utils.isElementExists(opLine, fieldPosInOpLine - 1)) {
                fieldVal = opLine[fieldPosInOpLine - 1];
            }
        }
        return fieldVal;
    }

}
