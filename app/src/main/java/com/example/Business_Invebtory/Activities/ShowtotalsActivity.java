package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowtotalsActivity extends AppCompatActivity {
private TextView billLeftTvs,billLeftPaids,billLefttobepaids;
private String totalsBillphnnumber;
private DatabaseReference databaseReferenceTotalBillings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtotals);

        totalsBillphnnumber=getIntent().getExtras().get("totalBillPhn").toString();
//Toast.makeText(ShowtotalsActivity.this,totalsBillphnnumber,Toast.LENGTH_LONG).show();
                billLeftTvs=findViewById(R.id.billLeftTv);
        billLeftPaids=findViewById(R.id.billLeftPaid);
        billLefttobepaids=findViewById(R.id.billLefttobepaid);

        databaseReferenceTotalBillings= FirebaseDatabase.getInstance().getReference().child("Calculations").child(totalsBillphnnumber);
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int ssd=snapshot.child("Bill_Total").getValue(Integer.class);
                billLeftTvs.setText(ssd+"");

                int dds=snapshot.child("Paid_Total").getValue(Integer.class);
                billLeftPaids.setText(dds+"");

                int results=(ssd-dds);
                billLefttobepaids.setText(results+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceTotalBillings.addValueEventListener(listener);

    }
}