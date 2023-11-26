package com.example.myapplication.model;

public class GioHang {
    private int magh;
    private int masp;
    private int matknd;

    public GioHang() {
    }

    public GioHang(int magh, int masp, int matknd) {
        this.magh = magh;
        this.masp = masp;
        this.matknd = matknd;
    }

    public int getMagh() {
        return magh;
    }

    public void setMagh(int magh) {
        this.magh = magh;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMatknd() {
        return matknd;
    }

    public void setMatknd(int matknd) {
        this.matknd = matknd;
    }
}
