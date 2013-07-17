package com.dimuthuupeksha.viewer.android.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DObject;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeActionParam;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceMember;
import com.dimuthuupeksha.viewer.android.uimodel.ViewMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ActionActivity extends Activity {
    private Action action;
    private String title;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ServiceMember member = (ServiceMember) getIntent().getSerializableExtra("member");
        title = (String)getIntent().getSerializableExtra("title");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(title);        
        System.out.println(member.getLinks().get(0).getHref());
        new ActionTask(ActionActivity.this).execute(member);
    }
    
    private void render(Action action){
        this.action = action;
        Link invokeLink =action.getLinkByRel("invoke");
        if(!invokeLink.getArguments().isEmpty()){
            new DomainTypeFetcherTask(ActionActivity.this).execute(action);
        }else{
            Intent intent = new Intent(ActionActivity.this,InvokeActionActivity.class);
            intent.putExtra("action", action);
            intent.putExtra("title", title);
            startActivity(intent);
            
        }
        
    }
    
    private void renderArguments(List<DomainType> domainTypes){
        LinearLayout layout = new LinearLayout(this);
        
        
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        List<Map<String, JsonNode>> parameters= action.getParameters();
        for(int i=0;i<parameters.size();i++){
            TextView tv= new TextView(this);
            //tv.setTextSize(24);
            tv.setText(parameters.get(i).get("name").getValueAsText());
            layout.addView(tv);
            
            if(!parameters.get(i).containsKey("choices")){
                View argsView= ViewMapper.convertToView(domainTypes.get(i).getCanonicalName(),this);
                if(argsView!=null){                
                    layout.addView(argsView);
                }
            }else{
                Spinner spinner = new Spinner(this);                
                JsonNode arrayNode = parameters.get(i).get("choices");
                String[] choices= new String[arrayNode.size()];
                int count=0;
                for(JsonNode node: arrayNode){
                    System.out.println(node.asText());
                    choices[count]= node.asText();
                    count++;
                }
                
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,choices);
                spinner.setAdapter(spinnerArrayAdapter);
                layout.addView(spinner);
                
            }
        }
        Button submitButton = new Button(this);
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                System.out.println(action.getLinkByRel("invoke").getHref());
                Map<String, String> args = new HashMap<String, String>();
                args.put("description", "Des5");
                args.put("category", "Professional");
                args.put("dueBy",null);
                args.put("cost", "10.5");   
                new TempTask().execute(args);
                //System.out.println(response);
            }
        });
        layout.addView(submitButton);
        setContentView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }
    
    private class ActionTask extends AsyncTask<ServiceMember, Void, Action>{

        private final ProgressDialog pd;
        private final ActionActivity activity;
        
        public ActionTask(ActionActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading action");
            this.activity = activity;
        }
        
        @Override
        protected void onPreExecute() {
            pd.show();
        }
        
        @Override
        protected Action doInBackground(ServiceMember... members) {
            RORequest request = ROClient.getInstance().RORequestTo(members[0].getLinks().get(0).getHref());
            Action action= ROClient.getInstance().executeT(Action.class, members[0].getLinks().get(0).getMethod(), request, null);
            return action;
        }
        
        @Override
        protected void onPostExecute(Action result) {
            pd.hide();
            activity.render(result);
        }
        
    }
    
    private class DomainTypeFetcherTask extends AsyncTask<Action, Void, List<DomainType>>{
        
        private ActionActivity activity;
        
        public DomainTypeFetcherTask(ActionActivity activity) {
            this.activity = activity;
        }
        
        @Override
        protected List<DomainType> doInBackground(Action... params) {
            Action action = params[0];
            List<Map<String, JsonNode>> parameters= action.getParameters();
            List<DomainType> domainTypes = new ArrayList<DomainType>();
            for(Map<String, JsonNode> parameter:parameters){
                System.out.println(parameter.get("name"));
                System.out.println(action.getLinkByRel("describedby").getHref()+"/params/"+ parameter.get("name").getValueAsText());
                RORequest request = ROClient.getInstance().RORequestTo(action.getLinkByRel("describedby").getHref()+"/params/"+ parameter.get("name").getValueAsText());
                DomainTypeActionParam des = ROClient.getInstance().executeT(DomainTypeActionParam.class , "GET", request, null);
                request = ROClient.getInstance().RORequestTo(des.getLinkByRel("return-type").getHref());
                DomainType domainType = ROClient.getInstance().executeT(DomainType.class,"GET",request, null);
                System.out.println(domainType.getCanonicalName());
                domainTypes.add(domainType);
            }
            return domainTypes;
        }
        
        @Override
        protected void onPostExecute(List<DomainType> result) {
            renderArguments(result);
        }
        
    }
    
    private class TempTask extends AsyncTask<Map<String, String>, Void, List<String>>{

        @Override
        protected List<String> doInBackground(Map<String, String>... params) {
            RORequest request = ROClient.getInstance().RORequestTo(action.getLinkByRel("invoke").getHref());
            //HttpResponse response= ROClient.getInstance().execute("POST",request, params[0]);
            //System.out.println(response);
            
            //HttpEntity httpEntity = response.getEntity();
            //BufferedReader reader;
            //try {
              //  reader = new BufferedReader(new InputStreamReader(
                //        httpEntity.getContent(), "iso-8859-1"), 8);
                //String line = null;
                //while ((line = reader.readLine()) != null) {
                 //   System.out.println(line);
                //}
            //}catch (Exception e) {
              //  e.printStackTrace();
            //}
            
            DObject dObject = ROClient.getInstance().executeT(DObject.class, action.getLinkByRel("invoke").getMethod(), request, params[0]);
            System.out.println(dObject.getMessage());
            System.out.println(dObject.getResult().getTitle());
            System.out.println(dObject.getResult().getMembers().get("category").getValue());
            System.out.println(dObject.getResult().getMembers().get("category").x_isis_format);
            
            return null;
        }
        
    }

}
