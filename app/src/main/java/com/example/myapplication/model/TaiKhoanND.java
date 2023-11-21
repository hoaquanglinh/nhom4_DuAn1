package com.example.myapplication.model;

public class TaiKhoanND {
    private Integer maTKND;
    private String taiKhoanND;
    private String matKhauND;

    public TaiKhoanND() {
        this.matKhauND = matKhauND;
    }

    public TaiKhoanND(Integer maTKND, String taiKhoanND, String matKhauND) {
        this.maTKND = maTKND;
        this.taiKhoanND = taiKhoanND;
        this.matKhauND = matKhauND;
    }

    public String getTaiKhoanND() {
        return taiKhoanND;
    }

    public void setTaiKhoanND(String taiKhoanND) {
        this.taiKhoanND = taiKhoanND;
    }

    public String getMatKhauND() {
        return matKhauND;
    }

    public void setMatKhauND(String matKhauND) {
        this.matKhauND = matKhauND;
    }

    public Integer getMaTKND() {
        return maTKND;
    }

    public void setMaTKND(Integer maTKND) {
        this.maTKND = maTKND;
    }
}
