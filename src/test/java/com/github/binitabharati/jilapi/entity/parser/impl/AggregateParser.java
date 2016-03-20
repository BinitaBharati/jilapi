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


public class AggregateParser implements EntityParser{

    public Map<String, Object> parse(String input) {
        // TODO Auto-generated method stub
        String tmp = input.trim();
        Map<String, Object> out = new LinkedHashMap<String, Object>();
        
        String aggrName = tmp.substring(0, tmp.indexOf("(")).trim();
        out.put("aggrName", aggrName);
        
        int idx1 = tmp.indexOf("(");
        int idx2 = tmp.indexOf(")");
        String aggrField1 = tmp.substring(idx1 + 1, idx2);
        out.put("aggrField1", aggrField1);
        
        int idx3 = tmp.indexOf("(", idx2);
        int idx4 = tmp.indexOf(")", idx3);
        String aggrField2 = tmp.substring(idx3 + 1, idx4);
        out.put("aggrField2", aggrField2);
        
        return out;
    }

}
