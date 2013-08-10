package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class DomainTypeCollection extends JsonRepr{
    private String id;   
    private String memberType;
    private Map<String, JsonNode> extensions;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public Map<String, JsonNode> getExtensions() {
		return extensions;
	}
	public void setExtensions(Map<String, JsonNode> extensions) {
		this.extensions = extensions;
	}
    
    
    
    
}
