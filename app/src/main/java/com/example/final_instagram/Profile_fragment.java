package com.example.final_instagram;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class Profile_fragment extends Fragment {
    private ImageView profile;
    private Button bt1,bt2;
    private TextView Username1,Userbio,Post;
    private ProgressBar PB1,PB2;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        //Hooks
        profile=(ImageView)view.findViewById(R.id.profile_image);
        bt2=view.findViewById(R.id.button5);
        bt1=(Button)view.findViewById(R.id.seting);
        PB1=view.findViewById(R.id.progressBar2);
        PB2=view.findViewById(R.id.progressBar3);
        Username1=(TextView)view.findViewById(R.id.Username1);
        Userbio=view.findViewById(R.id.Userbio);
        Post=view.findViewById(R.id.Post);

        //initialization...
        PB1.setVisibility(View.VISIBLE);
        String uid = mAuth.getCurrentUser().getUid();
        initImageLoader();//UniversalImageLoader Configuration...

        //setup....
        update(uid);
        //setup Gridview...
        setupGridview();
        //Button Listener..for setting and EditProfile...
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Profile_view.class));
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),click_on_seting.class));
            }
        });


        return view;
    }

    private void setupGridview() {

        final ArrayList<ContactsContract.Contacts.Photo>photos=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        Query query=reference.child("Photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }

    public void initImageLoader(){
        UniversalImageLoader universalImageLoader=new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }

    private void update(String uid) {

        Query check = FirebaseDatabase.getInstance().getReference("uid").orderByChild(uid);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child(uid).child("username").getValue().toString();
                String url =(snapshot.child(uid).child("profileurl").getValue()).toString();
                String desc = snapshot.child(uid).child("userdescp").getValue().toString();
                String post = snapshot.child(uid).child("post").getValue().toString();
                if(!url.isEmpty()){
                    PB2.setVisibility(View.VISIBLE);
                    UniversalImageLoader.SetImage(url,profile,"");
                    PB2.setVisibility(View.GONE);
                }
                if (!desc.isEmpty())
                    Userbio.setText(desc);
                Username1.setText(Name);
                Post.setText(post);
                PB1.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
