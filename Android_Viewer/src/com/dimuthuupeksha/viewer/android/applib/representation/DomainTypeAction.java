package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import android.R.bool;

/* Author - Dimuthu Upeksha*/
//http://localhost:8080/restful/domain-types/TODO/actions/similarTo
public class DomainTypeAction extends JsonRepr {
    private String id;
    private String memberType;

    private List<Link> parameters;

    private Map<String, String> extensions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public List<Link> getParameters() {
        return parameters;
    }

    public void setParameters(List<Link> parameters) {
        this.parameters = parameters;
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
