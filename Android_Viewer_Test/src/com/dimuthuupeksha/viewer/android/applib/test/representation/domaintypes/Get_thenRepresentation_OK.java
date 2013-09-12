package com.dimuthuupeksha.viewer.android.applib.test.representation.domaintypes;


import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypes;
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
        ROClient client = ROClient.getInstance();
        RORequest request = client.RORequestTo(HOST+"/");
        DomainTypes dtypesRepresentation = client.executeT(DomainTypes.class,"GET",request, null);
        assertNotNull(dtypesRepresentation);
        System.out.println("saff");
        assertEquals(dtypesRepresentation.getLinkByRel("domain-types").getHref(), HOST+"/domain-types");
        assertEquals(dtypesRepresentation.getLinkByRel("version").getHref(), HOST+"/version");
        
        
    }
}
