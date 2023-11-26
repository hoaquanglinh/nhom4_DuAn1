package com.example.myapplication.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.ManHinhChinh;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamAdapter;;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    ListView lvproduct;
    ArrayList<SanPham> list;
    SanPhamDAO dao;
    SanPhamAdapter adapter;
    public SanPham item;
    int matknd;
    TaiKhoanNDDAO nddao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        lvproduct = view.findViewById(R.id.lvProduct);
        dao = new SanPhamDAO(getActivity());

        nddao = new TaiKhoanNDDAO(getActivity());
        item = new SanPham();

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");

        matknd = nddao.getMatkndFromTaikhoannd(user, pass);

        capNhatlv();
        return view;
    }

    void capNhatlv() {
        list = (ArrayList<SanPham>) dao.getAllByMAtknd(matknd);
        adapter = new SanPhamAdapter(getContext(), list, getActivity(), dao);
        adapter.notifyDataSetChanged();
        lvproduct.setAdapter(adapter);
    }

}