package com.example.final_instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static java.sql.Types.NULL;

public class click_on_seting extends AppCompatActivity {
    Button backButton;
    TextView Profileview,signout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_on_seting);
        backButton=findViewById(R.id.button3);
        Profileview=findViewById(R.id.textView2);
        signout=findViewById(R.id.signout);
        mAuth=FirebaseAuth.getInstance();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //   mAuth.signOut();
                    startActivity(new Intent(click_on_seting.this,MainActivity.class));
                    finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(click_on_seting.this,Profile_fragment.class));
            }
        });
        Profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(click_on_seting.this,Profile_view.class));
            }
        });
    }
}