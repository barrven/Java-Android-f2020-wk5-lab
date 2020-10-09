package ca.gbc.comp3074.java_android_f2020_wk5_lab;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    EditText locationNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationNameText = findViewById(R.id.location_text);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = locationNameText.getText().toString();
                if(!name.isEmpty()){
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    //translate locations into addresses list
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(name, 1);
                        if(addresses != null && !addresses.isEmpty()){
                            Address a = addresses.get(0);
                            LatLng pos = new LatLng(a.getLatitude(), a.getLongitude());
                            if(mMap != null){
                                mMap.addMarker(new MarkerOptions().position(pos).title(name));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
                            }

                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //we have permission from the user to get location
            getLocation();
        }
        else{
            //asks the system to ask user to grant location permission to this app
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1
                && permissions.length == 1
                && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            //then request can be processed cause permissions are valid. have permission to access the location
            getLocation();
        }
    }

    void getLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //we have permission from the user to get location
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null){
                    LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                    if (mMap != null){
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));
                    }
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100, 1, this);
            }
            else{
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //change the map marker to Casa Loma Campus
        LatLng clc = new LatLng(43.675987,-79.410797);
        mMap.addMarker(new MarkerOptions().position(clc).title("Marker in GBC Casa Loma"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clc, 17)); //change method to zoom and pass zoom level 17
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
