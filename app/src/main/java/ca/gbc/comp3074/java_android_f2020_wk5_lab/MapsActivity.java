package ca.gbc.comp3074.java_android_f2020_wk5_lab;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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
}
