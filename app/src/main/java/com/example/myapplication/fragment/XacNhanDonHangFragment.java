package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class XacNhanDonHangFragment extends Fragment {
    ListView listView;
    SanPhamDAO dao;
    GioHangDao gioHangDao;
    SanPhamGioHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    private int matknd, mand;
    TextView tvGia;
    Toolbar toolbar;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDung item;
    EditText edHoTen, edSDT, edDiaChi;
    DonHangDAO donHangDAO;
    DonHang donHang;
    TextView tv_tongtien;
    double tongtien;
    RadioButton rdo1, rdo2, rdo3;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xac_nhan_don_hang, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tongtien = bundle.getDouble("tongtien");
            Log.d("tongtien", "onCreateView: " + tongtien);
        }

        donHangDAO = new DonHangDAO(getActivity());
        donHang = new DonHang();

        listView = view.findViewById(R.id.lvXacNhan);
        dao = new SanPhamDAO(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        tvGia = view.findViewById(R.id.tvtongtientt);
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());
        toolbar = view.findViewById(R.id.toolbarXacNhan);
        edHoTen = view.findViewById(R.id.ed_hoten);
        edSDT = view.findViewById(R.id.ed_soDienThoai);
        edDiaChi = view.findViewById(R.id.ed_DiaChi);
        tv_tongtien = view.findViewById(R.id.tv_TongTienXN);
        tv_tongtien.setText(String.valueOf(tongtien));

        rdo1 = view.findViewById(R.id.rdottknt);
        rdo2 = view.findViewById(R.id.rdotknh);
        rdo3 = view.findViewById(R.id.rdovimomo);

        Log.d("tongtien", "Tổng tiền: " + tongtien);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        matknd = nddao.getMatkndFromTaikhoannd(user, pass);
        mand = nguoiDungDAO.getMandByMatknd(matknd);

        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listNguoiDung = (ArrayList<NguoiDung>) nguoiDungDAO.getAllByMAtknd(matknd);
        if (!listNguoiDung.isEmpty()) {
            item = listNguoiDung.get(0);
            edHoTen.setText(item.getTen());
            edDiaChi.setText(item.getDiaChi());
            edSDT.setText(item.getSdt());
        } else {
            edHoTen.setText("");
            edDiaChi.setText("");
            edSDT.setText("");
        }

        view.findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donHang = new DonHang();
                for (SanPham sp : list) {
                    donHang.setMand(mand);
                    donHang.setMasp(sp.getMasp());
                    donHang.setTongtien(tongtien);
                    donHang.setThoigiandathang(new Date());
                    donHang.setThoigianhoanthanh(new Date());
                    donHang.setTrangthai(1);
                    if (rdo1.isChecked()) {
                        donHang.setPtttt(1);
                    } else if (rdo2.isChecked()) {
                        donHang.setPtttt(2);
                    } else {
                        donHang.setPtttt(3);
                    }

                    long insert = donHangDAO.insert(donHang);
                    if (insert > 0){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                DonMuaFragment fragment = new DonMuaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
}