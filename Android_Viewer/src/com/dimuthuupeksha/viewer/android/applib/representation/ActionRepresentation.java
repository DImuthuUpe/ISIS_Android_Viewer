package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ActionRepresentation {
    private String id;
    private Map<String, ParameterRepresentation> parameters;

    private String disabledReason;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Map<String, ParameterRepresentation> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, ParameterRepresentation> parameters) {
        this.parameters = parameters;
    }
    public String getDisabledReason() {
        return disabledReason;
    }
    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
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
