/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.model;

/**
 *
 * @author Dinh Giap
 */
public class SanPham {
    private int MaSanPham;
    private String TenSanPham;
    private int LoaiSanPham;
    private int DonGiaBan;
    private int DonGiaNhap;
    private int HangSanXuat;
    private String Ghichu;
    private int TonKho;
    public SanPham(){
    }
    public SanPham(int MaSanPham, String TenSanPham, int LoaiSanPham, int DonGiaBan, int DonGiaNhap, int HangSanXuat, String Ghichu,int TonKho) {
        this.MaSanPham = MaSanPham;
        this.TenSanPham = TenSanPham;
        this.LoaiSanPham = LoaiSanPham;
        this.DonGiaBan = DonGiaBan;
        this.DonGiaNhap = DonGiaNhap;
        this.HangSanXuat = HangSanXuat;
        this.TonKho = TonKho;
        this.Ghichu = Ghichu;
        this.TonKho = TonKho;
    }

    public int getTonKho() {
        return TonKho;
    }

    public void setTonKho(int TonKho) {
        this.TonKho = TonKho;
    }

    public int getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(int MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public int getLoaiSanPham() {
        return LoaiSanPham;
    }

    public void setLoaiSanPham(int LoaiSanPham) {
        this.LoaiSanPham = LoaiSanPham;
    }

    public int getDonGiaBan() {
        return DonGiaBan;
    }

    public void setDonGiaBan(int DonGiaBan) {
        this.DonGiaBan = DonGiaBan;
    }

    public int getDonGiaNhap() {
        return DonGiaNhap;
    }

    public void setDonGiaNhap(int DonGiaNhap) {
        this.DonGiaNhap = DonGiaNhap;
    }

    public int getHangSanXuat() {
        return HangSanXuat;
    }

    public void setHangSanXuat(int HangSanXuat) {
        this.HangSanXuat = HangSanXuat;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }
    
}
