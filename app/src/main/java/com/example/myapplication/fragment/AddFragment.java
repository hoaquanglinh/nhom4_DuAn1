package com.example.myapplication.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.HangDAO;
import com.example.myapplication.DAO.MauSacDAO;
import com.example.myapplication.DAO.SanPhamDAO;

import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HangSpinerAdapter;
import com.example.myapplication.adapter.MauSacSpinerAdapter;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;


public class AddFragment extends Fragment {
    EditText edTenSp, edGiaSp, edKhohang, edMota;
    Spinner spmamau, spmahang;
    ImageButton imageView;
    MauSacSpinerAdapter mauSacSpinerAdapter;
    ArrayList<MauSac> listMauSac;
    MauSacDAO mauSacDAO;
    int maMauSac;
    SanPhamDAO dao;
    SanPham item;
    HangSpinerAdapter hangSpinerAdapter;
    ArrayList<Hang> listHang;
    HangDAO hangDAO;
    int maHang;
    Uri selectedImageUri;
    public int PICK_IMAGE_REQUEST = 1;
    int REQUEST_CODE = 2;
    int initialColor= Color.RED;
    TaiKhoanNDDAO nddao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        dao = new SanPhamDAO(getActivity());

        edTenSp = view.findViewById(R.id.edTenSp);
        edGiaSp = view.findViewById(R.id.edGiaSp);
        edKhohang = view.findViewById(R.id.edKhohang);
        edMota = view.findViewById(R.id.edMoTa);
        spmahang = view.findViewById(R.id.spMaHang);
        spmamau = view.findViewById(R.id.spMaMau);
        imageView = view.findViewById(R.id.ivImage);

        mauSacDAO = new MauSacDAO(getContext());
        listMauSac = new ArrayList<>();
        listMauSac = (ArrayList<MauSac>) mauSacDAO.getAll();
        mauSacSpinerAdapter = new MauSacSpinerAdapter(getContext(), listMauSac);
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
        hangSpinerAdapter = new HangSpinerAdapter(getContext(), listHang);
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
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
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
                String imagePath = getPathFromUri(selectedImageUri);
                item.setAnh(imagePath);

                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String user = pref.getString("USERNAME", "");
                String pass = pref.getString("PASSWORD", "");

                nddao = new TaiKhoanNDDAO(getActivity());
                int matknd = nddao.getMatkndFromTaikhoannd(user, pass);

                long insert = dao.insert(item, matknd);
                if (insert > 0) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                   // Kiểm tra và yêu cầu quyền truy cập vào bộ nhớ ngoài
                    if (!Environment.isExternalStorageManager()) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                        return;
                    }
                } else {
                    // Kiểm tra và yêu cầu quyền truy cập vào bộ nhớ ngoài
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Yêu cầu quyền truy cập vào bộ nhớ ngoài
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE);
                        return;
                    }
                }
                // Mở trình chọn ảnh
                openImagePicker();
            }
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, mở picker ảnh
                openImagePicker();
            } else {
                // Quyền không được cấp, thông báo cho người dùng
                Toast.makeText(getContext(), "Bạn cần cấp quyền để chọn ảnh", Toast.LENGTH_SHORT).show();
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Nhận Uri của ảnh đã chọn
            selectedImageUri = data.getData();
            Log.d("Image", "Image URI: " + selectedImageUri.toString());
            // Tiến hành xử lý ảnh, ví dụ: hiển thị ảnh lên ImageView
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}