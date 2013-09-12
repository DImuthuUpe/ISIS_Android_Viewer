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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;

/* Author - Dimuthu Upeksha*/

public class ObjectActionRenderFragment extends SherlockListFragment implements ActionBar.TabListener {

    private Fragment mFragment;
    String describedby;
    // String self;
    Map<String, ObjectMember> actionMembers;
    private boolean refreshed = false;
    private String data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (String) getActivity().getIntent().getSerializableExtra("data");
        ActionResultItem actionResultItem = JsonRepr.fromString(ActionResultItem.class, data);
        describedby = actionResultItem.getLinkByRel("describedby").getHref();
        // self = actionResultItem.getLinkByRel("self").getHref();
        actionMembers = new HashMap<String, ObjectMember>();
        Iterator<String> it = actionResultItem.getMembers().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String memberType = actionResultItem.getMembers().get(key).getMemberType();
            if (memberType.equals("action")) {
                System.out.println("7");
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
        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

        case R.id.home:
            intent = new Intent(this.getActivity(), HomeActivity.class);
            startActivity(intent);
            break;
        case R.id.services:
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            com.dimuthuupeksha.viewer.android.uimodel.SlideoutActivity.prepare(getActivity(), getActivity().getWindow().getDecorView().getRootView(), width);
            startActivity(new Intent(this.getActivity(), MenuActivity.class));
            getActivity().overridePendingTransition(0, 0);
            break;
        case R.id.back:

        }
        return true;
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
        List<Map<String, String>> detailedTitles = new ArrayList<Map<String, String>>();
        for (int i = 0; i < actionTitles.length; i++) {
            String head = ((DomainTypeAction) referenceMap.get(actionTitles[i]).get("dtAction")).getExtensions().get("friendlyName");
            Map<String, String> titleMap = new HashMap<String, String>();
            titleMap.put("head", head);
            if (actionMembers.get(actionTitles[i]).getDisabledReason() != null) {
                titleMap.put("subhead", actionMembers.get(actionTitles[i]).getDisabledReason());
            } else {
                titleMap.put("subhead", "");
            }
            detailedTitles.add(titleMap);
        }
        SimpleAdapter sadapter = new SimpleAdapter(getSherlockActivity(), detailedTitles, R.layout.list_item_with_two_rows, new String[] { "head", "subhead" }, new int[] { R.id.txtHead, R.id.txtSubhead });
        setListAdapter(sadapter);
        getListView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String[] actionIds = new String[referenceMap.keySet().size()];
        actionIds = referenceMap.keySet().toArray(actionIds);
        String selectedId = actionIds[position];
        System.out.println(selectedId);
        if (actionMembers.get(selectedId).getDisabledReason() == null) {
            ObjectMember member = actionMembers.get(selectedId);
            Link detailLink = member.getLinkByRel("details");
            String data = detailLink.AsJson();
            Intent intent = new Intent(this.getActivity(), ActionActivity.class); // pass
                                                                                  // details
            // link to
            // ActionAction
            // class to
            // render the
            // action
            intent.putExtra("detailLink", data);
            intent.putExtra("title", ((Map<String, String>) l.getItemAtPosition(position)).get("head"));
            startActivity(intent);
        }else{
            Toast.makeText(getActivity().getApplicationContext(), actionMembers.get(selectedId).getDisabledReason(), Toast.LENGTH_SHORT).show();
        }

    }

    private class ResolveReferenceTask extends AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

        ProgressDialog pd;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public ResolveReferenceTask(ObjectActionRenderFragment fragment) {
            pd = new ProgressDialog(fragment.getActivity());
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
            try {
                while (it.hasNext()) {
                    String key = it.next();
                    // String url= self+"/actions/"+key;
                    // System.out.println("action "+url);
                    // RORequest request= client.RORequestTo(url);
                    // Action action = client.executeT(Action.class, "GET",
                    // request,
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
                Intent intent = new Intent(ObjectActionRenderFragment.this.getActivity(), LogInActivity.class);
                ObjectActionRenderFragment.this.getActivity().startActivity(intent);
            }
            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ObjectActionRenderFragment.this.getActivity()).create();
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

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        mFragment = new ObjectActionRenderFragment();
        // Attach fragment1.xml layout
        ft.add(android.R.id.content, mFragment);
        ft.attach(mFragment);
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        // Remove fragment1.xml layout
        ft.remove(mFragment);
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

}
