package com.example.groceryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class CalculationActivity extends AppCompatActivity {
private String uniquePhn;
private TextView shopNametxtViews;
private EditText amountEditTexts,amountPaidEditTexts;
private Button submitAmountbtns,showAmountbtns,submitPaidAmountbtns,showPaidAmountbtns,bakiBtns;
private String  saveCurrentDate,saveCurrentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        uniquePhn=getIntent().getExtras().get("phonepass").toString();


        bakiBtns=findViewById(R.id.bakiBtn);
        shopNametxtViews=findViewById(R.id.shopNametxtView);
        submitAmountbtns=findViewById(R.id.submitAmountbtn);
        amountEditTexts=findViewById(R.id.amountEditText);
        amountPaidEditTexts=findViewById(R.id.amountPaidEditText);
        shopNametxtViews.setText(uniquePhn);


        showAmountbtns=findViewById(R.id.showAmountbtn);
        submitPaidAmountbtns=findViewById(R.id.submitPaidAmountbtn);
        showPaidAmountbtns=findViewById(R.id.showPaidAmountbtn);
        submitAmountbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountEntry();

            }
        });
        showAmountbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passPhntoshowbill=new Intent(getApplicationContext(),ShowBillActivity.class);
                passPhntoshowbill.putExtra("billsphn",uniquePhn);
                passPhntoshowbill.putExtra("billsTimes",saveCurrentTime);
                startActivity(passPhntoshowbill);
            }
        });
        submitPaidAmountbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaidAmountEntry();
            }
        });
        showPaidAmountbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passPaidPhn=new Intent(getApplicationContext(),ShowPaidAmounts.class);
                passPaidPhn.putExtra("passPaid",uniquePhn);
                passPaidPhn.putExtra("billsTime",saveCurrentTime);

                startActivity(passPaidPhn);
            }
        });
        bakiBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newss=new Intent(CalculationActivity.this, ShowtotalsActivity.class);
                newss.putExtra("totalBillPhn",uniquePhn);
                startActivity(newss);
            }
        });
        shopNametxtViews.setOnClickListener(new View.OnClickListener() {
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


       String  amounts=amountEditTexts.getText().toString();

        if(TextUtils.isEmpty(amounts)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef = FirebaseDatabase.getInstance().getReference().child("Bill").child(uniquePhn).child(saveCurrentTime);

            final HashMap<String, Object> amountHash = new HashMap<>();
            amountHash.put("Bill", amounts);
            amountHash.put("Date", saveCurrentDate);

            amountRef.updateChildren(amountHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        amountEditTexts.setText("");
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


        String amountsPaid=amountPaidEditTexts.getText().toString();

        if(TextUtils.isEmpty(amountsPaid)){
            Toast.makeText(this, "Please Amount", Toast.LENGTH_SHORT).show();
        }
        else {

            final DatabaseReference amountRef;
            amountRef= FirebaseDatabase.getInstance().getReference().child("Paid").child(uniquePhn).child(saveCurrentTime);

            final HashMap<String,Object>amountHash=new HashMap<>();
            amountHash.put("Amount_Paid",amountsPaid);
            amountHash.put("Date",saveCurrentDate);

            amountRef.updateChildren(amountHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        amountPaidEditTexts.setText("");
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


