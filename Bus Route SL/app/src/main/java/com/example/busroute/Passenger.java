package com.example.busroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Passenger extends AppCompatActivity {

    String prevStarted = "prevStarted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(prevStarted, Boolean.TRUE);
        editor.apply();


        Button button_route_search = findViewById(R.id.button_search_route);
        button_route_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Passenger.this, Route_Search.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        Button button_bus_search = findViewById(R.id.button_search_bus);
        button_bus_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Passenger.this, Bus_Search.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item_p,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.nav_help_p)
        {
            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Help_p.class));

        }
        else if(id== R.id.nav_my_profile)
        {
            Toast.makeText(this, "My Bus", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, My_Profile.class));


        }
        else if(id== R.id.nav_about_us)
        {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, About_Us.class));

        }

        else if(id== R.id.nav_home)
        {
            SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.FALSE);
            editor.apply();
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }


}
