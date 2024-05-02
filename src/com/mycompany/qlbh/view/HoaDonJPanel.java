/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qlbh.view;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mycompany.qlbh.model.ChiTietHoaDon;
import com.mycompany.qlbh.model.HoaDonBan;
import com.mycompany.qlbh.model.KhachHang;
import com.mycompany.qlbh.model.NhanVien;
import com.mycompany.qlbh.view.MyDBConnection;
import java.awt.TextField;
import static java.lang.Double.parseDouble;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dinh Giap
 */
public class HoaDonJPanel extends javax.swing.JPanel {   
    List<HoaDonBan> Hoadon = new ArrayList<>();
    List<ChiTietHoaDon> chiTietHoaDons=new ArrayList<>();
    /**
     * Creates new form HoaDonJPanel
     */
//    --------------------------------------------------------------------------------------
    
   public HoaDonJPanel() {
        initComponents();       
        ShowdulieuHoaDon();
        ThemNhanvien();
        ThemKhachHang(); 
        ThemSP();
       
        
    }
   public void ErrorMessage(){
    try {
       JTextField tien = txtTongTienHD ; 
      
       if (tien.getText().isEmpty()) {
           JOptionPane.showMessageDialog(null, "Vui lòng nhập Tổng Tiền !", "Error", JOptionPane.ERROR_MESSAGE);
       }else {
           double value = Double.parseDouble(tien.getText());
           if(Double.isNaN(value)){
               JOptionPane.showMessageDialog(null, "Tổng tiền phải là số !", "Error" , JOptionPane.ERROR_MESSAGE);        
           } else{               
               JOptionPane.showMessageDialog(null, "Thêm dữ liệu hóa đơn thành công !", "Success",JOptionPane.INFORMATION_MESSAGE);
           }                 
       }
       
    } catch ( NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Tổng tiền phải là số !", "Error" , JOptionPane.ERROR_MESSAGE);
    }catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập Tổng Tiền !", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
public void ErrorMessageDelete() {
    try {
        JTextField tien = txtTongTienHD;
        JTextField MaHD = txtHoaDonHD;
        if (tien.getText().isEmpty() || MaHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu cần xóa !", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (!MaHD.getText().isEmpty()) { 
                    removeHoaDonBan();
                    JOptionPane.showMessageDialog(null, "Xóa dữ liệu hóa đơn thành công !", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu cần xóa !", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                
            }
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn dữ liệu cần xóa !", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


// -------------------------------------------------------------------------------   
    public void addHoaDonBan(){ 
        
    String NhanVien = txtNhanVien.getSelectedItem().toString();
    String KhachHang = txtKhachHang.getSelectedItem().toString();

    double TongTien=parseDouble(txtTongTienHD.getText());
    String Ghichu=GhiChuHoaDon.getText();
    Connection conn = new MyDBConnection().getConnection();
try {             
    Statement stmt = conn.createStatement();    
    String maKhachHangQuery = "SELECT MaKhachHang FROM KhachHang WHERE TenKhachHang = N'" + KhachHang + "'";

    ResultSet rsKhachHang = stmt.executeQuery(maKhachHangQuery);
    
    if (rsKhachHang.next()) {
        int maKhachHang = rsKhachHang.getInt("MaKhachHang");

        
        String maNhanVienQuery = "SELECT MaNhanVien FROM NhanVien WHERE TenNhanVien = N'" + NhanVien + "'";
        ResultSet rsNhanVien = stmt.executeQuery(maNhanVienQuery);
        
        if (rsNhanVien.next()) {
            int maNhanVien = rsNhanVien.getInt("MaNhanVien");

           
         String queryNV = "INSERT INTO HoaDon (MaKhachHang, MaNhanVien, TongTien, GhiChu) " +
                 "VALUES (" + maKhachHang + ", " + maNhanVien + ", " + 
                 TongTien + ", '" + Ghichu + "')";

            int rowsInserted = stmt.executeUpdate(queryNV);
            if (rowsInserted > 0) {
                System.out.println("Dữ liệu đã được chèn thành công!");
            } else {
                System.out.println("Không thể chèn dữ liệu vào bảng HoaDon!");
            }
        } else {
            System.out.println("Không tìm thấy Mã Nhân Viên : " + NhanVien);
        }
    } else {
        System.out.println("Không tìm thấy Mã Khách Hàng : " + KhachHang);
    }
    conn.close();
} catch (SQLException ex) {
    ex.printStackTrace();
}
    }
//    --------------------------------------------------------------------------------------
    public void removeCTHD(){
        String txt =txtHoaDonHD.getText();  
        if(txt.isEmpty()){
            return;
        }
        int maHoaDon = Integer.parseInt(txt);
       
    String querychitietHD = "DELETE FROM ChiTietHoaDon WHERE MaHoaDon = ?";
    Connection conn = new MyDBConnection().getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(querychitietHD)) {
    pstmt.setInt(1, maHoaDon);
    pstmt.executeUpdate();
    
} catch (SQLException ex) {
    ex.printStackTrace();
}

    }
    
    public void removeHoaDonBan(){
    int selectedRow = TableHoaDon.getSelectedRow();
    if (selectedRow != -1) { 
        Hoadon.remove(selectedRow); 
        ((DefaultTableModel) TableHoaDon.getModel()).removeRow(selectedRow); 
    }
    String NhanVien = txtNhanVien.getSelectedItem().toString();
    String KhachHang = txtKhachHang.getSelectedItem().toString();
    int mahoadon= Integer.parseInt(txtHoaDonHD.getText());
    double TongTien=parseDouble(txtTongTienHD.getText());
    String Ghichu=GhiChuHoaDon.getText();
    Connection conn = new MyDBConnection().getConnection();
try {             
    Statement stmt = conn.createStatement();    
    String maKhachHangQuery = "SELECT MaKhachHang FROM KhachHang WHERE TenKhachHang = N'" + KhachHang + "'";

    ResultSet rsKhachHang = stmt.executeQuery(maKhachHangQuery);
    
    if (rsKhachHang.next()) {
        int maKhachHang = rsKhachHang.getInt("MaKhachHang");

        
        String maNhanVienQuery = "SELECT MaNhanVien FROM NhanVien WHERE TenNhanVien = N'" + NhanVien + "'";
        ResultSet rsNhanVien = stmt.executeQuery(maNhanVienQuery);
        
        if (rsNhanVien.next()) {
            int maNhanVien = rsNhanVien.getInt("MaNhanVien");

          
           String queryNV = "DELETE FROM HoaDon " +
                 "WHERE MaKhachHang = " + maKhachHang +" AND MaHoaDon = " + mahoadon + " AND MaNhanVien = " + maNhanVien + " AND TongTien = " + TongTien + " AND (GhiChu = '' OR GhiChu = '"+ Ghichu +"')";
        
            int rowsInserted = stmt.executeUpdate(queryNV);
           
            if (rowsInserted > 0) {
                System.out.println("Dữ liệu đã được xóa thành công!");
            } else {
                System.out.println("Không thể xóa dữ liệu vào bảng HoaDon!");
            }
        } else {
            System.out.println("Không tìm thấy Mã Nhân Viên : " + NhanVien);
        }
    } else {
        System.out.println("Không tìm thấy Mã Khách Hàng : " + KhachHang);
    }
   
} catch (SQLException ex) {
    ex.printStackTrace();
}
    
}
    //    --------------------------------------------------------------------------------------
    public void updateSTTColumnHoaDon() {
    DefaultTableModel model = (DefaultTableModel) TableHoaDon.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        model.setValueAt(i + 1, i, 0); 
    }
}
//    --------------------------------------------------------------------------------------
public void showDetail(){
        int i= TableHoaDon.getSelectedRow();            
        HoaDonBan hd=Hoadon.get(i);
        txtHoaDonHD.setText(String.valueOf(hd.getMaHoaDon()));     
        GhiChuHoaDon.setText(hd.getGhichu());
        txtNhanVien.setSelectedItem(hd.getNhanvien());
        txtKhachHang.setSelectedItem(hd.getKhachhang());
        txtTongTienHD.setText(String.valueOf(hd.getTongTien()));
        txtNgayLap.setText(hd.getNgayBan());
        txtMaHoaDonCTHD.setText(String.valueOf(hd.getMaHoaDon()));
    }
//    --------------------------------------------------------------------------------------
    
//    --------------------------------------------------------------------------------------
    public void ThemNhanvien(){

 Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();
         String queryNV = "SELECT * FROM NhanVien";
         ResultSet rsNV = stmt.executeQuery(queryNV);   
            while (rsNV.next()) {               
                txtNhanVien.addItem(rsNV.getString("TenNhanVien"));               
            }
                      
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
    }
//    --------------------------------------------------------------------------------------
    void ThemKhachHang(){
         Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();        
          String queryKH = "SELECT * FROM KhachHang"; 
         ResultSet rsKH = stmt.executeQuery(queryKH);                           
             while (rsKH.next()) {               
                txtKhachHang.addItem(rsKH.getString("TenKhachHang"));              
            }           
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
    } 
//    -------------------------------------------------------------------------------------------
     private void ShowdulieuHoaDon() {    
    DefaultTableModel model = (DefaultTableModel)TableHoaDon.getModel();
    Connection conn = new MyDBConnection().getConnection();
        model.setRowCount(0);
        try{             
        String query = "select MaHoaDon ,TenKhachHang,TenNhanVien,NgayLapHoaDon,TongTien,HoaDon.GhiChu\n" +
                        "from HoaDon join NhanVien on HoaDon.MaNhanVien=NhanVien.MaNhanVien\n" +
                        "join KhachHang on HoaDon.MaKhachHang = KhachHang.MaKhachHang " ;                         
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);                       
            while (rs.next()) {        
                HoaDonBan gg = new HoaDonBan();
                Object obj[]=new Object[10];              
                obj[0]=TableHoaDon.getRowCount()+1;               
                obj[1]=rs.getInt("MaHoaDon");
                obj[2]=rs.getString("TenNhanVien");
                obj[3]=rs.getString("TenKhachHang"); 
                obj[4]=rs.getDate("NgayLapHoaDon");
                obj[5]=rs.getString("TongTien");
                obj[6]=rs.getString("GhiChu");                 
                model.addRow(obj); 
                 gg.setMaHoaDon(rs.getInt("MaHoaDon"));
                gg.setNhanvien(rs.getString("TenNhanVien"));
                gg.setKhachhang(rs.getString("TenKhachHang"));
                gg.setNgayBan(rs.getString("NgayLapHoaDon"));
                gg.setTongTien(rs.getDouble("TongTien"));
                gg.setGhichu(rs.getString("GhiChu"));                
                 Hoadon.add(gg);                                     
            }
            rs.close();
    } catch (SQLException ex) {       
        ex.printStackTrace();
    }
}
    public void suaHoaDon() {
    DefaultTableModel model = (DefaultTableModel) TableHoaDon.getModel();
   Connection conn = new MyDBConnection().getConnection();
    int selectedRowIndex = TableHoaDon.getSelectedRow();
    
    if (selectedRowIndex == -1) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần sửa.");
        return;
    }
    String NhanVien = txtNhanVien.getSelectedItem().toString();
    String KhachHang = txtKhachHang.getSelectedItem().toString();
    
    try {
        
        conn = new MyDBConnection().getConnection();
      
       Statement stmt = conn.createStatement();    
    String maKhachHangQuery = "SELECT MaKhachHang FROM KhachHang WHERE TenKhachHang = N'" + KhachHang + "'";

    ResultSet rsKhachHang = stmt.executeQuery(maKhachHangQuery);
    
    if (rsKhachHang.next()) {
        int maKhachHang = rsKhachHang.getInt("MaKhachHang");

        
        String maNhanVienQuery = "SELECT MaNhanVien FROM NhanVien WHERE TenNhanVien = N'" + NhanVien + "'";
        ResultSet rsNhanVien = stmt.executeQuery(maNhanVienQuery);
        
        if (rsNhanVien.next()) {
            int maNhanVien = rsNhanVien.getInt("MaNhanVien");
            String sql = "update HoaDon set MaKhachHang = ?, MaNhanVien = ?, NgayLapHoaDon = ?, TongTien = ?, GhiChu = ? where MaHoaDon = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1,  maKhachHang);
        st.setInt(2, maNhanVien);
        st.setString(3, txtNgayLap.getText());
        st.setDouble(4, parseDouble(txtTongTienHD.getText()));
        st.setString(5, GhiChuHoaDon.getText());
        st.setInt(6, Integer.parseInt(model.getValueAt(selectedRowIndex, 1).toString())); // Assuming MaHoaDon is in the first column
       
        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Update dữ liệu thành công", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Không có hóa đơn nào được cập nhật", "Warning", JOptionPane.WARNING_MESSAGE);
        } 
        }
       
    }
      
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đóng kết nối: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
//    --------------------------------------------------------------
    public void reset(){
        txtHoaDonHD.setText("");      
        txtNgayLap.setText("");
        txtTongTienHD.setText("");
        GhiChuHoaDon.setText("");
    }
//-------------------------------------------Chi Tiết Hóa Đơn---------------------------------------------------------------------
public void TinhTienSP() {
    int SoLuong = 0;
    double Tien = 0;
    String soLuongText = txtSoLuong.getText();
        SoLuong = Integer.parseInt(soLuongText);
            
    double Gia = GetGiaSanPham();
    Tien = Gia * SoLuong;
    txtTongTienCTHD.setText(String.valueOf(Tien));
}
public void batLoiSL(){
    int SoLuong = 0;
    String soLuongText = txtSoLuong.getText();
if (!soLuongText.isEmpty()) {
    try {
        SoLuong = Integer.parseInt(soLuongText);
        if (SoLuong <= 0) {
            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; 
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Số lượng phải là một số nguyên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return; 
    }
} else {
    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    return; 
}

}

public double GetGiaSanPham() {
    String TenSP = jcbSanPham.getSelectedItem().toString();
    
    double Gia=0;

    try (Connection conn = new MyDBConnection().getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT GiaBan FROM SanPham WHERE TenSanPham = ?")) {
        pstmt.setString(1, TenSP);
        ResultSet rsSP = pstmt.executeQuery();
        if (rsSP.next()) {
            Gia = rsSP.getDouble("GiaBan");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }    
    
    return Gia;
}
public int MaSanPham(){
    String TenSP = jcbSanPham.getSelectedItem().toString();
    int MaSP=0;
    try (Connection conn = new MyDBConnection().getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT MaSanPham FROM SanPham WHERE TenSanPham = ?")) {
        pstmt.setString(1, TenSP);
        ResultSet rsSP = pstmt.executeQuery();
        if (rsSP.next()) {
            MaSP = rsSP.getInt("MaSanPham");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }    
    return MaSP;
}

    public void ShowdulieuCTHD() {    
    DefaultTableModel model = (DefaultTableModel)TableCTHD.getModel();
    Connection conn = new MyDBConnection().getConnection();
    model.setRowCount(0);
    String maHoaDonText = txtHoaDonHD.getText();
    if (maHoaDonText.isEmpty()) {   
    return;
}
int MaHoaDon = Integer.parseInt(maHoaDonText);

         System.out.println(MaHoaDon);   
        try{             
String query = "SELECT MaCTHD, ChiTietHoaDon.MaHoaDon, MaSanPham, SoLuong, ChiTietHoaDon.TongTien, ChiTietHoaDon.GhiChu " +
               "FROM ChiTietHoaDon " +
               "JOIN HoaDon ON ChiTietHoaDon.MaHoaDon = HoaDon.MaHoaDon " +
               "WHERE ChiTietHoaDon.MaHoaDon = " + MaHoaDon;
                                                             
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);                       
            while (rs.next()) {        
                ChiTietHoaDon cthd =new  ChiTietHoaDon();
                Object obj[]=new Object[10];              
                obj[0]=TableCTHD.getRowCount()+1;               
                obj[1]=rs.getInt("MaCTHD");
                obj[2]=rs.getInt("MaHoaDon");
                obj[3]=rs.getInt("MaSanPham"); 
                obj[4] = rs.getInt("SoLuong");
                obj[5]=rs.getDouble("TongTien");
                obj[6]=rs.getString("GhiChu"); 
                model.addRow(obj); 
                cthd.setMaCTHD(rs.getInt("MaCTHD"));
                cthd.setMaHoaDon(rs.getInt("MaHoaDon"));
                cthd.setMaSanPham(rs.getInt("MaSanPham"));
                cthd.setSoLuong(rs.getInt("SoLuong"));
                cthd.setTongTien(rs.getDouble("TongTien"));
                cthd.setGhiChu(rs.getString("GhiChu"));
                chiTietHoaDons.add(cthd);
            }
            rs.close();
    } catch (SQLException ex) {       
        ex.printStackTrace();
    }
}
public void showDetailCTHD() {
    int selectedIndex = TableCTHD.getSelectedRow();
    if (selectedIndex == -1) {
        return;
    }   
    ChiTietHoaDon CTHD = chiTietHoaDons.get(selectedIndex);
    int maSanPham = CTHD.getMaSanPham();
    System.out.println(maSanPham);
    try (Connection conn = new MyDBConnection().getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT TenSanPham FROM SanPham WHERE MaSanPham = ?")) {                
        pstmt.setInt(1, maSanPham);
        ResultSet rsSP = pstmt.executeQuery();
        if (rsSP.next()) {
            String tenSanPham = rsSP.getString("TenSanPham");
            jcbSanPham.setSelectedItem(tenSanPham);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    txtMaCTHD.setText(String.valueOf(CTHD.getMaCTHD()));     
    GhiChuCTHD.setText(CTHD.getGhiChu());
    txtSoLuong.setText(String.valueOf(CTHD.getSoLuong()));        
    txtTongTienCTHD.setText(String.valueOf(CTHD.getTongTien()));       
}


     public void ThemSP(){
         Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();
         String queryNV = "SELECT * FROM SanPham";
         ResultSet rsNV = stmt.executeQuery(queryNV); 
         jcbSanPham.removeAllItems();
            while (rsNV.next()) {               
                jcbSanPham.addItem(rsNV.getString("TenSanPham"));               
            }
                      
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
     }
     public void addCTHD(){             
    int MaHoaDon=Integer.parseInt(txtHoaDonHD.getText());
    double TongTien=parseDouble(txtTongTienCTHD.getText());
    int SoLuong=Integer.parseInt(txtSoLuong.getText());
    int MaSanPham=MaSanPham();
    String Ghichu=GhiChuCTHD.getText();
    Connection conn = new MyDBConnection().getConnection();
    if(SoLuong>0){
        try {             
    Statement stmt = conn.createStatement();              
         String queryNV = "INSERT INTO ChiTietHoaDon ( MaHoaDon,MaSanPham,SoLuong, TongTien, ChiTietHoaDon.GhiChu) " +
                 "VALUES ( " + MaHoaDon + ", " + MaSanPham +","+ SoLuong+","+
                 TongTien + ", '" + Ghichu + "')";

            int rowsInserted = stmt.executeUpdate(queryNV);
            if (rowsInserted > 0) {
                System.out.println("Dữ liệu đã được chèn thành công!");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Không thể chèn dữ liệu vào bảng Chi tiet hoa don !");
            }
            
    conn.close();
} catch (SQLException ex) {
    ex.printStackTrace();
}
    }

    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHoaDonHD = new javax.swing.JTextField();
        txtNhanVien = new javax.swing.JComboBox<>();
        txtKhachHang = new javax.swing.JComboBox<>();
        txtNgayLap = new javax.swing.JTextField();
        txtTongTienHD = new javax.swing.JTextField();
        SuaHoaDon = new javax.swing.JButton();
        XoaHoaDon = new javax.swing.JButton();
        ResetHoaDon = new javax.swing.JButton();
        ThemHoaDon = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GhiChuHoaDon = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableCTHD = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaCTHD = new javax.swing.JTextField();
        jcbSanPham = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JTextField();
        txtTongTienCTHD = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        GhiChuCTHD = new javax.swing.JTextArea();
        txtMaHoaDonCTHD = new javax.swing.JTextField();

        jLabel15.setText("Hóa Đơn Bán");
        jLabel15.setToolTipText("");

        TableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Lập Hóa Đơn", "Tổng Tiền", "Ghi chú"
            }
        ));
        TableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableHoaDonMouseClicked(evt);
            }
        });
        TableHoaDon.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                TableHoaDonComponentShown(evt);
            }
        });
        jScrollPane3.setViewportView(TableHoaDon);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jPanel1InputMethodTextChanged(evt);
            }
        });

        jLabel2.setText("Mã Hóa Đơn");

        jLabel3.setText("Nhân Viên");

        jLabel4.setText("Ngày Lập");

        jLabel5.setText("Khách Hàng");

        jLabel6.setText("Tổng Tiền");

        txtHoaDonHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoaDonHDMouseClicked(evt);
            }
        });
        txtHoaDonHD.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtHoaDonHDInputMethodTextChanged(evt);
            }
        });
        txtHoaDonHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaDonHDActionPerformed(evt);
            }
        });

        txtNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienActionPerformed(evt);
            }
        });

        txtNgayLap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNgayLapMouseClicked(evt);
            }
        });

        SuaHoaDon.setText("Sửa");
        SuaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaHoaDonActionPerformed(evt);
            }
        });

        XoaHoaDon.setText("Xóa");
        XoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaHoaDonActionPerformed(evt);
            }
        });

        ResetHoaDon.setText("Reset");
        ResetHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetHoaDonActionPerformed(evt);
            }
        });

        ThemHoaDon.setText("Thêm");
        ThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemHoaDonActionPerformed(evt);
            }
        });

        jLabel7.setText("Ghi Chú");

        GhiChuHoaDon.setColumns(20);
        GhiChuHoaDon.setRows(5);
        jScrollPane1.setViewportView(GhiChuHoaDon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(XoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(SuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(ResetHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHoaDonHD)
                            .addComponent(txtNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKhachHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayLap, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(txtTongTienHD, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoaDonHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TableCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã CTHD", "Mã Hóa Đơn", "Mã Sản Phẩm", "Số Lượng", "Tổng Tiền", "Ghi Chú"
            }
        ));
        TableCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCTHDMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableCTHD);

        jLabel1.setText("Chi Tiết Hóa Đơn Bán");

        jLabel8.setText("Mã CTHD");

        jLabel9.setText("Mã Hóa Đơn");

        jLabel10.setText("Số Lượng");

        jLabel11.setText("Sản Phẩm");

        jLabel12.setText("Tổng Tiền");

        jcbSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbSanPhamMouseClicked(evt);
            }
        });

        txtSoLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSoLuongMouseClicked(evt);
            }
        });
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        txtTongTienCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienCTHDActionPerformed(evt);
            }
        });

        jButton5.setText("Sửa");

        jButton6.setText("Xóa");

        jButton7.setText("Reset");

        jButton8.setText("Thêm");
        jButton8.setToolTipText("");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel13.setText("Ghi Chú");

        GhiChuCTHD.setColumns(20);
        GhiChuCTHD.setRows(5);
        jScrollPane2.setViewportView(GhiChuCTHD);

        txtMaHoaDonCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonCTHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 32, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaCTHD)
                            .addComponent(jcbSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(txtTongTienCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(txtMaHoaDonCTHD))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaHoaDonCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


         
    
    private void ThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemHoaDonActionPerformed
       ErrorMessage();
       addHoaDonBan();
       ShowdulieuHoaDon();
       
       
      
    }//GEN-LAST:event_ThemHoaDonActionPerformed

    private void SuaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaHoaDonActionPerformed
        suaHoaDon();
        ShowdulieuHoaDon();
    }//GEN-LAST:event_SuaHoaDonActionPerformed

    private void ResetHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetHoaDonActionPerformed
      reset();
    }//GEN-LAST:event_ResetHoaDonActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        batLoiSL();
        addCTHD();
        ShowdulieuCTHD();
      
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtHoaDonHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoaDonHDActionPerformed
        
    }//GEN-LAST:event_txtHoaDonHDActionPerformed

   
    private void TableHoaDonComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_TableHoaDonComponentShown
                
    }//GEN-LAST:event_TableHoaDonComponentShown

    private void txtNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienActionPerformed

    private void txtHoaDonHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoaDonHDMouseClicked
       txtHoaDonHD.setEditable(false);
    }//GEN-LAST:event_txtHoaDonHDMouseClicked

    private void TableHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableHoaDonMouseClicked
    showDetail();
    ShowdulieuCTHD();
    }//GEN-LAST:event_TableHoaDonMouseClicked

    private void XoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaHoaDonActionPerformed
        removeCTHD();
        ErrorMessageDelete();
        updateSTTColumnHoaDon();
        
    }//GEN-LAST:event_XoaHoaDonActionPerformed

    private void jPanel1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jPanel1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1InputMethodTextChanged

    private void txtNgayLapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayLapMouseClicked
      txtNgayLap.setEditable(false);
    }//GEN-LAST:event_txtNgayLapMouseClicked

    private void txtHoaDonHDInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtHoaDonHDInputMethodTextChanged
        
    }//GEN-LAST:event_txtHoaDonHDInputMethodTextChanged

    private void txtMaHoaDonCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonCTHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonCTHDActionPerformed

    private void txtTongTienCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienCTHDActionPerformed
        
    }//GEN-LAST:event_txtTongTienCTHDActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
       
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtSoLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoLuongMouseClicked
       
    }//GEN-LAST:event_txtSoLuongMouseClicked

    private void txtSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyPressed
      
    }//GEN-LAST:event_txtSoLuongKeyPressed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
      TinhTienSP();
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void jcbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbSanPhamMouseClicked
        TinhTienSP();
    }//GEN-LAST:event_jcbSanPhamMouseClicked

    private void TableCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCTHDMouseClicked
        showDetailCTHD();
    }//GEN-LAST:event_TableCTHDMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea GhiChuCTHD;
    private javax.swing.JTextArea GhiChuHoaDon;
    private javax.swing.JButton ResetHoaDon;
    private javax.swing.JButton SuaHoaDon;
    private javax.swing.JTable TableCTHD;
    private javax.swing.JTable TableHoaDon;
    private javax.swing.JButton ThemHoaDon;
    private javax.swing.JButton XoaHoaDon;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox<String> jcbSanPham;
    private javax.swing.JTextField txtHoaDonHD;
    private javax.swing.JComboBox<String> txtKhachHang;
    private javax.swing.JTextField txtMaCTHD;
    private javax.swing.JTextField txtMaHoaDonCTHD;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JComboBox<String> txtNhanVien;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTongTienCTHD;
    private javax.swing.JTextField txtTongTienHD;
    // End of variables declaration//GEN-END:variables

   
}
