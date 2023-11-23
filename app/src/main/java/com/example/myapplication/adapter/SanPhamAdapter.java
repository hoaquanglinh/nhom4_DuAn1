package com.example.myapplication.adapter;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.ProductFragment;
import com.example.myapplication.fragment.UpdateFragment;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    MauSacSpinerAdapter mauSacSpinerAdapter;
    ArrayList<MauSac> listMauSac;
    MauSacDAO mauSacDAO;
    SanPhamDAO sanPhamDAO;
    HangSpinerAdapter hangSpinerAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    Hang hang;
    ProductFragment fragment;
    private ArrayList<SanPham> list;
    TextView tvtensp, tvgiasp, tvmau, tvkhohang;
    Button btnsua, btnxoa, chonAnh;
    EditText edTenSp, edGiaSp, edKhohang, edMota;
    Spinner spmamau, spmahang;

    ImageView imageView, imageUd;
    private Activity activity;

    private static final int REQUEST_IMAGE = 1;

    public SanPhamAdapter(@NonNull Context context, ProductFragment fragment, ArrayList<SanPham> list, Activity activity) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
        this.activity = activity;
        sanPhamDAO = new SanPhamDAO(context);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_product, null);
        }

        final SanPham item = list.get(position);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        if (item != null) {
            tvtensp = v.findViewById(R.id.tvTensp);
            tvtensp.setText(item.getTensp());

            mauSacDAO = new MauSacDAO(context);
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            tvmau = v.findViewById(R.id.tvMau);
            tvmau.setText("Màu: " + mauSac.getTenMau());

            tvgiasp = v.findViewById(R.id.tvGia);
            String giaviet = numberFormat.format(item.getGiasp());
            tvgiasp.setText("Giá: " + giaviet + " đ");

            tvkhohang = v.findViewById(R.id.tvKho);
            tvkhohang.setText("Kho: " + item.getKhoHang());

            Uri imageUri = Uri.parse(item.getAnh());
            imageView = v.findViewById(R.id.imageSP);
            imageView.setImageURI(imageUri);

            btnsua=v.findViewById(R.id.btnSua);
            btnxoa = v.findViewById(R.id.btnXoa);

            btnsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditProductFragment(item);
                }
            });

            btnxoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SanPham item = list.get(position);
                    fragment.xoa(String.valueOf(item.getMasp()));
                    notifyDataSetChanged();
                }
            });
        }

        return v;
    }

    private void openEditProductFragment(final SanPham sanPham) {
        // Tạo Bundle và truyền thông tin sản phẩm vào Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanPham", sanPham);

        // Tạo Fragment và truyền Bundle vào Fragment
        UpdateFragment updateFragment = new UpdateFragment();
        updateFragment.setArguments(bundle);

        // Gửi sự kiện tới FragmentActivity để thay thế Fragment hiện tại bằng Fragment chỉnh sửa
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, updateFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}

