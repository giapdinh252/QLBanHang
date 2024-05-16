/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qlbh.view;
import com.mycompany.qlbh.model.SanPham;
import com.mycompany.qlbh.model.ChiTietHoaDon;
import static java.lang.Double.parseDouble;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;     
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Dinh Giap
 */
public class SanPhamJPanel extends javax.swing.JPanel {
   List<SanPham> sp= new ArrayList<>();
   private String loaisp;
    /**
     * Creates new form SanPhamJPanel
     */
    public SanPhamJPanel() {
        initComponents();
        ShowdulieuSP();
        ThemSanPham();
        ThemHangSanXuat();
        
        
    }
     public void ShowdulieuSP() {    
    DefaultTableModel model = (DefaultTableModel) TableSanPham.getModel();
    Connection conn = new MyDBConnection().getConnection();
    model.setRowCount(0);
    try {
        String query = "SELECT MaSanPham, TenSanPham, TenLoaiSanPham, GiaNhap, GiaBan, MaHangSanXuat, TonKho, ChuThich " +
                       "FROM SanPham " +
                       "JOIN LoaiSanPham ON LoaiSanPham.MaLoaiSanPham = SanPham.MaLoaiSanPham";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Object[] rowData = new Object[10];
            rowData[0] = TableSanPham.getRowCount()+1;
            rowData[1] = rs.getInt("MaSanPham");
            rowData[2] = rs.getString("TenSanPham");
            rowData[3] = rs.getString("TenLoaiSanPham");
            rowData[4] = rs.getDouble("GiaNhap");
            rowData[5] = rs.getDouble("GiaBan");
            rowData[6] = rs.getInt("MaHangSanXuat");
            rowData[7] = rs.getInt("TonKho");
            rowData[8] = rs.getString("ChuThich");
            model.addRow(rowData);
            SanPham gg = new SanPham();
                 gg.setMaSanPham(rs.getInt("MaSanPham"));
                gg.setTenSanPham(rs.getString("TenSanPham"));
                gg.setLoaiSanPham(rs.getString("TenLoaiSanPham"));
                gg.setDonGiaNhap( rs.getInt("GiaNhap"));
                gg.setDonGiaBan(rs.getInt("GiaBan"));
                gg.setHangSanXuat(rs.getInt("MaHangSanXuat"));
                gg.setTonKho(rs.getInt("TonKho"));
                gg.setGhichu(rs.getString("ChuThich"));                
                 sp.add(gg);
                    
          
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
 

     public int MaLoaiSP(){
        
         int Ten = 0;
     try {
        Connection conn = new MyDBConnection().getConnection();
        String SQL1 = "SELECT MaLoaiSanPham FROM LoaiSanPham WHERE TenLoaiSanPham = ?";
       PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
       pstmt1.setString(1, LoaiSanPham.getSelectedItem().toString());
      ResultSet rs1 = pstmt1.executeQuery();
        while (rs1.next()) {
            Ten = rs1.getInt("MaLoaiSanPham");
        }     
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
         System.out.println("Ten" +Ten);
     return Ten;
     }
     
     public int MaHangSX(){
           int Hang = 0 ;
   
     try {
        Connection conn = new MyDBConnection().getConnection();
        String SQL2 = "SELECT MaHangSanXuat FROM HangSanXuat WHERE TenHangSanXuat = ?";
       PreparedStatement pstmt2 = conn.prepareStatement(SQL2);
       pstmt2.setString(1, HangSX.getSelectedItem().toString());
     ResultSet rs2 = pstmt2.executeQuery();
        while (rs2.next()) {
            Hang = rs2.getInt("MaHangSanXuat");
        }
        
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
         System.out.println(Hang);
     return Hang;
     }
public void showDetail() {
    int i = TableSanPham.getSelectedRow();            
    SanPham hd = sp.get(i);
  
    String Hang = null;
    
    try {
        Connection conn = new MyDBConnection().getConnection();
       
        String SQL2 = "SELECT TenHangSanXuat FROM HangSanXuat WHERE MaHangSanXuat = ?";

        
        PreparedStatement pstmt2 = conn.prepareStatement(SQL2);
        
        pstmt2.setInt(1, hd.getHangSanXuat());
        
       
        ResultSet rs2 = pstmt2.executeQuery();
      
        
        while (rs2.next()) {
            Hang = rs2.getString("TenHangSanXuat");
        }
        
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    System.out.println(Hang);
    System.out.println(i);
    MaSP.setText(String.valueOf(hd.getMaSanPham()));     
    TenSP.setText(hd.getTenSanPham());
    LoaiSanPham.setSelectedItem(hd.getLoaiSanPham());
    GiaBan.setText(String.valueOf(hd.getDonGiaBan()));
    GiaNhap.setText(String.valueOf(hd.getDonGiaNhap()));   
    HangSX.setSelectedItem(Hang);      
    TonKho.setText(String.valueOf(hd.getTonKho()));
    GhiChu.setText(hd.getGhichu());     
}
public void messege(){
    double gianhap = 0;
     double giaban = 0;
     int kho =0;
    String Giaban = GiaBan.getText();
    String Gianhap = GiaNhap.getText();
    String Kho=TonKho.getText();
    if(TenSP.getText().isEmpty()|| GiaNhap.getText().isEmpty()||GiaBan.getText().isEmpty()||TonKho.getText().isEmpty()){
        JOptionPane.showMessageDialog(null, "Vui lòng nhập dữ liệu đầy đủ !");
        return;
    }else if(!Giaban.isEmpty()||!Gianhap.isEmpty()||!Kho.isEmpty()){
    try {
        gianhap = Double.parseDouble(Gianhap);
        giaban=Double.parseDouble(Giaban);
        kho =Integer.parseInt(Kho);
        if (gianhap <= 0||giaban<=0) {
            JOptionPane.showMessageDialog(null, "Giá phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; 
        }else if(kho<0 ){
            JOptionPane.showMessageDialog(null, "Tồn kho phải lớn >= 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Giá và Tồn Kho phải là một số !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return; 
    }
} else {
    JOptionPane.showMessageDialog(null, "Vui lòng nhập Giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    return; 
}
}
public void ThemSP() {
    
    String Tensp = TenSP.getText();
    int Loaisp = MaLoaiSP();
    double giaBan = Double.parseDouble(GiaBan.getText());
    double giaNhap =  Double.parseDouble(GiaNhap.getText());
    String Ghichu = GhiChu.getText();
    int Hangsx = MaHangSX();
    int tonKho = Integer.parseInt(TonKho.getText());
    String TrangThai = "1";
    String Image = null;
    
    try (Connection conn = new MyDBConnection().getConnection();
         PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?, ?)")) {
         
        pstmt.setString(1, Tensp);
        pstmt.setInt(2, Loaisp);
        pstmt.setInt(3, Hangsx);
        pstmt.setDouble(4, giaNhap);
        pstmt.setDouble(5, giaBan);
        pstmt.setInt(6, tonKho); 
        pstmt.setString(7, Ghichu);
        
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Dữ liệu đã được chèn thành công!");
            JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Không thể chèn dữ liệu vào bảng sản phẩm !");
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
       
    }
}
public void Xoacthd(){ 
    DefaultTableModel model = (DefaultTableModel) TableSanPham.getModel();
        int selectedRow = TableSanPham.getSelectedRow();  
        int MaSP;
        MaSP=Integer.parseInt(model.getValueAt(selectedRow, 1).toString()); 
    String querychitietHD = "DELETE FROM ChiTietHoaDon WHERE MaSanPham = "+ MaSP ;
    Connection conn = new MyDBConnection().getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(querychitietHD)) {
   
    pstmt.executeUpdate();
    
} catch (SQLException ex) {
    ex.printStackTrace();
}
}
public void XoaSanPham(){
       DefaultTableModel model = (DefaultTableModel) TableSanPham.getModel();
        int selectedRow = TableSanPham.getSelectedRow();
        int Masp;
    if (selectedRow == -1) { 
         JOptionPane.showMessageDialog(null, "Vui lòng chọn Sản phẩm cần xoá !");
        return;
    }else{   
        Masp=Integer.parseInt(model.getValueAt(selectedRow, 1).toString());        
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
         
            if (option == JOptionPane.YES_OPTION) {
                if (!MaSP.getText().isEmpty()) { 
                    model.removeRow(selectedRow);
                    sp.remove(selectedRow);
                    JOptionPane.showMessageDialog(null, "Xóa dữ liệu hóa đơn thành công !", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Sản phẩm cần xoá!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return;
            }
    }    
    String querySP = "DELETE FROM SanPham WHERE MaSanPham = "+ Masp ;
    Connection conn = new MyDBConnection().getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(querySP)) {
   
    pstmt.executeUpdate();
    
} catch (SQLException ex) {
    ex.printStackTrace();
}
    }

 public void suaSP() {
    DefaultTableModel model = (DefaultTableModel) TableSanPham.getModel();
    Connection conn = new MyDBConnection().getConnection();
    int selectedRowIndex = TableSanPham.getSelectedRow();
    String Tensp = TenSP.getText();
    int Loaisp = MaLoaiSP();
    double giaBan = Double.parseDouble(GiaBan.getText());
    double giaNhap = Double.parseDouble(GiaNhap.getText());
    String Ghichu = GhiChu.getText();
    int Hangsx = MaHangSX();
    int tonKho = Integer.parseInt(TonKho.getText());
    
    if (selectedRowIndex == -1) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn Sản phẩm cần sửa.");
        return;
    }

    try {
        String sql = "UPDATE SanPham SET TenSanPham = ?, MaLoaiSanPham = ?, MaHangSanXuat = ?, GiaNhap = ?, GiaBan = ?, TonKho = ?, ChuThich = ? WHERE MaSanPham = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, Tensp);
        pstmt.setInt(2, Loaisp);
        pstmt.setInt(3, Hangsx);
        pstmt.setDouble(4, giaNhap);
        pstmt.setDouble(5, giaBan);
        pstmt.setInt(6, tonKho);
        pstmt.setString(7, Ghichu);
        pstmt.setInt(8, Integer.parseInt(model.getValueAt(selectedRowIndex, 1).toString()));
        int rowsUpdated = pstmt.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Sản phẩm đã được cập nhật thành công.");
        
    } catch (SQLException ex) {
        ex.printStackTrace();      
    }
}
 public void resetSP(){
    MaSP.setText("");     
    TenSP.setText("");
    GiaBan.setText("");
    GiaNhap.setText("");          
    TonKho.setText("");
    GhiChu.setText(""); 
    Text.setText("");
   ShowdulieuSP();
    }

      public void ThemSanPham(){
LoaiSanPham.removeAllItems();
LoaiSanPham1.removeAllItems();
 Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();
         String queryNV = "SELECT * FROM LoaiSanPham";
         ResultSet rsNV = stmt.executeQuery(queryNV);   
            while (rsNV.next()) {               
                LoaiSanPham.addItem(rsNV.getString("TenLoaiSanPham"));   
                LoaiSanPham1.addItem(rsNV.getString("TenLoaiSanPham"));   
            }
                      
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
    }
      public void ThemHangSanXuat(){
HangSX.removeAllItems();
 Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();
         String queryNV = "SELECT * FROM HangSanXuat";
         ResultSet rsNV = stmt.executeQuery(queryNV);   
            while (rsNV.next()) {               
                HangSX.addItem(rsNV.getString("TenHangSanXuat"));               
            }
                      
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
    } 
      ButtonGroup group = new ButtonGroup();
       public void createCheckboxGroup() {
        
        group.add(checkTenSP);
        group.add(CheckMaSP);
        group.add(CheckLoaiSP);
    }
 StringBuffer tensp = new StringBuffer(); 
    public void checkbox1() {
        if (checkTenSP.isSelected()) {
             tensp.setLength(0);
             tensp.append(Text.getText());
        } 
        
        System.out.println(tensp);
        
    }
     StringBuffer masp = new StringBuffer();    
    public void checkbox2(){
    if (CheckMaSP.isSelected()) {
        masp.setLength(0);
        masp.append(Text.getText());
        
    } 
     System.out.println(masp);

}
     public int MaLoaiSP1(){     
         int Ten = 0;
     try {
        Connection conn = new MyDBConnection().getConnection();
        String SQL1 = "SELECT MaLoaiSanPham FROM LoaiSanPham WHERE TenLoaiSanPham = ?";
       PreparedStatement pstmt1 = conn.prepareStatement(SQL1);
       pstmt1.setString(1, loaisp);
      ResultSet rs1 = pstmt1.executeQuery();
        while (rs1.next()) {
            Ten = rs1.getInt("MaLoaiSanPham");
        }     
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
         System.out.println("Ten" +Ten);
     return Ten;
     }
    public void checkbox3() {
    if (CheckLoaiSP.isSelected()) {
        loaisp="";
        loaisp = (String) LoaiSanPham1.getSelectedItem();    
    } 
    System.out.println(loaisp);
}
    public void TimKiemMaSP(){
        DefaultTableModel model = (DefaultTableModel)TableSanPham.getModel();
    Connection conn = new MyDBConnection().getConnection();
    model.setRowCount(0);       
        try{             
String query = "SELECT MaSanPham, TenSanPham, TenLoaiSanPham, GiaNhap, GiaBan, MaHangSanXuat, TonKho, ChuThich \n" +
"               FROM SanPham \n" +
"              JOIN LoaiSanPham ON LoaiSanPham.MaLoaiSanPham = SanPham.MaLoaiSanPham\n" +
"               WHERE MaSanPham ="+masp ;
                                                                                    
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);                       
            while (rs.next()) {        
               
                 Object obj[]=new Object[10];              
                obj[0]=TableSanPham.getRowCount()+1;               
                obj[1]=rs.getInt("MaSanPham");
                obj[2]=rs.getString("TenSanPham");
                obj[3]=rs.getString("TenLoaiSanPham"); 
                obj[4] = rs.getDouble("GiaNhap");
                obj[5]=rs.getDouble("GiaBan");
                obj[6]=rs.getInt("MaHangSanXuat");
                obj[7]=rs.getInt("TonKho");
                obj[8]=rs.getString("ChuThich"); 
                model.addRow(obj); 
                
            }
            rs.close();
    } catch (SQLException ex) {       
        ex.printStackTrace();
    }
      

      
    }
 public void TimKiemTenSP(){
        DefaultTableModel model = (DefaultTableModel)TableSanPham.getModel();
    Connection conn = new MyDBConnection().getConnection();
    model.setRowCount(0);       
        try{             
String query = "SELECT MaSanPham, TenSanPham, TenLoaiSanPham, GiaNhap, GiaBan, MaHangSanXuat, TonKho, ChuThich \n" +
"               FROM SanPham \n" +
"              JOIN LoaiSanPham ON LoaiSanPham.MaLoaiSanPham = SanPham.MaLoaiSanPham\n" +
               "WHERE TenSanPham like N'" + tensp + "%'";

                                                                                    
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);                       
            while (rs.next()) {                       
                Object obj[]=new Object[10];              
                obj[0]=TableSanPham.getRowCount()+1;               
                obj[1]=rs.getInt("MaSanPham");
                obj[2]=rs.getString("TenSanPham");
                obj[3]=rs.getString("TenLoaiSanPham"); 
                obj[4] = rs.getDouble("GiaNhap");
                obj[5]=rs.getDouble("GiaBan");
                obj[6]=rs.getInt("MaHangSanXuat");
                obj[7]=rs.getInt("TonKho");
                obj[8]=rs.getString("ChuThich"); 
                model.addRow(obj); 
                
            }
            rs.close();
    } catch (SQLException ex) {       
        ex.printStackTrace();
    }
      
    }
    public void TimKiemLoaiSP(){
    DefaultTableModel model = (DefaultTableModel)TableSanPham.getModel();
    Connection conn = new MyDBConnection().getConnection();
    model.setRowCount(0);       
        try{             
String query = "SELECT MaSanPham, TenSanPham, TenLoaiSanPham, GiaNhap, GiaBan, MaHangSanXuat, TonKho, ChuThich \n" +
"               FROM SanPham \n" +
"              JOIN LoaiSanPham ON LoaiSanPham.MaLoaiSanPham = SanPham.MaLoaiSanPham\n" +
               "WHERE SanPham.MaLoaiSanPham = N'" + MaLoaiSP1() + "'";

                                                                                    
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);                       
            while (rs.next()) {        
                SanPham spp =new  SanPham();
                Object obj[]=new Object[10];              
                obj[0]=TableSanPham.getRowCount()+1;               
                obj[1]=rs.getInt("MaSanPham");
                obj[2]=rs.getString("TenSanPham");
                obj[3]=rs.getString("TenLoaiSanPham"); 
                obj[4] = rs.getDouble("GiaNhap");
                obj[5]=rs.getDouble("GiaBan");
                obj[6]=rs.getInt("MaHangSanXuat");
                obj[7]=rs.getInt("TonKho");
                obj[8]=rs.getString("ChuThich"); 
                model.addRow(obj); 
                
            }
            rs.close();
    } catch (SQLException ex) {       
        ex.printStackTrace();
    }
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableSanPham = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        MaSP = new javax.swing.JTextField();
        TenSP = new javax.swing.JTextField();
        GiaNhap = new javax.swing.JTextField();
        GiaBan = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        TonKho = new javax.swing.JTextField();
        LoaiSanPham = new javax.swing.JComboBox<>();
        HangSX = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        GhiChu = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Text = new javax.swing.JTextField();
        TimKiem = new javax.swing.JButton();
        checkTenSP = new javax.swing.JCheckBox();
        CheckLoaiSP = new javax.swing.JCheckBox();
        CheckMaSP = new javax.swing.JCheckBox();
        LoaiSanPham1 = new javax.swing.JComboBox<>();

        TableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Loại Sản Phẩm", "Giá Nhập", "Giá Bán", "Hãng Sản Xuất", "Tồn Kho", "Chú Thích"
            }
        ));
        TableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableSanPham);

        jLabel1.setText("Giá Bán");

        jLabel14.setText("Tên Sản Phẩm");

        jLabel15.setText("Mã Sản Phẩm");

        jLabel16.setText("Giá Nhập");

        jLabel17.setText("Loại Sản Phẩm");

        MaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MaSPMouseClicked(evt);
            }
        });
        MaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaSPActionPerformed(evt);
            }
        });

        TenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenSPActionPerformed(evt);
            }
        });

        GiaNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GiaNhapMouseClicked(evt);
            }
        });
        GiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GiaNhapActionPerformed(evt);
            }
        });
        GiaNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                GiaNhapKeyReleased(evt);
            }
        });

        jLabel18.setText("Hãng Sản Xuất");

        jLabel19.setText("Tồn Kho");

        LoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        HangSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setText("Chiếc");

        jLabel21.setText("Ghi Chú");

        GhiChu.setColumns(20);
        GhiChu.setRows(5);
        jScrollPane4.setViewportView(GhiChu);

        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextActionPerformed(evt);
            }
        });

        TimKiem.setText("Tìm Kiếm");
        TimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TimKiemMouseClicked(evt);
            }
        });

        checkTenSP.setText("Tên Sản Phẩm");
        checkTenSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkTenSPMouseClicked(evt);
            }
        });
        checkTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTenSPActionPerformed(evt);
            }
        });

        CheckLoaiSP.setText("Loại Sản Phẩm");
        CheckLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CheckLoaiSPMouseClicked(evt);
            }
        });

        CheckMaSP.setText("Mã Sản Phẩm");
        CheckMaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CheckMaSPMouseClicked(evt);
            }
        });
        CheckMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckMaSPActionPerformed(evt);
            }
        });

        LoaiSanPham1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LoaiSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(HangSX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(Text, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(80, 80, 80)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkTenSP)
                            .addComponent(CheckMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoaiSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CheckLoaiSP))
                        .addGap(22, 22, 22))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HangSX, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Text, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(TonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(CheckMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LoaiSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void GiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GiaNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GiaNhapActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        messege();
        
        ThemSP();
       ShowdulieuSP();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       Xoacthd();
        XoaSanPham();
       ShowdulieuSP();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        suaSP();
        ShowdulieuSP();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       resetSP();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextActionPerformed

    private void MaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaSPActionPerformed

    private void checkTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkTenSPActionPerformed

    private void TenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenSPActionPerformed

    private void TableSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSanPhamMouseClicked
        showDetail();
    }//GEN-LAST:event_TableSanPhamMouseClicked

    private void GiaNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GiaNhapMouseClicked
       
    }//GEN-LAST:event_GiaNhapMouseClicked

    private void GiaNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GiaNhapKeyReleased
        
    }//GEN-LAST:event_GiaNhapKeyReleased

    private void checkTenSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkTenSPMouseClicked
        createCheckboxGroup();
        checkbox1();
    }//GEN-LAST:event_checkTenSPMouseClicked

    private void CheckMaSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckMaSPMouseClicked
        createCheckboxGroup();
        checkbox2();
    }//GEN-LAST:event_CheckMaSPMouseClicked

    private void CheckLoaiSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckLoaiSPMouseClicked
        createCheckboxGroup();
        checkbox3();
    }//GEN-LAST:event_CheckLoaiSPMouseClicked

    private void TimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TimKiemMouseClicked
     if (CheckMaSP.isSelected()){
        TimKiemMaSP();
    } else if (checkTenSP.isSelected()) {
        TimKiemTenSP();
    } else if(CheckLoaiSP.isSelected()){
        TimKiemLoaiSP();
    }

    }//GEN-LAST:event_TimKiemMouseClicked

    private void CheckMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckMaSPActionPerformed

    private void MaSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MaSPMouseClicked
        MaSP.setEditable(false);
    }//GEN-LAST:event_MaSPMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckLoaiSP;
    private javax.swing.JCheckBox CheckMaSP;
    private javax.swing.JTextArea GhiChu;
    private javax.swing.JTextField GiaBan;
    private javax.swing.JTextField GiaNhap;
    private javax.swing.JComboBox<String> HangSX;
    private javax.swing.JComboBox<String> LoaiSanPham;
    private javax.swing.JComboBox<String> LoaiSanPham1;
    private javax.swing.JTextField MaSP;
    private javax.swing.JTable TableSanPham;
    private javax.swing.JTextField TenSP;
    private javax.swing.JTextField Text;
    private javax.swing.JButton TimKiem;
    private javax.swing.JTextField TonKho;
    private javax.swing.JCheckBox checkTenSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
