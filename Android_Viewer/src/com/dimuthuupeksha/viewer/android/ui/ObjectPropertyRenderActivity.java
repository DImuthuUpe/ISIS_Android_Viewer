package com.dimuthuupeksha.viewer.android.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeProperty;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;
import com.dimuthuupeksha.viewer.android.uimodel.ViewMapper;

import org.apache.http.impl.client.RoutedRequest;

import android.R;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ObjectPropertyRenderActivity extends Activity {

    Map<String, ObjectMember> propertyMember;
    String describedby;
    private boolean refreshed = false;

    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        ActionResultItem actionResultItem = JsonRepr.fromString(ActionResultItem.class, data);
        describedby = actionResultItem.getLinkByRel("describedby").getHref();
        propertyMember = new HashMap<String, ObjectMember>();

        Iterator<String> it = actionResultItem.getMembers().keySet().iterator();
        while (it.hasNext()) { 
            String key = it.next();
            String memberType = actionResultItem.getMembers().get(key).getMemberType();
            if (memberType.equals("property")) {
                propertyMember.put(key, actionResultItem.getMembers().get(key));
                // System.out.println(key);
                // System.out.println(actionResultItem.getMembers().get(key));
                // System.out.println(actionResultItem.getMembers().get(key).getValue());
            }
        }

        // restaurants = propertyMember.keySet().toArray(restaurants);
        // ListView view = getListView();
        // view.setAdapter(new ArrayAdapter<String>(getBaseContext(),
        // R.layout.simple_list_item_1, restaurants));
        //

    }

    public void refresh() {
        if (!refreshed) {
            new ResolveReferenceTask(ObjectPropertyRenderActivity.this).execute();
            refreshed = true;
        }
    }

    private void render(Map<String, Map<String, Object>> referenceMap) { //<key, <(domaintype,returntype),object>>
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        Iterator<String> it = referenceMap.keySet().iterator();
        while (it.hasNext()) {
            String id = it.next();
            DomainTypeProperty property = (DomainTypeProperty) referenceMap.get(id).get("property");
            DomainType dtype = (DomainType) referenceMap.get(id).get("dtype");
            TextView tv = new TextView(this);
            tv.setText(property.getExtensions().get("friendlyName"));
            // System.out.println("1");
            // System.out.println(id);
            // System.out.println(propertyMember.get(id).getValue());
            // System.out.println("1.5");
            if (propertyMember.get(id).getValue() != null) {//check whether value is null. 
                System.out.println("Value " + propertyMember.get(id).getValue().getValueAsText());
                View view = ViewMapper.convertToView(dtype.getCanonicalName(), this, propertyMember.get(id).getValue().getValueAsText());
                // System.out.println("4");
                layout.addView(tv);
                if (view != null) {
                    layout.addView(view);
                }
            }//good start point to track domain objects as property
        }
        setContentView(layout);
    }

    private class ResolveReferenceTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

        ProgressDialog pd;

        public ResolveReferenceTask(ObjectPropertyRenderActivity activity) {
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
            Iterator<String> it = propertyMember.keySet().iterator();
            Map<String, Map<String, Object>> referenceMap = new HashMap<String, Map<String, Object>>();
            while (it.hasNext()) {
                String key = it.next();
                String url = describedby + "/properties/" + key;
                RORequest request = client.RORequestTo(url);
                DomainTypeProperty property = client.executeT(DomainTypeProperty.class, "GET", request, null);
                url = property.getLinkByRel("return-type").getHref();
                System.out.println(url);
                request = client.RORequestTo(url);
                DomainType domainType = client.executeT(DomainType.class, "GET", request, null);
                Map<String, Object> content = new HashMap<String, Object>();
                content.put("property", property);
                content.put("dtype", domainType);
                referenceMap.put(key, content);
                // System.out.println(property.getExtensions().get("friendlyName"));
                // System.out.println(domainType.getCanonicalName());
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
