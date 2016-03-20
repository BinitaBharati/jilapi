/**
 * 
 * @author binita.bharati@gmail.com
 * See com.github.binitabharati.jilapi.SampleTest:testSample6() test case.
 * 
 *
 *
 */
package com.github.binitabharati.jilapi.entity.parser.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.binitabharati.jilapi.entity.parser.EntityParser;

public class PlexParser implements EntityParser {

    public Map<String, Object> parse(String input) {
        // TODO Auto-generated method stub
        Map<String, Object> out = new LinkedHashMap<String, Object>();
        String tmp = input.trim();
        
        String plexName = tmp.substring(0, tmp.indexOf("(")).trim();
        out.put("plexName", plexName);
        
        int idx1 = tmp.indexOf("(");
        int idx2 = tmp.indexOf(")");
        String plexField1 = tmp.substring(idx1 + 1, idx2);
        out.put("plexField1", plexField1);
        
        return out;
    }

}
