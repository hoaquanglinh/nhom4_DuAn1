package com.example.myapplication.model;

public class NguoiDung {

    String ten, gioiTinh, diaChi, sdt, email;
    int namSinh, matknd, mand;

    public NguoiDung() {
    }

    public NguoiDung(String ten, String gioiTinh, String diaChi, String sdt, String email, int namSinh, int matknd, int mand) {
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.namSinh = namSinh;
        this.matknd = matknd;
        this.mand = mand;
    }

    public NguoiDung(String ten, String gioiTinh, String diaChi, String sdt, String email, int namSinh) {
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.namSinh = namSinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public int getMatknd() {
        return matknd;
    }

    public void setMatknd(int matknd) {
        this.matknd = matknd;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }
}
