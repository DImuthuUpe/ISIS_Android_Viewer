package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class Object extends JsonRepr{
    /// <summary>
    /// Applies only when the representation is of a domain service
    /// </summary>
    private String serviceId;

    /// <summary>
    /// Applies only when the representation is of a domain object
    /// </summary>
    private String domainType;

    /// <summary>
    /// Applies only when the representation is of a domain object
    /// </summary>
    private String instanceId;

    private String title;

    private Map<String, ObjectMember> members;

    private List<Link> links;
    private Map<String, String> extensions;
    public String getServiceId() {
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getDomainType() {
        return domainType;
    }
    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }
    public String getInstanceId() {
        return instanceId;
    }
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Map<String, ObjectMember> getMembers() {
        return members;
    }
    public void setMembers(Map<String, ObjectMember> members) {
        this.members = members;
    }
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
    
    
    

}
