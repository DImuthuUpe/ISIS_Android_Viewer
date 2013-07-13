package com.dimuthuupeksha.viewer.android.ui;

import java.util.List;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceMember;

import org.codehaus.jackson.JsonNode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;

public class ActionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Action");
        
        ServiceMember member = (ServiceMember) getIntent().getSerializableExtra("member");
        System.out.println(member.getLinks().get(0).getHref());
        new ActionTask(ActionActivity.this).execute(member);
    }
    
    private void render(Action action){
        Link invokeLink =action.getLinkByRel("invoke");
        if(!invokeLink.getArguments().isEmpty()){
            new DomainTypeFetcherTask().execute(action);
        }
        
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

        @Override
        protected List<DomainType> doInBackground(Action... params) {
            Action action = params[0];
            List<Map<String, JsonNode>> parameters= action.getParameters();
            for(Map<String, JsonNode> parameter:parameters){
                System.out.println(parameter.get("name"));
            }
            return null;
        }
        
    }

}
