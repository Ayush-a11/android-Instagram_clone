package com.example.final_instagram;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_fragment()).commit();
   }

   private BottomNavigationView.OnNavigationItemSelectedListener navigationSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           Fragment selectedFragment=new Home_fragment();
           switch (item.getItemId()) {
               case R.id.home:
                   selectedFragment=new Home_fragment();
                   break;
               case R.id.Add:
                   selectedFragment=new Add_fragment();
                   break;
               case R.id.Profile:
                   selectedFragment=new Profile_fragment();
                   break;
           }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
           return true;
       }
   };
}