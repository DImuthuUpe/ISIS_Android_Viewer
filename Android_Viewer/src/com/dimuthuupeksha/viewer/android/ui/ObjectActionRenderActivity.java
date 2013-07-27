package com.dimuthuupeksha.viewer.android.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeProperty;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;

import org.apache.http.impl.client.RoutedRequest;

import android.R;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ObjectActionRenderActivity extends ListActivity {

    String describedby;
    String self;
    Map<String, ObjectMember> actionMembers;
    private boolean refreshed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        ActionResultItem actionResultItem = JsonRepr.fromString(ActionResultItem.class, data);
        describedby = actionResultItem.getLinkByRel("describedby").getHref();
        self = actionResultItem.getLinkByRel("self").getHref();
        actionMembers = new HashMap<String, ObjectMember>();

        Iterator<String> it = actionResultItem.getMembers().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String memberType = actionResultItem.getMembers().get(key).getMemberType();
            if (memberType.equals("action")) {
                actionMembers.put(key, actionResultItem.getMembers().get(key));
                System.out.println("action key " + key);
                System.out.println(actionResultItem.getMembers().get(key));
                // System.out.println(actionResultItem.getMembers().get(key).getValue());
            }
        }
        // ListView view = getListView();
        // view.setAdapter(new ArrayAdapter<String>(getBaseContext(),
        // R.layout.simple_list_item_1, chains));
        //
    }

    public void refresh() {
        if (!refreshed) {
            new ResolveReferenceTask(this).execute();
            refreshed = true;
        }
    }

    private Map<String, Map<String, Object>> referenceMap;

    private void render(Map<String, Map<String, Object>> referenceMap) {

        this.referenceMap = referenceMap;
        String[] actionTitles = new String[referenceMap.keySet().size()];
        actionTitles = referenceMap.keySet().toArray(actionTitles);
        for (int i = 0; i < actionTitles.length; i++) {
            actionTitles[i] = ((DomainTypeAction) referenceMap.get(actionTitles[i]).get("dtAction")).getExtensions().get("friendlyName");
        }
        ListView view = getListView();
        view.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.simple_list_item_1, actionTitles));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String[] actionIds = new String[referenceMap.keySet().size()];
        actionIds = referenceMap.keySet().toArray(actionIds);
        String selectedId = actionIds[position];
        System.out.println(selectedId);
        ObjectMember member = actionMembers.get(selectedId);
        Link detailLink = member.getLinkByRel("details");
        String data = detailLink.AsJson();
        Intent intent = new Intent(this, ActionActivity.class); // pass details
                                                                // link to
                                                                // ActionAction
                                                                // class to
                                                                // render the
                                                                // action
        intent.putExtra("detailLink", data);
        intent.putExtra("title", "Domain Action");
        startActivity(intent);

    }

    private class ResolveReferenceTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

        ProgressDialog pd;

        public ResolveReferenceTask(ObjectActionRenderActivity activity) {
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
            Iterator<String> it = actionMembers.keySet().iterator();
            Map<String, Map<String, Object>> referenceMap = new HashMap<String, Map<String, Object>>();
            while (it.hasNext()) {
                String key = it.next();
                // String url= self+"/actions/"+key;
                // System.out.println("action "+url);
                // RORequest request= client.RORequestTo(url);
                // Action action = client.executeT(Action.class, "GET", request,
                // null);
                String url = describedby + "/actions/" + key;
                System.out.println("desc " + url);
                RORequest request = client.RORequestTo(url);
                DomainTypeAction dtAction = client.executeT(DomainTypeAction.class, "GET", request, null);
                Map<String, Object> content = new HashMap<String, Object>();
                // content.put("action", action);
                content.put("dtAction", dtAction);
                System.out.println("Fname " + dtAction.getExtensions().get("friendlyName"));
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