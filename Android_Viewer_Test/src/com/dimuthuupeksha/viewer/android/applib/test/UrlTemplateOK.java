package com.dimuthuupeksha.viewer.android.applib.test;

import java.util.List;

import android.util.Log;

import com.dimuthuupeksha.viewer.android.applib.UrlTemplate;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;

import junit.framework.TestCase;

public class UrlTemplateOK extends TestCase {

    public UrlTemplateOK(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testRepresentation(){
        testGetAllParams();
        putParams();
    }
    
    public void testGetAllParams(){
        UrlTemplate template = new UrlTemplate(Resource.DomainObjectAction.get_uriTemplateStr());
        List<String> list = template.getPrameterNames();
        
        assertTrue(list.get(0).equals("domainType"));
        assertTrue(list.get(1).equals("instanceId"));
        assertTrue(list.get(2).equals("actionId"));
    }
    
    public void putParams(){
        UrlTemplate template = new UrlTemplate(Resource.DomainObjectAction.get_uriTemplateStr());
        template.putParameter("domainType", "Student");
        template.putParameter("instanceId", "001");
        template.putParameter("actionId", "showAll");
        String resUrl = template.getResultUrl();
        Log.v("detail", resUrl);
        assertEquals("/objects/Student/001/actions/showAll",resUrl);
    }

}
