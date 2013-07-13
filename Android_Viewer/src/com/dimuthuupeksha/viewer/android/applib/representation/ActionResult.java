package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ActionResult extends JsonRepr {
  /// <summary>
    /// "object", "list", "scalar" or "void"
    /// </summary>
    private String resultType;

    /// <summary>
    /// Per the result type, may be cast to either ObjectRepr, ListRepr or ScalarRepr.
    /// </summary>
    /// <see cref="ResultType"/>
    private String result;
    private Map<String, String> extensions;
    public String getResultType() {
        return resultType;
    }
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
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
