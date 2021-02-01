package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Business_Invebtory.ModalClasses.PhoneBookClassModel;
import com.example.groceryapplication.R;
import com.example.Business_Invebtory.ViewHolder.PhnBookViewHolderClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneBookActivity extends AppCompatActivity {
    private RecyclerView contactsRecyclerViews;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReferencePhnBook,databaseReferencePhnBookDel;
    private EditText search_contacts_namePhns;
    private ImageButton searchImageBtnPhn;
    private String contacnNAme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);

        search_contacts_namePhns=findViewById(R.id.search_product_namePhn);
        searchImageBtnPhn=findViewById(R.id.search_iconPhn);


        contactsRecyclerViews=findViewById(R.id.contactsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        contactsRecyclerViews.setLayoutManager(layoutManager);

        databaseReferencePhnBook= FirebaseDatabase.getInstance().getReference().child("Phone_Book");
        databaseReferencePhnBookDel= FirebaseDatabase.getInstance().getReference().child("Phone_Book");


        searchImageBtnPhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacnNAme=  search_contacts_namePhns.getText().toString();

                onStart();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<PhoneBookClassModel> options=new FirebaseRecyclerOptions.Builder<PhoneBookClassModel>()
                .setQuery(databaseReferencePhnBook.orderByChild("Name").startAt(contacnNAme),PhoneBookClassModel.class).build();

        FirebaseRecyclerAdapter<PhoneBookClassModel, PhnBookViewHolderClass> adapter=
                new FirebaseRecyclerAdapter<PhoneBookClassModel, PhnBookViewHolderClass>(options) {


                    @NonNull
                    @Override
                    public PhnBookViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View views= LayoutInflater.from(parent.getContext()).inflate(R.layout.contactview,parent,false);
                        PhnBookViewHolderClass holders=new PhnBookViewHolderClass(views);
                        return holders;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull PhnBookViewHolderClass holder, int position, @NonNull PhoneBookClassModel model) {


                       holder.nameContactstxts.setText(model.getName());
                       holder.numberContactstxts.setText(model.getPhone());
                       holder.phoncallContactstxts.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent callIntent=new Intent(Intent.ACTION_CALL);
                               callIntent.setData(Uri.parse("tel:"+model.getPhone()));

                               if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                                   Toast.makeText(PhoneBookActivity.this,"Check Permission",Toast.LENGTH_LONG).show();
                                   ActivityCompat.requestPermissions(PhoneBookActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

                               }
                               else {

                                   startActivity(callIntent);

                               }
                           }
                       });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence charSequenceBill[]=new CharSequence[]{
                                        "Yes","No"
                                };
                                AlertDialog.Builder alertsBill=new AlertDialog.Builder(PhoneBookActivity.this);
                                alertsBill.setTitle("DO YOU WANT TO DELETE THIS ENTRY ?");
                                alertsBill.setItems(charSequenceBill, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){
                                            String si=model.getPhone();
                                            databaseReferencePhnBookDel.child(si).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(PhoneBookActivity.this,"DELETED",Toast.LENGTH_LONG).show();
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
        contactsRecyclerViews.setAdapter(adapter);
        adapter.startListening();

    }
}