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

import java.util.ArrayList;
import java.util.List;

public class HangDAO {
    private SQLiteDatabase db;

    public HangDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Hang obj) {
        ContentValues values = new ContentValues();
        values.put("tenhang", obj.getTenHang());
        return db.insert("hang", null, values);
    }
    public long update(Hang obj) {
        ContentValues values = new ContentValues();
        values.put("tenhang", obj.getTenHang());
        return db.update("hang", values, "mahang = ?", new String[]{String.valueOf(obj.getMahang())});
    }

    public List<Hang> getAll() {
        String sql = "SELECT * FROM hang";
        return getData(sql);
    }
    public void delete(Context context, String id) {
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("select * from sanpham where mahang = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() != 0){
            Toast.makeText(context, "Xóa không thành công do hãng có trong sản phẩm", Toast.LENGTH_SHORT).show();
        }else{
            long check = db.delete("hang", "mahang = ?", new String[]{String.valueOf(id)});

            if (check > 0){
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        }
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
