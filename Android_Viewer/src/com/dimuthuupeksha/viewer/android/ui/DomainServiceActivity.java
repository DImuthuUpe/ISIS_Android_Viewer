package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.Service;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceMember;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class DomainServiceActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Link link = (Link) getIntent().getSerializableExtra("link");
        setContentView(R.layout.list);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(link.getTitle());
        new DomainServiceTask(DomainServiceActivity.this).execute(link);
    }
    
    private void render(Service service){
        List<ServiceMember> members = service.getMembers();
        List<String> items = new ArrayList<String>();
        for(ServiceMember member: members){
            items.add(member.getId());
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.domain, menu);
        return true;
    }
    
    private class DomainServiceTask extends AsyncTask<Link, Void, Service>{
        private final ProgressDialog pd;
        private DomainServiceActivity activity;
        
        public DomainServiceTask(DomainServiceActivity activity){
            this.activity=activity;
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading...");
        }
        
        @Override
        protected Service doInBackground(Link... links) {
            List<Link> actions = new ArrayList<Link>();
            System.out.println("DomainService "+links[0].getHref());
            Service service = ROClient.getInstance().get(Service.class, links[0].getHref(), null);
            for(ServiceMember member: service.getMembers()){
                System.out.println(member.getId());
            }            
            return service;
        }
        
        @Override
        protected void onPreExecute() {
            pd.show();
        }
        
        @Override
        protected void onPostExecute(Service result) {
            activity.render(result);
            pd.hide();
        }
        
    }

}
