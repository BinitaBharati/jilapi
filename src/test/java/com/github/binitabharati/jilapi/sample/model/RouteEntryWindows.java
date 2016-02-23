/**
 * 
 * @author binita.bharati@gmail.com
 * A sample model class, used for converting json string to Java object.
 *
 *
 */

package com.github.binitabharati.jilapi.sample.model;

import java.util.List;

public class RouteEntryWindows {
    
    List<IpV4Route> ipv4Route;
    List<IPV6Route> ipv6Route;
    
    public RouteEntryWindows(List<IpV4Route> ipv4Route, List<IPV6Route> ipv6Route) {
        super();
        this.ipv4Route = ipv4Route;
        this.ipv6Route = ipv6Route;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof RouteEntryWindows) {
            RouteEntryWindows temp = (RouteEntryWindows)obj;
            
            if(temp.ipv4Route.containsAll(this.ipv4Route) && this.ipv4Route.containsAll(temp.ipv4Route)
                    && temp.ipv6Route.containsAll(this.ipv6Route) && this.ipv6Route.containsAll(temp.ipv6Route)) {
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

class IpV4Route {
    
    private String destinationNw;
    private String netMask;
    private String gateway;
    private String port;
    private String metric;
    
    IpV4Route(String destinationNw, String netMask, String gateway, String port, String metric) {
        super();
        this.destinationNw = destinationNw;
        this.netMask = netMask;
        this.gateway = gateway;
        this.port = port;
        this.metric = metric;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof IpV4Route) {
            IpV4Route temp = (IpV4Route)obj;
            if (this.destinationNw.equals(temp.destinationNw) && this.netMask.equals(temp.netMask)
                    && this.gateway.equals(temp.gateway) && this.port.equals(temp.port) 
                     && this.metric.equals(temp.metric)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return destinationNw.hashCode() + gateway.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "IpV4Route : destinationNw = " + destinationNw + ", netMask = " + netMask
                + ", gateway = " + gateway + ", port = " + port + ", metric = " + metric;
    }
    
}

class IPV6Route {
    private String field1;
    private String metric;
    private String destination;
    private String gateway;
    
    IPV6Route(String field1, String metric, String destination, String gateway) {
        super();      
        this.field1 = field1;
        this.metric = metric;
        this.destination = destination;
        this.gateway = gateway;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof IPV6Route) {
            IPV6Route temp = (IPV6Route)obj;
            System.out.println(temp);
            System.out.println(this);
            if (temp.field1.equals(this.field1) && temp.metric.equals(this.metric)
                    && temp.destination.equals(this.destination) && temp.gateway.equals(this.gateway)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return destination.hashCode() + gateway.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "field1 = " + field1 + ", metric = " + metric + ", destination = " + destination+ ", gateway = " + gateway;
    }
    
    
}