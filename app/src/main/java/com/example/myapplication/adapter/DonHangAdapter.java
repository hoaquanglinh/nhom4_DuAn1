package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
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
    int matknd, mand;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    DonHang donHang;
    ArrayList<DonHang> listDH;
    public DonHangAdapter(@NonNull Context context, ArrayList<SanPham> list, ArrayList<DonHang> listDH, SanPhamDAO dao) {
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
            v = inflater.inflate(R.layout.item_dangxuli, null);
        }

        final SanPham item = list.get(position);
        donHang = listDH.get(position);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        mauSacDAO = new MauSacDAO(context);
        nddao = new TaiKhoanNDDAO(context);
        nguoiDungDAO = new NguoiDungDAO(context);
        donHangDAO = new DonHangDAO(context);

        SharedPreferences pref = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        nddao = new TaiKhoanNDDAO(context);
        matknd = nddao.getMatkndFromTaikhoannd(user, pass);
        mand = nguoiDungDAO.getMandByMatknd(matknd);

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

            tvsoluong = v.findViewById(R.id.tvsoluong2);
            tvsoluong.setText("x"+item.getSoluong());
            v.findViewById(R.id.btnHuyDonHang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Bạn chắc chắn muốn hủy đơn hàng?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = donHang.getMadh();
                            Log.d("linhh", "onClick: " + listDH.get(position));
                            donHangDAO.delete(String.valueOf(id));
                            list.clear();
                            list.addAll(donHangDAO.getListSanPhamTrongDonHang(mand));
                            notifyDataSetChanged();
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
            });


        }

        return v;
    }
}
