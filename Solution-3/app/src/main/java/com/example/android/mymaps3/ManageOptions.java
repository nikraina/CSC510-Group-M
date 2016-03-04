package com.example.android.mymaps3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by nikhil on 3/1/16.
 */
public class ManageOptions extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageoptions);
    }

    public void onNewRegisteration(View view) {
        Intent intent = new Intent(this, NewRegisteration.class);
        //String name = getIntent().getStringExtra("Name");
        //intent.putExtra("Name", name);
        startActivity(intent);
    }

    public void onEditRegisteration(View view) {
        Intent intent = new Intent(this, EditRegisteration.class);
        //String name = getIntent().getStringExtra("Name");
        //intent.putExtra("Name", name);
        startActivity(intent);
    }


    public void onViewTickets(View view) {
        Intent intent = new Intent(this, ViewTickets.class);
        //String name = getIntent().getStringExtra("Name");
        //intent.putExtra("Name", name);
        startActivity(intent);
    }

}
