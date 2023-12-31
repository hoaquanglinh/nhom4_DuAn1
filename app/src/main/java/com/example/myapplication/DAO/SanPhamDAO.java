package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private SQLiteDatabase db;
    private Context context;
    public SanPhamDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(SanPham obj, int matk) {
        ContentValues values = new ContentValues();
        values.put("mamau", obj.getMamau());
        values.put("mahang", obj.getMahang());
        values.put("tensp", obj.getTensp());
        values.put("matknd", matk);
        values.put("gia", obj.getGiasp());
        values.put("khohang", obj.getKhoHang());
        values.put("mota", obj.getMota());
        values.put("soluong", 1);
        values.put("anh", obj.getAnh());
        return db.insert("sanpham", null, values);
    }

    public boolean update(SanPham obj) {
        ContentValues values = new ContentValues();
        values.put("mamau", obj.getMamau());
        values.put("mahang", obj.getMahang());
        values.put("tensp", obj.getTensp());
        values.put("gia", obj.getGiasp());
        values.put("khohang", obj.getKhoHang());
        values.put("mota", obj.getMota());
        values.put("soluong", obj.getSoluong());
        values.put("anh", obj.getAnh());
        long row = db.update("sanpham", values, "masp=?", new String[]{String.valueOf(obj.getMasp())});
        return (row > 0);
    }

    public long delete(String id) {
        return db.delete("sanpham", "masp = ?", new String[]{id});
    }

    public List<SanPham> getAll() {
        String sql = "SELECT * FROM sanpham";
        return getData(sql);
    }

    public List<SanPham> getAllByMAtknd(int matk) {
        String sql = "SELECT * FROM sanpham WHERE matknd = ?";
        String[] selectionArgs = {String.valueOf(matk)};
        return getData(sql, selectionArgs);
    }

    public List<SanPham> getAllByMaHang(int maHang) {
        String sql = "SELECT * FROM sanpham WHERE mahang = ?";
        String[] selectionArgs = {String.valueOf(maHang)};
        return getData(sql, selectionArgs);
    }

    @SuppressLint("Range")
    private List<SanPham> getData(String sql, String... selectionArgs) {
        List<SanPham> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            SanPham obj = new SanPham();
            obj.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("masp"))));
            obj.setMamau(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mamau"))));
            obj.setMahang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mahang"))));
            obj.setMatknd(Integer.parseInt(cursor.getString(cursor.getColumnIndex("matknd"))));
            obj.setTensp(cursor.getString(cursor.getColumnIndex("tensp")));
            obj.setGiasp(Double.parseDouble(cursor.getString(cursor.getColumnIndex("gia"))));
            obj.setKhoHang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("khohang"))));
            obj.setMota(cursor.getString(cursor.getColumnIndex("mota")));
            obj.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            obj.setAnh(cursor.getString(cursor.getColumnIndex("anh")));
            list.add(obj);
        }
        return list;
    }

    public long updateSL(int ma, int soLuongMoi) {
        ContentValues values = new ContentValues();
        values.put("soluong", soLuongMoi);;
        return db.update("sanpham", values, "masp=?", new String[]{String.valueOf(ma)});
    }

    public List<SanPham> getAllExceptMAtknd(int matknd) {
        List<SanPham> allSanPhamExceptMAtknd = new ArrayList<>();
        for (SanPham sanPham : getAll()) {
            if (sanPham.getMatknd() != matknd) {
                allSanPhamExceptMAtknd.add(sanPham);
            }
        }
        return allSanPhamExceptMAtknd;
    }

    @SuppressLint("Range")
    public List<SanPham> getListSanPhamTheoTongSoLuongMua() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String query = "SELECT sanpham.*, SUM(donhang.soluongmua) AS tongluongmua " +
                "FROM sanpham " +
                "LEFT JOIN (SELECT masp, SUM(soluongmua) AS soluongmua FROM donhang GROUP BY masp) AS donhang ON sanpham.masp = donhang.masp " +
                "GROUP BY sanpham.masp " +
                "ORDER BY tongluongmua DESC";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            SanPham obj = new SanPham();
            obj.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("masp"))));
            obj.setMamau(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mamau"))));
            obj.setMahang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mahang"))));
            obj.setMatknd(Integer.parseInt(cursor.getString(cursor.getColumnIndex("matknd"))));
            obj.setTensp(cursor.getString(cursor.getColumnIndex("tensp")));
            obj.setGiasp(Double.parseDouble(cursor.getString(cursor.getColumnIndex("gia"))));
            obj.setKhoHang(Integer.parseInt(cursor.getString(cursor.getColumnIndex("khohang"))));
            obj.setMota(cursor.getString(cursor.getColumnIndex("mota")));
            obj.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            obj.setAnh(cursor.getString(cursor.getColumnIndex("anh")));
            sanPhamList.add(obj);
        }

        cursor.close();
        return sanPhamList;
    }

    @SuppressLint("Range")
    public int getMaTKNDByMaSP(int masp) {
        String sql = "SELECT matknd FROM sanpham WHERE masp = ?";
        String[] selectionArgs = {String.valueOf(masp)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            int matknd = cursor.getInt(cursor.getColumnIndex("matknd"));
            cursor.close();
            return matknd;
        } else {
            cursor.close();
            return -1;
        }
    }

}
