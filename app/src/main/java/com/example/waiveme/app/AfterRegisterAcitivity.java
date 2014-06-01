package com.example.waiveme.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AfterRegisterAcitivity extends ActionBarActivity {


    private ProgressDialog pDialog;
    String status;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register_acitivity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new registerBackgroundTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after_register_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class registerBackgroundTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AfterRegisterAcitivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            String result = null;
            JSONObject json = null;
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://waive-me-staging.herokuapp.com/sessions.json");
            Intent intent = getIntent();
            String email = intent.getStringExtra("regEmail");
            String password = intent.getStringExtra("regPass");
            String confPass = intent.getStringExtra("confPass");
            try {
                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("email", email));
                list.add(new BasicNameValuePair("password", password));
                list.add(new BasicNameValuePair("confPassword", confPass));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                try {
                    json = new JSONObject(result);
                    status = json.getString("status");
                    message = json.getString("message");


                } catch (JSONException e) {
                    Log.e("JSON PARSER", "JSON error");
                }


            } catch (IOException e) {
                String error = "CatchError1";
                Log.e(error, e.toString());

            }

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView disp = (TextView) findViewById(R.id.noStatus);
            disp.setText(status);
            TextView temp = (TextView) findViewById(R.id.noMessage);
            temp.setText(message);

            pDialog.dismiss();


        }
    }

}
