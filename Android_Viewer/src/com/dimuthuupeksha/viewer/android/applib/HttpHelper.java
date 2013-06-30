package com.dimuthuupeksha.viewer.android.applib;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
    static final Object lock= new Object();
    static HttpHelper instance;
    private final HttpClient client;
    public final static String HOST= "http://10.0.2.2:39393";


    private HttpHelper() {
        //host = "http://localhost/";     
        client = new DefaultHttpClient();
    }

    public static HttpHelper getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new HttpHelper();
            }
            return instance;
        }
    }
    public HttpClient getClient() {
        return client;
    }
   

}
