package com.example.myapplication.model;

public class MauSac {
    private int mamau;
    private String tenMau;

    public MauSac() {
    }

    public MauSac(int mamau, String tenMau) {
        this.mamau = mamau;
        this.tenMau = tenMau;
    }

    public int getMamau() {
        return mamau;
    }

    public void setMamau(int mamau) {
        this.mamau = mamau;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }
}
