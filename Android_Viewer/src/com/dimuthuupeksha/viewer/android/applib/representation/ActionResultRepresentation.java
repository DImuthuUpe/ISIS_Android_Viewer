package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ActionResultRepresentation {
  /// <summary>
    /// "object", "list", "scalar" or "void"
    /// </summary>
    private String resultType;

    /// <summary>
    /// Per the result type, may be cast to either ObjectRepr, ListRepr or ScalarRepr.
    /// </summary>
    /// <see cref="ResultType"/>
    private GenericRepresentation result;
    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public String getResultType() {
        return resultType;
    }
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    public GenericRepresentation getResult() {
        return result;
    }
    public void setResult(GenericRepresentation result) {
        this.result = result;
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
}
