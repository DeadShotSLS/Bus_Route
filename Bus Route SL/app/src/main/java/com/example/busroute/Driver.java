package com.example.busroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Driver extends AppCompatActivity {

    String prevStarted = "prevStarted";

    TextView bus_NO;
    Boolean switchState_bus;
    Boolean switchState_seat;
    String location;
    String bus_no_t;
    Button submit;
    EditText status_bus;

    ProgressBar progressBar;

    String bus_no;

    FirebaseDatabase database;
    DatabaseReference ref,Busref;
    private FirebaseAuth auth;
    FirebaseUser userid;

    private Driver_Save driver_save;
    private Bus_Save bus_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        bus_save = new Bus_Save();
        driver_save = new Driver_Save();

        database =FirebaseDatabase.getInstance();
        Busref = database.getReference().child("Bus_Save");

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Driver_Save");

        String userid = auth.getCurrentUser().getUid();

        submit = (Button) findViewById(R.id.button_status_submit);
        status_bus = (EditText)findViewById(R.id.status_bus);
        bus_NO = (TextView) findViewById(R.id.bus_hello);

        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                bus_no = dataSnapshot.child("bus_no").getValue(String.class);
                bus_NO.setText(bus_no);

                String bus_no_t = bus_no;

                //Toast.makeText(Driver.this, "bus number"+bus_no_t, Toast.LENGTH_SHORT).show();

               /* bus_save.setBus_no_t(bus_no_t.toString());
                bus_save.setBus_available(switchState_bus.booleanValue());
                bus_save.setSeat_available(switchState_seat.booleanValue());

                Busref.child(bus_no_t).setValue(bus_save);*/

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Switch bus_available = (Switch) findViewById(R.id.switch_bus_available);
        Switch seat_available = (Switch) findViewById(R.id.switch_seat_available);

        Boolean switchState_bus = bus_available.isChecked();
        Boolean switchState_seat = seat_available.isChecked();

        boolean bus = switchState_bus.booleanValue();
        boolean seat = switchState_seat.booleanValue();


        Toast.makeText(Driver.this, "bus number"+bus_no_t, Toast.LENGTH_SHORT).show();

        /*bus_save.setBus_no_t(bus_no_t.toString());
        bus_save.setBus_available(switchState_bus.booleanValue());
        bus_save.setSeat_available(switchState_seat.booleanValue());

        Busref.child(bus_no_t).setValue(bus_save);*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit(){
        String Status = status_bus.getText().toString().trim();

        if (TextUtils.isEmpty(Status)) {
            Toast.makeText(getApplicationContext(), "Enter Your Status!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            bus_save.setStatus_bus(status_bus.getText().toString());
           // Busref.child(bus_no_t).setValue(bus_save);
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
