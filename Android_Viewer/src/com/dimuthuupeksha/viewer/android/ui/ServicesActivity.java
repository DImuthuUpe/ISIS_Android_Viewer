package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

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
import com.dimuthuupeksha.viewer.android.applib.exceptions.JsonParseException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.Services;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

/* Author - Dimuthu Upeksha*/

public class ServicesActivity extends SherlockListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.services_page));

        Link link = (Link) getIntent().getSerializableExtra("link");
        new ServicesTask(ServicesActivity.this).execute(link);

    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.basic_menu, (com.actionbarsherlock.view.Menu) menu);
        return super.onCreateOptionsMenu(menu);
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
            int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            com.dimuthuupeksha.viewer.android.uimodel.SlideoutActivity.prepare(this, getWindow().getDecorView().getRootView(), width);
            startActivity(new Intent(this,MenuActivity.class));
            overridePendingTransition(0, 0);
            break;
        case R.id.back:
            
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Services services = Model.getInstance().getServices();
        Link link = services.getValue().get(position);
        Intent intent = new Intent(ServicesActivity.this, DomainServiceActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    private void render() {
        System.out.println("link " + Model.getInstance().getServices().getValue().get(0).getHref());
        List<Link> links = Model.getInstance().getServices().getValue();
        List<String> items = new ArrayList<String>();
        for (Link link : links) {
            items.add(link.getTitle());
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));

    }

    private class ServicesTask extends AsyncTask<Link, Void, Services> {
        private final ProgressDialog pd;
        private final ServicesActivity activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public ServicesTask(ServicesActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading Services");
            this.activity = activity;
        }

        @Override
        protected Services doInBackground(Link... link) {
            System.out.println("Service link " + link[0].getHref());
            Services services = Model.getInstance().getServices();
            if (services == null) {
                System.out.println("Service link " + link[0].getHref());
                RORequest request = ROClient.getInstance().RORequestTo(link[0].getHref());
                try {
                    services = ROClient.getInstance().executeT(Services.class, link[0].getMethod(), request, null);
                } catch (JsonParseException e) {

                    e.printStackTrace();
                } catch (ConnectionException e) {
                    error = CONNECTION_ERROR;
                    e.printStackTrace();
                } catch (InvalidCredentialException e) {
                    error = INVALID_CREDENTIAL;
                    e.printStackTrace();
                } catch (UnknownErrorException e) {
                    error = UNKNOWN_ERROR;
                    e.printStackTrace();
                }
            }
            return services;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected void onPostExecute(Services services) {
            if (services != null) {
                Model.getInstance().setServices(services);
                activity.render();
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(ServicesActivity.this, LogInActivity.class);
                activity.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ServicesActivity.this).create();
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
