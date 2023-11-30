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
        values.put("masp", obj.getMasp());
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

    public List<DonHang> getAll() {
        String sql = "SELECT * FROM donhang";
        return getData(sql);
    }

    @SuppressLint("Range")
    private List<DonHang> getData(String sql, String... selectionArgs) {
        List<DonHang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            DonHang donHang = new DonHang();
            donHang.setMadh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("madh"))));
            donHang.setMand(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mand"))));
            donHang.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("masp"))));
            donHang.setTrangthai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("trangthai"))));
            donHang.setTongtien(Double.parseDouble(cursor.getString(cursor.getColumnIndex("mand"))));
            try {
                donHang.setThoigiandathang(sdf.parse(cursor.getString((cursor.getColumnIndex("thoigiandathang")))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                donHang.setThoigianhoanthanh(sdf.parse(cursor.getString((cursor.getColumnIndex("thoigianhoanthanh")))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            donHang.setPtttt(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ptttt"))));
            list.add(donHang);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<SanPham> getListSanPhamTrongDonHang(int mand) {
        List<SanPham> sanPhamList = new ArrayList<>();

        String query = "SELECT sanpham.* " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp " +
                "WHERE donhang.mand = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(mand)});

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
            sanPhamList.add(sanPham);
        }
        return sanPhamList;
    }

}
