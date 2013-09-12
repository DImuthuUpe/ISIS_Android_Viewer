package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

/* Author - Dimuthu Upeksha*/

public class Service extends JsonRepr {

    private Map<String, String> extensions;
    private String title;
    private String serviceId;
    private String oid;
    private Map<String, ServiceMember> members;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Map<String, ServiceMember> getMembers() {
        return members;
    }

    public void setMembers(Map<String, ServiceMember> members) {
        this.members = members;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

}
