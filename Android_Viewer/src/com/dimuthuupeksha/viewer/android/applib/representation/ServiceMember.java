package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;

public class ServiceMember extends JsonRepr{
    private List<Link> links;
    private String memberType;
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
   
    public String getMemberType() {
        return memberType;
    }
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
