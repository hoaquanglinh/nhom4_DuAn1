package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.DAO.DonHangDAO;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.DonHangAdapter;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;

public class DangXuLyFragment extends Fragment {
    ListView listView;
    DonHangDAO donHangDAO;
    SanPhamDAO dao;
    DonHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    NguoiDungDAO nguoiDungDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_xu_ly, container, false);
        listView = view.findViewById(R.id.lvDangXuLy);

        dao = new SanPhamDAO(getActivity());
        donHangDAO = new DonHangDAO(getContext());
        nddao = new TaiKhoanNDDAO(getActivity());
        nguoiDungDAO = new NguoiDungDAO(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        int matknd = nddao.getMatkndFromTaikhoannd(user, pass);
        int mand = nguoiDungDAO.getMandByMatknd(matknd);

//        list = (ArrayList<SanPham>) donHangDAO.getListSanPhamTrongDonHang(mand);
//        adapter = new DonHangAdapter(getActivity(), list, dao);
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);

        return view;
    }
}