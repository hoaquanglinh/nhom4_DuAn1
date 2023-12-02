package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class DaGiaoAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private ArrayList<SanPham> list;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    MauSacDAO mauSacDAO;
    TextView tvtensp, tvgiasp, tvmau, tvsoluong, tvtongtien;
    ImageView imageView;
    int matknd, mand;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    private Handler handler;
    ArrayList<DonHang> listDH;

    public DaGiaoAdapter(@NonNull Context context, ArrayList<SanPham> list, ArrayList<DonHang> listDH, SanPhamDAO dao) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.listDH = listDH;
        this.dao = dao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_da_giao, null);
        }

        final SanPham item = list.get(position);
        final DonHang donHang = listDH.get(position);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        mauSacDAO = new MauSacDAO(context);
        nddao = new TaiKhoanNDDAO(context);
        nguoiDungDAO = new NguoiDungDAO(context);
        donHangDAO = new DonHangDAO(context);

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        nddao = new TaiKhoanNDDAO(context);
        matknd = nddao.getMatkndFromTaikhoannd(user);
        mand = nguoiDungDAO.getMandByMatknd(matknd);

        if(donHang.getTrangthai() == 2){
            if(item != null){
                tvtensp = v.findViewById(R.id.tvTensp5);
                tvtensp.setText(item.getTensp());

                mauSacDAO = new MauSacDAO(context);
                MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
                tvmau = v.findViewById(R.id.tvMau5);
                tvmau.setText("Màu: " + mauSac.getTenMau());

                tvgiasp = v.findViewById(R.id.tvGia5);
                String giaviet = numberFormat.format(item.getGiasp());
                tvgiasp.setText("Giá: " + giaviet + " đ");

                Uri imageUri = Uri.parse(item.getAnh());
                imageView = v.findViewById(R.id.imageSP5);
                imageView.setImageURI(imageUri);

                tvsoluong = v.findViewById(R.id.tvsoluong5);
                tvsoluong.setText("x"+item.getSoluong());

                tvtongtien = v.findViewById(R.id.tvtongtien5);
                tvtongtien.setText(numberFormat.format(item.getGiasp() * item.getSoluong()) + "đ");
            }
            notifyDataSetChanged();
        }

        return v;
    }
}
