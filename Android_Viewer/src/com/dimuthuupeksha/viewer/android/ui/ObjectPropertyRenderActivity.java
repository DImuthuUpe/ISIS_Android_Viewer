package com.dimuthuupeksha.viewer.android.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeProperty;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.applib.representation.ObjectMember;
import com.dimuthuupeksha.viewer.android.applib.representation.Property;
import com.dimuthuupeksha.viewer.android.uimodel.ViewMapper;

import org.apache.http.impl.client.RoutedRequest;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.R;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class ObjectPropertyRenderActivity extends Activity {

	Map<String, ObjectMember> propertyMember;
	String describedby;
	private boolean refreshed = false;
	private Map<String, View> viewMap;
	private ActionResultItem actionResultItem;
	private Button updateButton;
	private LinearLayout layout;
	private Button okButton;
	private Button cancelButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String data = (String) getIntent().getSerializableExtra("data");
		actionResultItem = JsonRepr.fromString(ActionResultItem.class, data);
		viewMap = new HashMap<String, View>();
		describedby = actionResultItem.getLinkByRel("describedby").getHref();
		propertyMember = new HashMap<String, ObjectMember>();

		Iterator<String> it = actionResultItem.getMembers().keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String memberType = actionResultItem.getMembers().get(key)
					.getMemberType();
			if (memberType.equals("property")) {
				propertyMember.put(key, actionResultItem.getMembers().get(key));
				// System.out.println(key);
				// System.out.println(actionResultItem.getMembers().get(key));
				// System.out.println(actionResultItem.getMembers().get(key).getValue());
			}
		}

		// restaurants = propertyMember.keySet().toArray(restaurants);
		// ListView view = getListView();
		// view.setAdapter(new ArrayAdapter<String>(getBaseContext(),
		// R.layout.simple_list_item_1, restaurants));
		//

	}

	public void refresh() {
		if (!refreshed) {
			new ResolveReferenceTask(ObjectPropertyRenderActivity.this)
					.execute();
			refreshed = true;
		}
	}

	private void render(Map<String, Map<String, Object>> referenceMap) { // <key,
																			// <(domaintype,returntype),object>>
		layout = new LinearLayout(this);
		layout.setOrientation(android.widget.LinearLayout.VERTICAL);
		Iterator<String> it = referenceMap.keySet().iterator();
		while (it.hasNext()) {
			String id = it.next();
			DomainTypeProperty dproperty = (DomainTypeProperty) referenceMap
					.get(id).get("dproperty");
			DomainType dtype = (DomainType) referenceMap.get(id).get("dtype");
			Property property = (Property) referenceMap.get(id).get("property");
			TextView tv = new TextView(this);
			tv.setText(dproperty.getExtensions().get("friendlyName"));
			// System.out.println("1");
			// System.out.println(id);
			// System.out.println(propertyMember.get(id).getValue());
			// System.out.println("1.5");
			String dataType = property.getFormat();
			if (dataType == null) {
				dataType = dtype.getCanonicalName();
			}
			boolean isChoice = false;
			if (property.getChoices() != null) {
				isChoice = true;
			}
			if (isChoice) {
				Spinner spinner = new Spinner(this);

				String[] choices = new String[property.getChoices().size()];
				String selectedVal = property.getValue();
				choices = property.getChoices().toArray(choices);
				int selection = 0;
				for (int i = 0; i < choices.length; i++) {
					if (choices[i].equals(selectedVal)) {
						selection = i;
						break;
					}
				}

				ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
						android.R.layout.simple_spinner_dropdown_item, choices);
				spinner.setAdapter(spinnerArrayAdapter);
				spinner.setSelection(selection);
				layout.addView(tv);
				spinner.setEnabled(false);
				viewMap.put(id, spinner);
				layout.addView(spinner);
			} else if (propertyMember.get(id).getValue() != null) {// check
																	// whether
																	// value is
																	// null.
				System.out.println("Value "
						+ propertyMember.get(id).getValue().getValueAsText());
				View view = ViewMapper.convertToView(dataType, this,
						propertyMember.get(id).getValue().getValueAsText());
				// System.out.println("4");
				layout.addView(tv);
				if (view != null) {
					view.setEnabled(false);
					viewMap.put(id, view);
					layout.addView(view);
				}
			}// good start point to track domain objects as property
		}
		Link updateLink = actionResultItem.getLinkByRel("update");
		Map<String, Map<String, JsonNode>> arguments = updateLink
				.getArguments();
		if (arguments != null) {
			updateButton = new Button(this);
			updateButton.setText("Update");
			final LayoutParams lparams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			updateButton.setLayoutParams(lparams);
			updateButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onUpdateButtonClick();
				}
			});
			layout.addView(updateButton);

		}
		ScrollView scView = new ScrollView(this);
		scView.addView(layout);
		setContentView(scView);
	}

	private void onUpdateButtonClick() {
		Link updateLink = actionResultItem.getLinkByRel("update");

		Map<String, Map<String, JsonNode>> arguments = updateLink
				.getArguments();
		Iterator<String> keys = arguments.keySet().iterator();
		Map<String, Object> normalizedArgs = new HashMap<String, Object>();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println(key);
			if (arguments.get(key).get("disabledReason") == null) {
				if (viewMap.get(key) != null) {
					viewMap.get(key).setEnabled(true);
				}
				normalizedArgs.put(key, arguments.get(key));
			}
		}
		final LayoutParams lparams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		updateButton.setVisibility(View.GONE);
		okButton = new Button(this);
		okButton.setText("OK");
		okButton.setLayoutParams(lparams);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onOKButtonClicked();
			}
		});
		cancelButton = new Button(this);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutParams(lparams);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCancelButtonClicked();
			}
		});
		layout.addView(okButton);
		layout.addView(cancelButton);
		// new UpdateTask().execute(normalizedArgs);
	}

	private void onCancelButtonClicked() {
		okButton.setVisibility(View.GONE);
		cancelButton.setVisibility(View.GONE);
		updateButton.setVisibility(View.VISIBLE);
		Link updateLink = actionResultItem.getLinkByRel("update");
		Map<String, Map<String, JsonNode>> arguments = updateLink
				.getArguments();
		Iterator<String> keys = arguments.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println(key);
			if (arguments.get(key).get("disabledReason") == null) {
				if (viewMap.get(key) != null) {
					viewMap.get(key).setEnabled(false);
				}
			}
		}
	}

	private void onOKButtonClicked() {
		okButton.setVisibility(View.GONE);
		cancelButton.setVisibility(View.GONE);
		updateButton.setVisibility(View.VISIBLE);
		Link updateLink = actionResultItem.getLinkByRel("update");

		Map<String, Map<String, JsonNode>> arguments = updateLink
				.getArguments();
		Iterator<String> keys = arguments.keySet().iterator();
		Map<String, Object> normalizedArgs = new HashMap<String, Object>();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println(key);
			if (arguments.get(key).get("disabledReason") == null) {
				if (viewMap.get(key) != null) {
					
					View view = viewMap.get(key);
					String value = null;
					if (view instanceof EditText) {
						value = ((EditText) view).getText().toString();
					} else if (view instanceof DatePicker) {
						String year = ((DatePicker) view).getYear() + "";
						String month = (((DatePicker) view).getMonth()+1) + "";
						String day = ((DatePicker) view).getDayOfMonth() + "";

						if (month.length() == 1) {
							month = "0" + month;
						}
						if (day.length() == 1) {
							day = "0" + day;
						}

						value = year + month + day;
					} else if (view instanceof Spinner) {
						value = ((Spinner) view).getSelectedItem().toString();
					}
					
					ObjectMapper mapper = new ObjectMapper();
					try {
						String data = mapper.writeValueAsString(value);
						System.out.println(data);
						JsonNode node = JsonRepr.fromString(JsonNode.class, data);
						arguments.get(key).put("value",node);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				normalizedArgs.put(key, arguments.get(key));
			}
		}
		new UpdateTask().execute(normalizedArgs);
	}

	private class ResolveReferenceTask extends
			AsyncTask<Void, Void, Map<String, Map<String, Object>>> {

		ProgressDialog pd;

		public ResolveReferenceTask(ObjectPropertyRenderActivity activity) {
			pd = new ProgressDialog(activity);
			pd.setMessage("Loading");
		}

		@Override
		protected void onPreExecute() {
			pd.show();
		}

		@Override
		protected Map<String, Map<String, Object>> doInBackground(
				Void... params) {
			ROClient client = ROClient.getInstance();
			Iterator<String> it = propertyMember.keySet().iterator();
			Map<String, Map<String, Object>> referenceMap = new HashMap<String, Map<String, Object>>();
			while (it.hasNext()) {
				String key = it.next();
				String url = describedby + "/properties/" + key;
				RORequest request = client.RORequestTo(url);
				DomainTypeProperty dproperty = client.executeT(
						DomainTypeProperty.class, "GET", request, null);
				url = dproperty.getLinkByRel("return-type").getHref();
				System.out.println(url);
				request = client.RORequestTo(url);

				DomainType domainType = client.executeT(DomainType.class,
						"GET", request, null);

				url = propertyMember.get(key).getLinkByRel("details").getHref();
				request = client.RORequestTo(url);

				Property property = client.executeT(Property.class, "GET",
						request, null);

				Map<String, Object> content = new HashMap<String, Object>();
				content.put("dproperty", dproperty);
				content.put("property", property);
				content.put("dtype", domainType);
				referenceMap.put(key, content);
				// System.out.println(property.getExtensions().get("friendlyName"));
				// System.out.println(domainType.getCanonicalName());
			}
			return referenceMap;
		}

		@Override
		protected void onPostExecute(Map<String, Map<String, Object>> result) {
			render(result);
			pd.hide();
		}

	}

	private class UpdateTask extends AsyncTask<Map<String, Object>, Void, Void> {

		@Override
		protected Void doInBackground(Map<String, Object>... arg0) {
			Map<String, Object> args = arg0[0];
			Link updateLink = actionResultItem.getLinkByRel("update");
			ROClient client = ROClient.getInstance();
			RORequest request = client.RORequestTo(updateLink.getHref());
			ActionResultItem actionResultItem = client.executeT(
					ActionResultItem.class, updateLink.getMethod(), request,
					args);
			ObjectMapper mapper = new ObjectMapper();
			try {
				String data = mapper.writeValueAsString(actionResultItem);
				Intent intent = new Intent(ObjectPropertyRenderActivity.this,
						ObjectRenderActivity.class);
				intent.putExtra("data", data);
				startActivity(intent);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
