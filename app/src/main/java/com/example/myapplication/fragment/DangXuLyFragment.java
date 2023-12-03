package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

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

public class DangXuLyFragment extends Fragment {
    ListView listView;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    DonHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<DonHang> listDH;
    DonHang donHang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_xu_ly, container, false);
        listView = view.findViewById(R.id.lvDangXuLy);

        dao = new SanPhamDAO(getActivity());
        donHangDAO = new DonHangDAO(getContext());
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getActivity());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        int matknd = nddao.getMatkndFromTaikhoannd(user);
        int mand = nguoiDungDAO.getMandByMatknd(matknd);

        if (!user.equals("admin")){
            list = (ArrayList<SanPham>) donHangDAO.getListSanPhamTrongDonHang(mand);
            listDH = (ArrayList<DonHang>) donHangDAO.getAllByMand(mand);
        }else{
            list = (ArrayList<SanPham>) donHangDAO.getSanPhamByMadh();
            listDH = (ArrayList<DonHang>) donHangDAO.getAll();
        }

        adapter = new DonHangAdapter(getActivity(), list, listDH, dao);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

}