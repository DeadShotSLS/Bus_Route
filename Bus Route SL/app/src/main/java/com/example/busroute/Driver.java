package com.example.busroute;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class Driver extends AppCompatActivity {

    String prevStarted = "prevStarted";

    Switch bus_available;
    Switch seat_available;
    TextView bus_NO;
    Boolean switchState_bus;
    Boolean switchState_seat;
    String location;
    String bus_no_t;
    Button submit;
    EditText status_bus;
    Map map;

    String bus_no;
    String Bus_NO = "Bus_NO";
    String Bus_No;

    ProgressBar progressBar;


    FirebaseDatabase database;
    DatabaseReference ref,Busref;
    private FirebaseAuth auth;
    FirebaseUser userid;

    private Driver_Save driver_save;
    private Bus_Save bus_save;

    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        HashMap<String, String> Bus_No = new HashMap<String, String>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        bus_save = new Bus_Save();

        submit = (Button) findViewById(R.id.button_status_submit);
        status_bus = (EditText)findViewById(R.id.status_bus);
        bus_NO = (TextView) findViewById(R.id.bus_hello);

        final Switch bus_available = (Switch) findViewById(R.id.switch_bus_available);
        final Switch seat_available = (Switch) findViewById(R.id.switch_seat_available);

        bus_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bus_available.isChecked()){
                    bus_save.setBus_available(true);
                    Busref.child(bus_no).setValue(bus_save);
                    //Location();
                }else {
                    bus_save.setBus_available(false);
                    Busref.child(bus_no).setValue(bus_save);
                }
            }
        });

        seat_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seat_available.isChecked()){
                    bus_save.setSeat_available(true);
                    Busref.child(bus_no).setValue(bus_save);
                }else {
                    bus_save.setSeat_available(false);
                    Busref.child(bus_no).setValue(bus_save);
                }
            }
        });

        viewBusno();

        Toast.makeText(Driver.this, "bus no :"+bus_save.getBus_no(),Toast.LENGTH_SHORT).show();

        //bus_no = bus_save.getBus_no();

        bus_no = "TT0003";

        database =FirebaseDatabase.getInstance();
        Busref = database.getReference().child("Bus_Save");

        bus_save.setBus_no(bus_no);
        bus_save.setStatus_bus("No status Found");
        bus_save.setBus_available(false);
        bus_save.setSeat_available(false);

        Busref.child(bus_no).setValue(bus_save);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(bus_no);
            }
        });
    }

    public String  viewBusno(){

        driver_save = new Driver_Save();
        bus_save = new Bus_Save();

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Driver_Save");

        String userid = auth.getCurrentUser().getUid();

        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);

                String bus_no = dataSnapshot.child("bus_no").getValue(String.class);
                bus_NO.setText(bus_no);

                bus_save.setBus_no(bus_no);

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return bus_no;
    }



    private void submit(String bus_no){

        String Status = status_bus.getText().toString().trim();

        if (TextUtils.isEmpty(Status)) {
            Toast.makeText(getApplicationContext(), "Enter Your Status!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            bus_save.setStatus_bus(status_bus.getText().toString());
            Busref.child(bus_no).setValue(bus_save);
            Toast.makeText(Driver.this, "Add Status :"+bus_save.getStatus_bus(),Toast.LENGTH_SHORT).show();
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
        /*else if(id== R.id.nav_my_bus)
        {
            Toast.makeText(this, "My Bus", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, My_Bus.class));
        }*/
        else if(id== R.id.nav_about_us)
        {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, About_Us.class));
        }
        else if(id== R.id.nav_log_out)
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
