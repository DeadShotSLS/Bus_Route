package com.example.busroute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Route_Search_Result extends AppCompatActivity {

    public RecyclerView recyclerView;
    private RecyclerView.Adapter RAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Result_Adapter adapter;
    private List<Result_List> result_lists;

    FirebaseDatabase database;
    DatabaseReference Roref;
    private Route_Save route_save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route__search__result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);

        /*Result_List[] result_lists = new Result_List[] {
                new Result_List("Email", android.R.drawable.ic_dialog_email),
                new Result_List("Info", android.R.drawable.ic_dialog_info),
                new Result_List("Delete", android.R.drawable.ic_delete),
                new Result_List("Dialer", android.R.drawable.ic_dialog_dialer),
                new Result_List("Alert", android.R.drawable.ic_dialog_alert),
                new Result_List("Map", android.R.drawable.ic_dialog_map),
                new Result_List("Email", android.R.drawable.ic_dialog_email),
                new Result_List("Info", android.R.drawable.ic_dialog_info),
                new Result_List("Delete", android.R.drawable.ic_delete),
                new Result_List("Dialer", android.R.drawable.ic_dialog_dialer),
                new Result_List("Alert", android.R.drawable.ic_dialog_alert),
                new Result_List("Map", android.R.drawable.ic_dialog_map),
        };

        Result_Adapter adapter = new Result_Adapter(result_lists);*/




        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<Object> result_lists = new ArrayList<>();
        adapter = new Result_Adapter(this.result_lists);
        recyclerView.setAdapter(adapter);

        setTitle("Search_Result");

        Intent intent = getIntent();
        String start_text = String.valueOf(intent.getStringExtra("start_text"));
        String final_text = String.valueOf(intent.getStringExtra("final_text"));

        //route_save = new Route_Save();

        Roref = FirebaseDatabase.getInstance().getReference().child("Route_Save");

        //Roref.addListenerForSingleValueEvent(valueEventListener);


        Roref.child("Route_Save").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result_lists.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Result_List result_list = snapshot.getValue(Result_List.class);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
