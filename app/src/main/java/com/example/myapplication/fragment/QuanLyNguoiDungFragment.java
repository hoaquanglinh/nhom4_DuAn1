package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.TaiKhoanAdapter;
import com.example.myapplication.model.TaiKhoanND;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyNguoiDungFragment extends Fragment {
    ListView lvTaiKhoan;
    ArrayList<TaiKhoanND> list;
    static TaiKhoanNDDAO dao;
    TaiKhoanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_nguoi_dung, container, false);
        getActivity().setTitle("Quản lí người dùng");
        lvTaiKhoan = view.findViewById(R.id.lvQLND);
        dao = new TaiKhoanNDDAO(getActivity());
        capNhatLv();
        return view;
    }
    void capNhatLv() {
        list = (ArrayList<TaiKhoanND>) dao.getAll();
        ArrayList<TaiKhoanND> list1 = new ArrayList<>();
        for (TaiKhoanND tk: list){
            if(!tk.getTaiKhoanND().equals("admin")){
                list1.add(tk);
            }
        }
        adapter = new TaiKhoanAdapter(getActivity(), this, list1);
        lvTaiKhoan.setAdapter(adapter);
    }

    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

}