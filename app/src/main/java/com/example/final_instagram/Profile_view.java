package com.example.final_instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_view extends AppCompatActivity {
    private EditText Username, Useremial, Userpassword, Userphone, Userbio;
    private Button Update;
    private CircleImageView profile_image;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    private static final int GALLERY_PICK_CODE = 100;
    Uri imageuri;
    String imageurl;
    DatabaseReference reference;
    String img;
    String uid;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        initImageLoader();
        progressBar=findViewById(R.id.progressBar1);
        Username = findViewById(R.id.Username);
        Useremial = findViewById(R.id.Useremail);
        Userpassword = findViewById(R.id.password);
        Userphone = findViewById(R.id.phone);
        Userbio = findViewById(R.id.bio);
        Update = findViewById(R.id.button4);
        reference = FirebaseDatabase.getInstance().getReference("uid");
        profile_image = findViewById(R.id.profile_image);
        progressBar.setVisibility(View.GONE);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permsion();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        SetText(uid);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(uid);
            }
        });

    }
    public void initImageLoader(){
        UniversalImageLoader universalImageLoader=new UniversalImageLoader(Profile_view.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }

    private void update(String uid) {
        reference.child(uid).child("username").setValue(Username.getText().toString());
        reference.child(uid).child("useremail").setValue(Useremial.getText().toString());
        reference.child(uid).child("userpass").setValue(Userpassword.getText().toString());
        reference.child(uid).child("userdescp").setValue(Userbio.getText().toString());
        if (img != null)
            reference.child(uid).child("profileurl").setValue(img);
        reference.child(uid).child("Usernuber").setValue(Userphone.getText().toString());
        Toast.makeText(this, "data has been updated", Toast.LENGTH_SHORT).show();
    }

    private void SetText(String uid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query check = FirebaseDatabase.getInstance().getReference("uid").orderByChild(uid);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child(uid).child("username").getValue().toString();
                String mail = snapshot.child(uid).child("useremail").getValue().toString();
                String pass = snapshot.child(uid).child("userpass").getValue().toString();
                String phone = snapshot.child(uid).child("usernuber").getValue().toString();
                String desc = snapshot.child(uid).child("userdescp").getValue().toString();
                String url =(snapshot.child(uid).child("profileurl").getValue()).toString();
                if(url!=null) {
                    //Glide.with(Profile_view.this).load(url).into(profile_image);
                    UniversalImageLoader.SetImage(url.toString(), profile_image, "");
                }
                Username.setText(Name);
                Useremial.setText(mail);
                Userpassword.setText(pass);
                Userphone.setText(phone);
                if (desc != null)
                    Userbio.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void permsion() {
        //new method
        Dexter.withContext(Profile_view.this)
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                galleryInt();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken Token) {
                Token.continuePermissionRequest();

            }
        }).check();

    }

    private void galleryInt() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, GALLERY_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == GALLERY_PICK_CODE) {
            imageuri = data.getData();
//            profile_image.setImageURI(imageuri);
            //universal imageLodaer
            progressBar.setVisibility(View.VISIBLE);
            UniversalImageLoader.SetImage(imageuri.toString(),profile_image,"");
            upload();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        private void upload() {
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference put = mStorageRef.child("/photos/Users" + "/" + uid + "/dp" + "/");
            put.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mStorageRef.child("/photos/Users" + "/" + uid + "/dp" + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            img = uri.toString();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });

        }
    }