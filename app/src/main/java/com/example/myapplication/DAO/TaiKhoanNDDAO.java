package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.TaiKhoanND;


import java.util.ArrayList;
import java.util.List;

public class TaiKhoanNDDAO {
    private SQLiteDatabase db;

    public TaiKhoanNDDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(TaiKhoanND obj) {
        ContentValues values = new ContentValues();
        values.put("taikhoannd", obj.getTaiKhoanND());
        values.put("matkhaund", obj.getMatKhauND());
        return db.insert("taikhoanND", null, values);
    }

    public long updatePass(TaiKhoanND obj) {
        ContentValues values = new ContentValues();
        values.put("taikhoannd", obj.getTaiKhoanND());
        values.put("matkhaund", obj.getMatKhauND());
        return db.update("taikhoanND", values, "matknd = ?", new String[]{String.valueOf(obj.getMaTKND())});
    }

    public long delete(String id) {
        return db.delete("taikhoanND", "matknd = ?", new String[]{String.valueOf(id)});
    }

    public List<TaiKhoanND> getAll() {
        String sql = "SELECT * FROM taikhoanND";
        return getData(sql);
    }

    public TaiKhoanND getID(String id) {
        String sql = "SELECT * FROM taikhoanND WHERE matknd=?";
        List<TaiKhoanND> list = getData(sql, id);
        return list.get(0);
    }

    public long updatePassByMaTKND(int maTKND, String newPass) {
        ContentValues values = new ContentValues();
        values.put("matkhaund", newPass);
        return db.update("taikhoanND", values, "matknd = ?", new String[]{String.valueOf(maTKND)});
    }

    // check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM taikhoanND WHERE taikhoannd=? AND matkhaund=?";
        List<TaiKhoanND> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    @SuppressLint("Range")
    public int getMatkndFromTaikhoannd(String tentaikhoan) {
        String sql = "SELECT matknd FROM taikhoannd WHERE taikhoannd=?";
        String[] selectionArgs = {tentaikhoan};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("matknd"));
        }
        return -1;
    }

    @SuppressLint("Range")
    private List<TaiKhoanND> getData(String sql, String... selectionArgs) {
        List<TaiKhoanND> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            TaiKhoanND obj = new TaiKhoanND();
            obj.setMaTKND(Integer.parseInt(cursor.getString(cursor.getColumnIndex("matknd"))));
            obj.setTaiKhoanND(cursor.getString(cursor.getColumnIndex("taikhoannd")));
            obj.setMatKhauND(cursor.getString(cursor.getColumnIndex("matkhaund")));
            list.add(obj);
        }
        return list;
    }
}
