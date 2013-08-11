package com.dimuthuupeksha.viewer.android.ui;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.Collection;
import com.dimuthuupeksha.viewer.android.applib.representation.CollectionValue;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;

import android.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CollectionRenderActivity extends ListActivity {
	private Collection collection;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String data = (String) getIntent().getSerializableExtra("data");
		Link link = JsonRepr.fromString(Link.class, data);
		String title = (String) getIntent().getSerializableExtra("title");
		ActionBar actionBar = getActionBar();
        actionBar.setTitle(title);
        System.out.println(link.getHref());
        new CollectionTask().execute(link);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		CollectionValue colVal =  collection.getValue().get(position);
		new CollectionItemResolveTask().execute(colVal);
	}
	
	private void render(Collection collection){
		this.collection=collection;
		String collectionTitles[] = new String[collection.getValue().size()];
		
		for(int i=0;i<collection.getValue().size();i++){
			collectionTitles[i]=(collection.getValue().get(i).getTitle());
		}
		ListView view = getListView();
        view.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.simple_list_item_1, collectionTitles));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.collection_render, menu);
		return true;
	}
	
	private class CollectionTask extends AsyncTask<Link, Void, Collection>{

		@Override
		protected Collection doInBackground(Link... params) {
			Link link = params[0];
			ROClient client = ROClient.getInstance(); 
			RORequest request = client.RORequestTo(link.getHref());
			Collection collection = client.executeT(Collection.class, link.getMethod(), request, null);
			return collection;
		}
		
		@Override
		protected void onPostExecute(Collection result) {
			render(result);
		}
		
	}
	
	private class CollectionItemResolveTask extends AsyncTask<CollectionValue, Void, Void>{

		@Override
		protected Void doInBackground(CollectionValue... params) {
			CollectionValue link = params[0];
			ROClient client= ROClient.getInstance();
			RORequest request = client.RORequestTo(link.getHref());
			ActionResultItem result = client.executeT(ActionResultItem.class, link.getMethod(), request, null);
			String data = result.AsJson();
            Intent intent = new Intent(CollectionRenderActivity.this,ObjectRenderActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
			return null;
		}
		
	}

}
