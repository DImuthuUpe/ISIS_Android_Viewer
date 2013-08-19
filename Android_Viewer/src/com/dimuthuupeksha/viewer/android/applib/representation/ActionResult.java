package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class ActionResult extends JsonRepr {
    private String resulttype;
    private ActionResultItem result;
    private Map<String, JsonNode> extensions;

    public String getResulttype() {
        return resulttype;
    }

    public void setResulttype(String resulttype) {
        this.resulttype = resulttype;
    }

    public ActionResultItem getResult() {
        return result;
    }

    public void setResult(ActionResultItem result) {
        this.result = result;
    }

    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }

}
