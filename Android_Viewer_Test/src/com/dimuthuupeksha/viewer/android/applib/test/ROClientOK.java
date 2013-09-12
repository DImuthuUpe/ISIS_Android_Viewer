package com.dimuthuupeksha.viewer.android.applib.test;

import java.util.ArrayList;
import java.util.Map;


import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.Homepage;

import org.apache.http.HttpResponse;

import junit.framework.TestCase;

public class ROClientOK extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testExecuteStatusCode200() throws Exception{
        String params[]= {}; 
        RORequest request = RORequest.To("http://192.168.56.1:8080/restful", Resource.HomePage, params);
        ROClient client = ROClient.getInstance();
        HttpResponse response = client.execute("GET",request, null);
        assertEquals(200,response.getStatusLine().getStatusCode());
        
    }    
    
}
