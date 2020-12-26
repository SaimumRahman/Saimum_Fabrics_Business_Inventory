package com.example.groceryapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   public TextView datePaid,amountPaid;
    public itemOnClickListener Listener;

    public PaidViewHolder(@NonNull View itemView) {
        super(itemView);

        datePaid=itemView.findViewById(R.id.dateBillPaidfound);
        amountPaid=itemView.findViewById(R.id.amountBillPaidfound);

    }


    public void setItemOnClickListener(itemOnClickListener itemOnClickListener){
        this.Listener=itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}
