package com.example.busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class view extends AppCompatActivity {

    TextView busno;
    TextView user;
    TextView named;

    FirebaseDatabase database;
    DatabaseReference ref;

    FirebaseUser userid;

    private Driver_Save driver_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        busno = (TextView) findViewById(R.id.text_busno);
        user = (TextView) findViewById(R.id.text_uid);
        named = (TextView) findViewById(R.id.text_name);


        ref = FirebaseDatabase.getInstance().getReference("Driver_Save");


    }

}





