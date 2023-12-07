package com.example.myapplication.adapter;

import static android.content.Context.MODE_PRIVATE;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.DonHangFragment;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DonHangAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private ArrayList<SanPham> list;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    MauSacDAO mauSacDAO;
    TextView tvtensp, tvgiasp, tvmau, tvsoluong, tvtongtien, tvTrangThai;
    ImageView imageView;
    int matknd, mand;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<DonHang> listDH;
    LinearLayout linearLayout;

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
        final DonHang donHang = listDH.get(position);

        linearLayout = v.findViewById(R.id.linearDangxyly);
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
            tvsoluong.setText("x" + donHang.getSoluongmua());
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Không tìm thấy thông tin sản phẩm");
            builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        if (donHang != null) {
            tvtongtien = v.findViewById(R.id.tvtongtien2);
            tvtongtien.setText(numberFormat.format(donHang.getSoluongmua() * item.getGiasp()) + "đ");

            tvTrangThai = v.findViewById(R.id.tvtrangthai2);
            if (donHang.getTrangthai() == 1) {
                tvTrangThai.setText("Đang xử lý");
                tvTrangThai.setTextColor(Color.RED);
                v.findViewById(R.id.btnHuyDonHang).setVisibility(View.VISIBLE);
            } else if (donHang.getTrangthai() == 2) {
                tvTrangThai.setText("Đã giao");
                tvTrangThai.setTextColor(Color.GREEN);
                donHangDAO.updatengay(donHang.getMadh(), new Date());
                v.findViewById(R.id.btnHuyDonHang).setVisibility(View.GONE);
            }
        }

        if (matknd == donHangDAO.getMatkndInSanPhamByMadh(donHang.getMadh())) {
            tvTrangThai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showStatusSpinner(view, donHang);
                }
            });
        }

        if (matknd == donHangDAO.getMatkndInSanPhamByMadh(donHang.getMadh())){
            v.findViewById(R.id.btnHuyDonHang).setVisibility(View.GONE);
        }

        v.findViewById(R.id.btnHuyDonHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Bạn chắc chắn muốn hủy đơn hàng?");
                builder.setCancelable(true);
                final int deletedId = donHang.getMadh();
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("madh", "ma don hang " + deletedId);
                        donHangDAO.delete(String.valueOf(deletedId));

                        if (user.equals("admin")) {
                            list.clear();
                            list.addAll(donHangDAO.getSanPhamByMadh());
                        } else {
                            list.clear();
                            list.addAll(donHangDAO.getListSanPhamTrongDonHang(mand));
                        }

                        Iterator<DonHang> iterator = listDH.iterator();
                        while (iterator.hasNext()) {
                            DonHang donHang = iterator.next();
                            if (donHang.getMadh() == deletedId) {
                                iterator.remove();
                                break;
                            }
                        }

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

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham sanPham = new SanPham(item.getMasp(), item.getMamau(), item.getMahang(), item.getMatknd(),
                        item.getTensp(), item.getGiasp(), item.getKhoHang(), item.getMota(), donHang.getSoluongmua(), item.getAnh());
                open(sanPham, donHang);
            }
        });
        return v;
    }

    private void open(final SanPham sanPham, final DonHang donHang) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("chitietspdonhang", sanPham);
        bundle.putSerializable("donhang", donHang);
        DonHangFragment donHangFragment = new DonHangFragment();
        donHangFragment.setArguments(bundle);

        if (context instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, donHangFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showStatusSpinner(View view, DonHang donHang) {
        final String[] statusOptions = {"Đang xử lý", "Đã giao"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chọn trạng thái");
        builder.setItems(statusOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedStatus = statusOptions[which];
                updateStatus(selectedStatus, donHang);
            }
        });
        builder.show();
    }

    private void updateStatus(String selectedStatus, DonHang donHang) {
        int newStatus;
        if (selectedStatus.equals("Đang xử lý")) {
            newStatus = 1;
        } else {
            newStatus = 2;
        }

        int madh = donHang.getMadh();
        donHang.setTrangthai(newStatus);
        donHangDAO.updateTrangThai(madh, donHang.getTrangthai());

        if (newStatus == 1) {
            tvTrangThai.setText("Đang xử lý");
            tvTrangThai.setTextColor(Color.RED);
            notifyDataSetChanged();
        } else if (newStatus == 2) {
            tvTrangThai.setText("Đã giao");
            tvTrangThai.setTextColor(Color.GREEN);
            notifyDataSetChanged();
        }
    }

}
