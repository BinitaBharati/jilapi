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
    
    public static final String CMND_ENTITY_DELIMITER = ".entity.delimiter";
    public static final String CMND_ENTITY_DELIMITER_VAL_DEFAULT = "\n";
    public static final String CMND_RESULT_SECTIONS = ".result.sections";
    public static final String CMND_RESULT_HEADER = ".result.header";
    public static final String CMND_RESULT_FOOTER = ".result.footer";
    public static final String CMND_RESULT_IGNORE = ".result.ignore";
    public static final String CMND_RESULT_ENTITY_POSITIONAL_MAP = ".result.entity.field.positional.map";
    public static final String CMND_RESULT_ENTITY_PREFIX_MAP = ".result.entity.field.prefix.map";
    public static final String CMND_RESULT_ENTITY_MIX_MAP = ".result.entity.field.mix.map";
    public static final String CMND_FIELD_DELIMITER_KEY = ".result.entity.field.delimiter";
    public static final String CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX = " +"; // " +" -> one or more spaces.
    public static final String CMND_ENTITY_DELIMITER_EMPTY_LINE = "";

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
