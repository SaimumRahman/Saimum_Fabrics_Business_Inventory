package com.example.Business_Invebtory.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapplication.R;
import com.example.Business_Invebtory.Interfaces.itemOnClickListener;

public class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView billingAmount,dateView;
    public itemOnClickListener Listener;

    public BillViewHolder(@NonNull View itemView) {
        super(itemView);

    billingAmount=itemView.findViewById(R.id.dateBill);
    dateView=itemView.findViewById(R.id.amountBill);

    }
    public void setItemOnClickListener(itemOnClickListener itemOnClickListener){
        this.Listener=itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}