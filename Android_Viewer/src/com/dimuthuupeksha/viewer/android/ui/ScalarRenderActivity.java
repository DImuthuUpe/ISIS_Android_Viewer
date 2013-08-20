package com.dimuthuupeksha.viewer.android.ui;

import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScalarRenderActivity extends Activity {
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        String data = (String) getIntent().getSerializableExtra("data");
        title = (String) getIntent().getSerializableExtra("title");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(title);
        ActionResult result = JsonRepr.fromString(ActionResult.class, data);

        TextView tv = new TextView(this);
        tv.setText(result.getResult().getValue().asText());
        layout.addView(tv);
        setContentView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
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
    
    

}
