//package com.example.groceryapplication;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//
//public class CustomerListsAdapter extends FirebaseRecyclerAdapter<CustomerListModalClass, CustomerListsAdapter.CustomerListsAdapterViewHolder> {
//
//
//    public CustomerListsAdapter(@NonNull FirebaseRecyclerOptions<CustomerListModalClass> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull CustomerListsAdapterViewHolder holder, int position, @NonNull CustomerListModalClass model) {
//        holder.shopNames.setText(model.getShop_Name());
//        holder.customerNames.setText(model.getName());
//        holder.phoneNumbers.setText(model.getPhone());
//        holder.addresses.setText(model.getAddress());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent send=new Intent(CustomerListsAdapter.class,CalculationActivity.class);
//            }
//        });
//    }
//
//    @NonNull
//    @Override
//    public CustomerListsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customerdetailsformat,parent,false);
//
//        return new CustomerListsAdapterViewHolder(view);
//    }
//
//    class CustomerListsAdapterViewHolder extends RecyclerView.ViewHolder {
//
//        TextView customerNames,phoneNumbers,shopNames,addresses;
//
//        public CustomerListsAdapterViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            customerNames=itemView.findViewById(R.id.customerName);
//            phoneNumbers=itemView.findViewById(R.id.phoneNumber);
//            shopNames=itemView.findViewById(R.id.shopName);
//            addresses=itemView.findViewById(R.id.address);
//        }
//    }
//
//}
