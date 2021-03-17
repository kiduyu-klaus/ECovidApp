package com.yearfour.mercyproject.e_covidapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        if (message.equals("circles")){
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-1.302473, 36.7447594))
                    .radius(50000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED));
            Circle circlei = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-4.0351857, 39.596051))
                    .radius(50000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED));
            Circle circle2 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-0.0749728, 34.6679565))
                    .radius(50000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED));
            Circle circle3 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-3.2292082, 40.0195858))
                    .radius(30000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED));
            Circle circle4 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-0.4399548, 36.9351487))
                    .radius(20000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED));

            LatLng latLng = new LatLng(-1.302473, 36.7447594); // whatever
            float zoom = (float) 5.0;// whatever
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        } else {


            LatLng isso1 = new LatLng(-0.4399548, 36.9351487);
            mMap.addMarker(new MarkerOptions().position(isso1).title("issolation 1"));
            LatLng isso2 = new LatLng(-0.438333, 36.9009681);
            mMap.addMarker(new MarkerOptions().position(isso2).title("issolation 2"));

            LatLng isso3 = new LatLng(-0.4161573,36.9320543);
            mMap.addMarker(new MarkerOptions().position(isso3).title("issolation 3"));

            LatLng isso4 = new LatLng(-0.4147625, 36.9467881);
            mMap.addMarker(new MarkerOptions().position(isso4).title("issolation 4"));

            LatLng isso5 = new LatLng(-0.4219184, 36.9404447);
            mMap.addMarker(new MarkerOptions().position(isso5).title("issolation 5"));

            LatLng latLng = new LatLng(-0.412928,36.94914); // whatever
            float zoom = (float) 13.0;// whatever
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }


}