package com.dimuthuupeksha.viewer.android.applib.representation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* Author - Dimuthu Upeksha*/

public class Homepage extends JsonRepr {
    private Map<String, String> extensions;

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
