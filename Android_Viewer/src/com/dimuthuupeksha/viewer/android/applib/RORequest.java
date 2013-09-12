package com.dimuthuupeksha.viewer.android.applib;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimuthuupeksha.viewer.android.applib.constants.Resource;

/* Author - Dimuthu Upeksha*/

public class RORequest {
    private String _path;

    private Resource _resource;
    private String[] _pathElements;
    private Map<String, String> _pathElementMap;
    public String _baseUri;

    public static RORequest To(String path) {
        RORequest roRequest = new RORequest();
        roRequest._path = path.trim().replace(" ", "%20");
        return roRequest;
    }

    public static RORequest To(String baseUri, Resource resource, String[] pathElements) {
        RORequest roRequest = new RORequest();
        roRequest._baseUri = baseUri;
        roRequest._resource = resource;
        roRequest._pathElements = pathElements;
        return roRequest;
    }

    public static RORequest To(String baseUri, Resource resource, Map<String, String> pathElementMap) {
        RORequest roRequest = new RORequest();
        roRequest._baseUri = baseUri;
        roRequest._resource = resource;
        roRequest._pathElementMap = pathElementMap;
        return roRequest;
    }

    public String asUriStr() throws Exception {
        if (_path != null) {
            return _path;
        } else if (_pathElements != null) {
            UrlTemplate template = _resource.get_uriTemplateStr() != null ? new UrlTemplate(_resource.get_uriTemplateStr()) : new UrlTemplate(_resource.get_uriStr());
            List<String> allParams = template.getPrameterNames();
            if (allParams.size() != _pathElements.length) {
                throw new Exception("Mismatch between parameters in uriTemplate and supplied elements");
            }
            for (int i = 0; i < allParams.size(); i++) {
                String parm = allParams.get(i);
                template.putParameter(parm, _pathElements[i]);
            }
            if (_baseUri.endsWith("/")) {
                return _baseUri.substring(0, _baseUri.length() - 1) + template.getResultUrl();
            } else {
                return _baseUri + template.getResultUrl();
            }
        } else {
            UrlTemplate template = _resource.get_uriTemplateStr() != null ? new UrlTemplate(_resource.get_uriTemplateStr()) : new UrlTemplate(_resource.get_uriStr());
            List<String> allParams = template.getPrameterNames();
            Set<String> keySet = _pathElementMap.keySet();
            if (keySet.size() == allParams.size()) {
                for (String param : allParams) {
                    keySet.remove(param);
                }
                if (keySet.isEmpty()) {
                    for (String param : allParams) {
                        template.putParameter(param, _pathElementMap.get(param));
                    }
                    if (_baseUri.endsWith("/")) {
                        return _baseUri.substring(0, _baseUri.length() - 1) + template.getResultUrl();
                    } else {
                        return _baseUri + template.getResultUrl();
                    }
                } else {
                    throw new Exception("Mismatch between parameters in uriTemplate and supplied map");
                }

            } else {
                throw new Exception("Mismatch between parameters in uriTemplate and supplied map");
            }
        }
    }
}
