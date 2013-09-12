package com.dimuthuupeksha.viewer.android.applib.test.representation.homepage;


import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.Homepage;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
	String HOST = "http://192.168.56.1:8080/restful";
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }
    public void testHomepageRepresentation() throws Exception{
        String params[]= {}; 
        RORequest request = RORequest.To(HOST, Resource.HomePage, params);
        ROClient client = ROClient.getInstance();
        Homepage homepageRepresentation = client.executeT(Homepage.class,"GET",request, null);
        assertNotNull(homepageRepresentation);
        assertEquals(homepageRepresentation.getLinkByRel("self").getHref(),HOST+"/");
        assertEquals(homepageRepresentation.getLinkByRel("user").getHref(), HOST+"/user");
        assertEquals(homepageRepresentation.getLinkByRel("services").getHref(), HOST+"/services");
        assertEquals(homepageRepresentation.getLinkByRel("version").getHref(), HOST+"/version");
        assertEquals(homepageRepresentation.getLinkByRel("domain-types").getHref(), HOST+"/domain-types");
    }
}
