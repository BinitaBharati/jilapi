/**
 * 
 * @author binita.bharati@gmail.com
 * Nested Parser handles nested data.This reads data line wise.
 * See com.github.binitabharati.jilapi.SampleTest:testSample6() test case.
 * 
 */

package com.github.binitabharati.jilapi.parser.impl;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import com.github.binitabharati.jilapi.entity.parser.EntityParser;
import com.github.binitabharati.jilapi.parser.CommandParser;
import com.github.binitabharati.jilapi.parser.ds.NestedParserModel;
import com.github.binitabharati.jilapi.parser.worker.NestedHierarchyIdentifier;
import com.github.binitabharati.jilapi.util.Utils;
import com.google.gson.Gson;

public class NestedParser extends CommandParser {
    
    //private String nestedHierarchyIdentifierType;
    private Map<String, String> entityParserMap;
    private BufferedReader br;
    private String commandKey;
    private Properties prop;
    private NestedParserModel parserModel;
    //private String stop;
    private NestedHierarchyIdentifier hierachyId;
    
    @Override
    public void init(String commandKey, Properties prop) throws Exception {
        // TODO Auto-generated method stub
        this.commandKey = commandKey;
        this.prop = prop;
        String  tmpProp = null;
        List<String> tmpList = null;
        
        tmpProp = prop.getProperty(commandKey + Utils.CMND_PARSING_STOP);
        if (tmpProp != null) {
            stop = tmpProp;
        }
       
        //nestedHierarchyIdentifierType = prop.getProperty(commandKey + Utils.CMND_NESTED_HIERARCHY_ID_TYPE);
        tmpProp = prop.getProperty(commandKey + Utils.CMND_NESTED_HIERARCHY_ID_TYPE);
        if (tmpProp != null) {
            Class<?> x = Class.forName(tmpProp);
            hierachyId = (NestedHierarchyIdentifier)x.newInstance();
        }
        tmpProp = prop.getProperty(commandKey + Utils.CMND_NESTED_HIERARCHY_PREFIX); //Multiple independent hierarchies are seperated by semi-colon in property
        
        parserModel = new NestedParserModel();
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                //construct hierarchy
                for (String each : tmpList) { //splitting by semi colon gives us access to each independent hierrachy.
                    List<String> allHierarchies = new ArrayList<String>();
                    Map<String, String> childToParentMap  = new LinkedHashMap<String, String>();
                    String topMostEntity = each.trim().substring(1, each.indexOf("%", 1));
                    constructHierarchy2(each, null, each, allHierarchies, childToParentMap);
                    parserModel.getConsolidatedChildToParentHierachy().add(childToParentMap);
                    parserModel.getConsolidatedHierachies().add(allHierarchies);
                    parserModel.getTopMostEntity().add(topMostEntity);                    
                }
            }
        }
        entityParserMap = new LinkedHashMap<String, String>();
        tmpProp = prop.getProperty(commandKey + Utils.CMND_NESTED_ENTITY_PARSER);
        if (tmpProp != null) {
            tmpList = Arrays.asList(tmpProp.split(";"));
            if (!Utils.isListContainingAllBlanks(tmpList)) {
                //construct parser map
                entityParserMap = new LinkedHashMap<String, String>();
                for (String eachParser : tmpList) {
                    String x = eachParser.replace("\\s", "");
                    String key = x.substring(0, x.indexOf("="));
                    String value = x.substring(x.indexOf("=") + 1);
                            
                    entityParserMap.put(key, value);
                }
            }
        }
              
    }

    @Override
    public String parseCommand(BufferedReader br) throws Exception {       
        Object ret = parseCommand2(br);
        Gson gson = new Gson();
        return gson.toJson(ret);    
    }
    
    @Override
    public Object parseCommand2(BufferedReader br) throws Exception {
        // TODO Auto-generated method stub
        this.br = br;
        String line = null;
       
        String currentTopMostEntity = null;
        String currentEntity = null;
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Object> retList = new ArrayList<Object>();
        
        List<String> currentHierList = null;
        Map<String, String> childToParentMap = null;
        Object hierarchyBlockData = null;
        
        boolean stopFound = false;
        
        while ((line = br.readLine()) != null) {
            if (stop != null && line.trim().matches(stop)) {
                stopFound = true;
                break;
            }
            if (!stopFound) {
                //String retTopMostEntity = lineStartsWithAnyTopMostEntity(line);  
                String retTopMostEntity = hierachyId.lineMatchesAnyTopMostEntity(parserModel, line);
                
                if (currentTopMostEntity != null) {
                    if (retTopMostEntity != null) { //implies same hierarchy another block started / another independent hierarchy started.
                        if (currentTopMostEntity.equals(retTopMostEntity)) {
                            //same hierarchy another block started
                            retList.add(hierarchyBlockData);
                            
                            currentTopMostEntity = retTopMostEntity;
                            currentEntity = retTopMostEntity;
                            currentHierList = getHierarchy(currentTopMostEntity);
                            childToParentMap = getChildToParentMap(currentTopMostEntity);                       
                            hierarchyBlockData = parseEntity(line, currentTopMostEntity);
                            
                        } else { //another independent hierarchy started
                            retMap.put(currentTopMostEntity, retList);
                            retList = new ArrayList<Object>();
                            
                            currentTopMostEntity = retTopMostEntity;
                            currentEntity = retTopMostEntity;
                            currentHierList = getHierarchy(currentTopMostEntity);
                            childToParentMap = getChildToParentMap(currentTopMostEntity);                       
                            hierarchyBlockData = parseEntity(line, currentTopMostEntity);
                        }
                        
                    } else { //current line not a top most entity.
                        //String retEntity2 = lineStartsWithElementInList(currentHierList, line);
                        String retEntity2 = hierachyId.lineMatchesAnyHierarchicalEntity(currentHierList, line);
                        if (retEntity2 != null) {
                            currentEntity = retEntity2;
                            Object entityData = parseEntity(line, currentEntity);
                            
                            //get currentEntity's parent
                            String currentEntityParent = childToParentMap.get(currentEntity);                       
                            Stack<String> currentEntityHierarchy = new Stack<String>();
                            //currentEntityHierarchy.push(currentEntity);
                            String tmp = currentEntityParent;
                            while (!tmp.equals(currentTopMostEntity)) {
                                currentEntityHierarchy.push(tmp);
                                tmp = childToParentMap.get(tmp);  
                            }
                            currentEntityHierarchy.push(currentTopMostEntity);                    
                            
                            String childFieldName = currentEntity.replaceAll("\\s", "");
                            
                            Object parentEntity = getParentForHierarchy(hierarchyBlockData, currentEntityHierarchy, currentTopMostEntity, currentEntityParent);
                            if (parentEntity instanceof Map<?, ?>) {
                                Map<String, Object> map1 = (Map<String, Object>)parentEntity;
                                if (!map1.containsKey(childFieldName)) {
                                    if (!(entityData instanceof List<?>)) {
                                        List<Object> list1 = new ArrayList<Object>();
                                        list1.add(entityData);
                                        map1.put(childFieldName, list1);
                                    } else {
                                        map1.put(childFieldName, entityData);
                                    }
                                    
                                } else {                               
                                    Object oldChildData = map1.get(childFieldName);
                                    if (oldChildData instanceof Map<?, ?>) {
                                        List<Object> consolidatedChild = new ArrayList<Object>();
                                        consolidatedChild.add(oldChildData);
                                        consolidatedChild.add(entityData);
                                        map1.put(childFieldName, consolidatedChild);
                                    } else if (oldChildData instanceof List<?>) {
                                        List<Object> oldChildList = (List<Object>)oldChildData;
                                        oldChildList.add(entityData);
                                        map1.put(childFieldName, oldChildList);
                                    }
                                                                 
                                }
                                
                            } else if (parentEntity instanceof List<?>) {
                                List<Object> list1 = (List<Object>)parentEntity;
                                //add the child to the last list element.
                                Object lastElement = list1.get(list1.size() - 1);
                                if (lastElement instanceof Map<?, ?>) {
                                    Map<String, Object> tmpMap = (Map<String, Object>)lastElement;
                                    if (!(entityData instanceof List<?>)) {
                                        List<Object> list2 = new ArrayList<Object>();
                                        list2.add(entityData);
                                        tmpMap.put(childFieldName, list2);
                                    } else {
                                        tmpMap.put(childFieldName, entityData);
                                    }
                                    
                                }
                                                               
                            }
                                                        
                        }
                        
                    }
                   
                } else { //currentTopMostEntity == null.
                    
                    if (retTopMostEntity != null) { //top most entity found, and it is the first entity ever found.
                        currentTopMostEntity = retTopMostEntity;
                        currentEntity = retTopMostEntity;
                        currentHierList = getHierarchy(currentTopMostEntity);
                        childToParentMap = getChildToParentMap(currentTopMostEntity);
                        hierarchyBlockData = parseEntity(line, currentTopMostEntity);
                                             
                    }
                }
            }
            
            
        }
        //end the last hierarchy blocks data
        if (hierarchyBlockData != null) {
            retList.add(hierarchyBlockData);
        }
        if (retMap != null && retMap.size() == 0) {
            retMap.put(currentTopMostEntity, retList);
        }
        
        return retMap;
    }
    
    private void handleTopMostParent(String currentTopMostEntity, List<String> currentHierList, 
            Map<String, String> childToParentMap, Object hierarchyBlockData, String line) throws Exception {
        currentHierList = getHierarchy(currentTopMostEntity);
        childToParentMap = getChildToParentMap(currentTopMostEntity);
        hierarchyBlockData = parseEntity(line, currentTopMostEntity);
    }
    
    
    
    private Object getParentForHierarchy(Object entityData, Stack<String> currentHierarchy,
            String topMostEntityname, String immediateParent) {
        if (topMostEntityname.equals(immediateParent)) {
            return entityData;
        }
        //while (!currentHierarchy.empty()) {
        while (!currentHierarchy.empty()) {
            String temp = currentHierarchy.pop();                       
            String temp2 = temp.replaceAll("\\s", "");
            if (!temp.equals(topMostEntityname)) {
                if (entityData instanceof Map<?, ?>) {
                    Map<?, ?> map1 = (Map<?, ?>)entityData;
                    if (map1.containsKey(temp2)) {
                        Object x  = map1.get(temp2);                        
                        if (x instanceof Map<?, ?>) {
                            if (temp.equals(immediateParent)) {
                                return x;
                            }
                            return getParentForHierarchy(x, currentHierarchy, topMostEntityname, immediateParent);
                            
                        } else if (x instanceof List<?>) {
                            List<Object> z =  (List<Object>)x;
                            Object lastElem = z.get(z.size() - 1);
                            //return last element in the list.
                            if (temp.equals(immediateParent)) {
                                return lastElem;
                            }
                            return getParentForHierarchy(lastElem, currentHierarchy, topMostEntityname, immediateParent);
                            
                        }
                          
                        
                    }
                    
                } else if (entityData instanceof List<?>) {
                    List<Object> list1 = (List<Object>)entityData;
                    //add the child to the last list element.
                    Object x = list1.get(list1.size() - 1);
                    return getParentForHierarchy(x, currentHierarchy, topMostEntityname, immediateParent);
                        
                }
            }
        }
            
            //} 
        
        return null;
    }
       
       
    private Object parseEntity(String line, String entity) throws Exception {
        Object entityData = null;
        String parserKlass = entityParserMap.get(entity);
        if (!Arrays.asList(Utils.CMND_OOB_PARSER_KLASS).contains(parserKlass)) {
            Class<?> cmndParserKlass = Class.forName(parserKlass);
            EntityParser entityParser = (EntityParser)cmndParserKlass.newInstance();
            entityData = entityParser.parse(line);
        } else {
            Class<?> cmndParserKlass = Class.forName(parserKlass);
            CommandParser cmndParser = (CommandParser)cmndParserKlass.newInstance();
            Properties prop1 = null;
            if (parserKlass.equals(Utils.CMND_OOB_PARSER_KLASS[0])) { //TabularParser 
                List<String> tabularParserProp = new ArrayList<String>();
                tabularParserProp.add(Utils.CMND_RESULT_SECTIONS);
                tabularParserProp.add(Utils.CMND_RESULT_HEADER);
                tabularParserProp.add(Utils.CMND_RESULT_FOOTER);
                tabularParserProp.add(Utils.CMND_RESULT_IGNORE);
                tabularParserProp.add(Utils.CMND_RESULT_ENTITY_POSITIONAL_MAP);                
                prop1 = populatePropertyForOOBCommandParser(prop, entity, tabularParserProp);
                
            }
                
            cmndParser.init(commandKey, prop1);
            entityData = cmndParser.parseCommand2(br); 
        }
        return entityData;
    }
    
    private Properties populatePropertyForOOBCommandParser(Properties origProp, String entity, 
            List<String> propKeys) {
        Properties prop = new Properties();
        for (String each : propKeys) {
            if (origProp.containsKey(commandKey + each)) {
                String tmp = origProp.getProperty(commandKey + each);
                int entityIdx = tmp.indexOf(entity);
                if (entityIdx != -1) {
                    int semiColonIdx = tmp.indexOf(";", entityIdx);
                    String propVal = null;
                    if (semiColonIdx != -1) {
                        propVal  = tmp.substring(entityIdx + entity.length() + 1, semiColonIdx);
                    } else {
                        propVal  = tmp.substring(entityIdx + entity.length() + 1);
                    }
                    
                    if (propVal != null) {
                        prop.put(commandKey + each, propVal);
                    }
                    
                }
                
            }
        }
        return prop;
    }
    
    private List<String> getHierarchy(String topMostEntity) {
        List<List<String>> allHierarchies = parserModel.getConsolidatedHierachies();
        for (List<String> each : allHierarchies) {
            if (each.get(0).equals(topMostEntity)) {
                return each;
                
            }
        }
        return null;
    }
    
    private Map<String, String> getChildToParentMap(String topMostEntity) {
        List<Map<String, String>> list1 = parserModel.getConsolidatedChildToParentHierachy();
        for (Map<String, String> eachMap : list1) {
            if (eachMap.containsKey(topMostEntity)) {
                return eachMap;
            }
            
        }
        return null;
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
            }  else if (strFollowingCurrentNode.startsWith("->")) { //currentNode has children
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
