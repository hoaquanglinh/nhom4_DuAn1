package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class XacNhanDonHangFragment extends Fragment {
    ListView listView;
    SanPhamDAO dao;
    GioHangDao gioHangDao;
    SanPhamGioHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    private int matknd;
    TextView tvGia;
    Toolbar toolbar;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDung item;
    EditText edHoTen, edSDT, edDiaChi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xac_nhan_don_hang, container, false);

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

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        matknd = nddao.getMatkndFromTaikhoannd(user, pass);

        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listNguoiDung = (ArrayList<NguoiDung>) nguoiDungDAO.getAllByMAtknd(matknd);
        if (!listNguoiDung.isEmpty()){
            item = listNguoiDung.get(0);
            edHoTen.setText(item.getTen());
            edDiaChi.setText(item.getDiaChi());
            edSDT.setText(item.getSdt());
        }else{
            edHoTen.setText("");
            edDiaChi.setText("");
            edSDT.setText("");
        }

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