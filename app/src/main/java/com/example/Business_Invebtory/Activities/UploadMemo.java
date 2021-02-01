package com.example.Business_Invebtory.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.groceryapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UploadMemo extends AppCompatActivity {

private Button uploadImageBtns,view_memoBtns;
    private static final int galleryPick=1;
    private Uri imageUri;
    private StorageReference memoStorageRef;
    private DatabaseReference memoRefRef,sellerRef;
private ImageView select_memo_images;
    private EditText shopnamememoedits;

    private String saveCurrentTime,downloadImageUrl,saveCurrentDate,shopper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_memo);

     // uniquePhns=getIntent().getExtras().get("totalBillPhn").toString();

        uploadImageBtns=findViewById(R.id.add_memoBtn);
        view_memoBtns=findViewById(R.id.view_memoBtn);
        shopnamememoedits=findViewById(R.id.shopnamememoedit);
        select_memo_images=findViewById(R.id.select_memo_image);


        memoRefRef= FirebaseDatabase.getInstance().getReference().child("Memos");
        memoStorageRef= FirebaseStorage.getInstance().getReference().child("Memos");

        select_memo_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        uploadImageBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreImage();
            }
        });
        view_memoBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(UploadMemo.this,DisplayMemoActivity.class);
                //in.putExtra("PhoneNumbers",uniquePhns);
                startActivity(in);
            }
        });

    }
    private void OpenGallery() {

        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==galleryPick && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            select_memo_images.setImageURI(imageUri);

        }
    }

private void StoreImage(){
     shopper=shopnamememoedits.getText().toString();
    Calendar calendar=Calendar.getInstance();

    SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
    saveCurrentDate=currentDate.format(calendar.getTime());

    SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
    saveCurrentTime=currentTime.format(calendar.getTime());

    if(imageUri==null){
        Toast.makeText(getApplicationContext(),"Product Image is Mendatory",Toast.LENGTH_LONG).show();
    }
    else if(shopper==null){
        Toast.makeText(getApplicationContext(),"Shop Name is Mendatory",Toast.LENGTH_LONG).show();
    }else {
        final StorageReference filePath = memoStorageRef.child(imageUri.getLastPathSegment() + saveCurrentTime + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Memo Uploaded Successfully ", Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(getApplicationContext(), "getting Product Image Url Successfully ", Toast.LENGTH_LONG).show();

                            SaveProductInfoToDatabase();

                        }
                    }
                });
            }
        });
    }
}
    private void SaveProductInfoToDatabase() {



        HashMap<String, Object> productMap=new HashMap<>();

        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("Shop_Names",shopper.toLowerCase());
        productMap.put("Image",downloadImageUrl);



        memoRefRef.child(saveCurrentTime).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                   Toast.makeText(getApplicationContext(),"Image added Successfully ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String e=task.getException().toString();
                    Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}