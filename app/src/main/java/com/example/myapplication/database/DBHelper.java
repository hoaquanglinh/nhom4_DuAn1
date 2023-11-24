package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "FastPhone2";
    public static final int DB_VERSION = 10;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableNguoiDung = "create table nguoidung (" +
                "mand integer primary key autoincrement, " +
                "ten text not null, " +
                "gioitinh text not null, " +
                "namsinh integer not null, " +
                "diachi text not null, " +
                "sdt text not null," +
                "email text not null," +
                "matknd TEXT REFERENCES taikhoanND(matknd))";
        db.execSQL(createTableNguoiDung);

        String createTableTaiKhoanNguoiDung = "create table taikhoanND (" +
                "matknd integer primary key autoincrement, " +
                "taikhoannd text not null, " +
                "matkhaund text not null)";
        db.execSQL(createTableTaiKhoanNguoiDung);

        db.execSQL("insert into taikhoanND values (1, 'admin', 'admin')");
        db.execSQL("insert into taikhoanND values (2, 'linh', '123')");

        String createTableQuanTriVien = "create table taikhoanQTV(taikhoanadmin text primary key, tenadmin text not null, matkhauadmin text not null)";
        db.execSQL(createTableQuanTriVien);

        String createTableMau = "create table mausac(mamau bigint primary key, tenmau text not null)";
        db.execSQL(createTableMau);
        db.execSQL("insert into mausac values (-16711936, 'Xanh'), (-65536, 'Đỏ'),(-7829368, 'Xám'),(-16777216, 'Đen'), (-1, 'Trắng')");

        String createTableHang = "create table hang(mahang integer primary key autoincrement, tenhang text not null)";
        db.execSQL(createTableHang);
        db.execSQL("insert into hang values (1, 'SamSung'), (2, 'iphone'),(3, 'Xiaomi'),(4, 'Realme'), (5, 'Nokia')");

        String createTableSanPham = "create table sanpham (" +
                "masp integer primary key autoincrement, " +
                "mamau TEXT REFERENCES mausac(mamau)," +
                "mahang TEXT REFERENCES hang(mahang)," +
                "matknd TEXT REFERENCES taikhoanND(matknd)," +
                "tensp text not null, " +
                "gia real not null," +
                "khohang integer not null," +
                "mota text not null," +
                "anh text not null)";
        db.execSQL(createTableSanPham);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nguoidung");
        db.execSQL("DROP TABLE IF EXISTS taikhoanND");
        db.execSQL("DROP TABLE IF EXISTS taikhoanQTV");
        db.execSQL("DROP TABLE IF EXISTS mausac");
        db.execSQL("DROP TABLE IF EXISTS hang");
        db.execSQL("DROP TABLE IF EXISTS sanpham");

        // Tạo lại các bảng mới
        onCreate(db);
    }
}
