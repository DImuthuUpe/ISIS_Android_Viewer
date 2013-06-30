package com.dimuthuupeksha.viewer.android.applib.test.representation.version;

import com.dimuthuupeksha.viewer.android.applib.HttpHelper;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.VersionRepresentation;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }
    public void testVersionRepresentation() throws Exception{
        String params[]= {}; 
        RORequest request = RORequest.To(HttpHelper.HOST, Resource.Version, params);
        ROClient client = new ROClient(HttpHelper.HOST);
        VersionRepresentation versionRepresentation = client.executeT(VersionRepresentation.class,"GET",request, null);
        assertNotNull(versionRepresentation);
        assertEquals(versionRepresentation.getLinks().get(0).getHref(), HttpHelper.HOST+"/version");
        assertEquals(versionRepresentation.getLinks().get(1).getHref(), HttpHelper.HOST+"/");
        
    }
}
