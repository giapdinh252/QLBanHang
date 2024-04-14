/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.view;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dinh Giap
 */
public class DBConnection {
    
    private static String DB_URL ="jdbc:sqlserver://localhost:1433;" 
            + "databaseName=QuanLySanPham";
    private static String USER_NAME="sa";
    private static String PASSWORD="123456";
    public static Connection conn;
    public DBConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn =(Connection) DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Ket noi thành cong");
        }catch(ClassNotFoundException ex){
            System.out.println("Thiếu thư vien");
        } catch (SQLException ex) {
            System.out.println("loi ket noi SQL");
        }
   }
    
//    public static Connection getConnection(){
//        Connection connection =null;
//             var server = "localhost";
//var user = "sa";
//var password = "0388562129giap";
//var db = "QuanLySanPham";
//var port = "1433";
//SQLServerDataSource ds = new SQLServerDataSource();
//ds.setUser(user);
//ds.setPassword(password);
//ds.setServerName(server);
//ds.setDatabaseName(db);
//ds.setTrustServerCertificate(true);
//ds.setPortNumber(Integer.parseInt(port));
//
//try (java.sql.Connection conn = ds.getConnection()) {
//    // Kết nối thành công
//    System.out.println("Kết nối đến cơ sở dữ liệu thành công!");
//    
//    // Lấy dữ liệu từ cơ sở dữ liệu
//    String query = "SELECT * FROM HoaDon"; // Thay thế TenBang bằng tên bảng bạn muốn lấy dữ liệu từ đó
//       }
//catch (SQLException ex) {
//    ex.printStackTrace();
//}
//
//       return connection;
//   
//    } 
    public static void main(String[] args) {
        new DBConnection();
    }

   
    
}
