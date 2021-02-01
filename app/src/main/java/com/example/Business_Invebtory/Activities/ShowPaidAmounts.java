package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Business_Invebtory.ViewHolder.PaidViewHolder;
import com.example.Business_Invebtory.ModalClasses.PaidBillModal;
import com.example.groceryapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ShowPaidAmounts extends AppCompatActivity {
private RecyclerView recyclerViewPaidLists;
private TextView paidBillTotaltxts;
RecyclerView.LayoutManager layoutManager;
private String paidphn,billTime;
private DatabaseReference databaseReferenceBillPaid,deleteRef;
    private Button searchBtnpaid;
    private String search_input_BillPaid;
    private EditText dateSearchpaid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_paid_amounts);

        paidphn=getIntent().getExtras().get("passPaid").toString();
     // billTime=getIntent().getExtras().get("billsTime").toString();

        searchBtnpaid=findViewById(R.id.search_paid_button);
        dateSearchpaid=findViewById(R.id.search_Paid_ET);
        paidBillTotaltxts=findViewById(R.id.paidBillTotaltxt);
        recyclerViewPaidLists=findViewById(R.id.recyclerViewPaidList);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPaidLists.setLayoutManager(layoutManager);



        deleteRef=FirebaseDatabase.getInstance().getReference().child("Paid").child(paidphn);
        databaseReferenceBillPaid = FirebaseDatabase.getInstance().getReference().child("Paid").child(paidphn);
        searchBtnpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_input_BillPaid=dateSearchpaid.getText().toString();
                // Toast.makeText(getApplicationContext(),"searching",Toast.LENGTH_LONG).show();
                onStart();

            }
        });
        ShowTotals();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<PaidBillModal> options = new FirebaseRecyclerOptions.Builder<PaidBillModal>()
                .setQuery(databaseReferenceBillPaid.orderByChild("Date").startAt(search_input_BillPaid), PaidBillModal.class).build();

        FirebaseRecyclerAdapter<PaidBillModal, PaidViewHolder> adapter = new FirebaseRecyclerAdapter<PaidBillModal, PaidViewHolder>(options) {


            @NonNull
            @Override
            public PaidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View catches=LayoutInflater.from(parent.getContext()).inflate(R.layout.paidbillformat,parent,false);
                PaidViewHolder paidViewHolder=new PaidViewHolder(catches);
                return paidViewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull PaidViewHolder holder, int position, @NonNull PaidBillModal model) {



                    holder.datePaid.setText(model.getAmount_Paid());
                    holder.amountPaid.setText(model.getDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // String s=model.getTime();
                       // Toast.makeText(ShowPaidAmounts.this, s,Toast.LENGTH_LONG).show();
                        CharSequence sequence[]=new CharSequence[]

                                {
                                        "Yes",
                                        "No"
                                };
                        AlertDialog.Builder alert=new AlertDialog.Builder(ShowPaidAmounts.this);
                        alert.setTitle("DO YOU WANT TO DELETE ?");
                        alert.setItems(sequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    String s=model.getTime();
                                    deleteRef.child(s).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ShowPaidAmounts.this, "The Amount is Deleted Successfully",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                else if(which==1) {

                                }

                            }
                        });
                        alert.show();
                    }
                });
                }

        };
        recyclerViewPaidLists.setAdapter(adapter);
        adapter.startListening();
    }
    private void ShowTotals(){
        databaseReferenceBillPaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int Fiamanillah = 0;
                for(DataSnapshot snapshots:snapshot.getChildren()){
                    if (snapshots.exists()){
                        int Allahu=Integer.parseInt(snapshots.child("Amount_Paid").getValue(String.class));
                        Fiamanillah=Fiamanillah+Allahu;

                        paidBillTotaltxts.setText(Fiamanillah+"");

                    final DatabaseReference databaseReferenceCalculations;
                    databaseReferenceCalculations=FirebaseDatabase.getInstance().getReference().child("Calculations").child(paidphn);

                        final HashMap<String, Object> calHash = new HashMap<>();
                        calHash.put("Paid_Total", Fiamanillah);

                        databaseReferenceCalculations.updateChildren(calHash).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Total_Bill Added Successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Total_Bill not Added", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        paidBillTotaltxts.setText("0");
                    }

                    // Toast.makeText(ShowBillActivity.this,Allahu+"",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}