/**
 * 
 * @author binita.bharati@gmail.com
 * Jilapi Utility.
 *
 *
 */

package com.github.binitabharati.jilapi.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Utils {
    
    public static final String CMND_RESULT_SECTIONS = ".result.sections";
    public static final String CMND_RESULT_HEADER = ".result.sections.header";
    public static final String CMND_RESULT_FOOTER = ".result.sections.footer";
    public static final String CMND_RESULT_IGNORE = ".result.sections.ignore";
    public static final String CMND_RESULT_LINE_FIELD_MAP = ".result.sections.line.field.map";
    
    public static final String CMND_FIELD_DELIMITER_KEY = ".result.line.field.delimiter";
    public static final String CMND_FIELD_DELIMITER_VAL_DEFAULT = " +"; // " +" -> one or more spaces.

    public static String preetyPrintJson(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJson);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }
    
    public static boolean isElementExists(String[] data, int index) {
        try {
            String temp = data[index];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    public static boolean isListContainingAllBlanks(List<String> input) {
        if (input != null && input.size() != 0) {
            if (input.size() == 1 && input.get(0).equals("")) {
                return true;
            }            
        }
        return false;
    }
    
    

}
