package com.example.waiveme.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.*;
import android.content.*;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;
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


public class LoginActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private String status;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //run();
        new backgroundTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

   /* public void display() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("tagEmail");
        String password = intent.getStringExtra("tagPass");
        TextView getEmail = (TextView) findViewById(R.id.putEmail);
        getEmail.setText(email);
        TextView getPass = (TextView) findViewById(R.id.putPass);
        getPass.setText(password);

    }
}*/

   /* public void run() {
        String res = "res";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://harrenhal-rails-90471.use1.nitrousbox.com/sessions.json");
        Intent intent = getIntent();
        String email = intent.getStringExtra("tagEmail");
        String password = intent.getStringExtra("tagPass");
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>(1);
            list.add(new BasicNameValuePair("email", email));
            list.add(new BasicNameValuePair("password", password));
            post.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse response = client.execute(post);
            //HttpEntity entity = response.getEntity();
            //String result = entity.toString();
            //TextView newText = (TextView)findViewById(R.id.putEmail);
            //newText.setText(result);
        } catch (IOException e) {
            String error = "CatchError1";
            Log.e(error, e.toString());


        }

    }*/


   public class backgroundTask extends AsyncTask<String, String, String>{
     protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Validating...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String result=null;
            JSONObject json=null;
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://waive-me-staging.herokuapp.com/sessions.json");
            Intent intent = getIntent();
            String email = intent.getStringExtra("tagEmail");
            String password = intent.getStringExtra("tagPass");
            try {
                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("email", email));
                list.add(new BasicNameValuePair("password", password));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                System.out.println("-------------------------- results --------------------");
                System.out.println(result);
                try {
                     json = new JSONObject(result);
                    status=json.getString("status");
                    if(status.equalsIgnoreCase("success")){
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        Editor edit = sharedpreferences.edit();
                        edit.putString("userId",email);
                        edit.commit();
                    }
                    message=json.getString("message");
                }catch (JSONException e) {
                    Log.e("JSON PARSER", "JSON error");
                }



            } catch (IOException e) {
                String error ="CatchError1";
                Log.e(error,e.toString());

            }

            return result;
        }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           TextView disp = (TextView)findViewById(R.id.putEmail);
           SharedPreferences pref = getSharedPreferences("MyPref",
                   Context.MODE_PRIVATE);
           if (pref.contains("userId")){
               disp.setText(pref.getString("userId",null));
           }

           disp.setText(status);
           TextView temp = (TextView)findViewById(R.id.putMessage);
           temp.setText(message);

           pDialog.dismiss();


       }
   }

}
