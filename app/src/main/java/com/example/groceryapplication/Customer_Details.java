package com.example.groceryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer_Details extends AppCompatActivity {

    private RecyclerView recyclerViewCustomerLists;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference customerRef;
    private String search_input;
    private EditText search_input_text;
    private Button search_button;


 //   private CustomerListsAdapter customerListsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__details);

        search_input_text=findViewById(R.id.search_product_name);
        search_button=findViewById(R.id.search_button);
        recyclerViewCustomerLists=findViewById(R.id.recyclerViewCustomerList);
        recyclerViewCustomerLists.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerViewCustomerLists.setLayoutManager(layoutManager);

        customerRef=FirebaseDatabase.getInstance().getReference().child("Customer_Details");

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_input=search_input_text.getText().toString();
                // Toast.makeText(getApplicationContext(),"searching",Toast.LENGTH_LONG).show();
                onStart();

            }
        });


      //  customerListsAdapter=new CustomerListsAdapter(options);
      //  recyclerViewCustomerLists.setAdapter(customerListsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<CustomerListModalClass>options=new FirebaseRecyclerOptions.Builder<CustomerListModalClass>()
                .setQuery(customerRef.orderByChild("Shop_Name").startAt(search_input),CustomerListModalClass.class).build();

    FirebaseRecyclerAdapter<CustomerListModalClass,CustomerViewHolder>adapter=new FirebaseRecyclerAdapter<CustomerListModalClass, CustomerViewHolder>(options) {
    @Override
    protected void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull CustomerListModalClass model) {
        holder.shopNames.setText(model.getShop_Name());
        holder.customerNames.setText(model.getName());
        holder.phoneNumbers.setText(model.getPhone());
        holder.addresses.setText(model.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentnumberpass=new Intent(Customer_Details.this,CalculationActivity.class);
                intentnumberpass.putExtra("phonepass",model.getPhone());
                startActivity(intentnumberpass);
            }
        });

    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.customerdetailsformat,parent,false);
        CustomerViewHolder holder=new CustomerViewHolder(view);
        return holder;
    }
};
        recyclerViewCustomerLists.setAdapter(adapter);
        adapter.startListening();

}
}