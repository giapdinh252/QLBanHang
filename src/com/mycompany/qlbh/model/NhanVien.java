/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.model;

import java.util.Date;

public class NhanVien {
    private int MaNhanVien;
    private String TenNhanVien;
    private String DiaChi;
    private Date NgaySinh;
    private String SoDienThoai;
    private boolean GioiTinh;
    private String ChucVu;
    private Date NgayVaoLam; // Thêm trường Ngày vào làm
    private String GhiChu; // Thêm trường Ghi chú

    public NhanVien() {
        
    }

    public NhanVien(int MaNhanVien, String TenNhanVien, String DiaChi, Date NgaySinh, String SoDienThoai, boolean GioiTinh, String ChucVu, Date NgayVaoLam, String GhiChu) {
        this.MaNhanVien = MaNhanVien;
        this.TenNhanVien = TenNhanVien;
        this.DiaChi = DiaChi;
        this.NgaySinh = NgaySinh;
        this.SoDienThoai = SoDienThoai;
        this.GioiTinh = GioiTinh;
        this.ChucVu = ChucVu;
        this.NgayVaoLam = NgayVaoLam;
        this.GhiChu = GhiChu;
    }

    public int getMaNhanVien() {
        return MaNhanVien;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setMaNhanVien(int MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
}
