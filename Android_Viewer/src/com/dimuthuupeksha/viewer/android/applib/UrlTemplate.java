package com.dimuthuupeksha.viewer.android.applib;

import java.util.ArrayList;
import java.util.List;

public class UrlTemplate {
    private String urlTemplate;

    public UrlTemplate(String url) {
        urlTemplate = url;
    }

    public List<String> getPrameterNames() {
        List<String> paramNames = new ArrayList<String>();
        int start = 0;
        if (urlTemplate != null)
            for (int i = 0; i < urlTemplate.length(); i++) {
                if (urlTemplate.charAt(i) == '{') {
                    start = i;
                } else if (urlTemplate.charAt(i) == '}') {
                    paramNames.add(urlTemplate.substring(start + 1, i));
                }
            }
        return paramNames;
    }

    public void putParameter(String paramName, String value) {
        urlTemplate = urlTemplate.replace("{" + paramName + "}", value);
    }

    public String getResultUrl() {
        return urlTemplate;
    }
}
