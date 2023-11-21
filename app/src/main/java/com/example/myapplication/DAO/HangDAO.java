package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.Hang;

import java.util.ArrayList;
import java.util.List;

public class HangDAO {
    private SQLiteDatabase db;

    public HangDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Hang> getAll() {
        String sql = "SELECT * FROM hang";
        return getData(sql);
    }

    public Hang getID(String id) {
        String sql = "SELECT * FROM hang WHERE mahang=?";
        List<Hang> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<Hang> getData(String sql, String... selectionArgs) {
        List<Hang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            Hang obj = new Hang();
            obj.setMahang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mahang"))));
            obj.setTenHang(cursor.getString(cursor.getColumnIndex("tenhang")));
            list.add(obj);
        }
        return list;
    }
}
