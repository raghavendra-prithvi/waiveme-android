package com.example.waiveme.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RegisterActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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

    public void onRegister(View view){

        EditText email = (EditText)findViewById(R.id.regEditEmail);
        EditText password = (EditText)findViewById(R.id.regEditPass);
        EditText confPass= (EditText)findViewById(R.id.regEditPassword);
        Intent intent = new Intent(this,AfterRegisterAcitivity.class);
        intent.putExtra("regEmail",email.getText().toString());
        intent.putExtra("regPass",password.getText().toString());
        intent.putExtra("confPass",confPass.getText().toString());
        startActivity(intent);
    }


}

