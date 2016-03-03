/**
 * 
 * @author binita.bharati@gmail.com
 * 
 * PrefixFieldHandler - Deals with positional fields in the command line output.
 * Prefix fields are marked by stringified value as key in the fieldMap.
 *
 *
 */

package com.github.binitabharati.jilapi.util;

public class PrefixFieldHandler {
    
    private int lastPrefixMatchIdx;
    private String fieldDelimterRegex;
    
    public PrefixFieldHandler(String fieldDelimterRegex) {
        this.fieldDelimterRegex = fieldDelimterRegex;
        lastPrefixMatchIdx = -1;
    }
    
    public String handlePrefixMap(String fieldMapKey, String delimitedLine) {
        String fieldVal = null;
        int startIdx = -1;
        int endIdx = -1;
        if (fieldDelimterRegex.equals(Utils.CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX)) {
          
            startIdx = delimitedLine.indexOf(fieldMapKey, lastPrefixMatchIdx)  + fieldMapKey.length();
            endIdx = delimitedLine.indexOf(" ", startIdx);
        } else {
            startIdx = delimitedLine.indexOf(fieldMapKey, lastPrefixMatchIdx)  + fieldMapKey.length();
            endIdx =  delimitedLine.indexOf(fieldDelimterRegex, startIdx);
        }
        
        fieldVal = delimitedLine.substring(startIdx, endIdx).trim();
        lastPrefixMatchIdx = endIdx;   
        return fieldVal;
    }
}
