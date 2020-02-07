package com.example.busroute;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class My_Bus extends AppCompatActivity {

    Button submit;
    Button attch;
    EditText busNO;
    EditText route;

    Toast toast;

    Uri filePath;
    final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference Roref;

    private Route_Save route_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my__bus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        submit = (Button)findViewById(R.id.submit_route);
        attch = (Button) findViewById(R.id.button_attach);
        busNO = (EditText)findViewById(R.id.add_bus_no);
        route = (EditText)findViewById(R.id.add_route);

        route_save = new Route_Save();

        database =FirebaseDatabase.getInstance();
        Roref = database.getReference().child("Route_Save");

        attch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_route();
                uploadImage();
            }
        });

    }

    private void submit_route(){

        String bus = busNO.getText().toString().trim();
        String details = route.getText().toString().trim();

        if (TextUtils.isEmpty(bus)) {
            Toast.makeText(getApplicationContext(), "Enter Bus Registration Number!", Toast.LENGTH_SHORT).show();
            //progressBar.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(details)) {
            Toast.makeText(getApplicationContext(), "Enter Route Details!", Toast.LENGTH_SHORT).show();
            //progressBar.setVisibility(View.GONE);
            return;
        }else {
            route_save.setBusNO(busNO.getText().toString());
            route_save.setRoute(route.getText().toString());

            Roref.child(bus).setValue(route_save);

            toast = Toast.makeText(My_Bus.this,"",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            View view = getLayoutInflater().inflate(R.layout.toast_route_submit,(ViewGroup)findViewById(R.id.toastroute));
            toast.setView(view);
            toast.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(My_Bus.this, Driver.class));
                    finish();
                }
            }, 2000);
        }

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference imgref = storageReference.child("images/"+ UUID.randomUUID().toString());
            imgref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(My_Bus.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(My_Bus.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

}
