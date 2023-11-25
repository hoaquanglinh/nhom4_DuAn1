package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.QuanLyMauFragment;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;

import java.util.ArrayList;

public class HangChiTietAdapter extends ArrayAdapter<Hang> {

    private Context context;
    ArrayList<Hang> list;
    HangDAO dao;
    TextView tvmahang, tvtenhang;
    public HangChiTietAdapter(@NonNull Context context, ArrayList<Hang> list, HangDAO dao) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hangchitiet, null);
        }

        final Hang item = list.get(position);
        if (item != null) {
            tvmahang = v.findViewById(R.id.tv_MaHang);
            tvmahang.setText(String.valueOf(item.getMahang()));

            tvtenhang = v.findViewById(R.id.tv_TenHang);
            tvtenhang.setText(item.getTenHang());
        }

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        v.findViewById(R.id.btnDelete_hang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(position).getMahang();
                        dao.delete(String.valueOf(id));
                        list.clear();
                        list.addAll(dao.getAll());
                        notifyDataSetChanged();
                        dialog.cancel();
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
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
        });

        return v;    }
}
