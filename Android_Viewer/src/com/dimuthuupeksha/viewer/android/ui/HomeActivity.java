package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.UrlTemplate;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.LinkRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ListRepresentation;
import com.dimuthuupeksha.viewer.android.uimodel.Model;


import android.net.MailTo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HomeActivity extends ListActivity {

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 

        setContentView(R.layout.list);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.home_page));        
        new HomeTask(HomeActivity.this).execute();
        
        
    }
    
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        HomepageRepresentation homePage = Model.getInstance().getHomePage();
        Object selected = this.getListAdapter().getItem(position);
        System.out.println("a "+homePage.getLinks().get(position).getRel());
        if(selected.toString().equals("services")){
            Intent intent = new Intent(HomeActivity.this, ServicesActivity.class);
            intent.putExtra("link", homePage.getLinks().get(position));
            startActivity(intent);
            
        }
        
    }

    public void render(){
        List<LinkRepresentation> links = Model.getInstance().getHomePage().getLinks();
        List<String> items = new ArrayList<String>();
        for(LinkRepresentation link: links){
            items.add(link.getRel().replace("urn:org.restfulobjects:rels/", ""));
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    }
    
    private class HomeTask extends AsyncTask<Void, Void, HomepageRepresentation> {
        private final ProgressDialog pd;
        private final HomeActivity activity;

        private HomeTask(HomeActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading HomePage");
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }


        @Override
        protected HomepageRepresentation doInBackground(Void... voids) {
            HomepageRepresentation homePage = Model.getInstance().getHomePage();
            
            if(homePage==null){
                homePage = ROClient.getInstance().homePage();
            }
            return homePage;
        }

        @Override
        protected void onPostExecute(HomepageRepresentation homePage) {
            if(homePage!=null){
                Model.getInstance().setHomePage(homePage);
                activity.render();
            }
            pd.hide();
        }
    }


}
