/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.Dao;

import com.mycompany.qlbh.model.Users;
import com.mycompany.qlbh.view.MyDBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ADMIN
 */
public class UsersDaolmpl implements UsersDao {
     @Override
    public Users login(String tenDangNhap, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;

        try {
            conn = new MyDBConnection().getConnection();
            String sql = "SELECT * FROM Users WHERE TenDangNhap = ? AND Password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setId(rs.getInt("ID"));
                user.setMaNhanVien(rs.getInt("MaNhanVien"));
                user.setTenDangNhap(rs.getString("TenDangNhap"));
                user.setPassword(rs.getString("Password"));
                user.setQuyen(rs.getString("Quyen"));
                user.setChuThich(rs.getString("ChuThich"));                              
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }
}
