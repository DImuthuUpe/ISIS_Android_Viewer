package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ListRepr extends JsonRepr{
    private List<Link> value;
    
    
    private Map<String, String> extensions;
    public List<Link> getValue() {
        return value;
    }
    public void setValue(List<Link> value) {
        this.value = value;
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