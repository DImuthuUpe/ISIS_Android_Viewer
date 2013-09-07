package com.dimuthuupeksha.viewer.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.dimuthuupeksha.viewer.android.applib.ROClient;

public class LogInActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(loginListener);
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            ROClient.getInstance().setCredential(username.getText().toString(), password.getText().toString());

            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    };

}
