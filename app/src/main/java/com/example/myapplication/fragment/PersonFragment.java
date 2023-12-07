package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ManHinhDangKy;
import com.example.myapplication.ManHinhDangNhap;
import com.example.myapplication.R;

public class PersonFragment extends Fragment {
    ChinhSuaThongTinFragment fragment;
    TextView tvTenNguoiDung;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        tvTenNguoiDung = view.findViewById(R.id.tvTenNguoiDung);
        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);
        view.findViewById(R.id.btnDangXuat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManHinhDangNhap.class);
                startActivity(intent);
            }
        });
        fragment = new ChinhSuaThongTinFragment();

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");

        tvTenNguoiDung.setText(user);

        view.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        DangXuLyFragment dangXuLyFragment = new DangXuLyFragment();
        view.findViewById(R.id.tvDonMua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, dangXuLyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        DonBanFragment donBanFragment = new DonBanFragment();
        view.findViewById(R.id.tvDonBan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, donBanFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        DoMatKhauFragment doMatKhauFragment = new DoMatKhauFragment();
        view.findViewById(R.id.btnDoiMatKhau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, doMatKhauFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        LichSuMuaHangFragment lichSuMuaHangFragment = new LichSuMuaHangFragment();
        view.findViewById(R.id.tvLichSuMua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, lichSuMuaHangFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}