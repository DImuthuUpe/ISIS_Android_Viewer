package com.dimuthuupeksha.viewer.android.ui;

import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class ObjectCollectionRenderActivity extends ListActivity {
 
    private static final String[] coffeeShops = new String[] {
        "Barista", "Cafe Coffee Day"};
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        ListView view = getListView();
        view.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.simple_list_item_1, coffeeShops));
         
    }
     
}