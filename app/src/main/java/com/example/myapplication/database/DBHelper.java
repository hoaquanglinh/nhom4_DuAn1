package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "FastPhone8";
    public static final int DB_VERSION = 1;

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
                "matknd integer REFERENCES taikhoanND(matknd))";
        db.execSQL(createTableNguoiDung);
        db.execSQL("insert into nguoidung (mand,ten, gioitinh, namsinh, diachi, sdt, email, matknd) values (1,'Người dùng 1', 'Nam', 1990, 'Địa chỉ 1', '0123456789', 'nguoidung1@example.com', 1)");
        db.execSQL("insert into nguoidung (mand, ten, gioitinh, namsinh, diachi, sdt, email, matknd) values (2,'Người dùng 2', 'Nữ', 1995, 'Địa chỉ 2', '0987654321', 'nguoidung2@example.com', 2)");

        String createTableTaiKhoanNguoiDung = "create table taikhoanND (" +
                "matknd integer primary key autoincrement, " +
                "taikhoannd text not null, " +
                "matkhaund text not null)";
        db.execSQL(createTableTaiKhoanNguoiDung);
        db.execSQL("insert into taikhoanND values (1, 'admin', 'admin')");
        db.execSQL("insert into taikhoanND values (2, 'linh', '123')");

        String createTableMau = "create table mausac(mamau bigint primary key, tenmau text not null)";
        db.execSQL(createTableMau);
        db.execSQL("insert into mausac values (-16711936, 'Xanh'), (-65536, 'Đỏ'),(-7829368, 'Xám'),(-16777216, 'Đen'), (-1, 'Trắng')");

        String createTableHang = "create table hang(mahang integer primary key autoincrement, tenhang text not null)";
        db.execSQL(createTableHang);
        db.execSQL("insert into hang values (1, 'SamSung'), (2, 'iphone'),(3, 'Xiaomi'),(4, 'Realme'), (5, 'Nokia')");

        String createTableDonViVanChuyen = "create table donvivanchuyen (madvvc integer primary key autoincrement, tendvvc text not null, giaship real not null)";
        db.execSQL(createTableDonViVanChuyen);
        db.execSQL("insert into donvivanchuyen values (1, 'Vnexpress', 30000), (2, 'Giao hang tiet kiem', 10000)");

        String createTableSanPham = "create table sanpham (" +
                "masp integer primary key autoincrement, " +
                "mamau bigint REFERENCES mausac(mamau)," +
                "mahang integer REFERENCES hang(mahang)," +
                "matknd integer REFERENCES taikhoanND(matknd)," +
                "tensp text not null, " +
                "gia real not null," +
                "khohang integer not null," +
                "mota text not null," +
                "soluong integer not null," +
                "anh text not null)";
        db.execSQL(createTableSanPham);

        String createTableGioHang = "CREATE TABLE giohang (" +
                "magh integer PRIMARY KEY AUTOINCREMENT, " +
                "masp integer REFERENCES sanpham(masp)," +
                "matknd integer REFERENCES taikhoanND(matknd))";
        db.execSQL(createTableGioHang);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nguoidung");
        db.execSQL("DROP TABLE IF EXISTS taikhoanND");
        db.execSQL("DROP TABLE IF EXISTS taikhoanQTV");
        db.execSQL("DROP TABLE IF EXISTS mausac");
        db.execSQL("DROP TABLE IF EXISTS hang");
        db.execSQL("DROP TABLE IF EXISTS sanpham");
        db.execSQL("DROP TABLE IF EXISTS createTableDonViVanChuyen");

        // Tạo lại các bảng mới
        onCreate(db);
    }
}
