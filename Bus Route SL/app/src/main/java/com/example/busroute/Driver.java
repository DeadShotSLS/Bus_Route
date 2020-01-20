package com.example.busroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;

public class Driver extends AppCompatActivity {

    String prevStarted = "prevStarted";

    private FirebaseAuth auth;

    TextView bus_NO;
    Boolean switchState_bus;
    Boolean switchState_seat;
    String location;
    String bus_no;
    Button submit;
    EditText status_bus;

    FirebaseDatabase database;
    DatabaseReference ref,Busref;

    FirebaseUser userid;

    private Driver_Save driver_save;
    private Bus_Save bus_save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        bus_save = new Bus_Save();

        database =FirebaseDatabase.getInstance();
        Busref = database.getReference().child("Bus_Save");

        auth = FirebaseAuth.getInstance();

        submit = (Button) findViewById(R.id.button_status_submit);
        status_bus = (EditText)findViewById(R.id.status_bus);
        bus_NO = (TextView) findViewById(R.id.bus_hello);

        Switch bus_available = (Switch) findViewById(R.id.switch_bus_available);
        Switch seat_available = (Switch) findViewById(R.id.switch_seat_available);

        Boolean switchState_bus = bus_available.isChecked();
        Boolean switchState_seat = seat_available.isChecked();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

       // String currentuser = FirebaseAuth.getInstance();
        FirebaseAuth mAuth ;
        FirebaseDatabase mFirebaseDb;
        FirebaseAuth.AuthStateListener mAuthLit;
        DatabaseReference myref;
        String userId;

        ref = FirebaseDatabase.getInstance().getReference("Driver_Save");

/*        ref.child(currentuser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bus_no = dataSnapshot.child("bus_no").getValue(String.class);
                bus_NO.setText(bus_no);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private void submit(){
        String Status = status_bus.getText().toString().trim();

        if (TextUtils.isEmpty(Status)) {
            Toast.makeText(getApplicationContext(), "Enter Your Status!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            bus_save.setStatus_bus(status_bus.getText().toString());
            Busref.child(bus_no).setValue(bus_save);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_d,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.nav_help_d)
        {
            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Help_d.class));

        }
        else if(id== R.id.nav_my_bus)
        {
            Toast.makeText(this, "My Bus", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, My_Bus.class));


        }
        else if(id== R.id.nav_about_us)
        {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, About_Us.class));

        }else if(id== R.id.nav_log_out)
        {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            logout();
        }else if(id== R.id.nav_p_mode)
        {
            Toast.makeText(this, "Passenger Mode", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Passenger.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(prevStarted, Boolean.FALSE);
        editor.apply();
        startActivity(new Intent(Driver.this, MainActivity.class));
        finish();
    }


}
