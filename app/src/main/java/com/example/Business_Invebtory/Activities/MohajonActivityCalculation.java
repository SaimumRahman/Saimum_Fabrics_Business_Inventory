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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MohajonActivityCalculation extends AppCompatActivity {
    private String uniquePhn;
    private TextView shopNametxtViewsMs;
    private EditText amountEditTextsMs,amountPaidEditTextsMs;
    private Button submitAmountbtnsMs,showAmountbtnsMs,submitPaidAmountbtnsMs,showPaidAmountbtnsMs,bakiBtnsMs;
    private String  saveCurrentDateMs,saveCurrentTimeMs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohajon_calculation);

        uniquePhn=getIntent().getExtras().get("phonepass").toString();


        bakiBtnsMs=findViewById(R.id.bakiBtnM);
        shopNametxtViewsMs=findViewById(R.id.shopNametxtViewM);
        submitAmountbtnsMs=findViewById(R.id.submitAmountbtnM);
        amountEditTextsMs=findViewById(R.id.amountEditTextM);
        amountPaidEditTextsMs=findViewById(R.id.amountPaidEditTextM);
        shopNametxtViewsMs.setText(uniquePhn);

        showAmountbtnsMs=findViewById(R.id.showAmountbtnM);
        submitPaidAmountbtnsMs=findViewById(R.id.submitPaidAmountbtnM);
        showPaidAmountbtnsMs=findViewById(R.id.showPaidAmountbtnM);

        submitAmountbtnsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AmountEntryM();

            }
        });
        showAmountbtnsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passPhntoshowbill=new Intent(MohajonActivityCalculation.this, ShowBillMohajonActivity.class);
                passPhntoshowbill.putExtra("billsphn",uniquePhn);
                passPhntoshowbill.putExtra("billsTimes",saveCurrentTimeMs);
                startActivity(passPhntoshowbill);
            }
        });
        submitPaidAmountbtnsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PaidAmountEntryM();

            }
        });
        showPaidAmountbtnsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passPaidPhn=new Intent(getApplicationContext(), ShowPaidAmounts.class);
                passPaidPhn.putExtra("passPaid",uniquePhn);
                passPaidPhn.putExtra("billsTime",saveCurrentTimeMs);

                startActivity(passPaidPhn);
            }
        });
        bakiBtnsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newss=new Intent(MohajonActivityCalculation.this, ShowtotalsActivity.class);
                newss.putExtra("totalBillPhn",uniquePhn);
                startActivity(newss);
            }
        });
        shopNametxtViewsMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+uniquePhn));

                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MohajonActivityCalculation.this,"Check Permission",Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MohajonActivityCalculation.this,new String[]{Manifest.permission.CALL_PHONE},1);

                }
                else {

                    startActivity(callIntent);

                }

            }
        });

    }



    private void AmountEntryM() {


        Calendar callForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDateMs=currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTimeMs=currentTime.format(callForDate.getTime());


        String  amounts=amountEditTextsMs.getText().toString();

        if(TextUtils.isEmpty(amounts)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef = FirebaseDatabase.getInstance().getReference().child("Bill_Mohajon").child(uniquePhn).child(saveCurrentTimeMs);

            final HashMap<String, Object> amountHash = new HashMap<>();
            amountHash.put("Bill", amounts);
            amountHash.put("Date", saveCurrentDateMs);
            amountHash.put("Time", saveCurrentTimeMs);

            amountRef.updateChildren(amountHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        amountEditTextsMs.setText("");
                        Toast.makeText(getApplicationContext(), "Bill Added Successfully", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Bill not Added", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }
    private void PaidAmountEntryM() {


        Calendar callForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDateMs=currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTimeMs=currentTime.format(callForDate.getTime());


        String amountsPaid=amountPaidEditTextsMs.getText().toString();

        if(TextUtils.isEmpty(amountsPaid)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef= FirebaseDatabase.getInstance().getReference().child("Paid_Mohajon").child(uniquePhn).child(saveCurrentTimeMs);

            final HashMap<String,Object>amountHash=new HashMap<>();
            amountHash.put("Amount_Paid",amountsPaid);
            amountHash.put("Date",saveCurrentDateMs);
            amountHash.put("Time", saveCurrentTimeMs);

            amountRef.updateChildren(amountHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        amountPaidEditTextsMs.setText("");
                        Toast.makeText(getApplicationContext(),"Paid Amount Added Successfully",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Paid Amount not Added",Toast.LENGTH_LONG).show();
                    }
                }
            });



        }

    }


}
