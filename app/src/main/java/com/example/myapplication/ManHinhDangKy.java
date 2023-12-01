package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.model.TaiKhoanND;

import java.util.ArrayList;

public class ManHinhDangKy extends AppCompatActivity {
    TaiKhoanNDDAO dao;
    TaiKhoanND tknd;
    EditText edTaiKhoan, edMatKhau, edReMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ky);

        dao = new TaiKhoanNDDAO(this);

        edTaiKhoan = findViewById(R.id.edUserNameDK);
        edMatKhau = findViewById(R.id.edPasswordDK);
        edReMatKhau = findViewById(R.id.edRePasswordDK);

        findViewById(R.id.btnDangKyDK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edTaiKhoan.getText().toString();
                String mk = edMatKhau.getText().toString();
                String remk = edReMatKhau.getText().toString();

                if(tk.trim().isEmpty() || mk.trim().isEmpty() || remk.trim().isEmpty()){
                    Toast.makeText(ManHinhDangKy.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(mk.equalsIgnoreCase(remk)){
                        tknd = new TaiKhoanND();

                        tknd.setTaiKhoanND(tk);
                        tknd.setMatKhauND(mk);

                        if(dao.insert(tknd) > 0){
                            Toast.makeText(ManHinhDangKy.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ManHinhDangKy.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(ManHinhDangKy.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.btnCancelDK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhDangKy.this, ManHinhDangNhap.class);
                startActivity(intent);
            }
        });
    }
}