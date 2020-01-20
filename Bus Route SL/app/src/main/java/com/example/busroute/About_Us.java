package com.example.busroute;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about__us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
