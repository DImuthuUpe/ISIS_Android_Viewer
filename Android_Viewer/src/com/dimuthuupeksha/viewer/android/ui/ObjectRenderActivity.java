package com.dimuthuupeksha.viewer.android.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;
import com.dimuthuupeksha.viewer.android.applib.representation.Service;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ObjectRenderActivity extends TabActivity {

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String data = (String) getIntent().getSerializableExtra("data");
        final ActionResultItem result = JsonRepr.fromString(ActionResultItem.class, data);
        System.out.println(result.getLinkByRel("describedby").getHref());
        String title = result.getTitle();
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(title);

        Map<String, ObjectMember> propertyMembers = new HashMap<String, ObjectMember>();
        Map<String, ObjectMember> actionMembers = new HashMap<String, ObjectMember>();
        Map<String, ObjectMember> collectionMembers = new HashMap<String, ObjectMember>();

        Iterator<String> it = result.getMembers().keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String memberType = result.getMembers().get(key).getMemberType();
            if (memberType.equals("property")) {
                propertyMembers.put(key, result.getMembers().get(key));
            } else if (memberType.equals("action")) {
                actionMembers.put(key, result.getMembers().get(key));
            } else if (memberType.equals("collection")) {
                collectionMembers.put(key, result.getMembers().get(key));
            }
        }

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
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

}
