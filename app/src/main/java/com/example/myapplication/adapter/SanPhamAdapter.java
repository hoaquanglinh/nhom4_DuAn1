package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.fragment.ProductFragment;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

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
    Button btnsua, btnxoa;

    EditText edTenSp, edGiaSp, edKhohang, edMota;
    Spinner spmamau, spmahang;

    ImageView imageView;

    public SanPhamAdapter(@NonNull Context context, ProductFragment fragment, ArrayList<SanPham> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
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
        if (item != null) {
            tvtensp = v.findViewById(R.id.tvTensp);
            tvtensp.setText(item.getTensp());

            mauSacDAO = new MauSacDAO(context);
            MauSac mauSac = mauSacDAO.getID(String.valueOf(item.getMamau()));
            tvmau = v.findViewById(R.id.tvMau);
            tvmau.setText("Màu: " + mauSac.getTenMau());

            tvgiasp = v.findViewById(R.id.tvGia);
            tvgiasp.setText("Giá: " + item.getGiasp());

            tvkhohang = v.findViewById(R.id.tvKho);
            tvkhohang.setText("Kho: " + item.getKhoHang());

            Uri imageUri = Uri.parse(item.getAnh());
            imageView = v.findViewById(R.id.imageSP);
            imageView.setImageURI(imageUri);

            btnsua=v.findViewById(R.id.btnSua);
            btnxoa = v.findViewById(R.id.btnXoa);
        }

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialogsua(item);
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

        return v;

    }

    public void opendialogsua(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suaproduct, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        edTenSp = view.findViewById(R.id.edTenSp_sua);
        edGiaSp = view.findViewById(R.id.edGiaSp_sua);
        edKhohang = view.findViewById(R.id.edKhohang_sua);
        edMota = view.findViewById(R.id.edMoTa_sua);
        spmahang = view.findViewById(R.id.spMaHang_sua);
        spmamau = view.findViewById(R.id.spMaMau_sua);
        Button btnSave = view.findViewById(R.id.btnSave_suaSP);

        edTenSp.setText(sp.getTensp());
        edGiaSp.setText(String.valueOf(sp.getGiasp()));
        edKhohang.setText(String.valueOf(sp.getKhoHang()));
        edMota.setText(sp.getMota());

        mauSacDAO = new MauSacDAO(getContext());
        listMauSac = (ArrayList<MauSac>) mauSacDAO.getAll();
        mauSacSpinerAdapter = new MauSacSpinerAdapter(context, listMauSac);
        spmamau.setAdapter(mauSacSpinerAdapter);
        spmamau.setSelection(getMauSacPosition(sp.getMamau())); // Chọn màu sắc tương ứng với sản phẩm

        hangDAO = new HangDAO(getContext());
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        hangSpinerAdapter = new HangSpinerAdapter(context, listHang);
        spmahang.setAdapter(hangSpinerAdapter);
        spmahang.setSelection(getHangPosition(sp.getMahang())); // Chọn hãng tương ứng với sản phẩm

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edTenSp.getText().toString();
                String giaStr = edGiaSp.getText().toString();
                String khohangStr = edKhohang.getText().toString();
                String motasp = edMota.getText().toString();

                if (tensp.isEmpty() || giaStr.isEmpty() || khohangStr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                double gia = Double.parseDouble(giaStr);
                int khohang = Integer.parseInt(khohangStr);

                sp.setTensp(tensp);
                sp.setGiasp(gia);
                sp.setKhoHang(khohang);
                sp.setMota(motasp);
                sp.setMamau(listMauSac.get(spmamau.getSelectedItemPosition()).getMamau());
                sp.setMahang(listHang.get(spmahang.getSelectedItemPosition()).getMahang());

                if (sanPhamDAO.update(sp)) {
                    list.clear();
                    list.addAll(sanPhamDAO.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "ACập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); //
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getMauSacPosition(int mamau) {
        for (int i = 0; i < listMauSac.size(); i++) {
            if (listMauSac.get(i).getMamau() == mamau) {
                return i;
            }
        }
        return 0;
    }

    private int getHangPosition(int mahang) {
        for (int i = 0; i < listHang.size(); i++) {
            if (listHang.get(i).getMahang() == mahang) {
                return i;
            }
        }
        return 0;
    }
}

