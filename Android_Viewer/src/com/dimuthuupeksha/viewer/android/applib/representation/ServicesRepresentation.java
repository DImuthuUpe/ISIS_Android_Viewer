package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ServicesRepresentation extends JsonRepresentation{
    private List<LinkRepresentation> value;
    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public List<LinkRepresentation> getValue() {
        return value;
    }
    public void setValue(List<LinkRepresentation> value) {
        this.value = value;
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