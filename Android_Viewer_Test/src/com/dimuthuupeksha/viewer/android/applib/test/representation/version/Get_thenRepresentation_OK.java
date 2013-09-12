package com.dimuthuupeksha.viewer.android.applib.test.representation.version;


import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.Version;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
	String HOST = "http://192.168.56.1:8080/restful";
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }
    public void testVersionRepresentation() throws Exception{
        String params[]= {}; 
        RORequest request = RORequest.To(HOST, Resource.Version, params);
        ROClient client = ROClient.getInstance();
        Version versionRepresentation = client.executeT(Version.class,"GET",request, null);
        assertNotNull(versionRepresentation);
        assertEquals(versionRepresentation.getLinks().get(0).getHref(), HOST+"/version");
        assertEquals(versionRepresentation.getLinks().get(1).getHref(), HOST+"/");
        
    }
}
