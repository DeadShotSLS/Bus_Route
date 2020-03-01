package com.example.busroute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Driver_Assign extends AppCompatActivity {
    EditText bus_no;
    EditText licen;
    Button submit;

    FirebaseDatabase database;
    DatabaseReference Roref;
    public Bus_Driver bus_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver__assign);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bus_no = (EditText) findViewById(R.id.bus_no2);
        licen = (EditText) findViewById(R.id.licence);
        submit = (Button)findViewById(R.id.bus_to_d);

        bus_driver = new Bus_Driver();

        Roref = FirebaseDatabase.getInstance().getReference().child("Bus_Driver");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Roref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String bus_No = bus_no.getText().toString().trim();
                        String Licen = licen.getText().toString().trim();

                        bus_driver.setLicence_no(licen.getText().toString());
                        bus_driver.setBus_no(bus_no.getText().toString());
                        Roref.child(Licen).setValue(bus_driver);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Driver_Assign.this, "Route No Not found", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}
