/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qlbh.view;
import com.mycompany.qlbh.model.NhanVien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class NhanVienPanel extends javax.swing.JPanel {
    List <NhanVien> NhanVien = new ArrayList<>();
    /**
     * Creates new form NhanVienPanel
     */
    public NhanVienPanel() {
        initComponents();
        initComponents();
        buttonGroup1.add(rbtnNam);
        buttonGroup1.add(rbtnNu);
        ThemChucVu();
        ShowdulieuNhanVien();
        ShowdulieuTaiKhoan();
    }
private void ShowdulieuNhanVien() {
    DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
    Connection conn = new MyDBConnection().getConnection();
    try {
        String queryNV = "SELECT NhanVien.*, ChucVu.TenChucVu " +
                         "FROM NhanVien " +
                         "INNER JOIN ChucVu ON NhanVien.ChucVu = ChucVu.MaChucVu";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(queryNV);
        while (rs.next()) {
            Object[] objNV = new Object[11]; 
            objNV[0] = TableNhanVien.getRowCount() + 1; 
            objNV[1] = rs.getInt("MaNhanVien");
            objNV[2] = rs.getString("TenNhanVien");
            objNV[3] = rs.getString("DiaChi");
            objNV[4] = rs.getDate("NgaySinh"); 
            objNV[5] = rs.getString("SoDT"); 
            objNV[6] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ";
            objNV[7] = rs.getString("TenChucVu"); 
            objNV[8] = rs.getDate("NgayVaoLam");
            objNV[9] = rs.getString("GhiChu"); 
            model.addRow(objNV);
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
 private void ShowdulieuTaiKhoan() {
    DefaultTableModel model = (DefaultTableModel) TableTaiKhoan.getModel();
    Connection conn = new MyDBConnection().getConnection();
    try {
        String queryNV = "SELECT Users.*, Quyen.TenQuyen " +
                         "FROM Users " +
                         "INNER JOIN Quyen ON Users.Quyen = Quyen.MaQuyen";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(queryNV);
        while (rs.next()) {
            Object[] objNV = new Object[11];
            objNV[0] = TableTaiKhoan.getRowCount() + 1; 
            objNV[1] = rs.getInt("MaNhanVien");
            objNV[2] = rs.getString("TenDangNhap");
            objNV[3] = rs.getString("Password"); 
            objNV[4] = rs.getString("TenQuyen"); 
            objNV[5] = rs.getString("ChuThich"); 
            model.addRow(objNV);
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
public void addNhanVien() {
    if (txtten.getText().isEmpty() || txtdiachi.getText().isEmpty() || txtngayvaolam.getText().isEmpty() || txtchuthich.getText().isEmpty() || txtsdt.getText().isEmpty() || txtngaysinh.getText().isEmpty() || cbxChucVu.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
        return; 
    }
    
    String tenNhanVien = txtten.getText();
    boolean gioiTinh = rbtnNam.isSelected(); 
    String diaChi = txtdiachi.getText();
    String soDienThoai = txtsdt.getText();
    String chucVu = (String) cbxChucVu.getSelectedItem();
    String ghiChu = txtchuthich.getText();
    
   
    String ngaySinhStr = txtngaysinh.getText();
    Date ngaySinh = null;
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ngaySinh = sdf.parse(ngaySinhStr);
    } catch (ParseException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
        return; 
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        conn = new MyDBConnection().getConnection();
        
        
        String queryChucVu = "SELECT MaChucVu FROM ChucVu WHERE TenChucVu = ?";
        pstmt = conn.prepareStatement(queryChucVu);
        pstmt.setString(1, chucVu);
        ResultSet rsChucVu = pstmt.executeQuery();
        int maChucVu = -1;
        if (rsChucVu.next()) {
            maChucVu = rsChucVu.getInt("MaChucVu");
        }
        rsChucVu.close(); 
        
        if (maChucVu == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã chức vụ cho '" + chucVu + "'");
            return; 
        }
        
        
        String query = "INSERT INTO NhanVien (TenNhanVien, GioiTinh, DiaChi, SoDT, ChucVu, NgaySinh, NgayVaoLam, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(query);
        
       
        pstmt.setString(1, tenNhanVien);
        pstmt.setBoolean(2, gioiTinh); 
        pstmt.setString(3, diaChi);
        pstmt.setString(4, soDienThoai);
        pstmt.setInt(5, maChucVu); 
        pstmt.setDate(6, new java.sql.Date(ngaySinh.getTime())); 
        
       
        String ngayVaoLamStr = txtngayvaolam.getText();
        SimpleDateFormat sdfNgayVaoLam = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date ngayVaoLamUtil = sdfNgayVaoLam.parse(ngayVaoLamStr);
        pstmt.setDate(7, new java.sql.Date(ngayVaoLamUtil.getTime())); 
        
        pstmt.setString(8, ghiChu);
        
       
        pstmt.executeUpdate();
        
        
        pstmt.close();
        
        
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
        
        
        clearInputFields();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên");
    } catch (ParseException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ngày vào làm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
    } finally {
        
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


private void clearInputFields() {
    txtma.setText("");
    txtten.setText("");
    txtdiachi.setText("");
    txtngayvaolam.setText("");
    txtsdt.setText("");
    txtngaysinh.setText("");
    cbxChucVu.setSelectedIndex(-1);
    txtchuthich.setText("");
}
 public void ThemChucVu(){
     Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();
         String queryNV = "SELECT * FROM ChucVu";
         ResultSet rsNV = stmt.executeQuery(queryNV);   
            while (rsNV.next()) {               
                cbxChucVu.addItem(rsNV.getString("TenChucVu"));               
            }
                      
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
 }
public void removeNhanVien() {
    int selectedRow = TableNhanVien.getSelectedRow();
    if (selectedRow != -1) {
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        int realSelectedRow = TableNhanVien.convertRowIndexToModel(selectedRow);

       
        int maNhanVien = (int) model.getValueAt(realSelectedRow, 1); 

       
        Connection conn = new MyDBConnection().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NhanVien WHERE MaNhanVien = ?");
            pstmt.setInt(1, maNhanVien);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                model.removeRow(realSelectedRow); 
                JOptionPane.showMessageDialog(null, "Nhân Viên đã được xóa thành công từ cơ sở dữ liệu!");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa nhân viên từ cơ sở dữ liệu! Không có bản ghi nào bị xóa.");
            }
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi xóa Nhân Viên từ cơ sở dữ liệu!");
        }
    }
}
public void updateNhanVien() {
   
    int selectedRow = TableNhanVien.getSelectedRow();      
    if (selectedRow != -1) {
        
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        int realSelectedRow = TableNhanVien.convertRowIndexToModel(selectedRow);
        int maNhanVien = (int) model.getValueAt(realSelectedRow, 1); 

        
        String tenNhanVien = txtten.getText();
        boolean gioiTinh = rbtnNam.isSelected();
        String diaChi = txtdiachi.getText();
        String soDienThoai = txtsdt.getText();
        String tenChucVu = (String) cbxChucVu.getSelectedItem(); 
       
        String ngaySinhStr = txtngaysinh.getText();
        Date ngaySinh = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngaySinh = sdf.parse(ngaySinhStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
            return; 
        }
      
        String ngayVaoLamStr = txtngayvaolam.getText();
        Date ngayVaoLam = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngayVaoLam = sdf.parse(ngayVaoLamStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ngày vào làm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
            return; 
        }
        String ghiChu = txtchuthich.getText();
        
        
        Connection conn = new MyDBConnection().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            
            String chucvuQuery = "SELECT MaChucVu FROM ChucVu WHERE TenChucVu = ?";
            pstmt = conn.prepareStatement(chucvuQuery);
            pstmt.setString(1, tenChucVu);
            rs = pstmt.executeQuery();
            int maChucVu = -1;
            if (rs.next()) {
                maChucVu = rs.getInt("MaChucVu");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã chức vụ tương ứng với tên chức vụ đã chọn.");
                return; 
            }
            
           
            String updateQuery = "UPDATE NhanVien SET TenNhanVien = ?, GioiTinh = ?, DiaChi = ?, SoDT = ?, ChucVu = ?, NgaySinh = ?, NgayVaoLam = ?, GhiChu = ? WHERE MaNhanVien = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, tenNhanVien);
            pstmt.setBoolean(2, gioiTinh);
            pstmt.setString(3, diaChi);
            pstmt.setString(4, soDienThoai);
            pstmt.setInt(5, maChucVu);
            pstmt.setDate(6, new java.sql.Date(ngaySinh.getTime()));
            pstmt.setDate(7, new java.sql.Date(ngayVaoLam.getTime()));
            pstmt.setString(8, ghiChu);
            pstmt.setInt(9, maNhanVien);
            
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Thông tin nhân viên đã được cập nhật thành công trong cơ sở dữ liệu!");
                
               
                model.setValueAt(tenNhanVien, realSelectedRow, 2); 
                model.setValueAt(gioiTinh ? "Nam" : "Nữ", realSelectedRow, 3); 
                model.setValueAt(diaChi, realSelectedRow, 4); 
                model.setValueAt(soDienThoai, realSelectedRow, 5); 
                model.setValueAt(tenChucVu, realSelectedRow, 6); 
                model.setValueAt(ngaySinh, realSelectedRow, 7); 
                model.setValueAt(ngayVaoLam, realSelectedRow, 8); 
                model.setValueAt(ghiChu, realSelectedRow, 9); 
            } else {
                JOptionPane.showMessageDialog(null, "Không có thông tin nào được cập nhật trong cơ sở dữ liệu!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi cập nhật thông tin nhân viên trong cơ sở dữ liệu!");
        } finally {
           
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
public void resetNhanVien() {
   
    txtma.setText("");
    txtten.setText("");
    txtdiachi.setText("");
    txtsdt.setText("");
    txtngaysinh.setText("");
    txtngayvaolam.setText("");
    txtchuthich.setText("");

}

public void displayNhanVien() {
    DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
    model.setRowCount(0); 
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = new MyDBConnection().getConnection();
        
        String query = "SELECT MaNhanVien, TenNhanVien, DiaChi, NgaySinh, SoDT, GioiTinh,ChucVu, NgayVaoLam, GhiChu " +
                       "FROM NhanVien";
        
        pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Object[] objNV = new Object[10]; 
            
            
            objNV[0] = model.getRowCount() + 1; 
            objNV[1] = rs.getInt("MaNhanVien");
            objNV[2] = rs.getString("TenNhanVien");
            objNV[3] = rs.getString("DiaChi");
            objNV[4] = rs.getDate("NgaySinh"); 
            objNV[5] = rs.getString("SoDT");
            objNV[6] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ"; 
            objNV[7] = rs.getString("Chucvu"); 
            objNV[8] = rs.getDate("NgayVaoLam"); 
            objNV[9] = rs.getString("GhiChu");
            
            
            model.addRow(objNV);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
      
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
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
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        txtten = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtdiachi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtngaysinh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtsdt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rbtnNam = new javax.swing.JRadioButton();
        rbtnNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        cbxChucVu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtngayvaolam = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtchuthich = new javax.swing.JTextField();
        Thêm = new javax.swing.JButton();
        Xóa = new javax.swing.JButton();
        Sửa = new javax.swing.JButton();
        Reset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableTaiKhoan = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        TableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Nhân Viên", "Tên Nhân Viên", "Địa Chỉ", "Ngày Sinh", "SĐT", "Giới Tính", "Chức Vụ", "Ngày Vào Làm", "Chú Thích"
            }
        ));
        TableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableNhanVien);

        jLabel1.setText("Mã Nhân Viên");

        jLabel2.setText("Tên Nhân Viên");

        txtma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmaMouseClicked(evt);
            }
        });

        jLabel3.setText("Địa Chỉ");

        jLabel4.setText("Ngày Sinh");

        jLabel5.setText("SĐT");

        jLabel6.setText("Giới Tính");

        rbtnNam.setText("Nam");

        rbtnNu.setText("Nữ");

        jLabel7.setText("Chức Vụ");

        jLabel8.setText("Ngày Vào Làm");

        jLabel9.setText("Chú Thích");

        Thêm.setText("Thêm");
        Thêm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThêmActionPerformed(evt);
            }
        });

        Xóa.setText("Xóa");
        Xóa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XóaActionPerformed(evt);
            }
        });

        Sửa.setText("Sửa");
        Sửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SửaActionPerformed(evt);
            }
        });

        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtma)
                    .addComponent(txtten)
                    .addComponent(txtdiachi)
                    .addComponent(txtngaysinh)
                    .addComponent(txtsdt, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addGap(136, 136, 136)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(rbtnNam)
                        .addGap(50, 50, 50)
                        .addComponent(rbtnNu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxChucVu, 0, 206, Short.MAX_VALUE)
                            .addComponent(txtchuthich, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(txtngayvaolam))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Thêm)
                    .addComponent(Sửa))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Reset)
                    .addComponent(Xóa))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(rbtnNam)
                        .addComponent(rbtnNu)
                        .addComponent(Thêm)
                        .addComponent(Xóa)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbxChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtngayvaolam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtchuthich, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Sửa)
                                .addComponent(Reset)))))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("NhanVien", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel10.setText("Tài Khoản");

        TableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Nhân Viên", "Tên Đăng Nhập", "Password", "Quyền", "Chú Thích"
            }
        ));
        jScrollPane2.setViewportView(TableTaiKhoan);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel11.setText("Đây là thông tin các tài khoản đã lập của các nhân viên !");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setText("Mọi thông tin thắc mắc cần liên hệ ngay với Admin nhé !");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(390, 390, 390)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(375, 375, 375)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 368, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(494, 494, 494)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel12)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tài Khoản", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed

        resetNhanVien();
    }//GEN-LAST:event_ResetActionPerformed

    private void SửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SửaActionPerformed
        // TODO add your handling code here:
        updateNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_SửaActionPerformed

    private void XóaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XóaActionPerformed
        // TODO add your handling code here:
        removeNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_XóaActionPerformed

    private void ThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThêmActionPerformed
        // TODO add your handling code here:
        addNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_ThêmActionPerformed

    private void txtmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmaMouseClicked
        // TODO add your handling code here:
        txtma.setEditable(false);
    }//GEN-LAST:event_txtmaMouseClicked

    private void TableNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableNhanVienMouseClicked

        int viTriDongVuaBam = TableNhanVien.getSelectedRow();

        if (viTriDongVuaBam != -1) {

            DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
            int realSelectedRow = TableNhanVien.convertRowIndexToModel(viTriDongVuaBam);

            String maNhanVien = model.getValueAt(realSelectedRow, 1).toString();
            String tenNhanVien = model.getValueAt(realSelectedRow, 2).toString();
            String diaChi = model.getValueAt(realSelectedRow, 3).toString();
            String ngaySinhStr = model.getValueAt(realSelectedRow, 4).toString();
            String soDienThoai = model.getValueAt(realSelectedRow, 5).toString();
            String gioiTinh = model.getValueAt(realSelectedRow, 6).toString();
            String chucVu = model.getValueAt(realSelectedRow, 7).toString();
            String ngayVaoLamStr = model.getValueAt(realSelectedRow, 8).toString();
            String ghiChu = model.getValueAt(realSelectedRow, 9).toString();

            txtma.setText(maNhanVien);
            txtten.setText(tenNhanVien);
            txtdiachi.setText(diaChi);
            txtngaysinh.setText(ngaySinhStr);
            txtsdt.setText(soDienThoai);

            if (gioiTinh.equals("Nam")) {
                rbtnNam.setSelected(true);
                rbtnNu.setSelected(false);
            } else {
                rbtnNam.setSelected(false);
                rbtnNu.setSelected(true);
            }

            cbxChucVu.setSelectedItem(chucVu);

            txtngayvaolam.setText(ngayVaoLamStr);

            txtchuthich.setText(ghiChu);

        }
    }//GEN-LAST:event_TableNhanVienMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Reset;
    private javax.swing.JButton Sửa;
    private javax.swing.JTable TableNhanVien;
    private javax.swing.JTable TableTaiKhoan;
    private javax.swing.JButton Thêm;
    private javax.swing.JButton Xóa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxChucVu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbtnNam;
    private javax.swing.JRadioButton rbtnNu;
    private javax.swing.JTextField txtchuthich;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtngayvaolam;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txtten;
    // End of variables declaration//GEN-END:variables
}
