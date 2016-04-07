package com.example.android.mymaps1;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener
{

    UserLocationLog helper_db = new UserLocationLog(this);
    DatabaseHelper help_db = new DatabaseHelper(this);

    GoogleMap mMap;
    List<MarkerOptions> options;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final double
            CENT_LAT = 35.767694,
            CENT_LNG = -78.676168;

    private GoogleApiClient mLocationClient;
    private LocationListener mListener;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (servicesOK()) {
            setContentView(R.layout.activity_map);

            if (initMap()) {
                gotoLocation(CENT_LAT, CENT_LNG, 14);
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

                mLocationClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

                mLocationClient.connect();

                showParkingMarkers();

//                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Map not connected!", Toast.LENGTH_SHORT).show();
            }

        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void makeNewMarker(LatLng latlngobj){
        MarkerOptions options = new MarkerOptions()
                .position(latlngobj);
        marker = mMap.addMarker(options);
    }

    //BEGIN :: Fix for unfortunately stopped working
    protected void onResume() {
        super.onResume();
        initMap();
    }

    //END :: Fix for unfortunately stopped working

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean servicesOK() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }

        //BEGIN :: Add Marker Listener
        //mMap.setOnMarkerClickListener(this);

        //END :: Add Marker Listener

        return (mMap != null);
    }
    private void showParkingMarkers(){
        options = new ArrayList<MarkerOptions>();
        Cursor allLots = help_db.getLots();

        if(allLots.moveToFirst()){
            do {
                String temp_name;
                Double temp_lat, temp_lon;
                Integer temp_total, temp_avl;

                temp_name = allLots.getString(0);
                temp_lat = allLots.getDouble(1);
                temp_lon = allLots.getDouble(2);
                temp_total = allLots.getInt(3);
                temp_avl = allLots.getInt(4);

                MarkerOptions temp = new MarkerOptions()
                        .position(new LatLng(temp_lat, temp_lon))
                        .title(temp_name)
                        .snippet("Status : " + temp_avl.toString() + " lots");
                options.add(temp);
            }
            while(allLots.moveToNext());
        }

        if(!(options.isEmpty())){
            Marker[] markers = new Marker[options.size()];
            for (int i=0; i<options.size(); i++){
                markers[i] = mMap.addMarker(options.get(i));
                markers[i].showInfoWindow();
            }
        }
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, zoom);
        mMap.moveCamera(update);

        if(marker!=null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(latlng);
        marker = mMap.addMarker(options);
    }

    public void showCurrentLocation(MenuItem item) {
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mLocationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Couldn't connect!", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude()
            );
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
                    latLng, 15
            );
            mMap.animateCamera(update);

            Double temp_lat = currentLocation.getLatitude();
            Double temp_long = currentLocation.getLongitude();
            String temp_email = "unavailable";

            UserLocLog userLoc = new UserLocLog();
            userLoc.setEmail(temp_email);
            userLoc.setLatitude(temp_lat);
            userLoc.setLongitude(temp_long);
            helper_db.insertUpdateLog(userLoc);

            //if(marker!=null) {
                //marker.remove();      //Commented out to avoid deletion of last shown markers.
            //}
            //Commented to put a blue circle to show current location
            //MarkerOptions options = new MarkerOptions()
                    //.position(latLng);
            //marker = mMap.addMarker(options);
            //commented till here
            //BEGIN::show blue red circle as current position
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(latLng)//new LatLng(-33.87365, 151.20689))
                    .radius(50)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));
            //END::show blue red circle as current position
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();

        /*mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MainActivity.this,
                        "Location Changed: " + location.getLatitude() + ", " +
                                location.getLongitude(), Toast.LENGTH_SHORT).show();
                gotoLocation(location.getLatitude(), location.getLongitude(), 15);
            }
        };

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mLocationClient, request, mListener
        );*/
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    //this is overridden to pause the continuous location listener when the app is not being used
    protected void onPause() {
        super.onPause();
        //LocationServices.FusedLocationApi.removeLocationUpdates(
        //        mLocationClient, mListener
        //);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, "This is Marker Listener", Toast.LENGTH_SHORT).show();
        // handle the clicks here and show the availability of parking spots
        return true;
    }

}
