package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.GioHang;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class GioHangDao {
    private SQLiteDatabase db;

    public GioHangDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(GioHang obj) {
        ContentValues values = new ContentValues();
        values.put("masp", obj.getMasp());
        values.put("matknd", obj.getMatknd());
        return db.insert("giohang", null, values);
    }

    public long delete(String id) {
        return db.delete("giohang", "magh = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    private List<GioHang> getData(String sql, String... selectionArgs) {
        List<GioHang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            GioHang obj = new GioHang();
            obj.setMagh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("magh"))));
            obj.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("masp"))));
            obj.setMatknd(Integer.parseInt(cursor.getString(cursor.getColumnIndex("matknd"))));
            list.add(obj);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<SanPham> getSanPhamInGioHang() {
        List<SanPham> listSanPham = new ArrayList<>();

        String query = "SELECT sanpham.* " +
                "FROM sanpham " +
                "INNER JOIN giohang ON sanpham.masp = giohang.masp";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            SanPham sanPham = new SanPham();

            sanPham.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("masp"))));
            sanPham.setMamau(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mamau"))));
            sanPham.setMahang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mahang"))));
            sanPham.setMatknd(Integer.parseInt(cursor.getString(cursor.getColumnIndex("matknd"))));
            sanPham.setTensp(cursor.getString(cursor.getColumnIndex("tensp")));
            sanPham.setGiasp(Double.parseDouble(cursor.getString(cursor.getColumnIndex("gia"))));
            sanPham.setKhoHang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("khohang"))));
            sanPham.setMota(cursor.getString(cursor.getColumnIndex("mota")));
            sanPham.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            sanPham.setAnh(cursor.getString(cursor.getColumnIndex("anh")));

            listSanPham.add(sanPham);
        }

        cursor.close();
        return listSanPham;
    }

}
