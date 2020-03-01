package com.example.busroute;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.firebase.database.ServerValue;
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
    DatabaseReference ref,Busref,Aref,Lref;
    private FirebaseAuth auth;
    FirebaseUser userid;

    private Driver_Save driver_save;
    private Bus_Save bus_save;
    private Location_Save location_save;

    SharedPreferences sharedPreferences;

    LocationRequest request = new LocationRequest();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        bus_save = new Bus_Save();
        location_save = new Location_Save();


        submit = (Button) findViewById(R.id.button_status_submit);
        status_bus = (EditText)findViewById(R.id.status_bus);
        bus_NO = (TextView) findViewById(R.id.bus_hello);

        Intent intent = getIntent();
        String Bus_No = String.valueOf(intent.getStringExtra("Bus_No"));

        Toast.makeText(Driver.this, "bus no :"+Bus_No,Toast.LENGTH_SHORT).show();

        bus_no = Bus_No;
        bus_NO.setText(bus_no);


        final Switch bus_available = (Switch) findViewById(R.id.switch_bus_available);
        final Switch seat_available = (Switch) findViewById(R.id.switch_seat_available);

        bus_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bus_available.isChecked()){
                    bus_save.setBus_available(true);
                    Busref.child(bus_no).setValue(bus_save);
                    requestLocationUpdates(bus_no);
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

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void requestLocationUpdates(final String bus_no) {


        LocationRequest request = new LocationRequest();

//Specify how often your app should request the device’s location//

        request.setInterval(10000);

//Get the most accurate location data available//



        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the app currently has access to the location permission...//
        FusedLocationProviderClient client = new FusedLocationProviderClient(getApplicationContext());
        if (permission == PackageManager.PERMISSION_GRANTED) {

//...then request location updates//

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

//Get a reference to the database, so your app can perform read and write operations//


                    Lref = FirebaseDatabase.getInstance().getReference().child("Location_Save");
                    Location location = locationResult.getLastLocation();
                    if (location != null) {

                        location_save.setLatitude(location.getLatitude());
                        location_save.setLongitude(location.getLongitude());
                        location_save.setBus_no(bus_no);
                        Lref.child(bus_no).setValue(location_save);

                    }
                }
            }, null);
        }
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
