package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class ObjectResult extends JsonRepr{

    private Map<String, JsonNode> extensions;
    private String title;
    private String domainType;
    private String instanceId;
    private Map<String, ObjectMember> members;
    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public Map<String, ObjectMember> getMembers() {
        return members;
    }
    public void setMembers(Map<String, ObjectMember> members) {
        this.members = members;
    }
    
    
}
