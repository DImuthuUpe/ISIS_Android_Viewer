package com.dimuthuupeksha.viewer.android.applib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.style.ReplacementSpan;
import android.util.Log;

import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.ErrorRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Homepage;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

public class ROClient {
    private final DefaultHttpClient client;
    private final String host = "http://192.168.56.1:8080/restful/";

    private ROClient() {
        client = new DefaultHttpClient();
        setCredential("sven", "pass2");
    }

    private static ROClient roClient = null;

    public void setCredential(String username, String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(username, password));
        client.setCredentialsProvider(provider);
    }

    public static ROClient getInstance() {
        if (roClient == null) {
            roClient = new ROClient();
        }
        return roClient;
    }

    public RORequest RORequestTo(String path) {
        return RORequest.To(path);
    }

    public HttpResponse execute(String httpMethod, RORequest roRequest, Map<String, Object> args) throws ConnectionException, InvalidCredentialException, UnknownErrorException {
        HttpResponse response = null;
        if (httpMethod.equals("GET")) {
            // HttpClient client = HttpHelper.getInstance().getClient();
            // String query = "?" + URLEncodedUtils.format(params, "utf-8");
            HttpGet get;
            try {
                get = new HttpGet(roRequest.asUriStr());
                get.setHeader("Accept", "*/*");
                response = client.execute(get);
                // System.out.println("Status code "+response.getStatusLine().getStatusCode());
                // return response;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConnectionException();
            }
        } else if (httpMethod.equals("POST")) {
            HttpPost post;

            try {
                post = new HttpPost(roRequest.asUriStr());
                post.setHeader("Accept", "*/*");
                post.setHeader("Content-Type", "*/*");
                if (args != null) {

                    Map argmap = new HashMap<String, Map<String, Object>>();

                    String[] params = {};
                    params = args.keySet().toArray(params);

                    for (String param : params) {
                        Map value = new HashMap<String, Object>();
                        value.put("value", args.get(param));
                        argmap.put(param, value);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    String data = objectMapper.writeValueAsString(argmap);

                    System.out.println(data);
                    post.setEntity(new StringEntity(data));
                }

                response = client.execute(post);
                System.out.println("Status code " + response.getStatusLine().getStatusCode());

                // /////////////////Print response/////////////////////
                /*
                 * HttpEntity httpEntity = response.getEntity(); BufferedReader
                 * reader; try { reader = new BufferedReader(new
                 * InputStreamReader( httpEntity.getContent(), "iso-8859-1"),
                 * 8); String line = null; while ((line = reader.readLine()) !=
                 * null) { System.out.println(line); } }catch (Exception e) {
                 * e.printStackTrace(); }
                 */
                // ////////////////////////////////////////
                // return response;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConnectionException();
            }

        } else if (httpMethod.equals("PUT")) {
            HttpPut put;

            if (args != null) {

                try {
                    put = new HttpPut(roRequest.asUriStr());
                    put.setHeader("Accept", "*/*");
                    put.setHeader("Content-Type", "*/*");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String data = objectMapper.writeValueAsString(args);

                    System.out.println(data);
                    put.setEntity(new StringEntity(data));
                    response = client.execute(put);
                    System.out.println("Status code " + response.getStatusLine().getStatusCode());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ConnectionException();
                }
            }

        }

        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
            case 200:
                break;
            case 401:
                throw new InvalidCredentialException();

            default:
                System.out.println("Error " + statusCode);
                try {
                    String json = EntityUtils.toString(response.getEntity());
                    System.out.println(json);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                throw new UnknownErrorException();

            }
        }

        return response;
    }

    public <T extends JsonRepr> T executeT(Class<T> t, String httpMethod, RORequest roRequest, Map<String, Object> args) throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        HttpResponse response = execute(httpMethod, roRequest, args);

        try {
            String json = EntityUtils.toString(response.getEntity());
            JsonParser jp = new JsonFactory().createJsonParser(json);
            ObjectMapper objectMapper = new ObjectMapper();
            T representation = objectMapper.readValue(jp, t);
            return representation;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException();
        }
    }

    public <T extends JsonRepr> T get(Class<T> t, String path, Map<String, Object> args) throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        return executeT(t, "GET", RORequestTo(path), args);

    }

    public <T extends JsonRepr> T post(Class<T> t, String uri, Map<String, Object> args) throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        return executeT(t, "POST", RORequestTo(uri), args);
    }

    public <T extends JsonRepr> T put(Class<T> t, String uri, Map<String, Object> args) throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        return executeT(t, "PUT", RORequestTo(uri), args);
    }

    public <T extends JsonRepr> T delete(Class<T> t, String uri, Map<String, Object> args) throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        return executeT(t, "DELETE", RORequestTo(uri), args);
    }

    public Homepage homePage() throws com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException, ConnectionException, InvalidCredentialException, UnknownErrorException {
        String params[] = {};
        RORequest request = RORequest.To(host, Resource.HomePage, params);
        Homepage homepageRepresentation = executeT(Homepage.class, "GET", request, null);
        return homepageRepresentation;
    }

}
