 package com.example.Business_Invebtory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.groceryapplication.R;

 public class MainActivity extends AppCompatActivity {
private Button addCustomerBtns,customerListBtns,contactsBtn,cashMemoBtns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addCustomerBtns=findViewById(R.id.addCustomerBtn);
        customerListBtns=findViewById(R.id.customerListBtn);
        contactsBtn=findViewById(R.id.phoneBookBtn);
        cashMemoBtns=findViewById(R.id.cashMemoBtn);

        addCustomerBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCustomer=new Intent(getApplicationContext(), AddCustomers.class);
                startActivity(intentAddCustomer);
            }
        });
        customerListBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCustomerLists=new Intent(getApplicationContext(),Customer_Details.class);
                startActivity(intentCustomerLists);
            }
        });
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCustomer=new Intent(getApplicationContext(), PhoneBookActivity.class);
                startActivity(intentAddCustomer);
            }
        });

        cashMemoBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCustomerLists=new Intent(getApplicationContext(), UploadMemo.class);
                startActivity(intentCustomerLists);
            }
        });

    }
}