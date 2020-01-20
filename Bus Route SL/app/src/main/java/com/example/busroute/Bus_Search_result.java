package com.example.busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class Bus_Search_result extends AppCompatActivity {

    TextView bus_available;
    TextView seat_available;
    TextView busNo;
    Map loaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus__search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bus_available = (TextView)findViewById(R.id.text_bus);
        seat_available = (TextView)findViewById(R.id.text_seat);
        busNo = (TextView)findViewById(R.id.text_bus_no);
        loaction = (Map)findViewById(R.id.map_bus);
    }
}
