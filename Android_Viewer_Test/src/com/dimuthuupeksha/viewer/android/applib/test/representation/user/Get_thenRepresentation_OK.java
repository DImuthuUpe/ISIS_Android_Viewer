package com.dimuthuupeksha.viewer.android.applib.test.representation.user;

import com.dimuthuupeksha.viewer.android.applib.HttpHelper;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.UserRepresentation;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
    public void testuserRepresentation() throws Exception{
        String params[]= {}; 
        
        ROClient client = ROClient.getInstance();
        RORequest request =  client.RORequestTo(Resource.User, params);
        UserRepresentation userRepresentation = client.executeT(UserRepresentation.class,"GET",request, null);
        assertNotNull(userRepresentation);
        assertEquals(userRepresentation.getLinks().get(0).getHref(), HttpHelper.HOST+"/user");
        assertEquals(userRepresentation.getLinks().get(1).getHref(), HttpHelper.HOST+"/");
    }
}
