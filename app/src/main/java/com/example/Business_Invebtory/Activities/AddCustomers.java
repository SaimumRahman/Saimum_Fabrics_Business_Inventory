package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddCustomers extends AppCompatActivity {
private EditText nameEditTexts,phoneEditTexts,shopNameEditTexts,addressEditTexts;
private Button submitBtns;
private CheckBox mohajonCheckBoxs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        nameEditTexts=findViewById(R.id.nameEditText);
        phoneEditTexts=findViewById(R.id.phoneEditText);
        shopNameEditTexts=findViewById(R.id.shopNameEditText);
        addressEditTexts=findViewById(R.id.addressEditText);
        submitBtns=findViewById(R.id.submitBtn);
        mohajonCheckBoxs=findViewById(R.id.mohajonCheckBox);


        submitBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mohajonCheckBoxs.isChecked()){
                   AddMohajon();
               }
               else {
                   CustomerInput();
               }
                PhoneBook();


            }
        });

    }

       private void CustomerInput() {

    String name=nameEditTexts.getText().toString();
    String phone=phoneEditTexts.getText().toString();
    String shopName=shopNameEditTexts.getText().toString();
    String address=addressEditTexts.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(shopName)){
            Toast.makeText(this, "Please Enter Your Shop Name", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
        }
        else {
            dataEntry(name,phone,shopName,address);
        }

    }

    private void dataEntry(String name, String phone, String shopName, String address) {

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.child("Customer_Details").child("Phone").exists()){

                    HashMap<String,Object>customerData=new HashMap<>();
                    customerData.put("Name",name);
                    customerData.put("Phone",phone);
                    customerData.put("Shop_Name",shopName.toLowerCase());
                    customerData.put("Address",address);

                    rootRef.child("Customer_Details").child(phone).updateChildren(customerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(AddCustomers.this,"Congratulations Customer Added Successfully", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(AddCustomers.this,"Network Error Please try Again Letter or Check your Internet", Toast.LENGTH_SHORT).show();
                                                           }
                        }
                    });


                }
                else{

                    Toast.makeText(AddCustomers.this,"This"+phone+"already Exists", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void AddMohajon() {

        String nameM=nameEditTexts.getText().toString();
        String phoneM=phoneEditTexts.getText().toString();
        String shopNameM=shopNameEditTexts.getText().toString();
        String addressM=addressEditTexts.getText().toString();

        if(TextUtils.isEmpty(nameM)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(phoneM)){
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(shopNameM)){
            Toast.makeText(this, "Please Enter Your Shop Name", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(addressM)){
            Toast.makeText(this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
        }
        else {
            final DatabaseReference rootRef;
            rootRef= FirebaseDatabase.getInstance().getReference();

            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(!snapshot.child("Mohajon_Details").child("Phone").exists()){

                        HashMap<String,Object>customerData=new HashMap<>();
                        customerData.put("Name",nameM);
                        customerData.put("Phone",phoneM);
                        customerData.put("Shop_Name",shopNameM.toLowerCase());
                        customerData.put("Address",addressM);

                        rootRef.child("Mohajon_Details").child(phoneM).updateChildren(customerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(AddCustomers.this,"Congratulations Mohajon Added Successfully", Toast.LENGTH_SHORT).show();


                                }
                                else {
                                    Toast.makeText(AddCustomers.this,"Network Error Please try Again Letter or Check your Internet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    else{

                        Toast.makeText(AddCustomers.this,"This"+phoneM+"already Exists", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void  PhoneBook(){

        String namePhn=nameEditTexts.getText().toString();
        String numberPhn=phoneEditTexts.getText().toString();

        if(TextUtils.isEmpty(namePhn)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(numberPhn)){
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }else {

        final DatabaseReference databaseReferencePhnBook= FirebaseDatabase.getInstance().getReference();
        databaseReferencePhnBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("Phone_Book").child(numberPhn).exists()){
                    HashMap<String,Object>phoneNumberEnrty=new HashMap<>();
                    phoneNumberEnrty.put("Name",namePhn.toLowerCase());
                    phoneNumberEnrty.put("Phone",numberPhn);

                    databaseReferencePhnBook.child("Phone_Book").child(numberPhn).updateChildren(phoneNumberEnrty).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddCustomers.this,"Congratulations Contact Added Successfully", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(AddCustomers.this,"Network Error Please try Again Letter or Check your Internet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{

                    Toast.makeText(AddCustomers.this,"This"+numberPhn+"already Exists", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        }

    }
}