package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ServiceRepresentation extends JsonRepresentation{
    
    private List<LinkRepresentation> links;
    private Map<String, String> extensions;
    private String title;
    private String serviceId;
    private Map<String, ObjectMemberRepresentation> members;
    public List<LinkRepresentation> getLinks() {
        return links;
    }
    public void setLinks(List<LinkRepresentation> links) {
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
    public Map<String, ObjectMemberRepresentation> getMembers() {
        return members;
    }
    public void setMembers(Map<String, ObjectMemberRepresentation> members) {
        this.members = members;
    }
    
    
}
