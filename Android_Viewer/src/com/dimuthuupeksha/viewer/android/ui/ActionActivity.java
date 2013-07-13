package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ParamDescription;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceMember;
import com.dimuthuupeksha.viewer.android.uimodel.ViewMapper;

import org.codehaus.jackson.JsonNode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ActionActivity extends Activity {
    private Action action;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
          //      LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        
        
        
        
        
        ServiceMember member = (ServiceMember) getIntent().getSerializableExtra("member");
        String title = (String)getIntent().getSerializableExtra("title");
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
                ParamDescription des = ROClient.getInstance().executeT(ParamDescription.class , "GET", request, null);
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

}
