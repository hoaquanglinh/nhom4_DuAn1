package com.example.myapplication.fragment;

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

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragment extends Fragment {
    ListView listView;
    SanPhamDAO dao;
    GioHangDao gioHangDao;
    SanPhamGioHangAdapter adapter;
    ArrayList<SanPham> list;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        listView = view.findViewById(R.id.listViewGioHang);
        dao = new SanPhamDAO(getActivity());
        gioHangDao = new GioHangDao(getActivity());

        capNhat();
        return view;
    }

    void capNhat() {
        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHang();
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);
        listView.setAdapter(adapter);
    }

}