package Mohajon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.groceryapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowTotalMohajonActivity extends AppCompatActivity {
    private TextView billLeftTvsMs,billLeftPaidsMs,billLefttobepaidsMs;
    private String totalsBillphnnumber;
    private DatabaseReference databaseReferenceTotalBillingsMs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_total_mohajon);

        totalsBillphnnumber=getIntent().getExtras().get("totalBillPhn").toString();
//Toast.makeText(ShowtotalsActivity.this,totalsBillphnnumber,Toast.LENGTH_LONG).show();
        billLeftTvsMs=findViewById(R.id.billLeftTvM);
        billLeftPaidsMs=findViewById(R.id.billLeftPaidM);
        billLefttobepaidsMs=findViewById(R.id.billLefttobepaidM);

        databaseReferenceTotalBillingsMs= FirebaseDatabase.getInstance().getReference().child("Calculations_Mohajon").child(totalsBillphnnumber);
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int ssd=snapshot.child("Bill_Total").getValue(Integer.class);
                billLeftTvsMs.setText(ssd+"");

                int dds=snapshot.child("Paid_Total").getValue(Integer.class);
                billLeftPaidsMs.setText(dds+"");

                int results=(ssd-dds);
                billLefttobepaidsMs.setText(results+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceTotalBillingsMs.addValueEventListener(listener);

    }
}