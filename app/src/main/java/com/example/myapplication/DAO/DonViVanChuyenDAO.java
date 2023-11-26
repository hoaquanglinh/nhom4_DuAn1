package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.DonViVanChuyen;
import com.example.myapplication.model.Hang;

import java.util.ArrayList;
import java.util.List;

public class DonViVanChuyenDAO {
    private SQLiteDatabase db;

    public DonViVanChuyenDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(DonViVanChuyen obj) {
        ContentValues values = new ContentValues();
        values.put("tendvvc", obj.getTendvvc());
        return db.insert("donvivanchuyen", null, values);
    }
    public long update(DonViVanChuyen obj) {
        ContentValues values = new ContentValues();
        values.put("tendvvc", obj.getTendvvc());
        values.put("giaship", obj.getGia());
        return db.update("donvivanchuyen", values, "madvvc = ?", new String[]{String.valueOf(obj.getMadvvc())});
    }

    public List<DonViVanChuyen> getAll() {
        String sql = "SELECT * FROM donvivanchuyen";
        return getData(sql);
    }
    public long delete(String id) {
        return db.delete("donvivanchuyen", "madvvc = ?", new String[]{String.valueOf(id)});
    }

    public DonViVanChuyen getID(String id) {
        String sql = "SELECT * FROM donvivanchuyen WHERE madvvc=?";
        List<DonViVanChuyen> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<DonViVanChuyen> getData(String sql, String... selectionArgs) {
        List<DonViVanChuyen> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            DonViVanChuyen obj = new DonViVanChuyen();
            obj.setMadvvc(Integer.parseInt(cursor.getString(cursor.getColumnIndex("madvvc"))));
            obj.setTendvvc(cursor.getString(cursor.getColumnIndex("tendvvc")));
            obj.setGia(Double.valueOf(cursor.getString(cursor.getColumnIndex("giaship"))));
            list.add(obj);
        }
        return list;
    }
}
