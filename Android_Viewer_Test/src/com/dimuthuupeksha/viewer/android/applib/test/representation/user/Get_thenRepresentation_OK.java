package com.dimuthuupeksha.viewer.android.applib.test.representation.user;


import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.User;

import junit.framework.TestCase;

public class Get_thenRepresentation_OK extends TestCase {
	String HOST = "http://192.168.56.1:8080/restful";
    public void testuserRepresentation() throws Exception{
        String params[]= {}; 
        
        ROClient client = ROClient.getInstance();
        RORequest request =  RORequest.To(HOST,Resource.User, params);
        User userRepresentation = client.executeT(User.class,"GET",request, null);
        assertNotNull(userRepresentation);
        assertEquals(userRepresentation.getLinkByRel("self").getHref(), HOST+"/user");
        assertEquals(userRepresentation.getLinkByRel("up").getHref(), HOST+"/");
    }
}
