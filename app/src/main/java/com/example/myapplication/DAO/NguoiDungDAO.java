package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.NguoiDung;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;
    private Context context;

    public NguoiDungDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("ten", nguoiDung.getTen());
        values.put("gioitinh", nguoiDung.getGioiTinh());
        values.put("namsinh", nguoiDung.getNamSinh());
        values.put("diachi", nguoiDung.getDiaChi());
        values.put("sdt", nguoiDung.getSdt());
        values.put("email", nguoiDung.getEmail());
        values.put("matknd", nguoiDung.getMatknd());
        return db.insert("nguoidung", null, values);
    }

    public boolean update(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("ten", nguoiDung.getTen());
        values.put("gioitinh", nguoiDung.getGioiTinh());
        values.put("namsinh", nguoiDung.getNamSinh());
        values.put("diachi", nguoiDung.getDiaChi());
        values.put("sdt", nguoiDung.getSdt());
        values.put("email", nguoiDung.getEmail());
        values.put("matknd", nguoiDung.getMatknd());
        long row = db.update("nguoidung", values, "mand=?", new String[]{String.valueOf(nguoiDung.getMand())});
        return (row > 0);
    }

    public long delete(int maND) {
        return db.delete("nguoidung", "mand=?", new String[]{String.valueOf(maND)});
    }
    @SuppressLint("Range")

    public List<NguoiDung> getAllByMAtknd(int matknd) {
        String sql = "SELECT * FROM nguoidung WHERE matknd = ?";
        String[] selectionArgs = {String.valueOf(matknd)};
        return getData(sql, selectionArgs);
    }

    public List<NguoiDung> getAll() {
        String sql = "SELECT * FROM nguoidung";
        return getData(sql);
    }
    @SuppressLint("Range")

    private List<NguoiDung> getData(String sql, String... selectionArgs) {
        List<NguoiDung> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setMand(cursor.getInt(cursor.getColumnIndex("mand")));
            nguoiDung.setTen(cursor.getString(cursor.getColumnIndex("ten")));
            nguoiDung.setGioiTinh(cursor.getString(cursor.getColumnIndex("gioitinh")));
            nguoiDung.setNamSinh(cursor.getInt(cursor.getColumnIndex("namsinh")));
            nguoiDung.setDiaChi(cursor.getString(cursor.getColumnIndex("diachi")));
            nguoiDung.setSdt(cursor.getString(cursor.getColumnIndex("sdt")));
            nguoiDung.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            nguoiDung.setMatknd(cursor.getInt(cursor.getColumnIndex("matknd")));
            list.add(nguoiDung);
        }
        cursor.close();
        return list;
    }

}