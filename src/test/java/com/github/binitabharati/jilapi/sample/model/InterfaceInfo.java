/**
 * 
 * @author binita.bharati@gmail.com
 * A sample model class, used for converting json string to Java object.
 * See com.github.binitabharati.jilapi.SampleTest:testSample5() test case.
 *
 */

package com.github.binitabharati.jilapi.sample.model;

public class InterfaceInfo {
    
    private String linkEncapsulation;
    private String inetAddr;
    private String bcastAddress;
    private String mask;
    private String scope;
    private String rxPackets;
    private String errors;
    
    public InterfaceInfo(String linkEncapsulation, String inetAddr, String bcastAddress, String mask, String scope,
            String rxPackets, String errors) {
        super();
        this.linkEncapsulation = linkEncapsulation;
        this.inetAddr = inetAddr;
        this.bcastAddress = bcastAddress;
        this.mask = mask;
        this.scope = scope;
        this.rxPackets = rxPackets;
        this.errors = errors;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof InterfaceInfo) {
            InterfaceInfo temp = (InterfaceInfo)obj;
            if (temp.linkEncapsulation.equals(this.linkEncapsulation) && temp.inetAddr.equals(this.inetAddr)
                    && temp.bcastAddress.equals(this.bcastAddress) && temp.mask.equals(this.mask)
                    && temp.scope.equals(this.scope) && temp.rxPackets.equals(this.rxPackets)
                    && temp.errors.equals(this.errors)) {
                return true;
            }
        }
       
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return inetAddr.hashCode() + linkEncapsulation.hashCode();
    }
    
    

}
