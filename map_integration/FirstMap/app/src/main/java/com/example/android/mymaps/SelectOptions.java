package com.example.android.mymaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by sneha on 2/27/2016.
 */
public class SelectOptions extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_options);

    }

    public void onClickGoToMaps(View view) {
        //Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Map.class);
        String uname = getIntent().getStringExtra("Username");
        intent.putExtra("Username", uname); //example for passing variables between screens
        startActivity(intent);
    }

    public void selectFavParkingLot(View view) {
        //Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SelectParkingLot.class);
        startActivity(intent);
    }


}
