package com.example.busroute;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class My_Bus extends AppCompatActivity {

    Button submit;
    TextView busNO;
    TextView name;

    Toast toast;


    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference Roref;
    DatabaseReference ref,Busref,Aref,Dref;
    private FirebaseAuth auth;
    FirebaseUser userid;

    private Driver_Save driver_save;
    private Bus_Save bus_save;

    SharedPreferences sharedPreferences;

    private Route_Save route_save;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my__bus);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        submit = (Button) findViewById(R.id.submit_route);
        name =(TextView)findViewById(R.id.text_name_hello);
        busNO = (TextView)findViewById(R.id.text_busno);

        driver_save = new Driver_Save();
        bus_save = new Bus_Save();

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Driver_Save");
        Dref = FirebaseDatabase.getInstance().getReference("Bus_Driver");

        String userid = auth.getCurrentUser().getUid();


        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);

                String Licence = dataSnapshot.child("licence").getValue(String.class);
                String name_d = dataSnapshot.child("name_d").getValue(String.class);
                name.setText("Your Name is : "+name_d);

                Dref.child(Licence).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        //String name_d = dataSnapshot.child("name_d").getValue(String.class);
                        final String bus_no = dataSnapshot.child("bus_no").getValue(String.class);

                        //String Bus_no = bus_no;
                        if(bus_no == null){
                            busNO.setText("Bus is not assign yet");
                        }else {
                            busNO.setText("Your Bus No is : "+bus_no);
                        }



                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if(bus_no == null){
                                    Toast.makeText(My_Bus.this, "please contact our agent and assign the bus", Toast.LENGTH_LONG).show();
                                    finish();
                                }else {
                                    String Bus_No = String.format(bus_no);

                                    Intent intent = new Intent(My_Bus.this, Driver.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("Bus_No", Bus_No);
                                    startActivity(intent);
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                progressBar.setVisibility(View.GONE);




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        /*database = FirebaseDatabase.getInstance();
        Roref = database.getReference().child("Route_Save");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
    }
}