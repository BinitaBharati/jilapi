/**
 * 
 * @author binita.bharati@gmail.com
 * Test class.
 *
 *
 */

package com.github.binitabharati.jilapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.github.binitabharati.jilapi.sample.model.NestedModel;
import com.github.binitabharati.jilapi.sample.model.EtcPasswd;
import com.github.binitabharati.jilapi.sample.model.InterfaceInfo;
import com.github.binitabharati.jilapi.sample.model.RouteEntry;
import com.github.binitabharati.jilapi.sample.model.RouteEntryWindows;
import com.github.binitabharati.jilapi.sample.model.Uname;
import com.github.binitabharati.jilapi.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;


public class SampleTest extends TestCase {
    
    private static Properties prop;
    
    private static Properties loadPropertiesFromCP() {
        prop = new Properties();
        InputStream is = SampleTest.class.getClassLoader().getResourceAsStream("jilapi.properties");
        try {
            prop.load(is);
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prop;
    }
    
   
    @Test
    public void testSample1() throws Exception {
        loadPropertiesFromCP();
        String cmndKey = "cmnd1";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream.       
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd1: actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + expectedJson);
        Type listType = new TypeToken<ArrayList<Uname>>() { }.getType();
        if (!assertEqualJsonArray(cmndKey, actualJson, expectedJson, listType)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }      
    }
    
    @Test
    public void testSample2() throws Exception {
        loadPropertiesFromCP();
        String cmndKey = "cmnd2";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream. 
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd2: actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + expectedJson);
        Type listType = new TypeToken<ArrayList<RouteEntry>>() { }.getType();
        if (!assertEqualJsonArray(cmndKey, actualJson, expectedJson, listType)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }      
    }
    
    @Test
    public void testSample3() throws Exception {
        loadPropertiesFromCP();  
        String cmndKey = "cmnd3";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream. 
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd3 actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + expectedJson);
        if (!assertEqualJson(cmndKey, actualJson, expectedJson, RouteEntryWindows.class)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }      
    }
    
    @Test
    public void testSample4() throws Exception {
        loadPropertiesFromCP();
        String cmndKey = "cmnd4";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream. 
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd4: actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + expectedJson);
        Type listType = new TypeToken<ArrayList<EtcPasswd>>() { }.getType();
        if (!assertEqualJsonArray(cmndKey, actualJson, expectedJson, listType)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }      
    }
    
    @Test
    public void testSample5() throws Exception {
        loadPropertiesFromCP();
        String cmndKey = "cmnd5";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream. 
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd5: actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + Utils.preetyPrintJson(expectedJson));
        Type listType = new TypeToken<ArrayList<InterfaceInfo>>() { }.getType();
        if (!assertEqualJsonArray(cmndKey, actualJson, expectedJson, listType)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }      
    }
    
    
    @Test
    public void testSample6() throws Exception {
        loadPropertiesFromCP();
        String cmndKey = "cmnd6";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cmndKey); //Read command output file into InputStream. 
        Jilapi jilapi = new Jilapi(prop, cmndKey);
        String actualJson = jilapi.parseCommand(is);     
        System.out.println("cmnd6: actualJson = " + Utils.preetyPrintJson(actualJson));
        String expectedJson = readFile(cmndKey + "Op.json");
        System.out.println("expectedJson = " + Utils.preetyPrintJson(expectedJson));
        if (!assertEqualJson(cmndKey, actualJson, expectedJson, NestedModel.class)) {
            throw new AssertionFailedError("Expected json = " + Utils.preetyPrintJson(expectedJson) + ", actualJson = " + Utils.preetyPrintJson(actualJson));
        }   
    }
    
    private <Output> boolean assertEqualJsonArray(String commandKey, String actualJson, 
            String expectedJson, Type arrayType) throws Exception {      
        Gson gson = new Gson();
        List<Output> actualList = gson.fromJson(actualJson, arrayType);
        List<Output> expectedList = gson.fromJson(expectedJson, arrayType);
        System.out.println("actualRouteList = " + actualList + ", expectedRouteList = " + expectedList);
        if (actualList != null && expectedList != null) {
            if (actualList.size() == expectedList.size()) {
                if (actualList.containsAll(expectedList) && expectedList.containsAll(actualList)) {
                    return true;
                }
            }
        }
       
        return false;
    }
    
    private <Output> boolean assertEqualJson(String commandKey, String actualJson, 
            String expectedJson, Class mapperKlass) throws Exception {
        Gson gson = new Gson();
        Output actualObj = (Output)gson.fromJson(actualJson, mapperKlass);
        Output expectedObj = (Output)gson.fromJson(expectedJson, mapperKlass);
        if (actualObj != null && expectedObj != null
                && actualObj.equals(expectedObj)) {
            return true;
        }
        return false;        
    }

    private String readFile(String fileName) throws Exception {
        InputStream is = SampleTest.class.getClassLoader().getResourceAsStream(fileName);     
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line.trim());
        }
        return stringBuilder.toString();
        
        
    }

}
