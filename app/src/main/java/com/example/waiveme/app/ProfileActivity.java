package com.example.waiveme.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private String status;
    private String message;
    public static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        new backgroundTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    public class backgroundTask extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String result=null;
            JSONObject json=null;
            HttpClient client = new DefaultHttpClient();
            SharedPreferences pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            HttpPost post = new HttpPost("http://waive-me-staging.herokuapp.com/profile_data.json");
            Intent intent = getIntent();
            try {
                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("email", pref.getString("userId",null)));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                try {
                    json = new JSONObject(result);
                    status=json.getString("status");
                    String firstName = json.getJSONObject("data").getString("first_name");
                    String lastName = json.getJSONObject("data").getString("last_name");
                    String phone = json.getJSONObject("data").getString("phone");
                    String id = json.getJSONObject("data").getString("id");
                    String zip_code = json.getJSONObject("data").getString("zip_code");
                    String state = json.getJSONObject("data").getString("State");
                    String email = json.getJSONObject("data").getString("email");
                    String address = json.getJSONObject("data").getString("Adress");
                    String city = json.getJSONObject("data").getString("City");

                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedpreferences.edit();
                        edit.putString("email",email);
                        edit.putString("firstName",firstName);
                        edit.putString("lastName",lastName);
                        edit.putString("phone",phone);
                        edit.putString("id",id);
                        edit.putString("zip_code",zip_code);
                        edit.putString("state",state);
                        edit.putString("address",address);
                        edit.putString("city",city);
                        edit.commit();

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
            SharedPreferences pref = getSharedPreferences(MyPREFERENCES,
                    Context.MODE_PRIVATE);
            TextView firstName = (TextView)findViewById(R.id.profileFirstNameData);
            TextView lastName = (TextView)findViewById(R.id.profileLastNameData);
            TextView city = (TextView)findViewById(R.id.profileCityData);
            TextView state = (TextView)findViewById(R.id.profileStateData);
            TextView zipcode = (TextView)findViewById(R.id.profileZipCodeData);
            TextView address = (TextView)findViewById(R.id.profileAddressData);
            TextView phone = (TextView)findViewById(R.id.profilePhoneData);
            firstName.setText(pref.getString("firstName",null));
            lastName.setText(pref.getString("lastName",null));
            state.setText(pref.getString("state",null));
            city.setText(pref.getString("city",null));
            zipcode.setText(pref.getString("zip_code",null));
            address.setText(pref.getString("address",null));
            phone.setText(pref.getString("phone",null));
//            TextView temp = (TextView)findViewById(R.id.putMessage);
//            temp.setText(message);
            pDialog.dismiss();
        }
    }
}
