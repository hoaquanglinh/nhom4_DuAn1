package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    ImageView imageViewSanPham;
    TaiKhoanNDDAO nddao;
    Uri selectedImageUri;
    LinearLayout layoutduoi;
    TextView baidangkhac;
    RecyclerView recyclerViewSPCT;
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

        requireActivity().findViewById(R.id.navigation).setVisibility(View.GONE);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        toolbar = view.findViewById(R.id.toolbarSanPham);
        layoutduoi = view.findViewById(R.id.layoutduoi);
        baidangkhac = view.findViewById(R.id.baidangkhac);
        recyclerViewSPCT = view.findViewById(R.id.recyclerViewSPCT);

        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");

        if(user.equals("admin")){
            layoutduoi.setVisibility(View.GONE);
            baidangkhac.setVisibility(View.GONE);
            recyclerViewSPCT.setVisibility(View.GONE);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Thông tin sản phẩm");

        imageViewSP = view.findViewById(R.id.imageViewSanPham);
        tenspct = view.findViewById(R.id.tvTenSPCT);
        giaspct = view.findViewById(R.id.tvGiaSPCT);
        tentknd = view.findViewById(R.id.tvtentknd);
        mauspct = view.findViewById(R.id.tvMauSPCT);
        hangspct = view.findViewById(R.id.tvHangSPCT);
        motaspct = view.findViewById(R.id.tvMotaSPCT);
        imageViewSP = view.findViewById(R.id.imageViewSanPham);
        ibnguoidung = view.findViewById(R.id.ibNguoiDung);

        imageViewSanPham = view.findViewById(R.id.imageViewSanPham);
        selectedImageUri = Uri.parse(item.getAnh());
        imageViewSanPham.setImageURI(selectedImageUri);

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            // Xử lý sự kiện click vào mũi tên back ở đây
//            getActivity().onBackPressed(); // Hoặc thực hiện các hành động khác
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
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