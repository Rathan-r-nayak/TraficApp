package com.example.trafficapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficapp.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListPoliceActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<police> policeArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_police);


        policeArrayList=new ArrayList<>();

        List<police> policeList=
    }
}