package com.example.Business_Invebtory.ViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapplication.R;
import com.example.Business_Invebtory.Interfaces.itemOnClickListener;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView customerNames,phoneNumbers,shopNames,addresses;
    public ImageButton deleteCustomerBtns,editCustomerBtns;
    public itemOnClickListener Listener;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);


            customerNames=itemView.findViewById(R.id.customerName);
            phoneNumbers=itemView.findViewById(R.id.phoneNumber);
            shopNames=itemView.findViewById(R.id.shopName);
            addresses=itemView.findViewById(R.id.address);
        deleteCustomerBtns=itemView.findViewById(R.id.deleteCustomerBtn);
        editCustomerBtns=itemView.findViewById(R.id.editCustomerBtn);

    }
    public void setItemOnClickListener(itemOnClickListener itemOnClickListener){
        this.Listener=itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}
