package com.example.myapplication.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int masp;
    private int mamau;
    private int mahang;
    private  int matknd;
    private String tensp;
    private Double giasp;
    private int khoHang;
    private String mota;
    private int soluong;
    private String anh;
    public SanPham() {
    }

    public SanPham(int masp, String tensp, Double giasp, int soluong) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public SanPham(int masp, int mamau, int mahang, int matknd, String tensp, Double giasp, int khoHang, String mota, int soluong, String anh) {
        this.masp = masp;
        this.mamau = mamau;
        this.mahang = mahang;
        this.matknd = matknd;
        this.tensp = tensp;
        this.giasp = giasp;
        this.khoHang = khoHang;
        this.mota = mota;
        this.soluong = soluong;
        this.anh = anh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getMatknd() {
        return matknd;
    }

    public void setMatknd(int matknd) {
        this.matknd = matknd;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMamau() {
        return mamau;
    }

    public void setMamau(int mamau) {
        this.mamau = mamau;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Double getGiasp() {
        return giasp;
    }

    public void setGiasp(Double giasp) {
        this.giasp = giasp;
    }

    public int getKhoHang() {
        return khoHang;
    }

    public void setKhoHang(int khoHang) {
        this.khoHang = khoHang;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
