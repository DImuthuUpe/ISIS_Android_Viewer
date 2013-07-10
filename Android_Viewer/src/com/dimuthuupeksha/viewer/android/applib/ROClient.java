package com.dimuthuupeksha.viewer.android.applib;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


import android.text.style.ReplacementSpan;
import android.util.Log;

import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepresentation;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class ROClient {
    private final DefaultHttpClient client;
    private final String host= "http://10.0.2.2:8080/restful/";

    private ROClient(){        
        client = new DefaultHttpClient();
        setCredential("sven", "pass");
    }
    
    private static ROClient roClient=null;
    
    public void setCredential(String username,String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
        new UsernamePasswordCredentials(username, password));
        client.setCredentialsProvider( provider );
    }
    public static ROClient getInstance(){
        if(roClient==null){
            roClient= new ROClient();
        }
        return roClient;
    }
    
    public RORequest RORequestTo(String path){
        return RORequest.To(path);
    }

    
    
    public HttpResponse execute(String httpMethod, RORequest roRequest, Object args){
        
        if(httpMethod.equals("GET")){
            //HttpClient client = HttpHelper.getInstance().getClient();
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
        } catch (Exception e) {
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
        String params[]= {}; 
        RORequest request = RORequest.To(host, Resource.HomePage, params);
        HomepageRepresentation homepageRepresentation = executeT(HomepageRepresentation.class,"GET",request, null);
       return homepageRepresentation;
    }

    
}
