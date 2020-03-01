package com.example.busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class View_Info extends AppCompatActivity {

    EditText bus_no;
    Button submit;
    TextView name;
    TextView email;
    TextView phone;
    TextView licen;
    TextView route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view__info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bus_no = (EditText)findViewById(R.id.bus_no3);
        submit = (Button)findViewById(R.id.get_info);
        name = (TextView)findViewById(R.id.text_name);
        email = (TextView)findViewById(R.id.text_email);
        phone = (TextView)findViewById(R.id.text_phone);
        licen = (TextView)findViewById(R.id.text_licence);
        route = (TextView)findViewById(R.id.text_route_v);
    }
}
