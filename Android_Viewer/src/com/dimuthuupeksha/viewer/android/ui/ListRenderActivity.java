package com.dimuthuupeksha.viewer.android.ui;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;

/* Author - Dimuthu Upeksha*/

public class ListRenderActivity extends SherlockActivity {

    private String title;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        title = (String) getIntent().getSerializableExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        final ActionResult listRepr = JsonRepr.fromString(ActionResult.class, data);
        listView = new ListView(this);
        List<Link> values = listRepr.getResult().getValueAsList();

        String[] titles = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            titles[i] = values.get(i).getTitle();

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                System.out.println("list " + listRepr.getResult().getValueAsList().get(position).getHref());
                new ListItemResolveTask().execute(listRepr.getResult().getValueAsList().get(position));
            }
        });
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.addView(listView);
        setContentView(layout);
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

    private class ListItemResolveTask extends AsyncTask<Link, Void, ActionResultItem> {

        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        @Override
        protected ActionResultItem doInBackground(Link... params) {
            Link elementLink = params[0];
            ROClient client = ROClient.getInstance();
            RORequest request = client.RORequestTo(elementLink.getHref());
            try {
                ActionResultItem result = client.executeT(ActionResultItem.class, elementLink.getMethod(), request, null);
                return result;
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
        protected void onPostExecute(ActionResultItem result) {
            if (result != null) {
                String data = result.AsJson();
                Intent intent = new Intent(ListRenderActivity.this, ObjectRenderActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(ListRenderActivity.this, LogInActivity.class);
                ListRenderActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ListRenderActivity.this).create();
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
