package com.dimuthuupeksha.viewer.android.applib.test;

import com.dimuthuupeksha.viewer.android.applib.HttpHelper;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;

import junit.framework.TestCase;

public class RORequestOK extends TestCase {

    public RORequestOK(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testAsUriStr() throws Exception{
        RORequest request= RORequest.To(HttpHelper.HOST);
        assertEquals(HttpHelper.HOST, request.asUriStr());
        
        String args[] ={"Student","001","showAll"};
        request= RORequest.To(HttpHelper.HOST,Resource.DomainObjectAction,args);
        assertEquals(HttpHelper.HOST+"/objects/Student/001/actions/showAll", request.asUriStr());
        
        String params[]= {}; 
        request = RORequest.To(HttpHelper.HOST, Resource.HomePage, params);
        assertEquals(HttpHelper.HOST+"/", request.asUriStr());
    }
    

}
