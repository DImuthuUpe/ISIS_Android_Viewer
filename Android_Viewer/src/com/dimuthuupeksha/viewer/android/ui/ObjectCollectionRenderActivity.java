package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeCollection;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeProperty;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
 
public class ObjectCollectionRenderActivity extends ListActivity {
 
    private Map<String, ObjectMember> collectionMember;
    private String describedby;
    private boolean refreshed =false;
    Map<String, Map<String, Object>> referenceMap;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        ActionResultItem actionResultItem = JsonRepr.fromString(ActionResultItem.class, data);
        describedby = actionResultItem.getLinkByRel("describedby").getHref();
        collectionMember = new HashMap<String, ObjectMember>();

        Iterator<String> it = actionResultItem.getMembers().keySet().iterator();
        while (it.hasNext()) { 
            String key = it.next();
            String memberType = actionResultItem.getMembers().get(key).getMemberType();
            if (memberType.equals("collection")) {
                collectionMember.put(key, actionResultItem.getMembers().get(key));
            }
        }
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String[] collectionIds = new String[referenceMap.keySet().size()];
        collectionIds = referenceMap.keySet().toArray(collectionIds);
        String selectedId = collectionIds[position];
        Link detailLink = collectionMember.get(selectedId).getLinkByRel("details");
        String data = detailLink.AsJson();
        Intent intent = new Intent(this,CollectionRenderActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("title", l.getItemAtPosition(position).toString());
        System.out.println(selectedId);
        startActivity(intent);
    }
    
    public void refresh(){
    	if (!refreshed) {
            new ResolveReferenceTask(ObjectCollectionRenderActivity.this).execute();
            refreshed = true;
        }
    }
    
    private void render(Map<String, Map<String, Object>> referenceMap) { //<key, <(collection),object>>
    	this.referenceMap=referenceMap;
    	String[] collectionTitles = new String[referenceMap.keySet().size()];
        collectionTitles = referenceMap.keySet().toArray(collectionTitles);
        List<Map<String,String>> detailedTitles = new ArrayList<Map<String,String>>();
        for (int i = 0; i < collectionTitles.length; i++) {
        	String head = ((DomainTypeCollection) referenceMap.get(collectionTitles[i])
        			.get("collection")).getExtensions().get("friendlyName").getTextValue();
        	Map<String,String> titleMap = new HashMap<String, String>();
			titleMap.put("head", head);
			if(collectionMember.get(collectionTitles[i]).getDisabledReason()!=null){
			titleMap.put("subhead", collectionMember.get(collectionTitles[i]).getDisabledReason());
			}else{
				titleMap.put("subhead","");
			}
			detailedTitles.add(titleMap);
        }
        ListView view = getListView();
        SimpleAdapter sadapter = new SimpleAdapter(this, detailedTitles, R.layout.list_item_with_two_rows, new String[]{"head","subhead"},new int[]{R.id.txtHead,R.id.txtSubhead});
        view.setAdapter(sadapter);
    }
    
    private class ResolveReferenceTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

        ProgressDialog pd;

        public ResolveReferenceTask(ObjectCollectionRenderActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading");
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected Map<String, Map<String, Object>> doInBackground(Void... params) {
            ROClient client = ROClient.getInstance();
            Iterator<String> it = collectionMember.keySet().iterator();
            Map<String, Map<String, Object>> referenceMap = new HashMap<String, Map<String, Object>>();
            while (it.hasNext()) {
                String key = it.next();
                String url = describedby + "/collections/" + key;
                RORequest request = client.RORequestTo(url);
                DomainTypeCollection collection = client.executeT(DomainTypeCollection.class, "GET", request, null);
                Map<String, Object> content = new HashMap<String, Object>();
                content.put("collection", collection);
                referenceMap.put(key, content);
            }
            return referenceMap;
        }

        @Override
        protected void onPostExecute(Map<String, Map<String, Object>> result) {
            render(result);
            pd.hide();
        }

    }
     
}