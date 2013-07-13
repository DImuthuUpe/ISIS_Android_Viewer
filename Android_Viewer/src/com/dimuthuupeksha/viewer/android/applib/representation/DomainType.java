package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class DomainType extends JsonRepr{
    private String name;
    private String domainType;
    private String friendlyName;
    private String pluralName;
    private String description;
    private boolean isService;

    private Map<String, String> typeActions;
    private Map<String, String> members;

    private Map<String, String> extensions;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDomainType() {
        return domainType;
    }
    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }
    public String getFriendlyName() {
        return friendlyName;
    }
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    public String getPluralName() {
        return pluralName;
    }
    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isService() {
        return isService;
    }
    public void setService(boolean isService) {
        this.isService = isService;
    }
    public Map<String, String> getTypeActions() {
        return typeActions;
    }
    public void setTypeActions(Map<String, String> typeActions) {
        this.typeActions = typeActions;
    }
    public Map<String, String> getMembers() {
        return members;
    }
    public void setMembers(Map<String, String> members) {
        this.members = members;
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
