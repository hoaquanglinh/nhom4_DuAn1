package com.example.myapplication.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    EditText edTenSp, edGiaSp, edKhohang, edMota;
    Spinner spmamau, spmahang;

    Button btnaddanh;
    ImageView imageView;
    MauSacSpinerAdapter mauSacSpinerAdapter;
    ArrayList<MauSac> listMauSac;
    MauSacDAO mauSacDAO;
    MauSac mauSac;
    int maMauSac;

    SanPhamDAO dao;
    SanPham item;

    HangSpinerAdapter hangSpinerAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    Hang hang;
    int maHang;
    int positionMau, positionHang;

    Context context;
    Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        dao = new SanPhamDAO(getActivity());

        context = getContext();
        edTenSp = view.findViewById(R.id.edTenSp);
        edGiaSp = view.findViewById(R.id.edGiaSp);
        edKhohang = view.findViewById(R.id.edKhohang);
        edMota = view.findViewById(R.id.edMoTa);
        spmahang = view.findViewById(R.id.spMaHang);
        spmamau = view.findViewById(R.id.spMaMau);
        btnaddanh = view.findViewById(R.id.btnAddAnh);
        imageView = view.findViewById(R.id.ivImage);

        mauSacDAO = new MauSacDAO(getContext());
        listMauSac = new ArrayList<>();
        listMauSac = (ArrayList<MauSac>) mauSacDAO.getAll();
        mauSacSpinerAdapter = new MauSacSpinerAdapter(context, listMauSac);
        spmamau.setAdapter(mauSacSpinerAdapter);
        spmamau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maMauSac = listMauSac.get(position).getMamau();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hangDAO = new HangDAO(getContext());
        listHang = new ArrayList<>();
        listHang = (ArrayList<Hang>) hangDAO.getAll();
        hangSpinerAdapter = new HangSpinerAdapter(context, listHang);
        spmahang.setAdapter(hangSpinerAdapter);
        spmahang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maHang = listHang.get(position).getMahang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnSaveSP).setOnClickListener(new View.OnClickListener() {
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

                Double giasp = Double.parseDouble(giaStr);
                Integer khohang = Integer.parseInt(khohangStr);

                item = new SanPham();
                item.setMamau(maMauSac);
                item.setMahang(maHang);
                item.setTensp(tensp);
                item.setGiasp(giasp);
                item.setKhoHang(khohang);
                item.setMota(motasp);
                item.setAnh(selectedImageUri.toString());

                long insert = dao.insert(item);

                if (insert > 0) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnaddanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chọn ảnh từ bộ nhớ
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Nhận Uri của ảnh đã chọn
            selectedImageUri = data.getData();

            Log.d("Image", "Image URI: " + selectedImageUri.toString());

            // Tiến hành xử lý ảnh, ví dụ: hiển thị ảnh lên ImageView
            try {
                InputStream inputStream = this.context.getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        try (Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

}