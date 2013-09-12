package com.dimuthuupeksha.viewer.android.applib.constants;

/* Author - Dimuthu Upeksha*/

public class Resource {
    public static Resource HomePage = new Resource("/");
    public static Resource User = new Resource("/user");
    public static Resource Services = new Resource("/services");
    public static Resource Version = new Resource("/version");
    public static Resource ObjectsOfType = new Resource("/objects/{domainType}");
    public static Resource DomainObject = new Resource("/objects/{domainType}/{instanceId}");
    public static Resource DomainService = new Resource("/services/{serviceId}");

    public static Resource Property = new Resource("/objects/{domainType}/{instanceId}/properties/{propertyId}");

    public static Resource Collection = new Resource("/objects/{domainType}/{instanceId}/collections/{collectionId}");

    public static Resource DomainServiceAction = new Resource("/services/{serviceId}/actions/{actionId}");

    public static Resource DomainObjectAction = new Resource("/objects/{domainType}/{instanceId}/actions/{actionId}");

    public static Resource DomainServiceActionInvoke = new Resource("/services/{serviceId}/actions/{actionId}/invoke");

    public static Resource DomainObjectActionInvoke = new Resource("/objects/{domainType}/{instanceId}/actions/{actionId}/invoke");

    public static Resource DomainTypes = new Resource("/domain-types");
    public static Resource DomainType = new Resource("/domain-types/{domainType}");

    public static Resource DomainTypeProperty = new Resource("/domain-types/{domainType}/properties/{propertyId}");

    public static Resource DomainTypeCollection = new Resource("/domain-types/{domainType}/collections/{collectionId}");

    public static Resource DomainTypeAction = new Resource("/domain-types/{domainType}/actions/{actionId}");

    public static Resource DomainTypeActionParam = new Resource("/domain-types/{domainType}/actions/{actionId}/params/{paramName}");

    public static Resource DomainTypeActionInvoke = new Resource("/domain-types/{domainType}/type-actions/{typeactionId}/invoke");

    private String _uriTemplateStr;
    private String _uriStr;

    private Resource(String uriTemplateStr) {
        _uriTemplateStr = uriTemplateStr.contains("{") ? uriTemplateStr : null;
        _uriStr = _uriTemplateStr != null ? null : uriTemplateStr;
    }

    public String get_uriTemplateStr() {
        return _uriTemplateStr;
    }

    public String get_uriStr() {
        return _uriStr;
    }

}
