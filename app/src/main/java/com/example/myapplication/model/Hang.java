package com.example.myapplication.model;

public class Hang {
    private int mahang;
    private String tenHang;

    public Hang() {
    }

    public Hang(int mahang, String tenHang) {
        this.mahang = mahang;
        this.tenHang = tenHang;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
}
