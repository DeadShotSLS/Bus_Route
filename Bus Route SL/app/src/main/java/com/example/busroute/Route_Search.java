package com.example.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Route_Search extends AppCompatActivity {


    EditText start_d;
    EditText final_d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route__search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        start_d = findViewById(R.id.start_destination);
        final_d = findViewById(R.id.final_destination);

        Button advance_search =findViewById(R.id.button_advance_search);
        advance_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (start_d.getText().toString().equals("")
                        || final_d.getText().toString().equals("")) {
                    Toast.makeText(Route_Search.this, "Please insert destination", Toast.LENGTH_SHORT).show();
                } else {

                    String start_text = String.format(start_d.getText().toString());
                    String final_text = String.format(final_d.getText().toString());

                    Intent intent = new Intent(Route_Search.this, Advance_Search.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("start_text", start_text);
                    intent.putExtra("final_text",final_text);
                    startActivity(intent);

                }}
        });
    }
}
