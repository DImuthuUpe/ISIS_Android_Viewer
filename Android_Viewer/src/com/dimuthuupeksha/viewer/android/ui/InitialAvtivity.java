package com.dimuthuupeksha.viewer.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.dimuthuupeksha.viewer.android.applib.ROClient;

/* Author - Dimuthu Upeksha*/

public class InitialAvtivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Host");
        setContentView(R.layout.activity_initial_avtivity);
    }
 
  
    
    public void submitClicked(View view){
        EditText host = (EditText)findViewById(R.id.host);
        ROClient.getInstance().setHost(host.getText().toString());
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

}
