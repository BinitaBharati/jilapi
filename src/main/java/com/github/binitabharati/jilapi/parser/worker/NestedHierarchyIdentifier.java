/**
 * 
 * @author binita.bharati@gmail.com
 * NestedHierarchyIdentifier - Applicable only when command parsre type is Nested.
 * Defines a way to identify each element in a nested hierarchy.
 *
 *
 */
package com.github.binitabharati.jilapi.parser.worker;

import java.util.List;

import com.github.binitabharati.jilapi.parser.ds.NestedParserModel;

public interface NestedHierarchyIdentifier {
    
    public String lineMatchesAnyTopMostEntity(NestedParserModel npm, String line);
    
    public String lineMatchesAnyHierarchicalEntity(List<String> hierarchyList, String line);
    
    

}
