package com.example.busroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String prevStarted = "prevStarted";

    TextView register;
    EditText email_log;
    EditText pass;
    Button login;
    Button passengers;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference Aref;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, My_Bus.class));
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
            finish();
        }else if (sharedpreferences.getBoolean(prevStarted, false)) {
            startActivity(new Intent(MainActivity.this, Passenger.class));
            finish();

        }


        email_log = (EditText) findViewById(R.id.Log_Email);
        pass = (EditText) findViewById(R.id.Log_Pass);
        login = (Button) findViewById(R.id.button_Login);
        register = (TextView)findViewById(R.id.text_register);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


        passengers = (Button)findViewById(R.id.button_passenger);

        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Passenger.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email = email_log.getText().toString();
                final String password = pass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                if(email.equals("admin@gmail.com") && password.equals("123456789")){
                    Intent intent = new Intent(MainActivity.this, Admin.class);
                    startActivity(intent);
                    finish();
                }else {

                //authenticate user
               auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                FirebaseUser user = auth.getCurrentUser();
                                updateUI(user);
                                //progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    updateUI(null);
                                    // there was an error
                                    if (password.length() < 6) {
                                        pass.setError(getString(R.string.minimum_password));
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    Intent intent = new Intent(MainActivity.this, My_Bus.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }}
        });

    }

    private void updateUI(FirebaseUser user) {

    }
}
