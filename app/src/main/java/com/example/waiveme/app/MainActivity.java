package com.example.waiveme.app;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    public void login_button(View view)
    {

        EditText email = (EditText)findViewById(R.id.emailEdit);
        EditText password = (EditText)findViewById(R.id.passEdit);
        Intent intent = new Intent(this,LoginActivity.class);

        intent.putExtra("tagEmail",email.getText().toString() );
        intent.putExtra("tagPass",password.getText().toString());
        startActivity(intent);

    }

    public void forgot_password(View view){
        Intent intent = new Intent(this,ForgotpassActivity.class);
        startActivity(intent);

    }

    public void register_link(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

}
