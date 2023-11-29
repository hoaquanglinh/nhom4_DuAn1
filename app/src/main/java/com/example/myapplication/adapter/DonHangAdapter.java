package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DonHangAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private ArrayList<SanPham> list;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    MauSacDAO mauSacDAO;
    TextView tvtensp, tvgiasp, tvmau, tvsoluong;
    ImageView imageView;
    int matknd;
    TaiKhoanNDDAO nddao;
    public DonHangAdapter(@NonNull Context context, ArrayList<SanPham> list, SanPhamDAO dao) {
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
            v = inflater.inflate(R.layout.item_dangxuli, null);
        }

        final SanPham item = list.get(position);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        mauSacDAO = new MauSacDAO(context);
        nddao = new TaiKhoanNDDAO(context);

        if (item != null) {
            tvtensp = v.findViewById(R.id.tvTensp2);
            tvtensp.setText(item.getTensp());

            mauSacDAO = new MauSacDAO(context);
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            tvmau = v.findViewById(R.id.tvMau2);
            tvmau.setText("Màu: " + mauSac.getTenMau());

            tvgiasp = v.findViewById(R.id.tvGia2);
            String giaviet = numberFormat.format(item.getGiasp());
            tvgiasp.setText("Giá: " + giaviet + " đ");

            Uri imageUri = Uri.parse(item.getAnh());
            imageView = v.findViewById(R.id.imageSP2);
            imageView.setImageURI(imageUri);

            tvsoluong = v.findViewById(R.id.tvsoluong);
            tvsoluong.setText(String.valueOf(item.getSoluong()));

//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    open(item);
//                }
//            });

            SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String user = pref.getString("USERNAME", "");
            String pass = pref.getString("PASSWORD", "");
            nddao = new TaiKhoanNDDAO(context);
            matknd = nddao.getMatkndFromTaikhoannd(user, pass);

//            int id = list.get(position).getMasp();

        }

        return v;
    }
}
