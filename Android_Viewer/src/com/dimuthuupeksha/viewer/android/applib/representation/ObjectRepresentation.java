package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ObjectRepresentation extends JsonRepresentation{
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

    private Map<String, ObjectMemberRepresentation> members;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
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
    public Map<String, ObjectMemberRepresentation> getMembers() {
        return members;
    }
    public void setMembers(Map<String, ObjectMemberRepresentation> members) {
        this.members = members;
    }
    public List<LinkRepresentation> getLinks() {
        return links;
    }
    public void setLinks(List<LinkRepresentation> links) {
        this.links = links;
    }
    public Map<String, GenericRepresentation> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, GenericRepresentation> extensions) {
        this.extensions = extensions;
    }
    
    

}
