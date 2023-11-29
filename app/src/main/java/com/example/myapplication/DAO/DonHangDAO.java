package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public DonHangDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(DonHang obj) {
        ContentValues values = new ContentValues();
        values.put("mand", obj.getMand());
        values.put("trangthai", obj.getTrangthai());
        values.put("tongtien", obj.getTongtien());
        values.put("thoigiandathang", sdf.format(obj.getThoigiandathang()));
        values.put("thoigianhoanthanh", sdf.format(obj.getThoigianhoanthanh()));
        values.put("ptttt", obj.getPtttt());
        return db.insert("donhang", null, values);
    }

    public long delete(String id) {
        return db.delete("donhang", "madh = ?", new String[]{id});
    }

    @SuppressLint("Range")
    public ArrayList<DonHang> getData(String sql, String... selectionArgs) {
        ArrayList<DonHang> donHangList = new ArrayList<>();

        String query = "SELECT donhang.*, sanpham.*, nguoidung.* " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp " +
                "INNER JOIN nguoidung ON donhang.mand = nguoidung.mand";

        Cursor cursor = db.rawQuery(query, null);

        cursor.close();
        db.close();

        return donHangList;
    }


}
