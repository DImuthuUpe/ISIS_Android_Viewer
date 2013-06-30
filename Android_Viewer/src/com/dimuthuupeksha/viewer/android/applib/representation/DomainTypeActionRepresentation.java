package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import android.R.bool;

public class DomainTypeActionRepresentation {
    private String id;
    private String friendlyName;
    private String pluralForm;
    private String description;

    private String memberOrder;

    private boolean hasParams;

    private Map<String,DomainTypeActionParamRepresentation> params;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFriendlyName() {
        return friendlyName;
    }
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    public String getPluralForm() {
        return pluralForm;
    }
    public void setPluralForm(String pluralForm) {
        this.pluralForm = pluralForm;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMemberOrder() {
        return memberOrder;
    }
    public void setMemberOrder(String memberOrder) {
        this.memberOrder = memberOrder;
    }
    public boolean isHasParams() {
        return hasParams;
    }
    public void setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
    }
    public Map<String, DomainTypeActionParamRepresentation> getParams() {
        return params;
    }
    public void setParams(Map<String, DomainTypeActionParamRepresentation> params) {
        this.params = params;
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
