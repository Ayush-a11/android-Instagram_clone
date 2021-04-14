package com.example.final_instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText Username,Usermail,Userpass;
    Button Register;
    CountryCodePicker countryCodePicker;
    TextView Reglog;
    FirebaseAuth firebaseAuth;
    private boolean mk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        countryCodePicker=findViewById(R.id.country);
        Username=(EditText)findViewById(R.id.Regusername);
        Usermail=(EditText)findViewById(R.id.Regemail);
        Userpass=(EditText)findViewById(R.id.Regpassword);
        Register=(Button)findViewById(R.id.Regbutton);
        Reglog=(TextView)findViewById(R.id.RegLog);
        Reglog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setdata();
            }
        });

    }

    private void Setdata() {
        String name=Username.getText().toString();
        String email=Usermail.getText().toString();
        String pass=Userpass.getText().toString();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");


      //  if(allreadyexist()) {
            Intent intent=new Intent(RegisterActivity.this,verification.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("pass",pass);
            startActivity(intent);
//            Hell H2 = new Hell(name, pass, phone, email);
//            databaseReference.child(phone).setValue(H2);
//            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//            startActivity(intent);
       // }
        //else
          //  Toast.makeText(this,"phone no allready exist",Toast.LENGTH_SHORT).show();
    }

//    private boolean allreadyexist() {
//
//        //final String phonenumber=Userphone.getText().toString().trim();
//        final String pas1=Userpass.getText().toString().trim();
//        Query check1=FirebaseDatabase.getInstance().getReference("User").orderByChild("phone").equalTo(phonenumber);
//        check1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//                    mk=false;
//                mk=true;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return mk;
//    }
}