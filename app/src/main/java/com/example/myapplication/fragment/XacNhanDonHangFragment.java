package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
import com.example.myapplication.adapter.DonHangAdapter;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
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
    SanPhamDAO sanPhamDAO;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDung item;
    EditText edHoTen, edSDT, edDiaChi;
    DonHangDAO donHangDAO;
    DonHang donHang;
    TextView tv_tongtien;
    double tongtien;
    RadioButton rdo1, rdo2, rdo3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xac_nhan_don_hang, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tongtien = bundle.getDouble("tongtien");
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        donHangDAO = new DonHangDAO(getActivity());
        donHang = new DonHang();
        listView = view.findViewById(R.id.lvXacNhan);
        dao = new SanPhamDAO(getActivity());
        tvGia = view.findViewById(R.id.tvtongtientt);
        toolbar = view.findViewById(R.id.toolbarXacNhan);
        edHoTen = view.findViewById(R.id.ed_hoten);
        edSDT = view.findViewById(R.id.ed_soDienThoai);
        edDiaChi = view.findViewById(R.id.ed_DiaChi);
        tv_tongtien = view.findViewById(R.id.tv_TongTienXN);
        tv_tongtien.setText(numberFormat.format(tongtien) + "đ");
        rdo1 = view.findViewById(R.id.rdottknt);
        rdo2 = view.findViewById(R.id.rdotknh);
        rdo3 = view.findViewById(R.id.rdovimomo);
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");
        matknd = nddao.getMatkndFromTaikhoannd(user);
        mand = nguoiDungDAO.getMandByMatknd(matknd);

        sanPhamDAO = new SanPhamDAO(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        ArrayList<SanPham> list1 = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                if(edHoTen.getText().toString().trim().isEmpty()||edDiaChi.getText().toString().trim().isEmpty()||edSDT.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if (!rdo1.isChecked() && !rdo2.isChecked() && !rdo3.isChecked()){
                        Toast.makeText(getActivity(), "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                    }else{
                        for (SanPham sp : list) {
                            donHang = new DonHang();
                            donHang.setMand(mand);
                            donHang.setMasp(sp.getMasp());
                            donHang.setSoluongmua(sp.getSoluong());
                            donHang.setTongtien(sp.getGiasp() * donHang.getSoluongmua());
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
                            gioHangDao.deleteAll();
                            list1.clear();
                        }
                        for (SanPham sanPham: list){
                            sanPham.setSoluong(1);
                            sanPhamDAO.updateSL(sanPham.getMasp(), 1);
                        }
                        String hoten = edHoTen.getText().toString();
                        String diachi = edDiaChi.getText().toString();
                        String sdt = edSDT.getText().toString();
                        nguoiDungDAO.updateAddressNamePhoneByMand(mand, diachi, hoten, sdt);

                        DonMuaFragment donMuaFragment = new DonMuaFragment();
                        donMuaFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, donMuaFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
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