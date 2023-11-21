package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;

import java.util.ArrayList;

public class HangSpinerAdapter extends ArrayAdapter<Hang> {
    private Context context;
    ArrayList<Hang> list;

    TextView tvmahang, tvtenhang;
    public HangSpinerAdapter(@NonNull Context context, ArrayList<Hang> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hang_spinner, null);

        }
        final Hang item = list.get(position);
        if (item != null) {
            tvmahang = v.findViewById(R.id.tvMaHang);
            tvmahang.setText(item.getMahang() + ". ");

            tvtenhang = v.findViewById(R.id.tvTenHang);
            tvtenhang.setText(item.getTenHang());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hang_spinner, null);

        }
        final Hang item = list.get(position);
        if (item != null) {
            tvmahang = v.findViewById(R.id.tvMaHang);
            tvmahang.setText(item.getMahang() + ". ");

            tvtenhang = v.findViewById(R.id.tvTenHang);
            tvtenhang.setText(item.getTenHang());
        }
        return v;
    }
}
