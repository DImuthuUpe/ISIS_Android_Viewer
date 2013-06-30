package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class VersionRepresentation extends JsonRepresentation{
    
    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    private String specVersion;
    private String implVersion;
    private Map<String, String> optionalCapabilities;
    
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
    public String getSpecVersion() {
        return specVersion;
    }
    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }
    public String getImplVersion() {
        return implVersion;
    }
    public void setImplVersion(String implVersion) {
        this.implVersion = implVersion;
    }
    public Map<String, String> getOptionalCapabilities() {
        return optionalCapabilities;
    }
    public void setOptionalCapabilities(Map<String, String> optionalCapabilities) {
        this.optionalCapabilities = optionalCapabilities;
    }
    
    
}
