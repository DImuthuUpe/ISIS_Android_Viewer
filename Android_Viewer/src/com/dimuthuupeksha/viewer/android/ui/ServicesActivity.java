package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.Service;
import com.dimuthuupeksha.viewer.android.applib.representation.Services;
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

public class ServicesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.services_page));
        
        Link link = (Link) getIntent().getSerializableExtra("link");
        new ServicesTask(ServicesActivity.this).execute(link);
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Services services = Model.getInstance().getServices();
        Link link = services.getValue().get(position);
        Intent intent = new Intent(ServicesActivity.this, DomainServiceActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }
    
    private void render(){
        System.out.println("link " + Model.getInstance().getServices().getValue().get(0).getHref());
        List<Link> links = Model.getInstance().getServices().getValue();
        List<String> items = new ArrayList<String>();
        for(Link link: links){
            items.add(link.getTitle());
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    
    }
    
    private class ServicesTask extends AsyncTask<Link, Void, Services> {
        private final ProgressDialog pd;
        private final ServicesActivity activity;
        
        public ServicesTask(ServicesActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading Services");
            this.activity = activity;
        }
        
        @Override
        protected Services doInBackground(Link... link) {
            System.out.println("Service link "+link[0].getHref());
            Services services = Model.getInstance().getServices();
            if(services==null){
                System.out.println("Service link "+link[0].getHref());
                RORequest request = ROClient.getInstance().RORequestTo(link[0].getHref());
                services= ROClient.getInstance().executeT(Services.class,link[0].getMethod(), request, null);
            }
            return services;
        }
        
        @Override
        protected void onPreExecute() {
            pd.show();
        }
        
        @Override
        protected void onPostExecute(Services services) {
            if(services!=null){
                Model.getInstance().setServices(services);
                activity.render(); 
            }
            pd.hide();
        }
        
    }

}
