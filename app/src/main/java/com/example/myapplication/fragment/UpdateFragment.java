package com.example.myapplication.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangSpinerAdapter;
import com.example.myapplication.adapter.MauSacSpinerAdapter;
import com.example.myapplication.adapter.SanPhamAdapter;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UpdateFragment extends Fragment {
    EditText edTenSp, edGiaSp, edKhohang, edMota;
    Spinner spmamau, spmahang;
    ImageView imageUd;
    SanPham item;
    MauSacSpinerAdapter mauSacSpinerAdapter;
    ArrayList<MauSac> listMauSac;
    MauSacDAO mauSacDAO;
    HangSpinerAdapter hangSpinerAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    private static int PICK_IMAGE_REQUEST = 1;
    SanPhamDAO dao;
    ArrayList<SanPham> list;
    Uri selectedImageUri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (SanPham) getArguments().getSerializable("sanPham");
            Log.d("UpdateFragment", "SanPham: " + item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        list = new ArrayList<>();
        dao = new SanPhamDAO(getContext());

        imageUd = view.findViewById(R.id.imageUD);
        edTenSp = view.findViewById(R.id.edTenSp_sua);
        edGiaSp = view.findViewById(R.id.edGiaSp_sua);
        edKhohang = view.findViewById(R.id.edKhohang_sua);
        edMota = view.findViewById(R.id.edMoTa_sua);
        spmahang = view.findViewById(R.id.spMaHang_sua);
        spmamau = view.findViewById(R.id.spMaMau_sua);
        Button btnSave = view.findViewById(R.id.btnSave_suaSP);

        selectedImageUri = Uri.parse(item.getAnh());
        imageUd.setImageURI(selectedImageUri);

        edTenSp.setText(item.getTensp());
        edGiaSp.setText(String.valueOf(item.getGiasp()));
        edKhohang.setText(String.valueOf(item.getKhoHang()));
        edMota.setText(item.getMota());

        mauSacDAO = new MauSacDAO(getContext());
        listMauSac = (ArrayList<MauSac>) mauSacDAO.getAll();
        mauSacSpinerAdapter = new MauSacSpinerAdapter(getContext(), listMauSac);
        spmamau.setAdapter(mauSacSpinerAdapter);
        spmamau.setSelection(getMauSacPosition(item.getMamau())); // Chọn màu sắc tương ứng với sản phẩm

        hangDAO = new HangDAO(getContext());
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        hangSpinerAdapter = new HangSpinerAdapter(getContext(), listHang);
        spmahang.setAdapter(hangSpinerAdapter);
        spmahang.setSelection(getHangPosition(item.getMahang()));

        view.findViewById(R.id.chonAnh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Log.d("Image", "Image URI: " + selectedImageUri.toString());
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    imageUd.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProduct() {
        String tenSp = edTenSp.getText().toString();
        String giaSp = edGiaSp.getText().toString();
        String khoHang = edKhohang.getText().toString();
        String moTa = edMota.getText().toString();
        MauSac selectedMauSac = (MauSac) spmamau.getSelectedItem();
        Hang selectedHang = (Hang) spmahang.getSelectedItem();

        if (TextUtils.isEmpty(tenSp) || TextUtils.isEmpty(giaSp) || TextUtils.isEmpty(khoHang) || TextUtils.isEmpty(moTa)) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Double giaSpInt = Double.parseDouble(giaSp);
        int khoHangInt = Integer.parseInt(khoHang);

        // Cập nhật thông tin sản phẩm
        item.setTensp(tenSp);
        item.setGiasp(giaSpInt);
        item.setKhoHang(khoHangInt);
        item.setMota(moTa);
        item.setMamau(selectedMauSac.getMamau());
        item.setMahang(selectedHang.getMahang());
        String imagePath = getPathFromUri(selectedImageUri);
        item.setAnh(imagePath);

        if (dao.update(item)) {
            list.clear();
            list.addAll(dao.getAll());
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flContent, new ProductFragment())
                    .commit();
        } else {
            Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContext().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}