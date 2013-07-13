package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

public class ObjectMember extends JsonRepr{
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
    private String value;

    /// <summary>
    /// Populated only for collections.
    /// </summary>
    /// <see cref="MemberType"/>
    private int size;

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
    public String getDisabledReason() {
        return disabledReason;
    }
    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
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
