/**
 * 
 * @author binita.bharati@gmail.com
 * Entry point to Jilapi.
 * 
 * Jilapi is a unstructured data parser.
 *
 *
 */

package com.github.binitabharati.jilapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binitabharati.jilapi.parser.CommandParser;
import com.github.binitabharati.jilapi.parser.impl.ChunkedParser;
import com.github.binitabharati.jilapi.parser.impl.NestedParser;
import com.github.binitabharati.jilapi.parser.impl.TabularParser;
import com.github.binitabharati.jilapi.util.Utils;

public class Jilapi {
    
    public static final Logger logger = LoggerFactory.getLogger(Jilapi.class);

    private  Properties prop;
    private  String commandKey;
    private CommandParser commandParser;
    
    public Jilapi(Properties prop, String commandKey) throws Exception {
        this.prop = prop;
        this.commandKey = commandKey;
        init(commandKey);
    }
    
    private void init(String commandKey) throws Exception {           
        String  tmpProp = null;
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_PARSER_TYPE);
        if (tmpProp == null || tmpProp.equals("")) {
            throw new Exception("Mandatory property " + commandKey + Utils.CMND_PARSER_TYPE + " not specified");
        }
        
        if (tmpProp.equals(Utils.CMND_PARSER_TABULAR)) { //most simple parser. User not expected to write any custom entity parser.
            //Initialize tabular parser properties.
            commandParser = new TabularParser();
            commandParser.init(commandKey, prop);
            
        } else if (tmpProp.equals(Utils.CMND_PARSER_CHUNKED)) {
            commandParser = new ChunkedParser();
            commandParser.init(commandKey, prop);
            
        } else if (tmpProp.equals(Utils.CMND_PARSER_NESTED)) {
            commandParser = new NestedParser();
            commandParser.init(commandKey, prop);
            
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
        
        return commandParser.parseCommand(br);
    }
}
