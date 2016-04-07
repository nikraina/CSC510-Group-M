package com.example.android.mymaps3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nikhil on 3/2/16.
 */
public class NewRegisteration extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newregisteration);
    }

    public void onRegisterNewClick(View view){
        Toast.makeText(this, "Your Vehicle has been Registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManageOptions.class);
        startActivity(intent);
    }
}
