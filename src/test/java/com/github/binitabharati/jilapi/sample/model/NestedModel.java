package com.github.binitabharati.jilapi.sample.model;

import java.util.List;

public class NestedModel {   
    List<Aggregate> Aggregate;
    
    public List<Aggregate> getAggregate() {
        return Aggregate;
    }

    public void setAggregate(List<Aggregate> aggregate) {
        Aggregate = aggregate;
    }

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
        System.out.println("ALARM! - MISMATCH of NestedModel ");
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 123;
    }
    
    public class Aggregate {
        private String aggrName;
        private String aggrField1;
        private String aggrField2;
        private List<Plex> Plex;
        
        public Aggregate(String aggrName, String aggrField1, String aggrField2,
                List<Plex> plex) {
            super();
            this.aggrName = aggrName;
            this.aggrField1 = aggrField1;
            this.aggrField2 = aggrField2;
            Plex = plex;
        }
        
        
        
        public String getAggrName() {
            return aggrName;
        }



        public void setAggrName(String aggrName) {
            this.aggrName = aggrName;
        }



        public String getAggrField1() {
            return aggrField1;
        }



        public void setAggrField1(String aggrField1) {
            this.aggrField1 = aggrField1;
        }



        public String getAggrField2() {
            return aggrField2;
        }



        public void setAggrField2(String aggrField2) {
            this.aggrField2 = aggrField2;
        }



        public List<Plex> getPlex() {
            return Plex;
        }



        public void setPlex(List<Plex> plex) {
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
            System.out.println("ALARM! - MISMATCH of aggre fields --");
            System.out.println("aggrName = "+this.aggrName);
            return false;
        }
        
        @Override
        public int hashCode() {
            // TODO Auto-generated method stub
            return aggrName.hashCode();
        }
        

    public class Plex {
    private String plexName;
    private String plexField1;
    private List<RAIDgroup> RAIDgroup;
    
    public Plex(String plexName, String plexField1,
            List<RAIDgroup> rAIDgroup) {
        super();
        this.plexName = plexName;
        this.plexField1 = plexField1;
        RAIDgroup = rAIDgroup;
    }
    
    
    
    public String getPlexName() {
        return plexName;
    }



    public void setPlexName(String plexName) {
        this.plexName = plexName;
    }



    public String getPlexField1() {
        return plexField1;
    }



    public void setPlexField1(String plexField1) {
        this.plexField1 = plexField1;
    }



    public List<RAIDgroup> getRAIDgroup() {
        return RAIDgroup;
    }



    public void setRAIDgroup(List<RAIDgroup> rAIDgroup) {
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
    
    public class RAIDgroup {
        private String raidGrpName;
        private String raidGrpField1;
        private List<RAIDDisk> RAIDDisk;
        
        public RAIDgroup(String raidGrpName, String raidGrpField1,
                List<RAIDDisk> rAIDDisk) {
            super();
            this.raidGrpName = raidGrpName;
            this.raidGrpField1 = raidGrpField1;
            RAIDDisk = rAIDDisk;
        } 
        
        
        
        public String getRaidGrpName() {
            return raidGrpName;
        }



        public void setRaidGrpName(String raidGrpName) {
            this.raidGrpName = raidGrpName;
        }



        public String getRaidGrpField1() {
            return raidGrpField1;
        }



        public void setRaidGrpField1(String raidGrpField1) {
            this.raidGrpField1 = raidGrpField1;
        }



        public List<RAIDDisk> getRAIDDisk() {
            return RAIDDisk;
        }



        public void setRAIDDisk(List<RAIDDisk> rAIDDisk) {
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
        


public class RAIDDisk {
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
    
    
    
    public String getRaidDisk() {
        return raidDisk;
    }



    public void setRaidDisk(String raidDisk) {
        this.raidDisk = raidDisk;
    }



    public String getDevice() {
        return device;
    }



    public void setDevice(String device) {
        this.device = device;
    }



    public String getHa() {
        return ha;
    }



    public void setHa(String ha) {
        this.ha = ha;
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



    public String getChan() {
        return chan;
    }



    public void setChan(String chan) {
        this.chan = chan;
    }



    public String getPool() {
        return pool;
    }



    public void setPool(String pool) {
        this.pool = pool;
    }



    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }



    public String getRpm() {
        return rpm;
    }



    public void setRpm(String rpm) {
        this.rpm = rpm;
    }



    public String getUsedInMbPerBlocks() {
        return usedInMbPerBlocks;
    }



    public void setUsedInMbPerBlocks(String usedInMbPerBlocks) {
        this.usedInMbPerBlocks = usedInMbPerBlocks;
    }



    public String getPhysicalInMbPerBlocks() {
        return physicalInMbPerBlocks;
    }



    public void setPhysicalInMbPerBlocks(String physicalInMbPerBlocks) {
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
    
    
    }
        
        
    }
       
}
      
}
