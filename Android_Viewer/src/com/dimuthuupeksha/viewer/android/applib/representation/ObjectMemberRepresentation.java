package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ObjectMemberRepresentation extends GenericRepresentation{
    private String id;

    /// <summary>
    /// "property", "collection" or "action"
    /// </summary>
    private String memberType;

    private String disabledReason;

    /// <summary>
    /// Populated for property and collection, not for actions.
    /// </summary>
    /// <see cref="MemberType"/>
    private GenericRepresentation value;

    /// <summary>
    /// Populated only for collections.
    /// </summary>
    /// <see cref="MemberType"/>
    private int size;

    private List<LinkRepresentation> links;
    private Map<String, GenericRepresentation> extensions;
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
    public String getDisabledReason() {
        return disabledReason;
    }
    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }
    public GenericRepresentation getValue() {
        return value;
    }
    public void setValue(GenericRepresentation value) {
        this.value = value;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
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
