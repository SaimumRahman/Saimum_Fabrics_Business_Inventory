package com.example.Business_Invebtory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.groceryapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //status Bar hide start
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w =getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,  WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
    }
}