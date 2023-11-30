package com.example.myapplication.model;

import java.io.Serializable;
import java.util.Date;

public class DonHang implements Serializable {
    private int madh;
    private int mand;
    private int masp;
    private int trangthai;
    private Double tongtien;
    private Date thoigiandathang;
    private Date thoigianhoanthanh;
    private int ptttt;

    public DonHang() {
    }

    public DonHang(int madh, int mand, int masp, int trangthai, Double tongtien, Date thoigiandathang, Date thoigianhoanthanh, int ptttt) {
        this.madh = madh;
        this.mand = mand;
        this.masp = masp;
        this.trangthai = trangthai;
        this.tongtien = tongtien;
        this.thoigiandathang = thoigiandathang;
        this.thoigianhoanthanh = thoigianhoanthanh;
        this.ptttt = ptttt;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMadh() {
        return madh;
    }

    public void setMadh(int madh) {
        this.madh = madh;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public Date getThoigiandathang() {
        return thoigiandathang;
    }

    public void setThoigiandathang(Date thoigiandathang) {
        this.thoigiandathang = thoigiandathang;
    }

    public Date getThoigianhoanthanh() {
        return thoigianhoanthanh;
    }

    public void setThoigianhoanthanh(Date thoigianhoanthanh) {
        this.thoigianhoanthanh = thoigianhoanthanh;
    }

    public int getPtttt() {
        return ptttt;
    }

    public void setPtttt(int ptttt) {
        this.ptttt = ptttt;
    }
}
