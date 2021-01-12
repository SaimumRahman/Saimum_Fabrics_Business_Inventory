package Mohajon;

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

import com.example.groceryapplication.BillListsModalClass;
import com.example.groceryapplication.BillViewHolder;
import com.example.groceryapplication.R;
import com.example.groceryapplication.ShowBillActivity;
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

public class ShowBillMohajonActivity extends AppCompatActivity {
    private String billPhn;
    private TextView pleasetxtxsMs;
    private RecyclerView recyclerViewBillListsMs;
    RecyclerView.LayoutManager layoutManager;
    private EditText dateSearchMs;
    private DatabaseReference databaseReferenceBillMs;
    private DatabaseReference databaseReferenceTotalMs,deleteReferenceMs;
    private Button searchBtnMs;
    private String search_input_BillMs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_mohajon);
        billPhn = getIntent().getExtras().get("billsphn").toString();

        pleasetxtxsMs=findViewById(R.id.pleasetxtxM);
        searchBtnMs=findViewById(R.id.search_bill_buttonM);
        dateSearchMs=findViewById(R.id.search_Bill_ETM);

        recyclerViewBillListsMs = findViewById(R.id.recyclerViewBillListM);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewBillListsMs.setLayoutManager(layoutManager);


        databaseReferenceBillMs = FirebaseDatabase.getInstance().getReference().child("Bill_Mohajon").child(billPhn);
        databaseReferenceTotalMs=FirebaseDatabase.getInstance().getReference().child("Bill_Mohajon").child(billPhn);
        deleteReferenceMs=FirebaseDatabase.getInstance().getReference().child("Bill_Mohajon").child(billPhn);

        searchBtnMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_input_BillMs=dateSearchMs.getText().toString();
                // Toast.makeText(getApplicationContext(),"searching",Toast.LENGTH_LONG).show();
                onStart();

            }
        });

        ShowTotals();

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ShowBillMohajonClass> options=new FirebaseRecyclerOptions.Builder<ShowBillMohajonClass>()
                .setQuery(databaseReferenceBillMs.orderByChild("Date").startAt(search_input_BillMs),ShowBillMohajonClass.class).build();

        FirebaseRecyclerAdapter<ShowBillMohajonClass, BillViewHolder> adapter=
                new FirebaseRecyclerAdapter<ShowBillMohajonClass, BillViewHolder>(options) {


                    @NonNull
                    @Override
                    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View views= LayoutInflater.from(parent.getContext()).inflate(R.layout.billdetailsforrmat,parent,false);
                        BillViewHolder holders=new BillViewHolder(views);
                        return holders;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull BillViewHolder holder, int position, @NonNull ShowBillMohajonClass model) {


                        holder.dateView.setText(model.getDate());
                        holder.billingAmount.setText(model.getBill());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence charSequenceBill[]=new CharSequence[]{
                                        "Yes","No"
                                };
                                AlertDialog.Builder alertsBill=new AlertDialog.Builder(ShowBillMohajonActivity.this);
                                alertsBill.setTitle("DO YOU WANT TO DELETE THIS ENTRY ?");
                                alertsBill.setItems(charSequenceBill, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){
                                            String si=model.getTime();
                                            deleteReferenceMs.child(si).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(ShowBillMohajonActivity.this,"DELETED",Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }else if(which==1) {

                                        }
                                    }
                                });
                                alertsBill.show();
                            }
                        });


                    }
                };
        recyclerViewBillListsMs.setAdapter(adapter);
        adapter.startListening();

    }
    private void ShowTotals(){
        databaseReferenceTotalMs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int Fiamanillah = 0;
                for(DataSnapshot snapshots:snapshot.getChildren()){
                    if (snapshots.exists()){
                        int Allahu=Integer.parseInt(snapshots.child("Bill").getValue(String.class));
                        Fiamanillah=Fiamanillah+Allahu;

                        pleasetxtxsMs.setText(Fiamanillah+"");
                        final DatabaseReference databaseReferenceCalculations;
                        databaseReferenceCalculations=FirebaseDatabase.getInstance().getReference().child("Calculations_Mohajon").child(billPhn);

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
                        pleasetxtxsMs.setText("0");
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