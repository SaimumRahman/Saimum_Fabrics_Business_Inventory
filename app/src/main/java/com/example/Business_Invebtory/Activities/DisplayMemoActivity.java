package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.Business_Invebtory.ModalClasses.ImageModelClass;
import com.example.groceryapplication.R;
import com.example.Business_Invebtory.ViewHolder.MemosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DisplayMemoActivity extends AppCompatActivity {

   private RecyclerView recyclerViewMemos;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReferenceMemos,databaseReferenceDelMemos;
    private String seacrhingText;
    private ImageButton search_iconMemos;
    private EditText search_product_nameMemos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_memo);


        search_iconMemos=findViewById(R.id.search_iconMemo);
        search_product_nameMemos=findViewById(R.id.search_product_nameMemo);
        recyclerViewMemos=findViewById(R.id.recyclerViewMemo);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewMemos.setLayoutManager(layoutManager);

        databaseReferenceMemos= FirebaseDatabase.getInstance().getReference().child("Memos");
        databaseReferenceDelMemos=FirebaseDatabase.getInstance().getReference().child("Memos");

        search_iconMemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seacrhingText= search_product_nameMemos.getText().toString();
                onStart();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ImageModelClass> options=new FirebaseRecyclerOptions.Builder<ImageModelClass>()
                .setQuery(databaseReferenceMemos.orderByChild("Shop_Names").startAt(seacrhingText),ImageModelClass.class).build();

        FirebaseRecyclerAdapter<ImageModelClass, MemosViewHolder> adapter=
                new FirebaseRecyclerAdapter<ImageModelClass, MemosViewHolder>(options) {


                    @NonNull
                    @Override
                    public MemosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View views= LayoutInflater.from(parent.getContext()).inflate(R.layout.memolayoutdesignview,parent,false);
                        MemosViewHolder holders=new MemosViewHolder(views);
                        return holders;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull MemosViewHolder holder, int position, @NonNull ImageModelClass model) {


                        holder.nameMemotxts.setText(model.getShop_Names());
                        holder.dateMemotxts.setText(model.getDate());
                        Picasso.get().load(model.getImage()).into(holder.showImageMemos);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(DisplayMemoActivity.this, ImageFullView.class);
                                intent.putExtra("imageViews",model.getImage());
                                startActivity(intent);
                            }
                        });

//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                CharSequence charSequenceBill[]=new CharSequence[]{
//                                        "Yes","No"
//                                };
//                                AlertDialog.Builder alertsBill=new AlertDialog.Builder(DisplayMemoActivity.this);
//                                alertsBill.setTitle("DO YOU WANT TO DELETE THIS ENTRY ?");
//                                alertsBill.setItems(charSequenceBill, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if(which==0){
//                                            String si=model.getTime();
//                                            databaseReferenceDelMemos.child(si).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    Toast.makeText(DisplayMemoActivity.this,"DELETED",Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//
//                                        }else if(which==1) {
//
//                                        }
//                                    }
//                                });
//                                alertsBill.show();
//                            }
//                        });


                    }
                };
        recyclerViewMemos.setAdapter(adapter);
        adapter.startListening();

    }
}