/**
 * Created by nikhil on 2/26/16.
 */
package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Signup extends Activity{

    DatabaseHelper helper_db = new DatabaseHelper(this);
    //test

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignupClick(View view){
        EditText name_raw = (EditText)findViewById(R.id.TFname);
        EditText uname_raw = (EditText)findViewById(R.id.TFuname);
        EditText pass1_raw = (EditText)findViewById(R.id.TFpassword1);
        EditText pass2_raw = (EditText)findViewById(R.id.TFpassword2);

        String name = name_raw.getText().toString();
        String uname = uname_raw.getText().toString();
        String pass1 = pass1_raw.getText().toString();
        String pass2 = pass2_raw.getText().toString();

        if(!pass1.equals(pass2)){
            Toast.makeText(this, "The given passwords don't match!!", Toast.LENGTH_SHORT).show();
        }
        else{
            RegisteredUsers user = new RegisteredUsers();
            user.setName(name);
            user.setUname(uname);
            user.setPassword(pass1);

            helper_db.insertUser(user);
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

}
