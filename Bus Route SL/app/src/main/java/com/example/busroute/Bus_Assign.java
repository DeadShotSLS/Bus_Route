package com.example.busroute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Bus_Assign extends AppCompatActivity {

    EditText bus;
    EditText route;
    Button submit;

    FirebaseDatabase database;
    DatabaseReference Roref;

    private Route_Save route_save;

    String route_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus__assign);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        route_save =new Route_Save();

        bus =(EditText) findViewById(R.id.bus_no);
        route = (EditText) findViewById(R.id.text_route_no);
        submit =(Button)findViewById(R.id.bus_to_route);

        final String route_no = route.getText().toString().trim();



        Roref = FirebaseDatabase.getInstance().getReference().child("Route_Save");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query pendingTasks = Roref.orderByChild("Route_save").equalTo(route_no);

                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String bus_no = bus.getText().toString().trim();
                        String route_no = route.getText().toString().trim();

                        route_save.setBus_no(bus.getText().toString());
                        route_save.setRoute_no(route.getText().toString());
                        Roref.child(route_no).setValue(route_save);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Bus_Assign.this, "Route No Not found", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }
}
