package com.dimuthuupeksha.viewer.android.applib.test.representation.domaintypes;

import com.dimuthuupeksha.viewer.android.applib.HttpHelper;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypesRepresentation;
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
        ROClient client = ROClient.getInstance();
        RORequest request = client.RORequestTo(Resource.DomainTypes, params);
        DomainTypesRepresentation dtypesRepresentation = client.executeT(DomainTypesRepresentation.class,"GET",request, null);
        assertNotNull(dtypesRepresentation);
        assertEquals(dtypesRepresentation.getLinks().get(0).getHref(), HttpHelper.HOST+"/domain-types");
        assertEquals(dtypesRepresentation.getValues().get(0).getHref(), HttpHelper.HOST+"/domain-types/PIS3");
        
        
    }
}
