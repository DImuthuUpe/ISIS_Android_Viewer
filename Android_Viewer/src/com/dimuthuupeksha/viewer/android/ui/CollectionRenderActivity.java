package com.dimuthuupeksha.viewer.android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.Collection;
import com.dimuthuupeksha.viewer.android.applib.representation.CollectionValue;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

public class CollectionRenderActivity extends SherlockListActivity {
    private Collection collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        Link link = JsonRepr.fromString(Link.class, data);
        String title = (String) getIntent().getSerializableExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        System.out.println(link.getHref());
        new CollectionTask().execute(link);
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
            intent = new Intent(this, ServicesActivity.class);
            intent.putExtra("link", Model.getInstance().getHomePage().getLinkByRel("services"));
            startActivity(intent);
            break;
        case R.id.back:

        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        CollectionValue colVal = collection.getValue().get(position);
        new CollectionItemResolveTask().execute(colVal);
    }

    private void render(Collection collection) {
        this.collection = collection;
        String collectionTitles[] = new String[collection.getValue().size()];

        for (int i = 0; i < collection.getValue().size(); i++) {
            collectionTitles[i] = (collection.getValue().get(i).getTitle());
        }
        ListView view = getListView();
        view.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.simple_list_item, collectionTitles));
    }


    private class CollectionTask extends AsyncTask<Link, Void, Collection> {
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        @Override
        protected Collection doInBackground(Link... params) {
            Link link = params[0];
            ROClient client = ROClient.getInstance();
            RORequest request = client.RORequestTo(link.getHref());
            try {
                Collection collection = client.executeT(Collection.class, link.getMethod(), request, null);
                return collection;
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
        protected void onPostExecute(Collection result) {
            if (result != null) {
                render(result);
            }
            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(CollectionRenderActivity.this, LogInActivity.class);
                CollectionRenderActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(CollectionRenderActivity.this).create();
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

    private class CollectionItemResolveTask extends AsyncTask<CollectionValue, Void, Void> {
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        @Override
        protected Void doInBackground(CollectionValue... params) {
            CollectionValue link = params[0];
            ROClient client = ROClient.getInstance();
            RORequest request = client.RORequestTo(link.getHref());
            try {
                ActionResultItem result = client.executeT(ActionResultItem.class, link.getMethod(), request, null);
                String data = result.AsJson();
                Intent intent = new Intent(CollectionRenderActivity.this, ObjectRenderActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
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
        protected void onPostExecute(Void result) {
            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(CollectionRenderActivity.this, LogInActivity.class);
                CollectionRenderActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(CollectionRenderActivity.this).create();
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
