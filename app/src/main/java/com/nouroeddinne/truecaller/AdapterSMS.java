package com.nouroeddinne.truecaller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AdapterSMS extends RecyclerView.Adapter<AdapterSMS.ViewHolder> {

    private ArrayList<ModelSMS> mysms;
    private Context context;

    public AdapterSMS(Context context,ArrayList<ModelSMS> mysms) {
        this.mysms = mysms;
        this.context = context;
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viwe= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms,parent,false);
        return new ViewHolder(viwe);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelSMS sms= mysms.get(position);
        holder.adress.setText(sms.getAdress());
        holder.msg.setText(sms.getMsg());
        holder.date.setText(sms.getData());
    }



    @Override
    public int getItemCount() {
        return mysms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView adress ;
        private TextView msg;
        private TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adress=itemView.findViewById(R.id.titel);
            msg=itemView.findViewById(R.id.msg);
            date=itemView.findViewById(R.id.date);
        }
    }



}
