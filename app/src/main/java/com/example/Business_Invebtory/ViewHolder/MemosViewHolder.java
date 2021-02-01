package com.example.Business_Invebtory.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Business_Invebtory.Interfaces.itemOnClickListenerImage;
import com.example.groceryapplication.R;

public class MemosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView showImageMemos;
    public TextView nameMemotxts,dateMemotxts;
    public itemOnClickListenerImage Listener;


    public MemosViewHolder(@NonNull View itemView) {
        super(itemView);

        showImageMemos=itemView.findViewById(R.id.showImageMemo);
        nameMemotxts=itemView.findViewById(R.id.nameMemotxt);
        dateMemotxts=itemView.findViewById(R.id.dateMemotxt);



    }

    public void setItemOnClickListener(itemOnClickListenerImage itemOnClickListener){
        this.Listener=itemOnClickListener;
    }
    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}
