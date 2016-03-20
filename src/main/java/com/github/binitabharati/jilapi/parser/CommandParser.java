/**
 * 
 * @author binita.bharati@gmail.com
 * The CommandParser class.Varying types of command output needs varying types of parsers too.
 * Currently 3 types of CommandParsers are supported:
 * 1)TabularParser
 * 2)ChunkedParser
 * 3)NestedParser
 * Please see respective type for a ovreview of the same.
 * 
 *
 */

package com.github.binitabharati.jilapi.parser;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binitabharati.jilapi.parser.ds.ProcessingEntity;
import com.github.binitabharati.jilapi.util.Utils;

public abstract class CommandParser {
    
    public static final Logger logger = LoggerFactory.getLogger(CommandParser.class);

    protected  Map<ProcessingEntity, Boolean> headerMap;
    protected  Map<ProcessingEntity, Boolean> footerMap;
    protected  Map<ProcessingEntity, Boolean> sectionMap;
    protected  Map<ProcessingEntity, Boolean> ignoreMap;
    protected  String fieldDelimterRegex;
    protected  String entityDelimter;
    protected  boolean startFound;
    
    public abstract void init(String commandKey, Properties prop) throws Exception;
    
    /**
     * 
     * @param br
     * @return A Json representation of the command output parsing.
     * @throws Exception
     */
    public abstract String parseCommand(BufferedReader br) throws Exception;
    
    /**
     * 
     * @param br
     * @return A Object representation of the command output parsing.
     * @throws Exception
     */
    public abstract Object parseCommand2(BufferedReader br) throws Exception;
            
    protected boolean isStartFound(String line) {
        if (!startFound) {
            logger.info("Searching for headers");
            if (headerMap != null && headerMap.size() != 0) {
                //Implies headers present   
                ProcessingEntity procStatus = allDataProcessed(headerMap);
                logger.info("procStatus = " + procStatus);
                if (procStatus != null) {
                    String unprocessedHeader = procStatus.getKey();
                    if (unprocessedHeader != null) { //unprocessed header present                        
                        String temp1 = unprocessedHeader.trim().replaceAll(",", "").replaceAll("\\s+", "").toLowerCase();                     
                        String temp2 = line.trim().replaceAll(" +", "").toLowerCase();
                        if (temp1.equals(temp2)) {                               
                            startFound = true;                          
                            headerMap.put(procStatus, true);  //processed
                                                   
                        } 
                    } else { //no header at all | all headers exhausted.
                        if (headerMap != null && headerMap.size() == 0) { //no header at all
                            startFound = true;
                            
                        }
                        
                    }
                }              
                             
            } else { //no headers
                startFound = true;
            }
           
        } 
        return startFound;
    }
    
    //This can not be called till header is found.
    protected boolean isEndFound(String line) {
        logger.info("isEndFound : footerStatusMap = " + footerMap + "line = " + line);
        boolean stopProcessing = false;
        if (footerMap != null && footerMap.size() != 0) {
            ProcessingEntity procStatus = allDataProcessed(footerMap);
            if (procStatus != null) {
                String unprocessedFooter = procStatus.getKey();  
                logger.info("unproc footer = " + unprocessedFooter + ", line = " + line);
                if (unprocessedFooter != null) {
                    if (!unprocessedFooter.equals("")) {
                        if (Arrays.asList(Utils.CMND_TRICKY_FOOTERS).contains(unprocessedFooter)) {
                            unprocessedFooter = Utils.CMND_FOOTER_MAP.get(unprocessedFooter);
                            if (line.trim().equals(unprocessedFooter)) {
                                logger.info("line = " + line + " MATCHES " + unprocessedFooter);
                                stopProcessing = true;
                                footerMap.put(procStatus, true);    
                            }
                        } else {
                            if (line.trim().startsWith(unprocessedFooter)) {
                                logger.info("line = " + line + " MATCHES " + unprocessedFooter);
                                stopProcessing = true;
                                footerMap.put(procStatus, true);    
                            }
                                                           
                        }                      
                    }                       
                }
            }          
        }
        //this means no footer present at all.   
        return stopProcessing;
    }
    
    protected boolean isLineIgnored(String line) {
        ProcessingEntity status = allDataProcessed(ignoreMap);
        if (status != null) {
            String ignoredString = status.getKey();           
            if (ignoredString != null) {
                if (!ignoredString.equals("")) {
                    if (line.trim().startsWith(ignoredString)) {
                        ignoreMap.put(status, true);
                        return true;                            
                    }                           
                }                   
            }
        }      
        return false;
    }
    
    protected Map<ProcessingEntity, Boolean> constructUnprocessedStatusMap(List<String> input) {
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
    
    protected ProcessingEntity allDataProcessed(Map<ProcessingEntity, Boolean> statusMap) {
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
  
}
