package com.dimuthuupeksha.viewer.android.applib.test.representation.homepage;

import com.dimuthuupeksha.viewer.android.applib.HttpHelper;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }
    public void testHomepageRepresentation() throws Exception{
        String params[]= {}; 
        RORequest request = RORequest.To(HttpHelper.HOST, Resource.HomePage, params);
        ROClient client = new ROClient(HttpHelper.HOST);
        HomepageRepresentation homepageRepresentation = client.executeT(HomepageRepresentation.class,"GET",request, null);
        assertNotNull(homepageRepresentation);
        assertEquals(homepageRepresentation.getLinks().get(0).getHref(), HttpHelper.HOST+"/");
        assertEquals(homepageRepresentation.getLinks().get(1).getHref(), HttpHelper.HOST+"/user");
        assertEquals(homepageRepresentation.getLinks().get(2).getHref(), HttpHelper.HOST+"/services");
        assertEquals(homepageRepresentation.getLinks().get(3).getHref(), HttpHelper.HOST+"/version");
        assertEquals(homepageRepresentation.getLinks().get(4).getHref(), HttpHelper.HOST+"/domain-types");
    }
}
