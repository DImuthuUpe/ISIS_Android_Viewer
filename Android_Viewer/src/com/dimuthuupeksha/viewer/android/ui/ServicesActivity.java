package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.LinkRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ServicesRepresentation;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ServicesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.services_page));
        
        LinkRepresentation link = (LinkRepresentation) getIntent().getSerializableExtra("link");
        new ServicesTask(ServicesActivity.this).execute(link);
        
    }
    
    
    private void render(){
        System.out.println("link " + Model.getInstance().getServices().getValue().get(0).getHref());
        List<LinkRepresentation> links = Model.getInstance().getServices().getValue();
        List<String> items = new ArrayList<String>();
        for(LinkRepresentation link: links){
            items.add(link.getTitle());
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    
    }
    
    private class ServicesTask extends AsyncTask<LinkRepresentation, Void, ServicesRepresentation> {
        private final ProgressDialog pd;
        private final ServicesActivity activity;
        
        public ServicesTask(ServicesActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading Services");
            this.activity = activity;
        }
        
        @Override
        protected ServicesRepresentation doInBackground(LinkRepresentation... link) {
            System.out.println("Service link "+link[0].getHref());
            ServicesRepresentation services = Model.getInstance().getServices();
            if(services==null){
                System.out.println("Service link "+link[0].getHref());
                RORequest request = ROClient.getInstance().RORequestTo(link[0].getHref());
                services= ROClient.getInstance().executeT(ServicesRepresentation.class,link[0].getMethod(), request, null);
            }
            return services;
        }
        
        @Override
        protected void onPreExecute() {
            pd.show();
        }
        
        @Override
        protected void onPostExecute(ServicesRepresentation services) {
            if(services!=null){
                Model.getInstance().setServices(services);
                activity.render(); 
            }
            pd.hide();
        }
        
    }

}
