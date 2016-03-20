/**
 * 
 * @author binita.bharati@gmail.com
 * See com.github.binitabharati.jilapi.SampleTest:testSample5() test case.
 * 
 *
 *
 */

package com.github.binitabharati.jilapi.entity.parser.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.binitabharati.jilapi.entity.parser.EntityParser;

public class IfConfigParser implements EntityParser {
    
    private Map<String, String> prefixTojsonFieldMap;
    
    public IfConfigParser() {
        prefixTojsonFieldMap = new LinkedHashMap<String, String>();
        //encap::linkEncapsulation,addr::inetAddr,Bcast::bcastAddress,Mask::mask,Scope::scope,packets::rxPackets,errors::errors,dropped::dropped,overruns::overruns,frame::frame
        prefixTojsonFieldMap.put("encap:", "linkEncapsulation");
        prefixTojsonFieldMap.put("addr:", "inetAddr");
        prefixTojsonFieldMap.put("Bcast:", "bcastAddress");
        prefixTojsonFieldMap.put("Mask:", "mask");
        prefixTojsonFieldMap.put("Scope:", "scope");
        prefixTojsonFieldMap.put("packets:", "rxPackets");
        prefixTojsonFieldMap.put("errors:", "errors");
        
    }

    public Map<String, Object> parse(String input) {
        // TODO Auto-generated method stub
        Map<String, Object> out = new LinkedHashMap<String, Object>();
        String temp = input.trim();
        String temp2 = temp.substring(0, temp.indexOf(" "));
        out.put("port", temp2);
        
        int matchIdx1 = 0;
        int matchIdx2 = -1;
        Iterator<String> mapItr = prefixTojsonFieldMap.keySet().iterator();
        while (mapItr.hasNext()) {
            String outputPrefix = mapItr.next();
            String jsonField = prefixTojsonFieldMap.get(outputPrefix);
            matchIdx1 = temp.indexOf(outputPrefix, matchIdx1);
            if (matchIdx1 != -1) {
                matchIdx2 = temp.indexOf(" ", matchIdx1);             
                out.put(jsonField, temp.substring(matchIdx1 + outputPrefix.length(),  matchIdx2));
                matchIdx1 = matchIdx2;
            } else {
                out.put(jsonField, "N/A");
            }
            
        }
        
        return out;
    }

}
