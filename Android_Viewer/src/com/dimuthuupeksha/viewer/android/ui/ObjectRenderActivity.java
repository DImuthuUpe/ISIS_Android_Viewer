package com.dimuthuupeksha.viewer.android.ui;

import java.io.IOException;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ObjectRenderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = (String)getIntent().getSerializableExtra("data");           
            final ActionResultItem result = JsonRepr.fromString(ActionResultItem.class, data);
            System.out.println(result.getLinkByRel("describedby").getHref());
            String title = result.getTitle();
            ActionBar actionBar = getActionBar();
            actionBar.setTitle(title);
            LinearLayout layout = new LinearLayout(this);        
            layout.setOrientation(android.widget.LinearLayout.VERTICAL);
            TextView titleView = new TextView(this);
            titleView.setText(title);
            layout.addView(titleView);
            setContentView(layout);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.object_render, menu);
        return true;
    }

}
