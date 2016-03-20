/**
 * 
 * @author binita.bharati@gmail.com
 * NestedParserModel - defines the intermediate data structure that NestedParser uses.
 *
 *
 */

package com.github.binitabharati.jilapi.parser.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedParserModel {
     
    List<List<String>> consolidatedHierachies; //A list of all independent hierarchies. Each element in the list is a full listing of all possible hierarchies for each independent hierarchy.
    List<Map<String, String>> consolidatedChildToParentHierachy; //Each element in the list is a map containing mapping between a child entity and its immediate parent entity.
    List<String> topMostEntity; //Each element is the name of the top most entity in each independent hierarchy.s
    
    public NestedParserModel() {
        // TODO Auto-generated constructor stub
        consolidatedHierachies = new ArrayList<List<String>>();
        consolidatedChildToParentHierachy = new ArrayList<Map<String, String>>();
        topMostEntity = new ArrayList<String>();
                
    }

    public List<List<String>> getConsolidatedHierachies() {
        return consolidatedHierachies;
    }

    public void setConsolidatedHierachies(List<List<String>> consolidatedHierachies) {
        this.consolidatedHierachies = consolidatedHierachies;
    }

    public List<Map<String, String>> getConsolidatedChildToParentHierachy() {
        return consolidatedChildToParentHierachy;
    }

    public void setConsolidatedChildToParentHierachy(List<Map<String, String>> consolidatedChildToParentHierachy) {
        this.consolidatedChildToParentHierachy = consolidatedChildToParentHierachy;
    }
      
    public List<String> getTopMostEntity() {
        return topMostEntity;
    }

    public void setTopMostEntity(List<String> topMostEntity) {
        this.topMostEntity = topMostEntity;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "coconsolidatedHierachies -> " + consolidatedHierachies + ", consolidatedChildToParentHierachy -> " + consolidatedChildToParentHierachy;
    }

}
