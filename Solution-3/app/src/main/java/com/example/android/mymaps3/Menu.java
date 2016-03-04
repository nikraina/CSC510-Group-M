package com.example.android.mymaps3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nikhil on 3/1/16.
 */
public class Menu extends Activity{

    //String name;
    DatabaseHelper helper_db = new DatabaseHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String uname = getIntent().getStringExtra("Username"); //example for passing variables between screens
        //name = helper_db.getName(uname);
        Toast.makeText(this, "Welcome " + uname, Toast.LENGTH_SHORT).show();
    }

    public void onMapClick(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void onManageParkingClick(View view) {
        Intent intent = new Intent(this, ManageOptions.class);
        //intent.putExtra("Name", name);
        startActivity(intent);
    }

}
