package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeAction;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.Service;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceMember;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;

/* Author - Dimuthu Upeksha*/

public class DomainServiceActivity extends SherlockListActivity {

    private Service service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Link link = (Link) getIntent().getSerializableExtra("link");
        setContentView(R.layout.list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(link.getTitle());
        new DomainServiceTask(DomainServiceActivity.this).execute(link);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.basic_menu, (com.actionbarsherlock.view.Menu) menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

        case R.id.home:
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            break;
        case R.id.services:
            int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            com.dimuthuupeksha.viewer.android.uimodel.SlideoutActivity.prepare(this, getWindow().getDecorView().getRootView(), width);
            startActivity(new Intent(this,MenuActivity.class));
            overridePendingTransition(0, 0);
            break;
        case R.id.back:

        }
        return true;
    }

    private void render(Service service) {
        new FriendlyNameFetcherTask(DomainServiceActivity.this).execute(service);
        this.service = service;

    }

    private String[] friendlyNames;

    private void renderWithFirendlyNames(String names[]) {
        friendlyNames = names;
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, ServiceMember> members = service.getMembers();
        List<String> items = new ArrayList<String>();
        String[] temp = new String[members.keySet().size()];
        String selected = members.keySet().toArray(temp)[position];
        ServiceMember selectedMember = members.get(selected);
        Intent intent = new Intent(DomainServiceActivity.this, ActionActivity.class);
        String detailLink = selectedMember.getLinks().get(0).AsJson();
        intent.putExtra("detailLink", detailLink);
        intent.putExtra("title", friendlyNames[position]);
        startActivity(intent);
    }

    private class DomainServiceTask extends AsyncTask<Link, Void, Service> {

        private final ProgressDialog pd;
        private DomainServiceActivity activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public DomainServiceTask(DomainServiceActivity activity) {
            this.activity = activity;
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading...");
        }

        @Override
        protected Service doInBackground(Link... links) {
            List<Link> actions = new ArrayList<Link>();
            System.out.println("DomainService " + links[0].getHref());
            try {
                Service service = ROClient.getInstance().get(Service.class, links[0].getHref(), null);
                String temp[] = {};
                for (String id : service.getMembers().keySet().toArray(temp)) {
                    System.out.println(id);
                }
                return service;
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
            return null;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected void onPostExecute(Service result) {
            if (result != null) {
                activity.render(result);
            }
            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(DomainServiceActivity.this, LogInActivity.class);
                DomainServiceActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(DomainServiceActivity.this).create();
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

    private class FriendlyNameFetcherTask extends AsyncTask<Service, Void, String[]> {

        DomainServiceActivity activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public FriendlyNameFetcherTask(DomainServiceActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String[] doInBackground(Service... params) {
            Service service = params[0];
            Map<String, ServiceMember> members = service.getMembers();
            String memberIds[] = new String[members.keySet().size()];
            memberIds = members.keySet().toArray(memberIds);
            try {
                for (int i = 0; i < memberIds.length; i++) {
                    Link detailLink = members.get(memberIds[i]).getLinkByRel("details");
                    String href = detailLink.getHref();
                    RORequest request = ROClient.getInstance().RORequestTo(href);
                    Action action = ROClient.getInstance().executeT(Action.class, "GET", request, null);
                    request = ROClient.getInstance().RORequestTo(action.getLinkByRel("describedby").getHref());
                    DomainTypeAction dta = ROClient.getInstance().executeT(DomainTypeAction.class, "GET", request, null);
                    System.out.println(dta.getExtensions().get("friendlyName"));
                    memberIds[i] = dta.getExtensions().get("friendlyName");
                }

                return memberIds;
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
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                activity.renderWithFirendlyNames(result);
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(DomainServiceActivity.this, LogInActivity.class);
                DomainServiceActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(DomainServiceActivity.this).create();
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
        }

    }
}
