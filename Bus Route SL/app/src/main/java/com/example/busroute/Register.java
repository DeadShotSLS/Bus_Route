package com.example.busroute;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email_reg;
    EditText pass;
    EditText name_d;
    EditText bus_no;
    EditText phone;
    EditText licence;
    Button register_d;
    FirebaseUser userid;

    ProgressBar progressBar;

    private FirebaseAuth auth;

    FirebaseDatabase database;
    DatabaseReference ref;

    private Driver_Save driver_save;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        email_reg = (EditText) findViewById(R.id.Reg_Email);
        pass = (EditText) findViewById(R.id.Reg_pass);
        name_d = (EditText)findViewById(R.id.Reg_Name);
        licence = (EditText)findViewById(R.id.Reg_Licence);
        phone = (EditText)findViewById(R.id.Reg_mobile);
        register_d = (Button) findViewById(R.id.button_register);

        driver_save = new Driver_Save();

        database =FirebaseDatabase.getInstance();
        ref = database.getReference().child("Driver_Save");

        register_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                register();
            }
        });
    }

    private void register(){

        String email = email_reg.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String name = name_d.getText().toString().trim();
        String Licence = licence.getText().toString().trim();
        String Mobile = phone.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter Your name!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(Licence)) {
            Toast.makeText(getApplicationContext(), "Enter Your Licence Number!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(Mobile)) {
            Toast.makeText(getApplicationContext(), "Enter Mobile Number!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }else {


            //create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {

                                String userid = auth.getCurrentUser().getUid();

                                driver_save.setUserid(userid);
                                driver_save.setName_d(name_d.getText().toString());
                                driver_save.setLicence(licence.getText().toString());
                                driver_save.setEmail_reg(email_reg.getText().toString());
                                driver_save.setPhone(phone.getText().toString());

                                ref.child(userid).setValue(driver_save);

                                startActivity(new Intent(Register.this, My_Bus.class));
                                finish();
                            } else {
                                Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }


}
