package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class CollectionRepresentation {
    private String id;

    private List<LinkRepresentation> value;

    private String disabledReason;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<LinkRepresentation> getValue() {
        return value;
    }
    public void setValue(List<LinkRepresentation> value) {
        this.value = value;
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
