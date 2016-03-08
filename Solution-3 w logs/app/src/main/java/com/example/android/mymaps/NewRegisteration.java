package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nikhil on 3/2/16.
 */
public class NewRegisteration extends Activity{

    DatabaseHelper reg_helper_db = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newregisteration);
    }

    public void onRegisterNewClick(View view){

        EditText vid_raw = (EditText)findViewById(R.id.TFvehiclenumber);

        String vid = vid_raw.getText().toString();

        RegisteredUserVehicle userVehicle = new RegisteredUserVehicle();
        userVehicle.setVid(vid);
        userVehicle.setUname(Global.username);
        reg_helper_db.insertUserVehicle(userVehicle);


        Toast.makeText(this, "Your Vehicle has been Registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManageOptions.class);
        startActivity(intent);
    }
}
