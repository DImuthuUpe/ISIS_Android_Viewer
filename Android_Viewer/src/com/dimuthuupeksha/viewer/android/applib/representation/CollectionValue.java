package com.dimuthuupeksha.viewer.android.applib.representation;

//this represents the value part of a Collection
public class CollectionValue {

    private String rel;
    private String href;
    private String method;
    private String type;
    private String title;
    private DomainObject value;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DomainObject getValue() {
        return value;
    }

    public void setValue(DomainObject value) {
        this.value = value;
    }

}
