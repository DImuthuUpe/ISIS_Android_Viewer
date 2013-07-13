package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class ListResult extends JsonRepr{
    private List<Link> value;
    private Map<String, JsonNode> extensions;
    public List<Link> getValue() {
        return value;
    }
    public void setValue(List<Link> value) {
        this.value = value;
    }
    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }    
}
