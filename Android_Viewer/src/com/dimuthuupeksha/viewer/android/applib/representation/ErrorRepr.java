package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ErrorRepr extends JsonRepr{
    private String message;
    private Map<String, String> extensions;

    //Optional:
    private List<String> stackTrace;
    private ErrorRepr causedBy;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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
    public List<String> getStackTrace() {
        return stackTrace;
    }
    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
    public ErrorRepr getCausedBy() {
        return causedBy;
    }
    public void setCausedBy(ErrorRepr causedBy) {
        this.causedBy = causedBy;
    }
    
    
}
