package com.example.busroute;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class My_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my__profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
