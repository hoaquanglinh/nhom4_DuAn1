package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DAO.SanPhamDAO;
import com.example.myapplication.DAO.TaiKhoanNDDAO;
import com.example.myapplication.model.SanPham;

public class ManHinhDangNhap extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btnDangNhap, btnDangKy;
    CheckBox chkRememberPass;
    TaiKhoanNDDAO nddao;
    String strUser, strPass;
    int matk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        nddao = new TaiKhoanNDDAO(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        Boolean rem = pref.getBoolean("REMEMBER", false);

        edUserName.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        // luu lai toan bo du lieu
        edit.commit();
    }

    public void checkLogin() {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.trim().isEmpty() || strPass.trim().isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (nddao.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRememberPass.isChecked());

                Intent intent = new Intent(getApplicationContext(), ManHinhChinh.class);
                intent.putExtra("user", strUser);
                intent.putExtra("pass", strPass);
                startActivity(intent);

                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("USERNAME", strUser);

                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}