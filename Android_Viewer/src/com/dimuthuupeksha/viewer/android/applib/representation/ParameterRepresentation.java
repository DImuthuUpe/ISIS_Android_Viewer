package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ParameterRepresentation extends JsonRepresentation {
    private List<GenericRepresentation> choices;
    private GenericRepresentation Default;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public List<GenericRepresentation> getChoices() {
        return choices;
    }
    public void setChoices(List<GenericRepresentation> choices) {
        this.choices = choices;
    }
    public GenericRepresentation getDefault() {
        return Default;
    }
    public void setDefault(GenericRepresentation default1) {
        Default = default1;
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
