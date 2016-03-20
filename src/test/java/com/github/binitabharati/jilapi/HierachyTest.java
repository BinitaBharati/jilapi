/**
 * 
 * @author binita.bharati@gmail.com
 * HierarchyTest to test construction of NestedParserModel.
 *
 *
 */
package com.github.binitabharati.jilapi;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.Test;

import com.github.binitabharati.jilapi.parser.ds.NestedParserModel;

public class HierachyTest {
    
    //private List<List<String>> hierarchyConsolidatedList;
    //private List<Map<String, String>> childToParentMapList;
    private NestedParserModel parserModel;

    @Test
    public void test() {
        
        parserModel = new NestedParserModel();
        String test = "%A2%->[%A4%->[%A8%->[%A13%,%A14%],%A9%],%A5%];%A3%->[%A6%,%A7%];%A10%->[%A11%->[%A12%]]"; 
        
        String[] test3 = test.split(";");
        for (String each : test3) {
            List<String> allHierarchies = new ArrayList<String>();
            Map<String, String> childToParentMap  = new LinkedHashMap<String, String>();
            String topMostEntity = each.trim().substring(1, each.indexOf("%", 1));
            constructHierarchy2(each, null, each, allHierarchies, childToParentMap);
            parserModel.getConsolidatedChildToParentHierachy().add(childToParentMap);
            parserModel.getConsolidatedHierachies().add(allHierarchies);
            parserModel.getTopMostEntity().add(topMostEntity);
            
        }
        
        System.out.println(parserModel);
    }
    
   
    private String matchBracket(String input) {
        Stack<String> bracketCollector = new Stack<String>();
        char[] inputChar = input.toCharArray();
        for (int i = 0; i < inputChar.length; i++) {
            if (inputChar[i] == '[') {
                bracketCollector.push("[");
            } else if (inputChar[i] == ']') {
                bracketCollector.pop();
            }
            
            if (bracketCollector.size() == 0) {
                //yay, found matching bracket!
                return input.substring(0, i + 1);
            }
        }
        return null;
    }
    
    //%A2%->[%A4%->[%A8%->[%A13%,%A14%],%A9%,%A10%], pushChar = ']'
    private int matchBracketBackwards(String input, char pushChar, char popChar) {
        int bracketStartMatchIdx = -1;
        Stack<String> bracketCollector = new Stack<String>();
        char[] inputChar = input.toCharArray();
        for (int i = inputChar.length - 1; i >= 0; i--) {
            if (inputChar[i] == pushChar) {
                bracketCollector.push(pushChar + "");
            } else if (inputChar[i] == popChar) {
                bracketCollector.pop();
            }
            
            if (bracketCollector.size() == 0) {
                //yay, found matching bracket!
                bracketStartMatchIdx = i;
                break;
            }
        }
        return bracketStartMatchIdx;       
    }
    
    private String getParent(String test) {
        String ret = null;
        int backwardBracketMatch = matchBracketBackwards(test, ']', '[');
        if (backwardBracketMatch != -1) {
            String temp = test.substring(0, backwardBracketMatch - 3); // - 3 to eliminate 3 chars ->[
            int lastIdx = temp.lastIndexOf("%");
            ret = temp.substring(lastIdx + 1);
        }
        return ret;
    }
    
    private void constructHierarchy2(String test, String currentParent, 
            String origInput, List<String> allHierarchies, Map<String, String> childToParentMap) {
        
        String orig = new String(test);     
        int currentStartIdx = test.indexOf("%");
        int currentEndIdx = test.indexOf("%", currentStartIdx + 1);
        if (currentStartIdx != -1 && currentEndIdx != -1) {
            String currentNode = test.substring(currentStartIdx + 1, currentEndIdx);
            childToParentMap.put(currentNode, currentParent);
            if (!allHierarchies.contains(currentNode)) {
                allHierarchies.add(currentNode);
            }
            
            //check if a arrow or a comma follows currentEndIdx
            //if comma, then your current hierarchy is complete, as it has no children.
            //if arrow, then follow the hierarchy till u reach a node with no children.
            String strFollowingCurrentNode = test.substring(currentEndIdx + 1);
            //3 possibilities now
            if (strFollowingCurrentNode.startsWith(",")) { //end of currentNode. currentNode has no children.
                
                //parse next node.
                constructHierarchy2(strFollowingCurrentNode, currentParent, origInput, allHierarchies, childToParentMap);
            } else if (strFollowingCurrentNode.startsWith("->")) { //currentNode has children
                //parse currentNode's children.
                String currentNodeChildrenStr = matchBracket(test.substring(currentEndIdx + 3)); 
                int currentNodeChildrenStrIdx = test.indexOf(currentNodeChildrenStr);
                constructHierarchy2(currentNodeChildrenStr, currentNode, origInput, allHierarchies, childToParentMap);
                
                String remainder = test.substring(currentNodeChildrenStrIdx + currentNodeChildrenStr.length());
                if (remainder != null && !remainder.equals("")) {
                    int x1  = origInput.indexOf(test) + test.length();
                    String tmp = origInput.substring(0, x1);
                    String remainderParent = getParent(tmp);
                    constructHierarchy2(remainder, remainderParent, origInput, allHierarchies, childToParentMap);
                }                                       
            } 
        }
               
    }
}
