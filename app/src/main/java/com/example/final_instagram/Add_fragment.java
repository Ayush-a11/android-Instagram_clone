package com.example.final_instagram;

import android.Manifest;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

public class Add_fragment extends Fragment {
    private ImageView imageView1;
    private Button opengallery;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Uri Imageuri;
    String img;
    int count;
    DatabaseReference reference;
    String uid=mAuth.getCurrentUser().getUid();
    private static final int GALLERY_PICK_CODE=100;

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add,container,false);
        imageView1=view.findViewById(R.id.image1);
        opengallery=view.findViewById(R.id.openGalllery);
            FirebaseMethods_getdata();
            initImageLoader();
        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setimage();
            }
        });
        return view;
    }
    public void initImageLoader(){
        UniversalImageLoader universalImageLoader=new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }
    private void setimage()
    {
        //new method
        Dexter.withContext(getActivity())
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
    private void galleryInt(){
        Intent gallery=new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery,GALLERY_PICK_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        {
            if(requestCode==GALLERY_PICK_CODE ){
                Imageuri=data.getData();
//                imageView1.setImageURI(Imageuri);
                UniversalImageLoader.SetImage(Imageuri.toString(),imageView1,"");
                upload();

            }
        }
    }
    private void FirebaseMethods_getdata(){
            reference=FirebaseDatabase.getInstance().getReference("uid");
        Query query= reference.orderByChild(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(uid).child("post").getValue().toString().isEmpty())
                    count=0;
                else
                count= Integer.parseInt(snapshot.child(uid).child("post").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void FirebaseMethods_setdata(){
            reference=FirebaseDatabase.getInstance().getReference("uid");
            reference.child(uid).child("post").setValue(count);
            String cc= String.valueOf(count);
            reference=FirebaseDatabase.getInstance().getReference("Photos");
            Helper3 h4=new Helper3(img,"");
            reference.child(cc).setValue(h4);
    }

    private void upload() {
            mAuth=FirebaseAuth.getInstance();


            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference put = mStorageRef.child("/photos/Users" + "/" + uid + "/photos" +count+ "/");
            put.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   put.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            img = uri.toString();
//                          progressBar.setVisibility(View.GONE);
                            count++;
                            FirebaseMethods_setdata();
                     }
                    });
             }
            });

    }
}
