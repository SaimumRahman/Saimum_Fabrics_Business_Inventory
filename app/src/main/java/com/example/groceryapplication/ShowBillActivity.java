package com.example.groceryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.FileStore;
import java.util.HashMap;

public class ShowBillActivity extends AppCompatActivity {
    private String billPhn;
    private TextView pleasetxtxs;
    private RecyclerView recyclerViewBillLists;
    RecyclerView.LayoutManager layoutManager;
    private EditText dateSearch;
    private DatabaseReference databaseReferenceBill;
    private DatabaseReference databaseReferenceTotal;
    private Button searchBtn;
    private String search_input_Bill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);

        billPhn = getIntent().getExtras().get("billsphn").toString();

        pleasetxtxs=findViewById(R.id.pleasetxtx);
        searchBtn=findViewById(R.id.search_bill_button);
        dateSearch=findViewById(R.id.search_Bill_ET);

        recyclerViewBillLists = findViewById(R.id.recyclerViewBillList);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewBillLists.setLayoutManager(layoutManager);

        databaseReferenceBill = FirebaseDatabase.getInstance().getReference().child("Bill").child(billPhn);
        databaseReferenceTotal=FirebaseDatabase.getInstance().getReference().child("Bill").child(billPhn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_input_Bill=dateSearch.getText().toString();
                // Toast.makeText(getApplicationContext(),"searching",Toast.LENGTH_LONG).show();
                onStart();

            }
        });

        ShowTotals();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<BillListsModalClass>options=new FirebaseRecyclerOptions.Builder<BillListsModalClass>()
                .setQuery(databaseReferenceBill.orderByChild("Date").startAt(search_input_Bill),BillListsModalClass.class).build();

        FirebaseRecyclerAdapter<BillListsModalClass,BillViewHolder>adapter=
                new FirebaseRecyclerAdapter<BillListsModalClass, BillViewHolder>(options) {


            @NonNull
            @Override
            public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View views=LayoutInflater.from(parent.getContext()).inflate(R.layout.billdetailsforrmat,parent,false);
                BillViewHolder holders=new BillViewHolder(views);
                return holders;
            }

            @Override
            protected void onBindViewHolder(@NonNull BillViewHolder holder, int position, @NonNull BillListsModalClass model) {


                    holder.dateView.setText(model.getDate());
                    holder.billingAmount.setText(model.getBill());

            }
        };
        recyclerViewBillLists.setAdapter(adapter);
        adapter.startListening();

}
private void ShowTotals(){
databaseReferenceTotal.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        int Fiamanillah = 0;
        for(DataSnapshot snapshots:snapshot.getChildren()){
            if (snapshots.exists()){
                int Allahu=Integer.parseInt(snapshots.child("Bill").getValue(String.class));
                Fiamanillah=Fiamanillah+Allahu;

                pleasetxtxs.setText(Fiamanillah+"");
                final DatabaseReference databaseReferenceCalculations;
                databaseReferenceCalculations=FirebaseDatabase.getInstance().getReference().child("Calculations").child(billPhn);

                final HashMap<String, Object> calHash = new HashMap<>();
                calHash.put("Bill_Total", Fiamanillah);

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
                pleasetxtxs.setText("0");
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