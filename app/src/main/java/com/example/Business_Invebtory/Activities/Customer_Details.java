package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.Business_Invebtory.ModalClasses.CustomerListModalClass;
import com.example.Business_Invebtory.ModalClasses.MohajonModel;
import com.example.groceryapplication.R;
import com.example.Business_Invebtory.ViewHolder.CustomerViewHolder;
import com.example.groceryapplication.databinding.ActivityCustomerDetailsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;

public class Customer_Details extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference customerRef,customerDelRef,updateRef,mohajonRef,mohajonDelRef,mohajonUpdateRef;
    private String search_input;
    ActivityCustomerDetailsBinding customerDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customerDetailsBinding=ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        View customerDetailsView=customerDetailsBinding.getRoot();
        setContentView(customerDetailsView);

        customerDetailsBinding.recyclerViewCustomerList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        customerDetailsBinding.recyclerViewCustomerList.setLayoutManager(layoutManager);

        customerRef=FirebaseDatabase.getInstance().getReference().child("Customer_Details");
        customerDelRef=FirebaseDatabase.getInstance().getReference().child("Customer_Details");
        updateRef=FirebaseDatabase.getInstance().getReference().child("Customer_Details");
        mohajonRef=FirebaseDatabase.getInstance().getReference().child("Mohajon_Details");
        mohajonDelRef=FirebaseDatabase.getInstance().getReference().child("Mohajon_Details");
        mohajonUpdateRef=FirebaseDatabase.getInstance().getReference().child("Mohajon_Details");

        customerDetailsBinding.checkboxMohajonAllDetails.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked){
               onStart();
            }
            else {
               MojonDetails();
            }
        });

        customerDetailsBinding.searchButton.setOnClickListener(v -> {

          if(!customerDetailsBinding.checkboxMohajonAllDetails.isChecked()){
              search_input=customerDetailsBinding.searchProductName.getText().toString();
              onStart();
          }
          else {
              search_input=customerDetailsBinding.searchProductName.getText().toString();
              MojonDetails();
          }

        });


    }

    @Override
    protected void onStart() {


            super.onStart();
            FirebaseRecyclerOptions<CustomerListModalClass>options=new FirebaseRecyclerOptions.Builder<CustomerListModalClass>()
                    .setQuery(customerRef.orderByChild("Shop_Name").startAt(search_input),CustomerListModalClass.class).build();

            FirebaseRecyclerAdapter<CustomerListModalClass, CustomerViewHolder>adapter=new FirebaseRecyclerAdapter<CustomerListModalClass, CustomerViewHolder>(options) {
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
                                finish();

                        }
                    });

                    holder.deleteCustomerBtns.setOnClickListener(v -> {
                        CharSequence[] charSequenceBill =new CharSequence[]{
                                "Yes","No"
                        };
                        AlertDialog.Builder alertsBill=new AlertDialog.Builder(Customer_Details.this);
                        alertsBill.setTitle("DO YOU WANT TO DELETE THIS ENTRY ?");
                        alertsBill.setItems(charSequenceBill, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){

                                    customerDelRef.child(model.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Customer_Details.this,"DELETED",Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }else if(which==1) {

                                }
                            }
                        });
                        alertsBill.show();
                    });
                    holder.editCustomerBtns.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final DialogPlus dialog = DialogPlus.newDialog(Customer_Details.this)
                                    .setGravity(Gravity.CENTER)
                                    .setMargin(50,0,0,50)
                                    .setContentHolder(new ViewHolder(R.layout.updatecustomerdetails))
                                    .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                                    .create();
                            View view = (RelativeLayout)dialog.getHolderView();
                            EditText nameEditTextUpdates=(EditText) view.findViewById(R.id.nameEditTextUpdate);
                            EditText phnEditTextUpdates=(EditText) view.findViewById(R.id.phnEditTextUpdate);
                            EditText shopNameEditTextUpdates=(EditText) view.findViewById(R.id.shopNameEditTextUpdate);
                            EditText addressEditTextUpdates=(EditText) view.findViewById(R.id.addressEditTextUpdate);
                            Button updateButtons=(Button) view.findViewById(R.id.updateButton);

                            shopNameEditTextUpdates.setText(model.getShop_Name());
                            nameEditTextUpdates.setText(model.getName());
                            phnEditTextUpdates.setText(model.getPhone());
                            addressEditTextUpdates.setText(model.getAddress());
                            updateButtons.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    HashMap<String,Object>hasUpdate=new HashMap<>();
                                    hasUpdate.put("Address",addressEditTextUpdates.getText().toString());
                                    hasUpdate.put("Name",nameEditTextUpdates.getText().toString());
                                    hasUpdate.put("Phone",phnEditTextUpdates.getText().toString());
                                    hasUpdate.put("Shop_Name",shopNameEditTextUpdates.getText().toString());

                                    updateRef.child(model.getPhone()).updateChildren(hasUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Customer_Details.this,"UPDATED",Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    dialog.dismiss();
                                }
                            });


                            dialog.show();

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
        customerDetailsBinding.recyclerViewCustomerList.setAdapter(adapter);
            adapter.startListening();
        }





