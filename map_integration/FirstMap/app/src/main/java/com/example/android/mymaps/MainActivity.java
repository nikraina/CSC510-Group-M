package com.example.android.mymaps;

import android.app.Dialog;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener
{

    GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final double
            CENT_LAT = 35.767694,
            CENT_LNG = -78.676168,
            //BEGIN:: Addition of parking spots
            CENT_PARK_LAT = 35.769348,
            CENT_PARK_LNG = -78.678691,
            DAN_PARK_LAT = 35.787470,
            DAN_PARK_LNG = -78.675503;
            //END:: Addition of parking spots
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

                mLocationClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

                mLocationClient.connect();

                //BEGIN:: Addition of parking spots
                LatLng cent_park_latLng = new LatLng(
                        CENT_PARK_LAT,
                        CENT_PARK_LNG
                );

                makeNewMarker(cent_park_latLng);

                LatLng dan_park_latLng = new LatLng(
                        DAN_PARK_LAT,
                        DAN_PARK_LNG
                );

                makeNewMarker(dan_park_latLng);

                //END:: Addition of parking spots

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
        mMap.setOnMarkerClickListener(this);

        //END :: Add Marker Listener

        return (mMap != null);
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
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mLocationClient, mListener
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, "This is Marker Listener", Toast.LENGTH_SHORT).show();
        // handle the clicks here and show the availability of parking spots
        return true;
    }

}
