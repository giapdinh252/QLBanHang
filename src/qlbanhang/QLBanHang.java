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

DangNhapJDialog dialog = new DangNhapJDialog(null,true);
dialog.setTitle("Đăng nhập hệ thống");
dialog.setResizable(false);
dialog.setLocationRelativeTo(null);
dialog.setVisible(true);



  }
      
   }
