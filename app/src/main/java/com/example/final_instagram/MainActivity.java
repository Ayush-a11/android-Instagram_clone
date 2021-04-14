package com.example.final_instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText phone, password;
    private Button Login;
    private TextView Register;
    private static final String TAG = "MainActivity";

    private boolean validatephone() {
        if (phone.getText().toString().length() <= 0) {
            phone.setError("Field Cannot be empty");
            return false;
        } else {
            return true;
        }

    }

    private boolean validatepass() {
        if (password.getText().toString().length() <= 0) {
            password.setError("Field Cannot be empty");
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this,Home.class));
            finish();
        }
        phone = (EditText) findViewById(R.id.Logphone);
        password = (EditText) findViewById(R.id.LogPassword);
        Login = (Button) findViewById(R.id.Logbutt);
        mAuth = FirebaseAuth.getInstance();
        Register = (TextView) findViewById(R.id.LognewUser);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatephone() || !validatepass())
                    return;
                else
                    Check();
            }
        });
    }
    private void Check() {
        final String phonenumber = phone.getText().toString().trim();
        final String pas1 = password.getText().toString().trim();
        Query check1 = FirebaseDatabase.getInstance().getReference("User").orderByChild("phone").equalTo(phonenumber);
        check1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String syspass = dataSnapshot.child(phonenumber).child("password").getValue(String.class);
                    if (syspass.equals(pas1)) {
                        String sysname = dataSnapshot.child("name").getValue(String.class);
                        String sysemail = dataSnapshot.child("mail").getValue(String.class);
                        Toast.makeText(MainActivity.this, "loged in scuessfully", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "passeord incorect", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No such user exist", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}