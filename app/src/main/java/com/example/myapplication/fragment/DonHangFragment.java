package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DonHangFragment extends Fragment {
    SanPham item;
    TextView tenspct, giaspct, mauspct, tvtongtien3, tvdiachi3, tvpttt3 , tvsoluong, tvthoigiandathang, tvthoigianhoanthanh;
    ImageView imageViewSP;
    Uri selectedImageUri;
    MauSacDAO mauSacDAO;
    DonHang donHang;
    NguoiDungDAO nguoiDungDAO;
    TaiKhoanNDDAO nddao;
    DonHangDAO donHangDAO;
    int matknd, mand;
    String diachi;
    int ptttt;
    Date thoigiandathang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (SanPham) getArguments().getSerializable("chitietspdonhang");
            donHang = (DonHang) getArguments().getSerializable("donhang");
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

        selectedImageUri = Uri.parse(item.getAnh());
        imageViewSP.setImageURI(selectedImageUri);

        if(item != null){
            tenspct.setText(item.getTensp());
            tvsoluong.setText(String.valueOf(item.getSoluong()));
            String giaviet = numberFormat.format(item.getGiasp());
            giaspct.setText("Giá: " + giaviet + " đ");

            mauSacDAO = new MauSacDAO(getContext());
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            mauspct.setText("Màu: " + mauSac.getTenMau());
            tvtongtien3.setText("Giá: " + giaviet + " đ");
            tvsoluong.setText("x" + item.getSoluong());
        }

        nguoiDungDAO = new NguoiDungDAO(getContext());
        nddao = new TaiKhoanNDDAO(getContext());
        donHangDAO = new DonHangDAO(getContext());
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        matknd = nddao.getMatkndFromTaikhoannd(user, pass);
        mand = nguoiDungDAO.getMandByMatknd(matknd);
        diachi = donHangDAO.getDiaChiByMand(mand);
        ptttt = donHangDAO.getPtttByMadh(donHang.getMadh());
        thoigiandathang = donHangDAO.getThoiGianDatHangByMadh(donHang.getMadh());

        tvdiachi3.setText(diachi);
        if (ptttt == 1){
            tvpttt3.setText("Thanh toán khi nhận hàng");
        }else if(ptttt == 2){
            tvpttt3.setText("Thanh toán qua tài khoản ngân hàng");
        }else{
            tvpttt3.setText("Thanh toán qua ví momo");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", new Locale("vi", "VN"));
        String formattedDate = dateFormat.format(thoigiandathang);
        tvthoigiandathang.setText(formattedDate);

        return view;
    }

}