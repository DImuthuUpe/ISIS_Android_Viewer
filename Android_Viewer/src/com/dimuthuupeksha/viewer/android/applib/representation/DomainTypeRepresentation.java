package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class DomainTypeRepresentation {
    private String name;
    private String domainType;
    private String friendlyName;
    private String pluralName;
    private String description;
    private boolean isService;

    private Map<String, GenericRepresentation> typeActions;
    private Map<String, GenericRepresentation> members;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
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
    public Map<String, GenericRepresentation> getTypeActions() {
        return typeActions;
    }
    public void setTypeActions(Map<String, GenericRepresentation> typeActions) {
        this.typeActions = typeActions;
    }
    public Map<String, GenericRepresentation> getMembers() {
        return members;
    }
    public void setMembers(Map<String, GenericRepresentation> members) {
        this.members = members;
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
