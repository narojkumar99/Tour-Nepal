package com.brainants.tournepal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brainants.tournepal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceViewer extends AppCompatActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap map) {

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getIntent().getDoubleExtra("lng",0.0),
                getIntent().getDoubleExtra("lat",0.0));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title(getIntent().getStringExtra("title"))
                .position(sydney));

    }
}
