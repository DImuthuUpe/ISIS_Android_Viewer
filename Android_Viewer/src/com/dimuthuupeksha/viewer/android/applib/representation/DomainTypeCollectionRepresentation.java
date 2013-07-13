package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class DomainTypeCollectionRepresentation extends JsonRepr{
    private String id;
    private String friendlyName;
    private String pluralForm;
    private String description;    
    private String memberOrder;
    private Map<String, String> extensions;
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
