package com.dimuthuupeksha.viewer.android.applib.test;


import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import junit.framework.TestCase;

public class RORequestOK extends TestCase {
	String HOST = "http://192.168.56.1:8080/restful"; 
    public RORequestOK(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testAsUriStr() throws Exception{
        RORequest request= RORequest.To(HOST);
        assertEquals(HOST, request.asUriStr());
        
        String args[] ={"Student","001","showAll"};
        request= RORequest.To(HOST,Resource.DomainObjectAction,args);
        assertEquals(HOST+"/objects/Student/001/actions/showAll", request.asUriStr());
        
        String params[]= {}; 
        request = RORequest.To(HOST, Resource.HomePage, params);
        assertEquals(HOST+"/", request.asUriStr());
    }
    

}
