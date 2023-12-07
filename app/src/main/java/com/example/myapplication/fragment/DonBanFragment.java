package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.DonHangAdapter;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class DonBanFragment extends Fragment {
    ListView listView;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    DonHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    ArrayList<DonHang> listDH;
    int matknd;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_ban, container, false);
        toolbar = view.findViewById(R.id.toolbarDonban);
        nddao = new TaiKhoanNDDAO(getContext());
        listView = view.findViewById(R.id.lvDonBan);
        donHangDAO = new DonHangDAO(getActivity());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        matknd = nddao.getMatkndFromTaikhoannd(user);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Đơn bán");

        list = (ArrayList<SanPham>) donHangDAO.getListSanPhamByMatknd(matknd);
        listDH = (ArrayList<DonHang>) donHangDAO.getAllByMatknd(matknd);

        for (DonHang dh : listDH) {
            Log.d("madh", "madh: " + dh.getMadh());
        }
        adapter = new DonHangAdapter(getActivity(), list, listDH, dao);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}