/**
 * 
 * @author binita.bharati@gmail.com
 * 
 * PositionalFieldHandler - Deals with positional fields in the command line output.
 * Positional fields are marked by numerical positions as key in the fieldMap.
 *
 *
 */

package com.github.binitabharati.jilapi.util;

public class PositionalFieldHandler {
    
    public String handlePositionalField(String fieldMapKey, String[] opLine) {
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
