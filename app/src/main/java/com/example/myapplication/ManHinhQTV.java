package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.fragment.HangFragment;
import com.example.myapplication.fragment.QuanLyMauFragment;
import com.example.myapplication.fragment.QuanLyNguoiDungFragment;
import com.example.myapplication.fragment.QuanLySPFragment;
import com.google.android.material.navigation.NavigationView;

public class ManHinhQTV extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_qtv);

        Toolbar toolbar = findViewById(R.id.toolbarQTV);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                ManHinhQTV.this, drawerLayout, toolbar,
                R.string.open,
                R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_thanhvien){
                    fragment = new QuanLyNguoiDungFragment();
                }else if(item.getItemId() == R.id.nav_sanpham){
                    fragment = new QuanLySPFragment();
                }else if(item.getItemId() == R.id.nav_mau){
                    fragment = new QuanLyMauFragment();
                }else if(item.getItemId() == R.id.nav_hang){
                    fragment = new HangFragment();
                }else if(item.getItemId() == R.id.dangxuat){
                    Intent intent = new Intent(ManHinhQTV.this, ManHinhDangNhap.class);
                    startActivity(intent);
                }

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayoutQTV, fragment)
                            .commit();
                    drawerLayout.close();
                }
                return true;
            }
        });

    }
}