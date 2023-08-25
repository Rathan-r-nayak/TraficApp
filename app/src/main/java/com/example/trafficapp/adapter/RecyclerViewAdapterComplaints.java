package com.example.trafficapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficapp.R;
import com.example.trafficapp.complaint;

import java.util.ArrayList;

public class RecyclerViewAdapterComplaints extends RecyclerView.Adapter<RecyclerViewAdapterComplaints.ViewHolder> {
    Context context;
    ArrayList<complaint> list;



    public RecyclerViewAdapterComplaints(Context context, ArrayList<complaint> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_complaint_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        complaint c=list.get(position);
        String name=c.getFname()+" "+c.getLname();
        holder.name.setText(name);
        holder.email.setText(c.getEmail());
        holder.numplate.setText(c.getPlateNo());
        String typ=c.getTypeVehicle().toString()+"Wheeler";
        holder.type.setText(typ);
        holder.violation.setText(c.getViolation());
        String fine="fine:"+c.getFine().toString();
        holder.fine.setText(fine);


        String base64Image = c.getImage();
        byte[] imageData = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        holder.img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,numplate,type,violation,fine;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_challan);
            email=itemView.findViewById(R.id.email_challan);
            numplate=itemView.findViewById(R.id.num_plate);
            type=itemView.findViewById(R.id.type_vehicle_challan);
            violation=itemView.findViewById(R.id.violation_challan);
            fine=itemView.findViewById(R.id.fine_challan);
            img=itemView.findViewById(R.id.img_challan);
        }
    }
}
