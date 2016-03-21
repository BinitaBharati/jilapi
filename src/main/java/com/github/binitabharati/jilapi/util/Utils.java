/**
 * 
 * @author binita.bharati@gmail.com
 * Jilapi Utility.
 *
 *
 */

package com.github.binitabharati.jilapi.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Utils {
    
    public static final String CMND_PARSER_TYPE = ".parser.type";
    public static final String CMND_PARSER_TABULAR = "tabular";
    public static final String CMND_PARSER_CHUNKED = "chunked";
    public static final String CMND_PARSER_NESTED = "nested";
    
    public static final String CMND_ENTITY_DELIMITER = ".entity.end";
    public static final String CMND_ENTITY_DELIMITER_VAL_DEFAULT = "\n";
    
    public static final String CMND_RESULT_SECTIONS = ".result.sections";
    public static final String CMND_RESULT_HEADER = ".result.header";
    public static final String CMND_RESULT_FOOTER = ".result.footer";
    public static final String CMND_RESULT_IGNORE = ".result.ignore";
    public static final String CMND_RESULT_ENTITY_POSITIONAL_MAP = ".result.entity.field.positional.map";
    public static final String CMND_RESULT_ENTITY_PREFIX_MAP = ".result.entity.field.prefix.map";
    public static final String CMND_RESULT_ENTITY_MIX_MAP = ".result.entity.field.mix.map";
    public static final String CMND_FIELD_DELIMITER_KEY = ".result.entity.field.delimiter";
    public static final String CMND_FIELD_DELIMITER_VAL_DEFAULT_REGEX = "\\s+";
    public static final String CMND_CHUNKED_ENTITY_PARSER = ".result.entity.field.parser";
    public static final String CMND_NESTED_HIERARCHY_ID_TYPE= ".nested.hierarchy.id";
    public static final String CMND_NESTED_HIERARCHY_PREFIX = ".nested.hierarchy";
    public static final String CMND_NESTED_ENTITY_PARSER = ".result.entity.field.parser";
    public static final String[] CMND_OOB_PARSER_KLASS = new String[]{"com.github.binitabharati.jilapi.parser.impl.TabularParser",
        "com.github.binitabharati.jilapi.parser.impl.ChunkedParser"};
    public static final String[] CMND_TRICKY_FOOTERS = new String[]{"EMPTY_LINE"};
    public static final Map<String, String> CMND_FOOTER_MAP = new LinkedHashMap<String, String>();
    public static final String CMND_PARSING_STOP = ".result.stop";
    
    static {
        
        CMND_FOOTER_MAP.put(CMND_TRICKY_FOOTERS[0], "");
    }

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
