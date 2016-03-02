package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nikhil on 3/2/16.
 */
public class EditRegisteration extends Activity {

    static String number = "Bat Mobile";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editregisteration);
    }

    public void onRegisterEditClick(View view){
        Toast.makeText(this, "Your registered vehicle has been updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManageOptions.class);
        startActivity(intent);
    }

}
