/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.model;

/**
 *
 * @author ADMIN
 */
public class Users {
    private int id;
    private int maNhanVien;
    private String tenDangNhap;
    private String password;
    private String quyen;
    private String chuThich;

    // Constructors
    public Users() {
    }

    public Users(int id, int maNhanVien, String tenDangNhap, String password, String quyen, String chuThich) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.password = password;
        this.quyen = quyen;
        this.chuThich = chuThich;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }
}

