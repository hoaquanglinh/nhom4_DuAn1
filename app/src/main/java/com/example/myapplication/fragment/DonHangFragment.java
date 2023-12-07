package com.example.myapplication.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DonHangFragment extends Fragment {
    SanPham item;
    TextView tenspct, giaspct, mauspct, tvtongtien3, tvdiachi3, tvpttt3 , tvsoluong, tvthoigiandathang, tvthoigianhoanthanh, hoten, sdt;
    ImageView imageViewSP;
    Uri selectedImageUri;
    MauSacDAO mauSacDAO;
    DonHang donHang;
    NguoiDungDAO nguoiDungDAO;
    TaiKhoanNDDAO nddao;
    DonHangDAO donHangDAO;
    int ptttt;
    Toolbar toolbar;
    LinearLayout linearLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            item = (SanPham) getArguments().getSerializable("chitietspdonhang");
            donHang = (DonHang) getArguments().getSerializable("donhang");
            Log.d("bundle", getArguments().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        imageViewSP = view.findViewById(R.id.imageSP3);
        tenspct = view.findViewById(R.id.tvTensp3);
        giaspct = view.findViewById(R.id.tvGia3);
        tvsoluong = view.findViewById(R.id.tvsoluong3);
        mauspct = view.findViewById(R.id.tvMau3);
        tvtongtien3 = view.findViewById(R.id.tvtongtien3);
        tvdiachi3 = view.findViewById(R.id.tvdiachi3);
        tvpttt3 = view.findViewById(R.id.tvpttt3);
        tvthoigiandathang = view.findViewById(R.id.tvThoiGianDatHang);
        tvthoigianhoanthanh = view.findViewById(R.id.tvThoiGianHoanThanh);
        toolbar = view.findViewById(R.id.toolbardonhang);
        hoten = view.findViewById(R.id.tvhoten3);
        sdt = view.findViewById(R.id.tvsdt3);


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Đơn hàng");

        if(item != null){
            selectedImageUri = Uri.parse(item.getAnh());
            imageViewSP.setImageURI(selectedImageUri);

            tenspct.setText(item.getTensp());
            tvsoluong.setText(String.valueOf(item.getSoluong()));
            String giaviet = numberFormat.format(item.getGiasp());
            giaspct.setText("Giá: " + giaviet + " đ");

            mauSacDAO = new MauSacDAO(getContext());
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            mauspct.setText("Màu: " + mauSac.getTenMau());

            Double tong = item.getGiasp() * item.getSoluong();
            tvtongtien3.setText("Tổng thanh toán: " + numberFormat.format(tong) + " đ");
            tvsoluong.setText("x" + item.getSoluong());
        }

        nguoiDungDAO = new NguoiDungDAO(getContext());
        nddao = new TaiKhoanNDDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());

        ptttt = donHangDAO.getPtttByMadh(donHang.getMadh());
        NguoiDung nd = donHangDAO.getThongTinNguoiDungByMaDH(donHang.getMadh());

        tvdiachi3.setText(nd.getDiaChi());
        hoten.setText(nd.getTen());
        sdt.setText(nd.getSdt());

        if (ptttt == 1){
            tvpttt3.setText("Thanh toán khi nhận hàng");
        }else if(ptttt == 2){
            tvpttt3.setText("Thanh toán qua tài khoản ngân hàng");
        }else{
            tvpttt3.setText("Thanh toán qua ví momo");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String formattedDate = dateFormat.format(donHang.getThoigiandathang());
        tvthoigiandathang.setText(formattedDate);

        if (donHang.getTrangthai() == 1){
            view.findViewById(R.id.l13).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.l13).setVisibility(View.VISIBLE);
            tvthoigianhoanthanh.setText(dateFormat.format(donHang.getThoigianhoanthanh()));
        }

        view.findViewById(R.id.l12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openttct(item);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void openttct(final SanPham sanPham) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPhamChiTiet", sanPham);
        ThongTinChiTietFragment thongTinChiTietFragment = new ThongTinChiTietFragment();
        thongTinChiTietFragment.setArguments(bundle);

        if (getActivity() instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, thongTinChiTietFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}