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
    private String nhanvien;
    private String NgayBan;
    private String khachhang;
    private double TongTien;
    private String Ghichu;
    public HoaDonBan() {
     
    }
    public HoaDonBan(int MaHoaDon, String nhanvien, String NgayBan, String khachhang, double TongTien,String GhiChu) {
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

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String NgayBan) {
        this.NgayBan = NgayBan;
    }

    public String getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(String khachhang) {
        this.khachhang = khachhang;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }


    
    

 

    
}
