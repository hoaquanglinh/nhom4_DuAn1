package com.example.myapplication.fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangSpinerAdapter;
import com.example.myapplication.adapter.MauSacSpinerAdapter;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.TaiKhoanND;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ThongTinChiTietFragment extends Fragment {
    SanPham item;
    Toolbar toolbar;
    ImageView imageViewSP;
    TextView tenspct, giaspct, tentknd, mauspct, hangspct, motaspct;
    ImageButton ibnguoidung;
    MauSacDAO mauSacDAO;
    HangDAO hangDAO;

    TaiKhoanNDDAO nddao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (SanPham) getArguments().getSerializable("sanPhamChiTiet");
            Log.d("UpdateFragment", "sanphachitiet: " + item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_chi_tiet, container, false);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        imageViewSP = view.findViewById(R.id.imageViewSanPham);
        tenspct = view.findViewById(R.id.tvTenSPCT);
        giaspct = view.findViewById(R.id.tvGiaSPCT);
        tentknd = view.findViewById(R.id.tvtentknd);
        mauspct = view.findViewById(R.id.tvMauSPCT);
        hangspct = view.findViewById(R.id.tvHangSPCT);
        motaspct = view.findViewById(R.id.tvMotaSPCT);
        imageViewSP = view.findViewById(R.id.imageViewSanPham);
        ibnguoidung = view.findViewById(R.id.ibNguoiDung);

        if (item != null) {
            tenspct.setText(item.getTensp());
            giaspct.setText(String.valueOf(item.getGiasp()));

            nddao = new TaiKhoanNDDAO(getContext());
            TaiKhoanND taiKhoanND = nddao.getID(String.valueOf(item.getMatknd()));
            tentknd.setText(taiKhoanND.getTaiKhoanND());

            mauSacDAO = new MauSacDAO(getContext());
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            mauspct.setText("Màu: " + mauSac.getTenMau());

            hangDAO = new HangDAO(getContext());
            Hang hang = hangDAO.getID(String.valueOf(item.getMahang()));
            hangspct.setText("Hãng: " + hang.getTenHang());

            String giaviet = numberFormat.format(item.getGiasp());
            giaspct.setText("Giá: " + giaviet + " đ");
            motaspct.setText(item.getMota());
        }

        return view;
    }
}