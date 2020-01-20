package com.example.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Advance_Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advance_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Advance_search");

        Intent intent = getIntent();
        String start_text = String.valueOf(intent.getStringExtra("start_text"));
        String final_text = String.valueOf(intent.getStringExtra("final_text"));

        EditText editText_start = findViewById(R.id.start_destination_a);
        editText_start.setText("" + start_text );

        EditText editText_final = findViewById(R.id.final_destination_a);
        editText_final.setText("" + final_text );

        Spinner spinner_start = findViewById(R.id.spinner_start_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_start_time_part, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_start.setAdapter(adapter);
        spinner_start.setOnItemSelectedListener(this);


        Spinner spinner_final = findViewById(R.id.spinner_final_time);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_start_time_part, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_final.setAdapter(adapter);
        spinner_final.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
