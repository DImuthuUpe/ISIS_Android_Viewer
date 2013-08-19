package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class Version extends JsonRepr {

    private Map<String, String> extensions;
    private String specVersion;
    private String implVersion;
    private Map<String, String> optionalCapabilities;

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
