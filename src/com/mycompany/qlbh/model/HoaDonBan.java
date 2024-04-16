/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.model;

/**
 *
 * @author Dinh Giap
 */
public class HoaDonBan {
    private int MaHoaDon;
    private NhanVien nhanvien;
    private String NgayBan;
    private KhachHang khachhang;
    private double TongTien;
    private String Ghichu;
    public HoaDonBan() {
     
    }
    public HoaDonBan(int MaHoaDon, NhanVien nhanvien, String NgayBan, KhachHang khachhang, double TongTien,String GhiChu) {
        this.MaHoaDon = MaHoaDon;
        this.nhanvien = nhanvien;
        this.NgayBan = NgayBan;
        this.khachhang = khachhang;
        this.TongTien = TongTien;
        this.Ghichu = Ghichu;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public NhanVien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String NgayBan) {
        this.NgayBan = NgayBan;
    }

    public KhachHang getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(KhachHang khachhang) {
        this.khachhang = khachhang;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    
 
    
}
