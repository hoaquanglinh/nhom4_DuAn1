package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MauAdapter;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class QuanLyMauFragment extends Fragment {
    MauSacDAO dao;
    ArrayList<MauSac> list;
    MauAdapter adapter;
    ListView listView;
    FloatingActionButton fab;
    Dialog dialog;
    ImageButton imageButton;
    EditText edTenMau;
    int initialColor= Color.RED;
    MauSac item;
    SharedPreferences sp;
    int dem = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_mau, container, false);
        dao = new MauSacDAO(getContext());
        listView = view.findViewById(R.id.listViewSach);

        fab = view.findViewById(R.id.fabMau);
        fab.setOnClickListener(new View.OnClickListener() {
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

        capNhatlv();
        return view;
    }
    void capNhatlv() {
        list = (ArrayList<MauSac>) dao.getAll();
        adapter = new MauAdapter(getActivity(), list, dao,this);
        listView.setAdapter(adapter);
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
                capNhatlv();
                dialog.cancel();
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getContext(), "Không xóa", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    public  void openAdd(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_them_mau);
        imageButton = dialog.findViewById(R.id.btnImageMau);
        edTenMau = dialog.findViewById(R.id.edTenMau);

        sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        if(sp.getString("color", "").equals("")){

        }else{
            initialColor = (int) Double.parseDouble(sp.getString("color", ""));
        }
        if (type == 0){
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AmbilWarnaDialog dialog1= new AmbilWarnaDialog(getActivity(), initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {
                            imageButton.setBackgroundColor(color);
                            initialColor = color;
                            Log.d("sanpham", "color" + initialColor);
                            sp.edit().putString("color", String.valueOf(color)).commit();
                        }

                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {
                            // cancel was selected by the user
                        }
                    });
                    dialog1.show();
                }
            });
        }
        if(type != 0){
            edTenMau.setText(String.valueOf(item.getTenMau()));
            imageButton.setBackgroundColor(item.getMamau());
        }
        dialog.findViewById(R.id.btnThemMau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edTenMau.getText().toString().isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    item = new MauSac();
                    item.setMamau(initialColor);
                    item.setTenMau(edTenMau.getText().toString());
                    if (type == 0){
                        if(dao.insert(item) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            capNhatlv();
                            dialog.dismiss();
                        } else {
                            for (MauSac mau: list){
                                if(initialColor == mau.getMamau()){
                                     dem = 1;
                                }
                            }
                            if(dem == 1){
                                Toast.makeText(context, "Màu này đã tồn tại", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Chưa chọn màu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        imageButton.setEnabled(false);
                        int colorCode = ((ColorDrawable) imageButton.getBackground()).getColor();
                        item.setMamau(colorCode);
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            capNhatlv();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(layoutParams.width, layoutParams.height);
        dialog.show();
    }
}