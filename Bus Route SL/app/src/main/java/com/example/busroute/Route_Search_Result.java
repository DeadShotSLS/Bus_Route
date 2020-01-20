package com.example.busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Route_Search_Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route__search__result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
