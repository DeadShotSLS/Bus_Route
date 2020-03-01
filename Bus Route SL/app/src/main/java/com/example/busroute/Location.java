package com.example.busroute;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String pno,pno2,lat,lagi;
    private Circle circle;
    CircleOptions co;
    private Marker marker;
    private boolean firstload = true;

    private Location_Save location_save;
    FirebaseDatabase database;
    DatabaseReference Lref;

    String bus_no;
    private LatLng origin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        Lref = FirebaseDatabase.getInstance().getReference("Location_Save");

        Intent intent = getIntent();
        String Bus_No = String.valueOf(intent.getStringExtra("Bus_No"));

        bus_no = Bus_No;

        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Lref.child(bus_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double longitude = dataSnapshot.child("longitude").getValue(Double.class);
                Double latitude = dataSnapshot.child("latitude").getValue(Double.class);

                origin = new LatLng(Double.parseDouble(String.valueOf(latitude)),Double.parseDouble(String.valueOf(longitude)));

                LatLngBounds bounds;
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                if(firstload) {

                    firstload = false;

                    marker = mMap.addMarker(new MarkerOptions().position(origin));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 16));

                }else{
                    circle.setCenter(origin);
                    marker.setPosition(origin);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(origin));
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
