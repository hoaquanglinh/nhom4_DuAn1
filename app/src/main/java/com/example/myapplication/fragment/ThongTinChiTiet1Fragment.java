package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.GioHangDao;
import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SanPhamGioHangAdapter;
import com.example.myapplication.adapter.SanPhamHomeAdapter;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;
import com.example.myapplication.model.TaiKhoanND;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ThongTinChiTiet1Fragment extends Fragment {
    SanPham item;
    Toolbar toolbar;
    ImageView imageViewSP;
    TextView tenspct, giaspct, tentknd, mauspct, hangspct, motaspct, khohang1;
    ImageButton ibnguoidung;
    MauSacDAO mauSacDAO;
    HangDAO hangDAO;
    ImageView imageViewSanPham;
    TaiKhoanNDDAO nddao;
    Uri selectedImageUri;
    GioHangDao gioHangDao;
    private int matknd;
    GioHang gioHang;
    SanPhamDAO dao;
    SanPhamGioHangAdapter adapter;
    GioHangFragment gioHangFragment;
    RecyclerView recyclerView;
    SanPhamHomeAdapter sanPhamHomeAdapter;
    ArrayList<SanPham> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (SanPham) getArguments().getSerializable("sanPhamChiTiet");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_chi_tiet1, container, false);

        requireActivity().findViewById(R.id.navigation).setVisibility(View.GONE);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        gioHangFragment = new GioHangFragment();
        toolbar = view.findViewById(R.id.toolbarSanPham1);
        gioHangDao = new GioHangDao(getContext());
        gioHang = new GioHang();
        dao = new SanPhamDAO(getActivity());

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Thông tin sản phẩm");

        imageViewSP = view.findViewById(R.id.imageViewSanPham1);
        tenspct = view.findViewById(R.id.tvTenSPCT1);
        giaspct = view.findViewById(R.id.tvGiaSPCT1);
        tentknd = view.findViewById(R.id.tvtentknd1);
        mauspct = view.findViewById(R.id.tvMauSPCT1);
        hangspct = view.findViewById(R.id.tvHangSPCT1);
        motaspct = view.findViewById(R.id.tvMotaSPCT1);
        imageViewSP = view.findViewById(R.id.imageViewSanPham1);
        ibnguoidung = view.findViewById(R.id.ibNguoiDung1);
        khohang1 = view.findViewById(R.id.tvKhoHang1);
        recyclerView = view.findViewById(R.id.recyclerViewBaiDangKhac);

        imageViewSanPham = view.findViewById(R.id.imageViewSanPham1);
        selectedImageUri = Uri.parse(item.getAnh());
        imageViewSanPham.setImageURI(selectedImageUri);

        Intent i = getActivity().getIntent();
        String user = i.getStringExtra("user");

        if (user.equals("admin")){
            view.findViewById(R.id.btnGioHang).setVisibility(View.GONE);
            view.findViewById(R.id.btnMuaNgay).setVisibility(View.GONE);
            view.findViewById(R.id.btngoidien).setVisibility(View.GONE);
        }

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

            khohang1.setText("Kho hàng: " + item.getKhoHang());
        }

        matknd = nddao.getMatkndFromTaikhoannd(user);

        list = (ArrayList<SanPham>) dao.getAllExceptMAtknd(matknd);
        ArrayList<SanPham> list1 = new ArrayList<>();
        for (SanPham sanPham: list){
            if (sanPham.getMasp() != item.getMasp()){
                list1.add(sanPham);
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        sanPhamHomeAdapter = new SanPhamHomeAdapter(getContext(), getActivity(), list1);
        recyclerView.setAdapter(sanPhamHomeAdapter);

        ArrayList<SanPham> listSPGH = (ArrayList<SanPham>) gioHangDao.getSanPhamInGioHangByMatkd(matknd);

        for (SanPham product : listSPGH) {
            if (product.getMatknd() != matknd) {
                dao.updateSL(product.getMasp(), 1);
                break;
            }
        }

        view.findViewById(R.id.btnGioHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gioHang.setMasp(item.getMasp());
                gioHang.setMatknd(matknd);

                boolean cos = false;

                for (SanPham product : listSPGH) {
                    if (product.getMasp() == item.getMasp()) {
                        cos = true;
                        break;
                    }
                }

                if (cos) {
                    Toast.makeText(activity, "Sản phẩm này đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    listSPGH.add(item);
                    long insert = gioHangDao.insert(gioHang);
                    if (insert > 0) {
                        Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        adapter = new SanPhamGioHangAdapter(getContext(), listSPGH, getActivity(), dao);
        GioHangFragment gioHangFragment = new GioHangFragment();
        view.findViewById(R.id.btnMuaNgay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("adapter", item.getMasp());
                gioHangFragment.setArguments(bundle);

                gioHang.setMasp(item.getMasp());
                gioHang.setMatknd(matknd);
                boolean cos = false;

                for (SanPham product : listSPGH) {
                    if (product.getMasp() == item.getMasp()) {
                        cos = true;
                        break;
                    }
                }

                if (cos) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, gioHangFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    listSPGH.add(item);
                    long insert = gioHangDao.insert(gioHang);
                    if (insert > 0) {
                        Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, gioHangFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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