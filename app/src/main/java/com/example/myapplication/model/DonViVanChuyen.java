package com.example.myapplication.model;

public class DonViVanChuyen {
    private int madvvc;
    private String tendvvc;
    private Double gia;

    public DonViVanChuyen() {
    }

    public DonViVanChuyen(int madvvc, String tendvvc, Double gia) {
        this.madvvc = madvvc;
        this.tendvvc = tendvvc;
        this.gia = gia;
    }

    public int getMadvvc() {
        return madvvc;
    }

    public void setMadvvc(int madvvc) {
        this.madvvc = madvvc;
    }

    public String getTendvvc() {
        return tendvvc;
    }

    public void setTendvvc(String tendvvc) {
        this.tendvvc = tendvvc;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }
}
