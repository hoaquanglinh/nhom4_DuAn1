package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.NguoiDungDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.model.NguoiDung;
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
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDungDAO nguoiDungDAO;
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
        nguoiDungDAO = new NguoiDungDAO(getActivity());

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");

        matknd = nddao.getMatkndFromTaikhoannd(user);

        list = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);
        adapter = new SanPhamGioHangAdapter(getContext(), list, getActivity(), dao);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        adapter.setOnItemSelectedListener(this);

        fragment = new XacNhanDonHangFragment();
        view.findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listNguoiDung = (ArrayList<NguoiDung>) nguoiDungDAO.getAllByMAtknd(matknd);
                if (listView == null){
                    Toast.makeText(getContext(), "Bạn chưa có sản phẩm nào để thanh toán", Toast.LENGTH_SHORT).show();
                }else{
                    if (listNguoiDung.isEmpty()){
                        thongbao();
                    }else{
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
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

    public void thongbao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Vui lòng nhập thông tin cá nhân trước khi đặt hàng");
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChinhSuaThongTinFragment fragment1 = new ChinhSuaThongTinFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
}