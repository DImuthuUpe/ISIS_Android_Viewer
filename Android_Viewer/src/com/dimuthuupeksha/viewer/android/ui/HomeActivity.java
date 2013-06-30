package com.dimuthuupeksha.viewer.android.ui;

import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.UrlTemplate;
import com.dimuthuupeksha.viewer.android.applib.constants.Resource;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

}
