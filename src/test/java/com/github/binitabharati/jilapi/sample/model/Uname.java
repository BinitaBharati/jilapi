/**
 * 
 * @author binita.bharati@gmail.com
 * A sample model class, used for converting json string to Java object.
 *
 *
 */

package com.github.binitabharati.jilapi.sample.model;

public class Uname {
    
    private String kernelName;
    private String nodeName;
    private String kernelVersion;
    private String buildTime;
    private String processorType;
    private String hwPlatform;
    private String processorArch;
    private String osName;
    
    public Uname(String kernelName, String nodeName, String kernelVersion, String buildTime, String processorType,
            String hwPlatform, String processorArch, String osName) {
        super();
        this.kernelName = kernelName;
        this.nodeName = nodeName;
        this.kernelVersion = kernelVersion;
        this.buildTime = buildTime;
        this.processorType = processorType;
        this.hwPlatform = hwPlatform;
        this.processorArch = processorArch;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof Uname) {
            Uname temp = (Uname)obj;
            if (temp.buildTime.equals(this.buildTime) && temp.hwPlatform.equals(this.hwPlatform)
                    && temp.nodeName.equals(this.nodeName) && temp.processorArch.equals(this.processorArch)
                    && temp.processorType.endsWith(this.processorType) && temp.kernelVersion.equals(this.kernelVersion)
                    && temp.kernelName.equals(this.kernelName) && temp.osName.equals(this.osName)) {
                return true;
            }
            
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return nodeName.hashCode();
    }

}
