package com.example.final_instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class spalsh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Spash=new Intent(spalsh.this,MainActivity.class) ;
                startActivity(Spash);
                finish();
            }
        },3000);
    }
}