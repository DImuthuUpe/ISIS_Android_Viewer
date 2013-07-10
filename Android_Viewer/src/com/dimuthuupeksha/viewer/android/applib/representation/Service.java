package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class Service extends JsonRepr{
    
    private List<Link> links;
    private Map<String, String> extensions;
    private String title;
    private String serviceId;
    private String oid;    
    private List<ServiceMember> members;
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    public Map<String, String> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, String> extensions) {
        this.extensions = extensions;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getServiceId() {
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    
    public List<ServiceMember> getMembers() {
        return members;
    }
    public void setMembers(List<ServiceMember> members) {
        this.members = members;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    
    
    
    
}
