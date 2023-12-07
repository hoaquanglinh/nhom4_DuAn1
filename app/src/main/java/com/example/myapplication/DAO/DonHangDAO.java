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
import java.util.Date;
import java.util.List;

public class DonHangDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
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
        values.put("soluongmua", obj.getSoluongmua());
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

    public List<DonHang> getAllByMand(int mand) {
        String sql = "SELECT * FROM donhang WHERE mand = ?";
        String[] selectionArgs = {String.valueOf(mand)};
        return getData(sql, selectionArgs);
    }

    public List<DonHang> getAllByMatknd(int matknd) {
        String sql = "SELECT donhang.* " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp " +
                "WHERE sanpham.matknd = ?";
        String[] selectionArgs = {String.valueOf(matknd)};
        return getData(sql, selectionArgs);
    }

    public int updateTrangThai(int madh, int trangthai) {
        ContentValues values = new ContentValues();
        values.put("trangthai", trangthai);
        return db.update("donhang", values, "madh = ?", new String[]{String.valueOf(madh)});
    }

    @SuppressLint("Range")
    public int getMatkndInSanPhamByMadh(int madh) {
        int matknd = 0;
        String sql = "SELECT DISTINCT sanpham.matknd " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp " +
                "WHERE donhang.madh = ?";
        String[] selectionArgs = {String.valueOf(madh)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            matknd = cursor.getInt(cursor.getColumnIndex("matknd"));
        }
        cursor.close();
        return matknd;
    }


    @SuppressLint("Range")
    public int getPtttByMadh(int madh) {
        int pttt = 0;
        String query = "SELECT ptttt " +
                "FROM donhang " +
                "WHERE madh = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(madh)});

        if (cursor.moveToFirst()) {
            pttt = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ptttt")));
        }
        cursor.close();
        return pttt;
    }

    public long updatengay(int madh, Date thoigianhoanthanh) {
        ContentValues values = new ContentValues();
        values.put("thoigianhoanthanh", sdf.format(thoigianhoanthanh));;
        return db.update("donhang", values, "madh=?", new String[]{String.valueOf(madh)});
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
            donHang.setSoluongmua(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluongmua"))));
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

    @SuppressLint("Range")
    public List<SanPham> getListSanPhamByMatknd(int matknd) {
        List<SanPham> sanPhamList = new ArrayList<>();

        String query = "SELECT sanpham.* " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp " +
                "WHERE sanpham.matknd = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(matknd)});

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

    @SuppressLint("Range")
    public List<SanPham> getSanPhamByMadh() {
        List<SanPham> listSanPham = new ArrayList<>();
        String query = "SELECT sanpham.* " +
                "FROM donhang " +
                "INNER JOIN sanpham ON donhang.masp = sanpham.masp";
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

    @SuppressLint("Range")
    public NguoiDung getThongTinNguoiDungByMaDH(int madh) {
        NguoiDung nguoiDung = null;
        String query = "SELECT nguoidung.* " +
                "FROM donhang " +
                "INNER JOIN nguoidung ON donhang.mand = nguoidung.mand " +
                "WHERE donhang.madh = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(madh)});

        if (cursor.moveToFirst()) {
            String ten = cursor.getString(cursor.getColumnIndex("ten"));
            String gioitinh = cursor.getString(cursor.getColumnIndex("gioitinh"));
            Integer namsinh = Integer.parseInt(cursor.getString(cursor.getColumnIndex("namsinh")));
            String diachi = cursor.getString(cursor.getColumnIndex("diachi"));
            String sdt = cursor.getString(cursor.getColumnIndex("sdt"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            nguoiDung = new NguoiDung(ten, gioitinh, diachi, sdt, email, namsinh);
        }
        cursor.close();
        return nguoiDung;
    }
}
