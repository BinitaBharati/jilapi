/**
 * 
 * @author binita.bharati@gmail.com
 * A sample model class, used for converting json string to Java object.
 *
 *
 */

package com.github.binitabharati.jilapi.sample.model;

public class RouteEntry {
    
    private String destinationNw;
    private String gateway;
    private String netMask;
    private String metric;
    private String port;
    
    public RouteEntry(String destinationNw, String gateway, String netmask, String metric, String port) {
        super();
        this.destinationNw = destinationNw;
        this.gateway = gateway;
        this.netMask = netmask;
        this.metric = metric;
        this.port = port;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof RouteEntry) {
            RouteEntry temp = (RouteEntry)obj;
            if (this.destinationNw.equals(temp.destinationNw)
                    && this.gateway.equals(temp.gateway)
                         && this.netMask.equals(temp.netMask)
                             && this.metric.equals(temp.metric)
                                 && this.port.equals(temp.port)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return destinationNw.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "destinationNw = " + destinationNw + ", gateway = " + gateway 
                + ", netmask = " + netMask + ", metric = " + metric + ", port = " + port;
    }

}
