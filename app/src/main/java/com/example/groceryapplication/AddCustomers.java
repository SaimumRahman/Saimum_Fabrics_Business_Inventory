package com.example.groceryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class AddCustomers extends AppCompatActivity {
private EditText nameEditTexts,phoneEditTexts,shopNameEditTexts,addressEditTexts;
private Button submitBtns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        nameEditTexts=findViewById(R.id.nameEditText);
        phoneEditTexts=findViewById(R.id.phoneEditText);
        shopNameEditTexts=findViewById(R.id.shopNameEditText);
        addressEditTexts=findViewById(R.id.addressEditText);
        submitBtns=findViewById(R.id.submitBtn);

        submitBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInput();
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
                    customerData.put("Shop_Name",shopName);
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
}