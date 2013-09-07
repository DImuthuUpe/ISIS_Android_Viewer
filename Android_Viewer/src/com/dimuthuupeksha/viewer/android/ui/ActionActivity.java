package com.dimuthuupeksha.viewer.android.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainType;
import com.dimuthuupeksha.viewer.android.applib.representation.DomainTypeActionParam;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.uimodel.ViewMapper;

import org.codehaus.jackson.JsonNode;




public class ActionActivity extends SherlockActivity{
    private Action action;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String data = (String) getIntent().getSerializableExtra("detailLink");
        Link detailLink = JsonRepr.fromString(Link.class, data);
        title = (String) getIntent().getSerializableExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        System.out.println(detailLink.getHref());
        new ActionTask(ActionActivity.this).execute(detailLink);
    }

    private void render(Action action) {
        this.action = action;
        Link invokeLink = action.getLinkByRel("invoke");
        if (!invokeLink.getArguments().isEmpty()) {
            new DomainTypeFetcherTask(ActionActivity.this).execute(action);
        } else {
            new ActionResultMapper(action, title, this, getApplicationContext());
        }

    }

    private Map<String, View> viewMap = new HashMap<String, View>();

    private void renderArguments(List<DomainType> domainTypes) {
        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        List<Map<String, JsonNode>> parameters = action.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            TextView tv = new TextView(this);
            // tv.setTextSize(24);
            tv.setText(parameters.get(i).get("name").getValueAsText());
            layout.addView(tv);

            if (!parameters.get(i).containsKey("choices")) {
                View argsView = ViewMapper.convertToView(domainTypes.get(i).getCanonicalName(), this, null);
                if (argsView != null) {
                    viewMap.put(parameters.get(i).get("id").getValueAsText(), argsView);
                    layout.addView(argsView);
                }
            } else {
                Spinner spinner = new Spinner(this);
                JsonNode arrayNode = parameters.get(i).get("choices");
                String[] choices = new String[arrayNode.size()];
                int count = 0;
                for (JsonNode node : arrayNode) {
                    System.out.println(node.asText());
                    choices[count] = node.asText();
                    count++;
                }

                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choices);
                spinner.setAdapter(spinnerArrayAdapter);
                viewMap.put(parameters.get(i).get("id").getValueAsText(), spinner);
                layout.addView(spinner);

            }
        }
        Button submitButton = new Button(this);
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(action.getLinkByRel("invoke").getHref());
                Map<String, Object> args = new HashMap<String, Object>();

                for (Object id : viewMap.keySet().toArray()) {
                    View view = viewMap.get((String) id);
                    Object value = null;
                    if (view instanceof EditText) {
                        value = ((EditText) view).getText().toString();
                    } else if (view instanceof DatePicker) {
                        String year = ((DatePicker) view).getYear() + "";
                        String month = (((DatePicker) view).getMonth() + 1) + "";
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
                    args.put((String) id, value);
                }

                action.setArgs(args); 
                new ActionResultMapper(action, title, ActionActivity.this, getApplicationContext());
                // new TempTask().execute(args);
                // System.out.println(response);
            }
        });
        layout.addView(submitButton);
        ScrollView sv = new ScrollView(this);
        sv.addView(layout);
        setContentView(sv);
    }


    private class ActionTask extends AsyncTask<Link, Void, Action> {

        private final ProgressDialog pd;
        private final ActionActivity activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public ActionTask(ActionActivity activity) {
            pd = new ProgressDialog(activity);
            pd.setMessage("Loading action");
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected Action doInBackground(Link... links) {
            RORequest request = ROClient.getInstance().RORequestTo(links[0].getHref());
            try {
                Action action = ROClient.getInstance().executeT(Action.class, links[0].getMethod(), request, null);
                return action;
            } catch (ConnectionException e) {
                error = CONNECTION_ERROR;
                e.printStackTrace();
            } catch (InvalidCredentialException e) {
                error = INVALID_CREDENTIAL;
                e.printStackTrace();
            } catch (UnknownErrorException e) {
                error = UNKNOWN_ERROR;
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Action result) {
            if (result != null) {
                activity.render(result);
            }
            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(ActionActivity.this, LogInActivity.class);
                ActionActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ActionActivity.this).create();
                alertDialog.setTitle("Connection Error");
                alertDialog.setMessage("Please check your settings.");

                // Setting OK Button
                alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
            pd.hide();
        }

    }

    private class DomainTypeFetcherTask extends AsyncTask<Action, Void, List<DomainType>> {

        private ActionActivity activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public DomainTypeFetcherTask(ActionActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<DomainType> doInBackground(Action... params) {
            Action action = params[0];
            List<Map<String, JsonNode>> parameters = action.getParameters();
            List<DomainType> domainTypes = new ArrayList<DomainType>();
            try {
                for (Map<String, JsonNode> parameter : parameters) {
                    System.out.println(parameter.get("name"));
                    System.out.println(action.getLinkByRel("describedby").getHref() + "/params/" + parameter.get("name").getValueAsText());
                    RORequest request = ROClient.getInstance().RORequestTo(action.getLinkByRel("describedby").getHref() + "/params/" + parameter.get("name").getValueAsText());
                    DomainTypeActionParam des = ROClient.getInstance().executeT(DomainTypeActionParam.class, "GET", request, null);
                    request = ROClient.getInstance().RORequestTo(des.getLinkByRel("return-type").getHref());
                    DomainType domainType = ROClient.getInstance().executeT(DomainType.class, "GET", request, null);
                    System.out.println(domainType.getCanonicalName());
                    domainTypes.add(domainType);
                }
                return domainTypes;
            } catch (ConnectionException e) {
                error = CONNECTION_ERROR;
                e.printStackTrace();
            } catch (InvalidCredentialException e) {
                error = INVALID_CREDENTIAL;
                e.printStackTrace();
            } catch (UnknownErrorException e) {
                error = UNKNOWN_ERROR;
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<DomainType> result) {
            if (result != null) {
                renderArguments(result);
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(ActionActivity.this, LogInActivity.class);
                ActionActivity.this.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(ActionActivity.this).create();
                alertDialog.setTitle("Connection Error");
                alertDialog.setMessage("Please check your settings.");

                // Setting OK Button
                alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        }

    }

}
