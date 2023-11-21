package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.fragment.AddFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ManHinhChinh extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.add) {
                    fragment = new AddFragment();
                } else if (item.getItemId() == R.id.product) {
                    fragment = new ProductFragment();
                } else if (item.getItemId() == R.id.home) {
                    fragment = new HomeFragment();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.flContent, fragment);
                    ft.commit();
                }
                return true;
            }
        });

        // Hiển thị màn hình Home khi vào ứng dụng
        fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContent, fragment);
        ft.commit();
    }
}