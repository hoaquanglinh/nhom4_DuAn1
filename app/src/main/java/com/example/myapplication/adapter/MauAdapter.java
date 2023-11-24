package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.ProductFragment;
import com.example.myapplication.fragment.QuanLyMauFragment;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class MauAdapter extends ArrayAdapter<MauSac> {
    private Context context;
    ArrayList<MauSac> listMauSac;
    MauSacDAO dao;
    QuanLyMauFragment fragment;
    ImageView imageViewMau;
    TextView tvMau;
    public MauAdapter(@NonNull Context context, ArrayList<MauSac> listMauSac, MauSacDAO dao, QuanLyMauFragment fragment) {
        super(context, 0, listMauSac);
        this.context = context;
        this.listMauSac = listMauSac;
        this.dao = dao;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_mau, null);
        }

        final MauSac item = listMauSac.get(position);
        if (item != null) {
            tvMau = v.findViewById(R.id.tvMauqtv);
            tvMau.setText(item.getTenMau());

            imageViewMau = v.findViewById(R.id.imageMau);
            imageViewMau.setBackgroundColor(item.getMamau());
        }

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        v.findViewById(R.id.btnXoaMau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.getMamau()));
            }
        });

        return v;
    }
}
