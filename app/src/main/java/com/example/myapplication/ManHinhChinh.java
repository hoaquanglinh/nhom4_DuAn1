package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.fragment.AddFragment;
import com.example.myapplication.fragment.HangFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.ProductFragment;
import com.example.myapplication.fragment.QuanLyMauFragment;
import com.example.myapplication.fragment.QuanLyNguoiDungFragment;
import com.example.myapplication.fragment.QuanLySPFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ManHinhChinh extends AppCompatActivity {
    Fragment fragment;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);

        bottomNavigationView = findViewById(R.id.navigation);
        navigationView = findViewById(R.id.navigationView1);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");

        if(user.equals("admin")){
            bottomNavigationView.setVisibility(View.GONE);
            navigationView.setVisibility(View.VISIBLE);

            Toolbar toolbar = findViewById(R.id.toolbarQTV);
            DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                    ManHinhChinh.this, drawerLayout, toolbar,
                    R.string.open,
                    R.string.close);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
            drawerLayout.addDrawerListener(drawerToggle);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.nav_sanpham){
                        fragment = new QuanLySPFragment();
                    }else if(item.getItemId() == R.id.nav_mau){
                        fragment = new QuanLyMauFragment();
                    }else if(item.getItemId() == R.id.nav_thanhvien){
                        fragment = new QuanLyNguoiDungFragment();
                    }else if(item.getItemId() == R.id.nav_hang){
                        fragment = new HangFragment();
                    }else{
                        Intent intent = new Intent(ManHinhChinh.this, ManHinhDangNhap.class);
                        startActivity(intent);
                    }

                    drawerLayout.closeDrawer(GravityCompat.START);

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
            fragment = new QuanLySPFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
        }else{
            bottomNavigationView.setVisibility(View.VISIBLE);
            navigationView.setVisibility(View.GONE);

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
}