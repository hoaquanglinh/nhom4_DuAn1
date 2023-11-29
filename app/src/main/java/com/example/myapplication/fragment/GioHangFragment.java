package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GioHangFragment extends Fragment implements SanPhamGioHangAdapter.OnItemSelectedListener{
    ListView listView;
    SanPhamDAO dao;
    GioHangDao gioHangDao;
    SanPhamGioHangAdapter adapter;
    ArrayList<SanPham> list;
    TaiKhoanNDDAO nddao;
    private int matknd;
    TextView tvGia;
    XacNhanDonHangFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        requireActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);

        listView = view.findViewById(R.id.listViewGioHang);
        dao = new SanPhamDAO(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        tvGia = view.findViewById(R.id.tvTongTien);

        nddao = new TaiKhoanNDDAO(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");

        matknd = nddao.getMatkndFromTaikhoannd(user, pass);

        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        adapter.setOnItemSelectedListener(this);

        fragment = new XacNhanDonHangFragment();
        view.findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    @Override
    public void onItemSelected(double gia) {
        tvGia.setText(numberFormat.format(gia)+ " đ");

        Bundle bundle = new Bundle();
        bundle.putDouble("tongtien", gia);
        fragment.setArguments(bundle);
    }
}