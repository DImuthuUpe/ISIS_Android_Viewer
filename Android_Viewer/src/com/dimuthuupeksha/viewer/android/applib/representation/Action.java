package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class Action extends JsonRepr{
    private String id;
    private List<Map<String, JsonNode>> parameters;

    private String memberType;

    private Map<String, String> extensions;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
    
    
    
    public List<Map<String, JsonNode>> getParameters() {
        return parameters;
    }
    public void setParameters(List<Map<String, JsonNode>> parameters) {
        this.parameters = parameters;
    }
    public String getMemberType() {
        return memberType;
    }
    public void setMemberType(String memberType) {
        this.memberType = memberType;
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
