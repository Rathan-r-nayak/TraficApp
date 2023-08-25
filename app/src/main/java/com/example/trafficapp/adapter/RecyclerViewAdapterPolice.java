package com.example.trafficapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficapp.R;
import com.example.trafficapp.police;

import java.util.ArrayList;

public class RecyclerViewAdapterPolice extends RecyclerView.Adapter<RecyclerViewAdapterPolice.MyViewHolder> {
    Context context;
    ArrayList<police> list;
    public RecyclerViewAdapterPolice(Context context, ArrayList<police> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_police_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        police p=list.get(position);
        holder.name.setText(p.getName());
        holder.email.setText(p.getEmail());
        holder.phno.setText(p.getPhone());
        holder.sid.setText(p.getStationId());
        holder.dist.setText(p.getDistrict());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,phno,sid,dist;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_list_police);
            email=itemView.findViewById(R.id.email_list_police);
            phno=itemView.findViewById(R.id.phno_list_police);
            sid=itemView.findViewById(R.id.sid_list_police);
            dist=itemView.findViewById(R.id.dist_list_police);

        }
    }


}
