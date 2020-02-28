package com.example.busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.CollationElementIterator;
import java.util.Map;

public class Bus_Search_result extends AppCompatActivity {

    TextView bus_available;
    TextView seat_available;
    TextView busNo;
    TextView status;
    //Map map;

    FirebaseDatabase database;
    DatabaseReference ref,Busref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus__search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Bus Search Results");

        Intent intent = getIntent();
        String Bus_No = String.valueOf(intent.getStringExtra("Bus_No"));

        busNo =(TextView) findViewById(R.id.text_bus_no);
        busNo.setText("" + Bus_No );

        bus_available = (TextView)findViewById(R.id.text_bus);
        seat_available = (TextView)findViewById(R.id.text_seat);
        status = (TextView)findViewById(R.id.text_status);
        //map = (Map)findViewById(R.id.map_bus);

        database = FirebaseDatabase.getInstance();
        Busref = database.getReference().child("Bus_Save");

        String bus_no = Bus_No;

        Toast.makeText(Bus_Search_result.this, "bus no :"+bus_no, Toast.LENGTH_SHORT).show();

        Busref.child(bus_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean b_available = dataSnapshot.child("bus_available").getValue(Boolean.class);
                Boolean s_available = dataSnapshot.child("seat_available").getValue(Boolean.class);
                if(dataSnapshot.exists()) {

                    if(b_available == true){
                        bus_available.setText("Bus is Available");
                        if(s_available == true){
                            seat_available.setText("Seat is Available");
                        }else {
                            seat_available.setText("Seat is currently unavailable");
                        }
                    }else {
                        bus_available.setText("Bus is currently unavailable");
                    }
                }else {
                    bus_available.setText("Bus not Fount");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Busref.child(bus_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  stat = dataSnapshot.child("status_bus").getValue(String.class);
                if(dataSnapshot.exists()) {
                    status.setText(stat);
                }else {

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



    }
}
