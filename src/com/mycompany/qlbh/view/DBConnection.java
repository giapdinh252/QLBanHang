/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.view;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Dinh Giap
 */
import java.sql.Connection;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DBConnection {
    private static final String SERVER = "localhost";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";
    private static final String DB_NAME = "QuanLySanPham";
    private static final String PORT = "1433";

    public static Connection getConnection() {
        Connection conn = null;          
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(USER);
            ds.setPassword(PASSWORD);
            ds.setServerName(SERVER);
            ds.setDatabaseName(DB_NAME);
            ds.setTrustServerCertificate(true);
            ds.setPortNumber(Integer.parseInt(PORT));
            
            conn = ds.getConnection();
            System.out.println("Kết nối đến cơ sở dữ liệu thành công!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }    
}

