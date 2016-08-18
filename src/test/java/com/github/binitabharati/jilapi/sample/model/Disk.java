package com.github.binitabharati.jilapi.sample.model;

public class Disk {
    
    /*"uid": "20000024:B60973CD:00000000:00000000:00000000:00000000:00000000:00000000:00000000:00000000",
    "disk": "6a.34",
    "rev": "NA03",
    "diskPort": "A",
    "model": "X291_S15K6420F15"
*/
    
    private String uid;
    private String disk;
    private String rev;
    private String diskPort;
    private String model;
    private String rpm;
    private String sn;
    private String shelf;
    private String bay;
    private String vendor;
    private String curOwner;
    private String homeOwner;
    private String resrvnOwner;
    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getDisk() {
        return disk;
    }
    public void setDisk(String disk) {
        this.disk = disk;
    }
    public String getRev() {
        return rev;
    }
    public void setRev(String rev) {
        this.rev = rev;
    }
    public String getDiskPort() {
        return diskPort;
    }
    public void setDiskPort(String diskPort) {
        this.diskPort = diskPort;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getRpm() {
        return rpm;
    }
    public void setRpm(String rpm) {
        this.rpm = rpm;
    }
    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public String getShelf() {
        return shelf;
    }
    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
    public String getBay() {
        return bay;
    }
    public void setBay(String bay) {
        this.bay = bay;
    }
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public String getCurOwner() {
        return curOwner;
    }
    public void setCurOwner(String curOwner) {
        this.curOwner = curOwner;
    }
    public String getHomeOwner() {
        return homeOwner;
    }
    public void setHomeOwner(String homeOwner) {
        this.homeOwner = homeOwner;
    }
    public String getResrvnOwner() {
        return resrvnOwner;
    }
    public void setResrvnOwner(String resrvnOwner) {
        this.resrvnOwner = resrvnOwner;
    }
        
}
