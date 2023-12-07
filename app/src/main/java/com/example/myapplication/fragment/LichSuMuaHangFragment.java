package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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

public class LichSuMuaHangFragment extends Fragment {
    Toolbar toolbar;
    ListView listView;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    DonHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<DonHang> listDH;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_su_mua_hang, container, false);
        toolbar = view.findViewById(R.id.toolbarLichSuMua);
        listView = view.findViewById(R.id.lvLichSuMuaHang);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Lịch sử mua hàng");

        dao = new SanPhamDAO(getActivity());
        donHangDAO = new DonHangDAO(getContext());
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getActivity());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        int matknd = nddao.getMatkndFromTaikhoannd(user);
        int mand = nguoiDungDAO.getMandByMatknd(matknd);

        list = (ArrayList<SanPham>) donHangDAO.getListSanPhamTrongDonHang(mand);
        listDH = (ArrayList<DonHang>) donHangDAO.getAllByMand(mand);

        ArrayList<DonHang> list1 = new ArrayList<>();
        ArrayList<SanPham> list2 = new ArrayList<>();

        for (DonHang donHang: listDH){
            if (donHang.getTrangthai() == 2){
                list1.add(donHang);
                SanPham sanPham = donHangDAO.getSanPhamByMadh(donHang.getMadh());
                list2.add(sanPham);
            }
        }

        adapter = new DonHangAdapter(getActivity(), list2, list1, dao);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return  view;
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