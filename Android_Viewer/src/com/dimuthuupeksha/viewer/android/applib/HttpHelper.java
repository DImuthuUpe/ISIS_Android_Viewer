package com.dimuthuupeksha.viewer.android.applib;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
    static final Object lock= new Object();
    static HttpHelper instance;
    private final DefaultHttpClient client;
    public final static String HOST= "http://10.0.2.2:8080/restful/";


    private HttpHelper() {
        //host = "http://localhost/";  
        
        client = new DefaultHttpClient();
       
    }

    public static HttpHelper getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new HttpHelper();
                HttpHelper.getInstance().setCredential("sven", "pass");
            }
            return instance;
        }
    }
    public HttpClient getClient() {
        return client;
    }
   
    public void setCredential(String username,String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
        new UsernamePasswordCredentials(username, password));
        client.setCredentialsProvider( provider );
    }

}
