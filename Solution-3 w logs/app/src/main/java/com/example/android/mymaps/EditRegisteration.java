package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nikhil on 3/2/16.
 */
public class EditRegisteration extends Activity {

    //static String number = "Bat Mobile";
    DatabaseHelper reg_helper_db = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editregisteration);
        String vid = reg_helper_db.getVid(Global.username);
        ((TextView) findViewById(R.id.TFvehiclenumber)).setHint(vid);
    }

    public void onRegisterEditClick(View view){
        EditText vid_raw = (EditText)findViewById(R.id.TFvehiclenumber);
        String vid = vid_raw.getText().toString();
        reg_helper_db.updateUserVehicle(vid);
        Toast.makeText(this, "Your registered vehicle has been updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManageOptions.class);
        startActivity(intent);
    }

}
