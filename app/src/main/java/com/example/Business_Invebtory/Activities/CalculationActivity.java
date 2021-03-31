package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapplication.R;
import com.example.groceryapplication.databinding.ActivityCalculationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CalculationActivity extends AppCompatActivity {
private String uniquePhn;
ActivityCalculationBinding calculationBinding;
private String  saveCurrentDate,saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calculationBinding=ActivityCalculationBinding.inflate(getLayoutInflater());
        View calculationsView=calculationBinding.getRoot();
        setContentView(calculationsView);

        uniquePhn=getIntent().getExtras().get("phonepass").toString();
        calculationBinding.shopNametxtView.setText(uniquePhn);


        calculationBinding.submitAmountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AmountEntry();

            }
        });
        calculationBinding.showAmountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passPhntoshowbill=new Intent(getApplicationContext(),ShowBillActivity.class);
                passPhntoshowbill.putExtra("billsphn",uniquePhn);
                passPhntoshowbill.putExtra("billsTimes",saveCurrentTime);
                startActivity(passPhntoshowbill);
            }
        });
        calculationBinding.submitPaidAmountbtn.setOnClickListener(v -> PaidAmountEntry());

        calculationBinding.showPaidAmountbtn.setOnClickListener(v -> {
            Intent passPaidPhn=new Intent(getApplicationContext(),ShowPaidAmounts.class);
            passPaidPhn.putExtra("passPaid",uniquePhn);
            passPaidPhn.putExtra("billsTime",saveCurrentTime);

            startActivity(passPaidPhn);
        });
        calculationBinding.bakiBtn.setOnClickListener(v -> {
            Intent newss=new Intent(CalculationActivity.this, ShowtotalsActivity.class);
            newss.putExtra("totalBillPhn",uniquePhn);
            startActivity(newss);
        });
        calculationBinding.shopNametxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+uniquePhn));

                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(CalculationActivity.this,"Check Permission",Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(CalculationActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

                }
                else {

                    startActivity(callIntent);

                }

            }
        });



    }



    private void AmountEntry() {


        Calendar callForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(callForDate.getTime());


       String  amounts=calculationBinding.amountEditText.getText().toString();

        if(TextUtils.isEmpty(amounts)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef = FirebaseDatabase.getInstance().getReference().child("Bill").child(uniquePhn).child(saveCurrentTime);

            final HashMap<String, Object> amountHash = new HashMap<>();
            amountHash.put("Bill", amounts);
            amountHash.put("Date", saveCurrentDate);
            amountHash.put("Time", saveCurrentTime);

            amountRef.updateChildren(amountHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        calculationBinding.amountEditText.setText("");
                        Toast.makeText(getApplicationContext(), "Bill Added Successfully", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Bill not Added", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }
    private void PaidAmountEntry() {


        Calendar callForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(callForDate.getTime());


        String amountsPaid=calculationBinding.amountPaidEditText.getText().toString();

        if(TextUtils.isEmpty(amountsPaid)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef= FirebaseDatabase.getInstance().getReference().child("Paid").child(uniquePhn).child(saveCurrentTime);

            final HashMap<String,Object>amountHash=new HashMap<>();
            amountHash.put("Amount_Paid",amountsPaid);
            amountHash.put("Date",saveCurrentDate);
            amountHash.put("Time", saveCurrentTime);

            amountRef.updateChildren(amountHash).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    calculationBinding.amountPaidEditText.setText("");
                    Toast.makeText(getApplicationContext(),"Paid Amount Added Successfully",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Paid Amount not Added",Toast.LENGTH_LONG).show();
                }
            });



        }

    }




}


