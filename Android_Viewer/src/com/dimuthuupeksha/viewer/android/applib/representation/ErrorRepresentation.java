package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ErrorRepresentation extends JsonRepresentation{
    private String message;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;

    //Optional:
    private List<String> stackTrace;
    private ErrorRepresentation causedBy;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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
    public List<String> getStackTrace() {
        return stackTrace;
    }
    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
    public ErrorRepresentation getCausedBy() {
        return causedBy;
    }
    public void setCausedBy(ErrorRepresentation causedBy) {
        this.causedBy = causedBy;
    }
    
    
}
