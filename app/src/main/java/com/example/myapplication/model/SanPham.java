package com.example.myapplication.model;

public class SanPham {
    private int masp;
    private int mamau;
    private int mahang;
    private  int matknd;
    private String tensp;
    private Double giasp;
    private int khoHang;
    private String mota;
    private String anh;
    public SanPham() {
    }

    public SanPham(int masp, int mamau, int mahang, int matknd, String tensp, Double giasp, int khoHang, String mota, String anh) {
        this.masp = masp;
        this.mamau = mamau;
        this.mahang = mahang;
        this.matknd = matknd;
        this.tensp = tensp;
        this.giasp = giasp;
        this.khoHang = khoHang;
        this.mota = mota;
        this.anh = anh;
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
