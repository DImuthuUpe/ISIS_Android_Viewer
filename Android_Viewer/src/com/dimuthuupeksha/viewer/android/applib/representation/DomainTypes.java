package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

/* Author - Dimuthu Upeksha*/

public class DomainTypes extends JsonRepr {
    private List<Link> values;
    private Map<String, String> extensions;

    public List<Link> getValues() {
        return values;
    }

    public void setValues(List<Link> values) {
        this.values = values;
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
