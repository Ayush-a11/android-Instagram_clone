package com.example.final_instagram;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class CameraFragment extends Fragment {
    private Button openCamera;
    private ImageView photo;
    private Uri ImageUri;
    private final int capture_code = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_camera, container, false);
        openCamera=view.findViewById(R.id.openCameara);
        photo=view.findViewById(R.id.Photo);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prmission();
            }
        });

        return view;
    }
    private void prmission()
    {
        //new method
        Dexter.withContext(getActivity())
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                CameraIntent();
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

    private void CameraIntent() {
        Intent i = new Intent();
        i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,capture_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==capture_code){
            ImageUri=data.getData();
            photo.setImageURI(ImageUri);

        }
    }
}