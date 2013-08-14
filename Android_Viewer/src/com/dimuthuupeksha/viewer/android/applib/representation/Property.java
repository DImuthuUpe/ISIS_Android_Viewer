package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class Property extends JsonRepr{
    private String id;
    private String memberType;
    private String value;
    private String format;
    
    private List<String> choices;
    private String disabledReason;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public String getDisabledReason() {
		return disabledReason;
	}

	public void setDisabledReason(String disabledReason) {
		this.disabledReason = disabledReason;
	}

	public Map<String, JsonNode> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<String, JsonNode> extensions) {
		this.extensions = extensions;
	}
    
    
    
    
}
