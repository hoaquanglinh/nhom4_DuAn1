package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.Hang;
import com.example.myapplication.model.MauSac;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class MauSacDAO {
    private SQLiteDatabase db;

    public MauSacDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(MauSac obj) {
        ContentValues values = new ContentValues();
        values.put("mamau", obj.getMamau());
        values.put("tenmau", obj.getTenMau());
        return db.insert("mausac", null, values);
    }

    public long update(MauSac obj) {
        ContentValues values = new ContentValues();
        values.put("tenmau", obj.getTenMau());
        return db.update("mausac", values, "mamau = ?", new String[]{String.valueOf(obj.getMamau())});
    }

    public void delete(Context context, String id) {
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("select * from sanpham where mamau = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() != 0){
            Toast.makeText(context, "Xóa không thành công do màu có trong sản phẩm", Toast.LENGTH_SHORT).show();
        }else{
            long check = db.delete("mausac", "mamau = ?", new String[]{String.valueOf(id)});

            if (check > 0){
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<MauSac> getAll() {
        String sql = "SELECT * FROM mausac";
        return getData(sql);
    }

    public MauSac getID(String id) {
        String sql = "SELECT * FROM mausac WHERE mamau=?";
        List<MauSac> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<MauSac> getData(String sql, String... selectionArgs) {
        List<MauSac> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            MauSac obj = new MauSac();
            obj.setMamau(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mamau"))));
            obj.setTenMau(cursor.getString(cursor.getColumnIndex("tenmau")));
            list.add(obj);
        }
        return list;
    }
}
