package com.example.trafficapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficapp.adapter.RecyclerViewAdapterPolice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPoliceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<police> list;
    RecyclerViewAdapterPolice recyclerViewAdapterPolice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_police);


        recyclerView=findViewById(R.id.police_list_recycler);
        database= FirebaseDatabase.getInstance().getReference("police");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        recyclerViewAdapterPolice =new RecyclerViewAdapterPolice(this,list);
        recyclerView.setAdapter(recyclerViewAdapterPolice);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    police p=dataSnapshot.getValue(police.class);
                    list.add(p);
                }

                recyclerViewAdapterPolice.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}