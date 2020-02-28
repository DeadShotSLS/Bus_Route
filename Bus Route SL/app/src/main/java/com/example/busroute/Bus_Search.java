package com.example.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bus_Search extends AppCompatActivity {

    EditText bus_no;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus__search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bus_no = (EditText)findViewById(R.id.search_bus_no);
        search = (Button)findViewById(R.id.search_bus);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bus_no.getText().toString().equals("")) {
                    Toast.makeText(Bus_Search.this, "Please insert Bus No", Toast.LENGTH_SHORT).show();
                } else {

                    String Bus_No = String.format(bus_no.getText().toString());

                    Intent intent = new Intent(Bus_Search.this, Bus_Search_result.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Bus_No", Bus_No);
                    startActivity(intent);

                }

            }
        });

    }
}