private void MojonDetails(){
    FirebaseRecyclerOptions<MohajonModel>options=new FirebaseRecyclerOptions.Builder<MohajonModel>()
            .setQuery(mohajonRef.orderByChild("Shop_Name").startAt(search_input),MohajonModel.class).build();

    FirebaseRecyclerAdapter<MohajonModel,CustomerViewHolder>adapter=new FirebaseRecyclerAdapter<MohajonModel, CustomerViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull MohajonModel model) {
            holder.shopNames.setText(model.getShop_Name());
            holder.customerNames.setText(model.getName());
            holder.phoneNumbers.setText(model.getPhone());
            holder.addresses.setText(model.getAddress());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentnumberpass=new Intent(Customer_Details.this, MohajonActivityCalculation.class);
                    intentnumberpass.putExtra("phonepass",model.getPhone());
                    startActivity(intentnumberpass);
                    finish();

                }
            });

            holder.deleteCustomerBtns.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CharSequence[] charSequenceBill =new CharSequence[]{
                            "Yes","No"
                    };
                    AlertDialog.Builder alertsBill=new AlertDialog.Builder(Customer_Details.this);
                    alertsBill.setTitle("DO YOU WANT TO DELETE THIS ENTRY ?");
                    alertsBill.setItems(charSequenceBill, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){

                                mohajonDelRef.child(model.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Customer_Details.this,"DELETED",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else if(which==1) {

                            }
                        }
                    });
                    alertsBill.show();
                }
            });
            holder.editCustomerBtns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialog = DialogPlus.newDialog(Customer_Details.this)
                            .setGravity(Gravity.CENTER)
                            .setMargin(50,0,0,50)
                            .setContentHolder(new ViewHolder(R.layout.updatecustomerdetails))
                            .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                            .create();
                    View view = (RelativeLayout)dialog.getHolderView();
                    EditText nameEditTextUpdates=(EditText) view.findViewById(R.id.nameEditTextUpdate);
                    EditText phnEditTextUpdates=(EditText) view.findViewById(R.id.phnEditTextUpdate);
                    EditText shopNameEditTextUpdates=(EditText) view.findViewById(R.id.shopNameEditTextUpdate);
                    EditText addressEditTextUpdates=(EditText) view.findViewById(R.id.addressEditTextUpdate);
                    Button updateButtons=(Button) view.findViewById(R.id.updateButton);

                    shopNameEditTextUpdates.setText(model.getShop_Name());
                    nameEditTextUpdates.setText(model.getName());
                    phnEditTextUpdates.setText(model.getPhone());
                    addressEditTextUpdates.setText(model.getAddress());
                    updateButtons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            HashMap<String,Object>hasUpdate=new HashMap<>();
                            hasUpdate.put("Address",addressEditTextUpdates.getText().toString());
                            hasUpdate.put("Name",nameEditTextUpdates.getText().toString());
                            hasUpdate.put("Phone",phnEditTextUpdates.getText().toString());
                            hasUpdate.put("Shop_Name",shopNameEditTextUpdates.getText().toString());

                            mohajonUpdateRef.child(model.getPhone()).updateChildren(hasUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Customer_Details.this,"UPDATED",Toast.LENGTH_LONG).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    });


                    dialog.show();

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
    customerDetailsBinding.recyclerViewCustomerList.setAdapter(adapter);
    adapter.startListening();





}

}

