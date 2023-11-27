package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class GioHangFragment extends Fragment{
    ListView listView;
    SanPhamDAO dao;
    GioHangDao gioHangDao;
    SanPhamGioHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    private int matknd;
    TextView tvGia;
    CheckBox checkBoxAll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        listView = view.findViewById(R.id.listViewGioHang);
        dao = new SanPhamDAO(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        tvGia = view.findViewById(R.id.tvTongTien);
        checkBoxAll = view.findViewById(R.id.checkBoxAll);

        nddao = new TaiKhoanNDDAO(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");

        matknd = nddao.getMatkndFromTaikhoannd(user, pass);

        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);

        adapter.setOnItemSelectedListener(new SanPhamGioHangAdapter.OnItemSelectedListener() {
            @Override public void onItemSelected(double gia) {
                tvGia.setText(String.valueOf(gia));
            }
        });

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        return view;
    }
}