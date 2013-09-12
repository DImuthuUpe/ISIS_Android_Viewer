package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;

/* Author - Dimuthu Upeksha*/

public class ServiceMember extends JsonRepr {
    private String memberType;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
