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
import com.example.myapplication.model.MauSac;

import java.util.ArrayList;

public class MauSacSpinerAdapter extends ArrayAdapter<MauSac> {
    private Context context;
    ArrayList<MauSac> list;

    TextView tvmamau, tvtenmau;
    public MauSacSpinerAdapter(@NonNull Context context, ArrayList<MauSac> list) {
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
            v = inflater.inflate(R.layout.item_mau_spinner, null);

        }
        final MauSac item = list.get(position);
        if (item != null) {
            tvmamau = v.findViewById(R.id.tvMaMau);
            tvmamau.setText(item.getMamau() + ". ");

            tvtenmau = v.findViewById(R.id.tvTenMau);
            tvtenmau.setText(item.getTenMau());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_mau_spinner, null);

        }
        final MauSac item = list.get(position);
        if (item != null) {
            tvmamau = v.findViewById(R.id.tvMaMau);
            tvmamau.setText(item.getMamau() + ". ");

            tvtenmau = v.findViewById(R.id.tvTenMau);
            tvtenmau.setText(item.getTenMau());
        }
        return v;
    }
}
