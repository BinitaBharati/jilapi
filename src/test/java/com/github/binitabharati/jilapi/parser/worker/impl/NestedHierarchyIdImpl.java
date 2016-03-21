/**
 * 
 * @author binita.bharati@gmail.com
 * NestedHierarchyIdentifier - Applicable only when command parser type is Nested.
 * Defines a way to identify each element in a nested hierarchy.
 *
 *
 */

package com.github.binitabharati.jilapi.parser.worker.impl;

import java.util.List;

import com.github.binitabharati.jilapi.parser.ds.NestedParserModel;
import com.github.binitabharati.jilapi.parser.worker.NestedHierarchyIdentifier;

public class NestedHierarchyIdImpl implements NestedHierarchyIdentifier {

    public String lineMatchesAnyTopMostEntity(NestedParserModel npm, String line) {
        // TODO Auto-generated method stub
        return lineStartsWithElementInList(npm.getTopMostEntity(), line);
    }

    public String lineMatchesAnyHierarchicalEntity(List<String> hierarchyList, String line) {
        // TODO Auto-generated method stub
        return lineStartsWithElementInList(hierarchyList, line);
    }
    
    private String lineStartsWithElementInList(List<String> inputList, String line) {
        for (String each : inputList) {
            if (line != null) {
                if (line.trim().startsWith(each)) {
                    return each;
                }
                   
            }
            
        }
        return null;
    }
    
}
