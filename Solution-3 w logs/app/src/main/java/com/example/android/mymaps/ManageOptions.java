package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nikhil on 3/1/16.
 */
public class ManageOptions extends Activity {

    DatabaseHelper reg_helper_db = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageoptions);
    }

    public void onNewRegisteration(View view) {
        String vid = reg_helper_db.getVid(Global.username);
        if(vid == "null"){
            Intent intent = new Intent(this, NewRegisteration.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "This User has already registered a vehicle, Please go to Edit Option", Toast.LENGTH_SHORT).show();
        }

    }

    public void onEditRegisteration(View view) {
        Intent intent = new Intent(this, EditRegisteration.class);
        startActivity(intent);
    }


    public void onViewTickets(View view) {
        Intent intent = new Intent(this, ViewTickets.class);
        startActivity(intent);
    }

}
