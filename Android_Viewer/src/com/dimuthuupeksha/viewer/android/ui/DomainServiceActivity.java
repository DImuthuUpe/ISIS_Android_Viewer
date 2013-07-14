package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
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
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    
    private Service service=null;
    private void render(Service service){
        new FriendlyNameFetcherTask(DomainServiceActivity.this).execute(service);
        this.service=service;
        
    }
    
    private String[] friendlyNames;
    
    private void renderWithFirendlyNames(String names[]){        
        friendlyNames=names;
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String,ServiceMember> members = service.getMembers();
        List<String> items = new ArrayList<String>();
        String[] temp= new String[ members.keySet().size()];
        String selected= members.keySet().toArray(temp)[position];
        ServiceMember selectedMember = members.get(selected);
        Intent intent = new Intent(DomainServiceActivity.this, ActionActivity.class);
        intent.putExtra("member", selectedMember);
        intent.putExtra("title", friendlyNames[position]);
        startActivity(intent);
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
            String temp[]={};
            for(String id: service.getMembers().keySet().toArray(temp)){
                System.out.println(id);
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

    private class FriendlyNameFetcherTask extends AsyncTask<Service, Void, String[]>{
        
        DomainServiceActivity activity;
        public FriendlyNameFetcherTask(DomainServiceActivity activity) {
            this.activity=activity;
        }
        @Override
        protected String[] doInBackground(Service... params) {
            Service service = params[0];
            Map<String, ServiceMember> members = service.getMembers();
            String memberIds[]=new String[members.keySet().size()];
            memberIds= members.keySet().toArray(memberIds);
            for(int i=0;i<memberIds.length;i++){
                Link detailLink = members.get(memberIds[i]).getLinkByRel("details");
                String href = detailLink.getHref();
                RORequest request = ROClient.getInstance().RORequestTo(href);
                Action action= ROClient.getInstance().executeT(Action.class, "GET", request, null);
                request = ROClient.getInstance().RORequestTo(action.getLinkByRel("describedby").getHref());
                DomainTypeAction dta = ROClient.getInstance().executeT(DomainTypeAction.class, "GET", request, null);
                System.out.println(dta.getExtensions().get("friendlyName"));
                memberIds[i]=dta.getExtensions().get("friendlyName");
            }
            
            return memberIds;
        }
        
        @Override
        protected void onPostExecute(String[] result) {
            
            activity.renderWithFirendlyNames(result);
        }
        
    }
}
