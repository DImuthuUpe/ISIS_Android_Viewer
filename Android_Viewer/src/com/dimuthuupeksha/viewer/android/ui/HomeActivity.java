package com.dimuthuupeksha.viewer.android.ui;

import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.UrlTemplate;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.uimodel.DomainServiceModel;
import com.dimuthuupeksha.viewer.android.uimodel.HomePageModel;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class HomeActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.home);
        new HomeTask(HomeActivity.this).execute();        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
    public void render(HomePageModel homePage){
        for(DomainServiceModel model:homePage.getServices()){
            System.out.println(model.getTitle());            
        }
    }
    
    private class HomeTask extends AsyncTask<Void, Void, HomePageModel> {
        private final ProgressDialog pd;
        private final HomeActivity activity;

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
        protected HomePageModel doInBackground(Void... voids) {
            return HomePageModel.getByDefault();
        }

        @Override
        protected void onPostExecute(HomePageModel homePage) {
            activity.render( homePage );
            pd.hide();
        }
    }


}
