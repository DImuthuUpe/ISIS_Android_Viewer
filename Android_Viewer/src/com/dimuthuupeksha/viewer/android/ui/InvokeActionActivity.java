package com.dimuthuupeksha.viewer.android.ui;

import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ListRepr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class InvokeActionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_invoke_action);
        Action action = (Action)getIntent().getSerializableExtra("action");
        new RepresentationSelectorTask(InvokeActionActivity.this).execute(action);
    }
    
    private void renderList(ListRepr listRepr){
        ListView listView = new ListView(this);
        List<Link> values = listRepr.getResult().getValue();
        String[] titles = new String[values.size()];
        for(int i=0;i<values.size();i++){
            titles[i]=values.get(i).getTitle();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.addView(listView);
        setContentView(layout);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.invoke_action, menu);
        return true;
    }
    
    private class RepresentationSelectorTask extends AsyncTask<Action, Void, JsonRepr>{

        private InvokeActionActivity activity;
        
        public RepresentationSelectorTask(InvokeActionActivity activity) {
            this.activity=activity;
        }
        
        @Override
        protected JsonRepr doInBackground(Action... params) {
            Link describedByLink = params[0].getLinkByRel("describedby");
            RORequest request = ROClient.getInstance().RORequestTo(describedByLink.getHref());
            DomainTypeAction dt= ROClient.getInstance().executeT(DomainTypeAction.class, describedByLink.getMethod(), request, null);
            request = ROClient.getInstance().RORequestTo(dt.getLinkByRel("return-type").getHref());
            DomainType domainType= ROClient.getInstance().executeT(DomainType.class, dt.getLinkByRel("return-type").getMethod(), request, null);
            System.out.println(domainType.getCanonicalName());
            JsonRepr result = null;
            if(domainType.getCanonicalName().equals("java.util.List")){
                request= ROClient.getInstance().RORequestTo(params[0].getLinkByRel("invoke").getHref());
                result = ROClient.getInstance().executeT(ListRepr.class, params[0].getLinkByRel("invoke").getMethod(), request, null); 
            }
            return result;
        }
        
        @Override
        protected void onPreExecute() {
            
        }
        
        @Override
        protected void onPostExecute(JsonRepr result) {
            if(result instanceof ListRepr){
                System.out.println("Done");
                renderList((ListRepr)result);
            }
        }
        
    }

}
