package com.dimuthuupeksha.viewer.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dimuthuupeksha.viewer.android.uimodel.MenuActivity;

/* Author - Dimuthu Upeksha*/

public class ObjectRenderActivity extends SherlockFragmentActivity {

    /*
     * @Override protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState); String data =
     * (String)getIntent().getSerializableExtra("data"); final ActionResultItem
     * result = JsonRepr.fromString(ActionResultItem.class, data);
     * System.out.println(result.getLinkByRel("describedby").getHref()); String
     * title = result.getTitle(); ActionBar actionBar = getActionBar();
     * actionBar.setTitle(title); LinearLayout layout = new LinearLayout(this);
     * layout.setOrientation(android.widget.LinearLayout.VERTICAL); TextView
     * titleView = new TextView(this); titleView.setText(title);
     * layout.addView(titleView); setContentView(layout);
     * 
     * }
     */

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String data = (String) getIntent().getSerializableExtra("data");
        final ActionResultItem result = JsonRepr.fromString(ActionResultItem.class, data);
        System.out.println(result.getLinkByRel("describedby").getHref());
        String title = result.getTitle();
        //ActionBar actionBar = getActionBar();
        //actionBar.setTitle(title);
        
        TabHost tabHost = getTabHost();
        Intent intent = null;
        ObjectMapper mapper = new ObjectMapper();

        intent = new Intent().setClass(this, ObjectPropertyRenderActivity.class);

        intent.putExtra("data", data);
        TabHost.TabSpec spec = tabHost.newTabSpec("properties");
        spec.setIndicator("Properties");
        spec.setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ObjectActionRenderActivity.class);

        intent.putExtra("data", data);
        spec = tabHost.newTabSpec("actions");
        spec.setIndicator("Actions");
        spec.setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ObjectCollectionRenderActivity.class);

        intent.putExtra("data", data);
        spec = tabHost.newTabSpec("collections");
        spec.setIndicator("Collections");
        spec.setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        ((ObjectPropertyRenderActivity) getLocalActivityManager().getActivity("properties")).refresh();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                System.out.println("Tab id " + tabId);
                Activity activity = getLocalActivityManager().getActivity(tabId);
                if (tabId.equals("properties")) {
                    ((ObjectPropertyRenderActivity) activity).refresh();
                } else if (tabId.equals("actions")) {
                    ((ObjectActionRenderActivity) activity).refresh();
                } else if (tabId.equals("collections")) {
                    ((ObjectCollectionRenderActivity) activity).refresh();
                }

            }
        });

    }*/
    
    
    Tab tab;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String) getIntent().getSerializableExtra("data");
        // Create the Actionbar
        ActionBar actionBar = getSupportActionBar();
 
        // Hide Actionbar Icon
        actionBar.setDisplayShowHomeEnabled(false);
 
        // Hide Actionbar Title
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Create first Tab
        tab = actionBar.newTab().setTabListener(new ObjectPropertyRenderFragment());
        // Create your own custom icon
        tab.setText("Properties");
        actionBar.addTab(tab);
 
        // Create Second Tab
        tab = actionBar.newTab().setTabListener(new ObjectActionRenderFragment());
        // Set Tab Title
        tab.setText("Actions");
        actionBar.addTab(tab);
 
        // Create Third Tab
        tab = actionBar.newTab().setTabListener(new ObjectCollectionRenderFragment());
        // Set Tab Title
        tab.setText("Collection");
        actionBar.addTab(tab);
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

}
