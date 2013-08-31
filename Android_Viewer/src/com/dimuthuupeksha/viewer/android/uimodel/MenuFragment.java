package com.dimuthuupeksha.viewer.android.uimodel;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.Services;
import com.dimuthuupeksha.viewer.android.ui.DomainServiceActivity;
import com.dimuthuupeksha.viewer.android.ui.HomeActivity;
import com.dimuthuupeksha.viewer.android.ui.ServicesActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends ListFragment {
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Link> links = Model.getInstance().getServices().getValue();
        List<String> items = new ArrayList<String>();
        for (Link link : links) {
            items.add(link.getTitle());
        }
        String[] values = items.toArray(new String[items.size()]);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values));
        getListView().setCacheColorHint(0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ((MenuActivity) getActivity()).getSlideoutHelper().close();
        Services services = Model.getInstance().getServices();
        Link link = services.getValue().get(position);
        Intent intent = new Intent(getActivity().getApplicationContext(),DomainServiceActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    } 

}
