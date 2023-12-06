package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangChiTietAdapter;
import com.example.myapplication.model.Hang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HangFragment extends Fragment {
    private ListView listView;
    private HangDAO dao;
    FloatingActionButton fladd;
    private ArrayList<Hang> list;
    HangChiTietAdapter adapter;
    Dialog dialog;
    EditText edTenHang, edMaHang;
    Hang item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hang, container, false);
        fladd = view.findViewById(R.id.fladdHang);
        listView = view.findViewById(R.id.listViewHang);

        dao = new HangDAO(getContext());

        CapNhatlv();

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd(getActivity(), 0);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                openAdd(getActivity(), 1);
                return false;
            }
        });

        return view;
    }

    public void CapNhatlv() {
        list = (ArrayList<Hang>) dao.getAll();
        ArrayList<Hang> list1 = new ArrayList<>();
        for (Hang hang: list){
            if(hang.getMahang() != 1){
                list1.add(hang);
            }
        }
        adapter = new HangChiTietAdapter(getActivity(), list1, dao, this);
        listView.setAdapter(adapter);
    }

    public void openAdd(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_hang);
        edMaHang = dialog.findViewById(R.id.ed_MaHang);
        edTenHang = dialog.findViewById(R.id.ed_TenHang);

        edMaHang.setEnabled(false);
        if (type != 0) {
            edMaHang.setText(String.valueOf(item.getMahang()));
            edTenHang.setText(item.getTenHang());
        }
        dialog.findViewById(R.id.btnThemHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTenHang.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    item = new Hang();
                    item.setTenHang(edTenHang.getText().toString());
                    if (type == 0){
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            CapNhatlv();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        item.setMahang(Integer.parseInt(edMaHang.getText().toString()));
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            CapNhatlv();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        dialog.show();
    }

    public void xoaHang(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(getActivity(), id);
                CapNhatlv();
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