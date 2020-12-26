 package com.example.groceryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class MainActivity extends AppCompatActivity {
private Button addCustomerBtns,customerListBtns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addCustomerBtns=findViewById(R.id.addCustomerBtn);
        customerListBtns=findViewById(R.id.customerListBtn);

        addCustomerBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCustomer=new Intent(getApplicationContext(),AddCustomers.class);
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


    }
}