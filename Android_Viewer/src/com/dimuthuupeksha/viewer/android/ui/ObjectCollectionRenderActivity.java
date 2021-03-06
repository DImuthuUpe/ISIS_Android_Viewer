package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeCollection;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

/* Author - Dimuthu Upeksha*/

public class ObjectCollectionRenderActivity extends SherlockListActivity {

    private Map<String, ObjectMember> collectionMember;
    private String describedby;
    private boolean refreshed = false;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
        
        case R.id.home:
            intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            break;
        case R.id.services:
            intent = new Intent(this,ServicesActivity.class);
            intent.putExtra("link", Model.getInstance().getHomePage().getLinkByRel("services"));
            startActivity(intent);
            break;
        case R.id.back:
            
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String[] collectionIds = new String[referenceMap.keySet().size()];
        collectionIds = referenceMap.keySet().toArray(collectionIds);
        String selectedId = collectionIds[position];
        Link detailLink = collectionMember.get(selectedId).getLinkByRel("details");
        String data = detailLink.AsJson();
        Intent intent = new Intent(this, CollectionRenderActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("title", ((Map<String,String>)l.getItemAtPosition(position)).get("head"));
        System.out.println(selectedId);
        startActivity(intent);
    }

    public void refresh() {
        if (!refreshed) {
            new ResolveReferenceTask(ObjectCollectionRenderActivity.this).execute();
            refreshed = true;
        }
    }

    private void render(Map<String, Map<String, Object>> referenceMap) { // <key,
                                                                         // <(collection),object>>
        this.referenceMap = referenceMap;
        String[] collectionTitles = new String[referenceMap.keySet().size()];
        collectionTitles = referenceMap.keySet().toArray(collectionTitles);
        List<Map<String, String>> detailedTitles = new ArrayList<Map<String, String>>();
        for (int i = 0; i < collectionTitles.length; i++) {
            String head = ((DomainTypeCollection) referenceMap.get(collectionTitles[i]).get("collection")).getExtensions().get("friendlyName").getTextValue();
            Map<String, String> titleMap = new HashMap<String, String>();
            titleMap.put("head", head);
            if (collectionMember.get(collectionTitles[i]).getDisabledReason() != null) {
                titleMap.put("subhead", collectionMember.get(collectionTitles[i]).getDisabledReason());
            } else {
                titleMap.put("subhead", "");
            }
            detailedTitles.add(titleMap);
        }
        ListView view = getListView();
        SimpleAdapter sadapter = new SimpleAdapter(this, detailedTitles, R.layout.list_item_with_two_rows, new String[] { "head", "subhead" }, new int[] { R.id.txtHead, R.id.txtSubhead });
        view.setAdapter(sadapter);
    }

    private class ResolveReferenceTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

        ProgressDialog pd;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

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
            try {
                while (it.hasNext()) {
                    String key = it.next();
                    String url = describedby + "/collections/" + key;
                    RORequest request = client.RORequestTo(url);
                    DomainTypeCollection collection = client.executeT(DomainTypeCollection.class, "GET", request, null);
                    Map<String, Object> content = new HashMap<String, Object>();
                    content.put("collection", collection);
                    referenceMap.put(key, content);
                }
            } catch (ConnectionException e) {
                error = CONNECTION_ERROR;
                e.printStackTrace();
            } catch (InvalidCredentialException e) {
                error = INVALID_CREDENTIAL;
                e.printStackTrace();
            } catch (UnknownErrorException e) {
                error = UNKNOWN_ERROR;
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return referenceMap;
        }

        @Override
        protected void onPostExecute(Map<String, Map<String, Object>> result) {
            if (result != null) {
                render(result);
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(ObjectCollectionRenderActivity.this, LogInActivity.class);
                ObjectCollectionRenderActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ObjectCollectionRenderActivity.this).create();
                alertDialog.setTitle("Connection Error");
                alertDialog.setMessage("Please check your settings.");

                // Setting OK Button
                alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }

            pd.hide();

        }

    }

}