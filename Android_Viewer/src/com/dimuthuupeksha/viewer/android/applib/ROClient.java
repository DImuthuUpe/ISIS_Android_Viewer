package com.dimuthuupeksha.viewer.android.applib;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


import android.util.Log;

import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepresentation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class ROClient {
    private String baseUri;
    public ROClient(String baseUrl){
        this.baseUri = baseUrl;
    }
    
    public RORequest RORequestTo(String path){
        return RORequest.To(path);
    }

    public RORequest RORequestTo(Resource resource, String[] pathElements){
        return RORequest.To(baseUri, resource, pathElements);
    }

    public RORequest RORequestTo(Resource resource, Map<String, String> pathElementMap){
        return RORequest.To(baseUri, resource, pathElementMap);
    }

    public HttpResponse execute(String httpMethod, RORequest roRequest, Object args){
        if(httpMethod.equals("GET")){
            HttpClient client = HttpHelper.getInstance().getClient();
            //String query = "?" + URLEncodedUtils.format(params, "utf-8");
            HttpGet get;
            try {
                get = new HttpGet(roRequest.asUriStr());
                get.setHeader("Accept","*/*");
                HttpResponse response = client.execute(get);
                return response;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
      return null;  
    }
    
    public <T extends JsonRepresentation> T executeT(Class<T> t,String httpMethod, RORequest roRequest, Object args){
        HttpResponse response = execute(httpMethod, roRequest, args);        
        
        try {
            String json = EntityUtils.toString(response.getEntity());
            JsonParser jp = new JsonFactory().createJsonParser(json);
            ObjectMapper objectMapper = new ObjectMapper();
            
            T representation = objectMapper.readValue(jp,t);
            return representation;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      return null;  
    }


    public <T extends JsonRepresentation> T get(Class<T> t,String path, Object args){
        return executeT(t, "GET", RORequestTo(path), args);        
        
    }
    public <T extends JsonRepresentation> T post(Class<T> t,String uri, Object args){
        return executeT(t, "POST", RORequestTo(uri), args); 
    }
    public <T extends JsonRepresentation> T put(Class<T> t,String uri, Object args){
        return executeT(t, "PUT", RORequestTo(uri), args);
    }
    public <T extends JsonRepresentation> T delete(Class<T> t,String uri, Object args){
        return executeT(t, "DELETE", RORequestTo(uri), args);
    }


    public HomepageRepresentation homePage(){
        return null;
    }
}
