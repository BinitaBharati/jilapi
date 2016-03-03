package com.github.binitabharati.jilapi.sample.model;

public class InterfaceInfo {
    
    private String linkEncapsulation;
    private String inetAddr;
    private String bcastAddress;
    private String mask;
    private String scope;
    private String rxPackets;
    private String errors;
    private String dropped;
    private String overruns;
    private String frame;
    
    public InterfaceInfo(String linkEncapsulation, String inetAddr, String bcastAddress, String mask, String scope,
            String rxPackets, String errors, String dropped, String overruns, String frame) {
        super();
        this.linkEncapsulation = linkEncapsulation;
        this.inetAddr = inetAddr;
        this.bcastAddress = bcastAddress;
        this.mask = mask;
        this.scope = scope;
        this.rxPackets = rxPackets;
        this.errors = errors;
        this.dropped = dropped;
        this.overruns = overruns;
        this.frame = frame;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof InterfaceInfo) {
            InterfaceInfo temp = (InterfaceInfo)obj;
            if (temp.linkEncapsulation.equals(this.linkEncapsulation) && temp.inetAddr.equals(this.inetAddr)
                    && temp.bcastAddress.equals(this.bcastAddress) && temp.mask.equals(this.mask)
                    && temp.scope.equals(this.scope) && temp.rxPackets.equals(this.rxPackets)
                    && temp.errors.equals(this.errors) && temp.dropped.equals(this.dropped)
                    && temp.overruns.equals(this.overruns) && temp.frame.equals(this.frame)) {
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
