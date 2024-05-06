package qlbanhang;

import com.mycompany.qlbh.view.MainJFrame;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mycompany.qlbh.view.DangNhapJDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
/**
 *
 * @author Dinh Giap
 */
public class QLBanHang {
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

   

//       new MainJFrame();
DangNhapJDialog dialog = new DangNhapJDialog(null,true);
dialog.setTitle("Đăng nhập hệ thống");
dialog.setResizable(false);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);


//        var server = "localhost";
//var user = "sa";
//var password = "123456";
//var db = "QuanLySanPham";
//var port = "1433";
//SQLServerDataSource ds = new SQLServerDataSource();
//ds.setUser(user);
//ds.setPassword(password);
//ds.setServerName(server);
//ds.setDatabaseName(db);
//ds.setTrustServerCertificate(true);
//ds.setPortNumber(Integer.parseInt(port));
//Connection conn=null;
//try {
//   
//    conn = ds.getConnection();
//    System.out.println("Kết nối đến cơ sở dữ liệu thành công!");  
////    String query = "SELECT * FROM HoaDon"; 
////    Statement stmt = conn.createStatement();
////    ResultSet rs = stmt.executeQuery(query);      
////        while (rs.next()) {
////            
////            System.out.println(rs.getString("MaHoaDon") + " " + rs.getString("MaKhachHang"));
////        }
//    
//} catch (SQLException ex) {
//    ex.printStackTrace();
//}

  }
      
   }
