package com.dimuthuupeksha.viewer.android.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;

public class ScalarRenderActivity extends SherlockActivity {
    private String title;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        String data = (String) getIntent().getSerializableExtra("data");
        title = (String) getIntent().getSerializableExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        ActionResult result = JsonRepr.fromString(ActionResult.class, data);

        TextView tv = new TextView(this);
        tv.setText(result.getResult().getValue().asText());
        layout.addView(tv);
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
    
    

}
