package com.example.Business_Invebtory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.Business_Invebtory.ModalClasses.ImageModelClass;
import com.example.groceryapplication.R;
import com.squareup.picasso.Picasso;

public class ImageFullView extends AppCompatActivity  {

    private ImageView fullViewImageMemos;
    private String imageDowns;
    final static float move=200;
    float ratio=1.0f;
    int baseDist;
    float baseRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_view);

        fullViewImageMemos=findViewById(R.id.fullViewImageMemo);

        imageDowns=getIntent().getExtras().get("imageViews").toString();

        ImageModelClass imageModelClass=new ImageModelClass();

        Picasso.get().load(imageDowns).into(fullViewImageMemos);
       // Picasso.get().load(imageDowns.into(imageModelClass.getImage());


    }


}