package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.Homepage;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.uimodel.Model;



public class HomeActivity extends SherlockListActivity {

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 

        //setContentView(R.layout.list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.home_page));        
        new HomeTask(HomeActivity.this).execute();
        
        
    }
    
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Homepage homePage = Model.getInstance().getHomePage();
        Object selected = this.getListAdapter().getItem(position);
        //System.out.println("a "+homePage.getLinks().get(position).getRel());
        if(selected.toString().equals("services")){
            Intent intent = new Intent(HomeActivity.this, ServicesActivity.class);
            intent.putExtra("link", homePage.getLinkByRel("services"));
            startActivity(intent);
            
        }
        
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
            intent = new Intent(this,ServicesActivity.class);
            intent.putExtra("link", Model.getInstance().getHomePage().getLinkByRel("services"));
            startActivity(intent);
            break;
        case R.id.back:
            
        }
        return true;
    }

    public void render(){
        List<Link> links = Model.getInstance().getHomePage().getLinks();
        List<String> items = new ArrayList<String>();
        for(Link link: links){
            items.add(link.getRel().replace("urn:org.restfulobjects:rels/", ""));
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    }
    
    private class HomeTask extends AsyncTask<Void, Void, Homepage> {
        private final ProgressDialog pd;
        private final HomeActivity activity;
        int error = 0;
		private static final int INVALID_CREDENTIAL = -1;
		private static final int CONNECTION_ERROR = -2;
		private static final int UNKNOWN_ERROR = -3;

        private HomeTask(HomeActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading HomePage");
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }


        @Override
        protected Homepage doInBackground(Void... voids) {
            Homepage homePage = Model.getInstance().getHomePage();
            
            if(homePage==null){
            	try{
                homePage = ROClient.getInstance().homePage();
            	}catch (ConnectionException e) {
    				error = CONNECTION_ERROR;
    				e.printStackTrace();
    			} catch (InvalidCredentialException e) {
    				error = INVALID_CREDENTIAL;
    				e.printStackTrace();
    			} catch (UnknownErrorException e) {
    				error = UNKNOWN_ERROR;
    				e.printStackTrace();
    			} catch (Exception e){
    				e.printStackTrace();
    			}
            }
            return homePage;
        }

        @Override
        protected void onPostExecute(Homepage homePage) {
            if(homePage!=null){
                Model.getInstance().setHomePage(homePage);
                activity.render();
            }
            
            if (error == INVALID_CREDENTIAL) {
				/* Username and password not valid show the Login */
				Intent intent = new Intent(HomeActivity.this,
						LogInActivity.class);
				HomeActivity.this.startActivity(intent);
			}

			if (error == CONNECTION_ERROR) {
				/** Show the error Dialog */
				AlertDialog alertDialog = new AlertDialog.Builder(
						HomeActivity.this).create();
				alertDialog.setTitle("Connection Error");
				alertDialog.setMessage("Please check your settings.");

				// Setting OK Button
				alertDialog.setButton("Close",
 new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
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
