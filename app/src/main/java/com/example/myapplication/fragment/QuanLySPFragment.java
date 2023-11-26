package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class QuanLySPFragment extends Fragment  {
    ListView listView;
    SanPhamDAO dao;
    SanPhamAdapter adapter;
    ArrayList<SanPham> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_s_p, container, false);

        listView = view.findViewById(R.id.lvQLSP);
        dao = new SanPhamDAO(getActivity());

        capNhat();
        return view;
    }

    void capNhat() {
        list = (ArrayList<SanPham>) dao.getAll();
        adapter = new SanPhamAdapter(getContext(), list, getActivity(), dao);
        listView.setAdapter(adapter);
    }

}