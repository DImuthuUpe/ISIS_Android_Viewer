package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.Map;

public class LinkRepresentation extends JsonRepresentation{
    private String Id;
    private String title;
    private String rel;
    private String href;
    private String method;
    private String type;

    public Map<String, GenericRepresentation> arguments;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, GenericRepresentation> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, GenericRepresentation> arguments) {
        this.arguments = arguments;
    }
    
    
}
