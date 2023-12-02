package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.myapplication.adapter.DaGiaoAdapter;
import com.example.myapplication.adapter.DonHangAdapter;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class DaGiaoFragment extends Fragment {
    ListView listView;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<DonHang> listDH;
    DaGiaoAdapter daGiaoAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_da_giao, container, false);

        listView = view.findViewById(R.id.lvDaGiao);

        dao = new SanPhamDAO(getActivity());
        donHangDAO = new DonHangDAO(getContext());
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getActivity());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        int matknd = nddao.getMatkndFromTaikhoannd(user);
        int mand = nguoiDungDAO.getMandByMatknd(matknd);

        list = (ArrayList<SanPham>) donHangDAO.getListSanPhamByTrangThai(mand, 2);
        listDH = (ArrayList<DonHang>) donHangDAO.getAll();

        daGiaoAdapter = new DaGiaoAdapter(getActivity(), list, listDH, dao);
        listView.setAdapter(daGiaoAdapter);

        return view;
    }
}