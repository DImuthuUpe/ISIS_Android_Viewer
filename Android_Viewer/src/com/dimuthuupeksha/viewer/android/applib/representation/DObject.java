package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class DObject extends JsonRepr{
    

    private String resulttype;
    private ObjectResult result;
    private String message="no error";
    private String stackTrace;
    private Map<String, JsonNode> extensions;
    
    
    
    
    public String getStackTrace() {
        return stackTrace;
    }
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getResulttype() {
        return resulttype;
    }
    public void setResulttype(String resulttype) {
        this.resulttype = resulttype;
    }
    public ObjectResult getResult() {
        return result;
    }
    public void setResult(ObjectResult result) {
        this.result = result;
    }
    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }
    
}
