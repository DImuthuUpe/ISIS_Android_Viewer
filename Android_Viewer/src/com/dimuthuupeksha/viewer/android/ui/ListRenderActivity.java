package com.dimuthuupeksha.viewer.android.ui;

import java.io.IOException;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListRenderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String)getIntent().getSerializableExtra("data");
        final ActionResult listRepr = JsonRepr.fromString(ActionResult.class, data);
        ListView listView = new ListView(this);
        List<Link> values = listRepr.getResult().getValue();
        String[] titles = new String[values.size()];
        for(int i=0;i<values.size();i++){
            titles[i]=values.get(i).getTitle();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            System.out.println(listRepr.getResult().getValue().get(position).getHref());
             new ListItemResolveTask().execute(listRepr.getResult().getValue().get(position));
             }
      });
      LinearLayout layout = new LinearLayout(this);
      layout.setOrientation(android.widget.LinearLayout.VERTICAL);
      layout.addView(listView);
      setContentView(layout);
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_render, menu);
        return true;
    }
    
    private class ListItemResolveTask extends AsyncTask<Link, Void, ActionResultItem>{

        @Override
        protected ActionResultItem doInBackground(Link... params) {
            Link elementLink = params[0];
            ROClient client= ROClient.getInstance();
            RORequest request = client.RORequestTo(elementLink.getHref());
            ActionResultItem result = client.executeT(ActionResultItem.class, elementLink.getMethod(), request, null);
            return result;
        }
        
        @Override
        protected void onPostExecute(ActionResultItem result) {
            if(result!=null){
                String data = result.AsJson();
                Intent intent = new Intent(ListRenderActivity.this,ObjectRenderActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        }
    }

}
