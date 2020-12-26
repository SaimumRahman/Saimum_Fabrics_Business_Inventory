package com.example.groceryapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView customerNames,phoneNumbers,shopNames,addresses;
    public itemOnClickListener Listener;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);


            customerNames=itemView.findViewById(R.id.customerName);
            phoneNumbers=itemView.findViewById(R.id.phoneNumber);
            shopNames=itemView.findViewById(R.id.shopName);
            addresses=itemView.findViewById(R.id.address);

    }
    public void setItemOnClickListener(itemOnClickListener itemOnClickListener){
        this.Listener=itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}
