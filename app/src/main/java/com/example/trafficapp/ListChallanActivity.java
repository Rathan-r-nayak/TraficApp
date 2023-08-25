package com.example.trafficapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficapp.adapter.RecyclerViewAdapterComplaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListChallanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<complaint> list;
    RecyclerViewAdapterComplaints recyclerViewAdapterComplaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_challan);

        recyclerView=findViewById(R.id.complaint_list_recycler);
        database= FirebaseDatabase.getInstance().getReference("complaint");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        recyclerViewAdapterComplaints=new RecyclerViewAdapterComplaints(this,list);
        recyclerView.setAdapter(recyclerViewAdapterComplaints);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    complaint c=dataSnapshot.getValue(complaint.class);
                    list.add(c);

                }
                recyclerViewAdapterComplaints.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}