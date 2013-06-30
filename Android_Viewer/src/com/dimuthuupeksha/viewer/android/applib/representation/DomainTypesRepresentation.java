package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class DomainTypesRepresentation extends JsonRepresentation{
    private List<LinkRepresentation> values;
    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public List<LinkRepresentation> getValues() {
        return values;
    }
    public void setValues(List<LinkRepresentation> values) {
        this.values = values;
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
