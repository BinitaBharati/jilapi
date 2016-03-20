/**
 * 
 * @author binita.bharati@gmail.com
 * A intermediate data structure used by Tabular/Chunked parser.
 *
 *
 */

package com.github.binitabharati.jilapi.parser.ds;

public class ProcessingEntity {
    private int startIndex;
    private String key;
    
    public ProcessingEntity(int startIndex, String key) {
        this.startIndex = startIndex;
        this.key = key;
    }
    
    public int getKeyIndex() {
        return startIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.startIndex = keyIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
     
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        ProcessingEntity tmp = (ProcessingEntity)obj;
        if (tmp.startIndex == this.startIndex && tmp.key.equals(this.key)) {
            return true;
        }
            
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return key.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "index = " + startIndex + ", key = " + key;
    }
}
