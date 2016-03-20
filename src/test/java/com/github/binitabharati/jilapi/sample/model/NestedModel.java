package com.github.binitabharati.jilapi.sample.model;

import java.util.List;

public class NestedModel {   
    List<Aggregate> Aggregate;

    public NestedModel(List<Aggregate> aggregate) {
        super();
        Aggregate = aggregate;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        NestedModel temp = (NestedModel)obj;
        if (temp instanceof NestedModel) {
            if (this.Aggregate.equals(temp.Aggregate)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 123;
    }
       
}

class Aggregate {
    private String aggrName;
    private String aggrField1;
    private String aggrField2;
    private List<Plex> Plex;
    
    public Aggregate(String aggrName, String aggrField1, String aggrField2,
            List<com.github.binitabharati.jilapi.sample.model.Plex> plex) {
        super();
        this.aggrName = aggrName;
        this.aggrField1 = aggrField1;
        this.aggrField2 = aggrField2;
        Plex = plex;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        Aggregate temp = (Aggregate)obj;
        if (this.aggrName.equals(temp.aggrName) && this.aggrField1.equals(temp.aggrField1)
                && this.aggrField2.equals(temp.aggrField2)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return aggrName.hashCode();
    }
    
    
}

class Plex {
    private String plexName;
    private String plexField1;
    private List<RAIDgroup> RAIDgroup;
    
    public Plex(String plexName, String plexField1,
            List<com.github.binitabharati.jilapi.sample.model.RAIDgroup> rAIDgroup) {
        super();
        this.plexName = plexName;
        this.plexField1 = plexField1;
        RAIDgroup = rAIDgroup;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        Plex temp = (Plex)obj;
        if (this.plexName.equals(temp.plexName) && this.plexField1.equals(temp.plexField1)
                && this.RAIDgroup.equals(temp.RAIDgroup)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return plexName.hashCode();
    }
    
    
}

class RAIDgroup {
    private String raidGrpName;
    private String raidGrpField1;
    private List<RAIDDisk> RAIDDisk;
    
    public RAIDgroup(String raidGrpName, String raidGrpField1,
            List<com.github.binitabharati.jilapi.sample.model.RAIDDisk> rAIDDisk) {
        super();
        this.raidGrpName = raidGrpName;
        this.raidGrpField1 = raidGrpField1;
        RAIDDisk = rAIDDisk;
    } 
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        //return super.equals(obj);
        RAIDgroup temp = (RAIDgroup)obj;
        if (this.raidGrpName.equals(temp.raidGrpName) && this.raidGrpField1.equals(temp.raidGrpField1)
                && this.RAIDDisk.equals(temp.RAIDDisk)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return raidGrpName.hashCode() ;
    }
    
    
}

class RAIDDisk {
    private String raidDisk;
    private String device;
    private String ha;
    private String shelf;
    private String bay;
    private String chan;
    private String pool;
    private String type;
    private String rpm;
    private String usedInMbPerBlocks;
    private String physicalInMbPerBlocks;
    
    public RAIDDisk(String raidDisk, String device, String ha, String shelf, String bay, String chan, String pool,
            String type, String rpm, String usedInMbPerBlocks, String physicalInMbPerBlocks) {
        super();
        this.raidDisk = raidDisk;
        this.device = device;
        this.ha = ha;
        this.shelf = shelf;
        this.bay = bay;
        this.chan = chan;
        this.pool = pool;
        this.type = type;
        this.rpm = rpm;
        this.usedInMbPerBlocks = usedInMbPerBlocks;
        this.physicalInMbPerBlocks = physicalInMbPerBlocks;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        RAIDDisk temp = (RAIDDisk)obj;
        if (this.raidDisk.equals(temp.raidDisk) && this.device.equals(temp.device) 
                && this.ha.equals(temp.ha) && this.shelf.equals(temp.shelf)
                && this.bay.equals(temp.bay) && this.chan.equals(temp.chan)
                && this.pool.equals(temp.pool) && this.type.equals(temp.type)
                && this.rpm.equals(temp.rpm) && this.usedInMbPerBlocks.equals(temp.usedInMbPerBlocks)
                && this.physicalInMbPerBlocks.equals(temp.physicalInMbPerBlocks)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return raidDisk.hashCode() + device.hashCode();
    }
       
}
