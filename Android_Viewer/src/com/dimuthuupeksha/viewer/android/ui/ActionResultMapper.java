package com.dimuthuupeksha.viewer.android.ui;

import java.io.IOException;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.RORequest;
import com.dimuthuupeksha.viewer.android.applib.exceptions.ConnectionException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.InvalidCredentialException;
import com.dimuthuupeksha.viewer.android.applib.exceptions.UnknownErrorException;
import com.dimuthuupeksha.viewer.android.applib.representation.Action;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResult;
import com.dimuthuupeksha.viewer.android.applib.representation.ActionResultItem;
import com.dimuthuupeksha.viewer.android.applib.representation.JsonRepr;
import com.dimuthuupeksha.viewer.android.applib.representation.Link;
import com.dimuthuupeksha.viewer.android.uimodel.Model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActionResultMapper {

    private String title;
    private Action action;
    private Activity parentActivity;
    private Context context;
    public ActionResultMapper(Action action,String title,Activity parentActivity,Context context) {
        this.action=action;
        this.title = title;
        this.parentActivity =parentActivity;
        this.context=context;
        new InvokeTask(ActionResultMapper.this).execute(action);
        
    }
    
    private void renderList(final ActionResult listRepr) {

        String data = listRepr.AsJson();
        Intent intent = new Intent(parentActivity, ListRenderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data", data);
        intent.putExtra("title", title);

        context.startActivity(intent);

    }

    private void renderDObject(ActionResultItem result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String data = mapper.writeValueAsString(result);
            Intent intent = new Intent(parentActivity, ObjectRenderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("data", data);
            context.startActivity(intent);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void renderScalar(ActionResult result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String data = mapper.writeValueAsString(result);
            Intent intent = new Intent(parentActivity, ScalarRenderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("data", data);
            intent.putExtra("title", title);
            context.startActivity(intent);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class InvokeTask extends AsyncTask<Action, Void, ActionResult> {

        private ActionResultMapper activity;
        int error = 0;
        private static final int INVALID_CREDENTIAL = -1;
        private static final int CONNECTION_ERROR = -2;
        private static final int UNKNOWN_ERROR = -3;

        public InvokeTask(ActionResultMapper activity) {
            this.activity = activity;
        }

        @Override
        protected ActionResult doInBackground(Action... params) {
            RORequest request = ROClient.getInstance().RORequestTo(params[0].getLinkByRel("invoke").getHref());
            System.out.println("Invoke href " + params[0].getLinkByRel("invoke").getHref());
            System.out.println("Invoke method " + params[0].getLinkByRel("invoke").getMethod());
            try {
                ActionResult result = ROClient.getInstance().executeT(ActionResult.class, params[0].getLinkByRel("invoke").getMethod(), request, params[0].getArgs());
                return result;

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
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(ActionResult result) {
            if (result != null) {
                if (result.getResulttype().equals("list")) {
                    System.out.println("list Render");
                    renderList(result);
                } else if (result.getResulttype().equals("domainobject")) {
                    System.out.println("DObject render");
                    renderDObject(result.getResult());
                } else if (result.getResulttype().equals("scalarvalue")) {
                    System.out.println("scalar render");
                    renderScalar(result);
                }
            }

            if (error == INVALID_CREDENTIAL) {
                /* Username and password not valid show the Login */
                Intent intent = new Intent(parentActivity, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            if (error == CONNECTION_ERROR) {
                /** Show the error Dialog */
                AlertDialog alertDialog = new AlertDialog.Builder(parentActivity).create();
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
