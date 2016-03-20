package com.github.binitabharati.jilapi.parser.ds;

import java.util.List;

//Aggregate[:plex->[Plex:raidGrp->[RAID group:raidDisk->[RAID Disk]]]]
//[Aggregate->[Plex->[RAID group->[RAID Disk]]]]
//[a1->[b1->[b2->[b4,b5],b3],c1],d1->[e1,f1]]
public class NestedEntity {
    
    private String name;
    private List<NestedEntity> children;
    private String parentName;
    
    public NestedEntity() {
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NestedEntity> getChildren() {
        return children;
    }

    public void setChildren(List<NestedEntity> children) {
        this.children = children;
    }
    
    
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "{ name = "+ name + ", parent = " + parentName + ", children = " + children + " }";
    }

}
