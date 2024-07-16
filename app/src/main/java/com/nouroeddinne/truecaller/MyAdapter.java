package com.nouroeddinne.truecaller;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Model> listitems;

    public MyAdapter(Context context, ArrayList<Model> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new ViewHolder(viwe);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model item = listitems.get(position);
        holder.name.setText(item.getName());

        if (item.getNumber() == null || item.getNumber() == ""){
            if (item.getHomePhoneNumber() == null || item.getHomePhoneNumber()== ""){
                if (item.getWorkPhoneNumber() == null || item.getWorkPhoneNumber() == ""){
                    holder.number.setText("no Phone Number ");
                }else {
                    holder.number.setText(item.getWorkPhoneNumber());
                }
            }else {
                holder.number.setText(item.getHomePhoneNumber());
            }
        }else {
            holder.number.setText(item.getNumber());
        }


        byte[] imgData = item.getImg();
        if (imgData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            holder.img.setImageResource(R.drawable.baseline_person_24);
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imgDataString ="";
                if (item.getImg()!=null){
                    imgDataString = Base64.encodeToString(item.getImg(), Base64.DEFAULT);
                }

                Intent intent = new Intent(context,ShowInformatinContactActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("number",item.getNumber());
                intent.putExtra("nickname",item.getNickname());
                intent.putExtra("homeEmail",item.getHomeEmail());
                intent.putExtra("workEmail",item.getWorkEmail());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("workPhoneNumber",item.getWorkPhoneNumber());
                intent.putExtra("homePhoneNumber",item.getHomePhoneNumber());
                intent.putExtra("others",item.getOthers());
                intent.putExtra("img",imgDataString);
                context.startActivity(intent);

            }
        });
    }

    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,number;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_name);
            number = itemView.findViewById(R.id.textView_number);
            img = itemView.findViewById(R.id.imageView);
        }

    }
}



























