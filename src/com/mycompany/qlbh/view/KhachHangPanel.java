/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qlbh.view;

import com.mycompany.qlbh.model.KhachHang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dinh Giap
 */
public class KhachHangPanel extends javax.swing.JPanel {
    List <KhachHang> KhachHang = new ArrayList<>();
     public KhachHangPanel() {
        initComponents();
        buttonGroup2.add(rdtnNam);
        buttonGroup2.add(rdtnNu);
         ThemLoaiKhachHang();
        ShowdulieuKhachHang();
    } 
    /**
     * Creates new form jpnKhachHang
     */
public void addKhachHang() {
    if (txtten.getText().isEmpty() || txtdiachi.getText().isEmpty() || 
        txtsdt.getText().isEmpty() || txtngaysinh.getText().isEmpty() || cbxloai.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
        return; // Không thực hiện thêm khách hàng nếu có trường nhập liệu còn trống
    }
    
    String tenKhachHang = txtten.getText();
    boolean gioiTinh = rdtnNam.isSelected(); // Kiểm tra nút radio cho giới tính Nam
    String diaChi = txtdiachi.getText();
    String soDienThoai = txtsdt.getText();
    String loaiKhachHang = (String) cbxloai.getSelectedItem();
    String ghiChu = txtghichu.getText();
    
    // Lấy ngày sinh từ JTextField và chuyển đổi sang kiểu dữ liệu Date
    String ngaySinhStr = txtngaysinh.getText();
    Date ngaySinh = null;
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ngaySinh = sdf.parse(ngaySinhStr);
    } catch (ParseException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
        return; // Thoát khỏi phương thức nếu ngày sinh không hợp lệ
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null; // ResultSet for querying MaLoaiKhachHang
    
    try {
        conn = new MyDBConnection().getConnection();
        
        // Lấy mã loại khách hàng tương ứng với tên loại khách hàng
        String queryLoaiKhachHang = "SELECT MaLoaiKhachHang FROM LoaiKhachHang WHERE TenLoaiKhachHang = ?";
        pstmt = conn.prepareStatement(queryLoaiKhachHang);
        pstmt.setString(1, loaiKhachHang);
        ResultSet rsLoaiKhachHang = pstmt.executeQuery();
        int maLoaiKhachHang = -1;
        if (rsLoaiKhachHang.next()) {
            maLoaiKhachHang = rsLoaiKhachHang.getInt("MaLoaiKhachHang");
        }
        rsLoaiKhachHang.close(); // Đóng ResultSet sau khi sử dụng
        
        if (maLoaiKhachHang == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã loại khách hàng cho '" + loaiKhachHang + "'");
            return; // Thoát khỏi phương thức nếu không tìm thấy mã loại khách hàng
        }
        
        // SQL query for inserting data into KhachHang table
        String query = "INSERT INTO KhachHang (TenKhachHang, GioiTinh, NgaySinh, DiaChi, SDT,LoaiKhachHang, GhiChu) VALUES (?, ?, ?, ?, ?, ?,?)";
        pstmt = conn.prepareStatement(query);
        
        // Set values for parameters in the SQL query
        pstmt.setString(1, tenKhachHang);
        pstmt.setBoolean(2, gioiTinh); // Chuyển đổi giá trị boolean thành kiểu dữ liệu bit
        pstmt.setDate(3, new java.sql.Date(ngaySinh.getTime())); // Chuyển đổi ngày sinh thành kiểu dữ liệu SQL Date
        pstmt.setString(4, diaChi);
        pstmt.setString(5, soDienThoai);
        pstmt.setInt(6, maLoaiKhachHang); // Sử dụng mã loại khách hàng thay vì tên loại khách hàng
        pstmt.setString(7, ghiChu);
        
        // Execute the SQL query
        pstmt.executeUpdate();
        
        // Close the PreparedStatement and Connection
        pstmt.close();
        
        // Notify user that the data has been added successfully
        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
        
        // Clear input fields after adding data
        clearInputFields();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + ex.getMessage());
    } finally {
        // Close the PreparedStatement and Connection in the finally block
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


private void clearInputFields() {
    // Xóa nội dung của các trường nhập liệu sau khi thêm khách hàng thành công
    txtma.setText("");
    txtten.setText("");
    txtdiachi.setText("");
    txtsdt.setText("");
    txtngaysinh.setText(""); // Xóa nội dung của trường nhập liệu ngày sinh
    cbxloai.setSelectedIndex(-1);
    txtghichu.setText("");
    // Đặt lại trạng thái của radio button giới tính
    buttonGroup2.clearSelection();
}
public void ThemLoaiKhachHang(){
     Connection conn = new MyDBConnection().getConnection();
try{             
         Statement stmt = conn.createStatement();        
          String queryKH = "SELECT * FROM LoaiKhachHang"; 
         ResultSet rsKH = stmt.executeQuery(queryKH);                           
             while (rsKH.next()) {               
                cbxloai.addItem(rsKH.getString("TenLoaiKhachHang"));              
            }           
    } catch (SQLException ex) {
       
        ex.printStackTrace();
    }
}
public void displayKhachHang() {
    DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel(); 
    model.setRowCount(0);
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = new MyDBConnection().getConnection();
        
        // SQL query to retrieve data from KhachHang table
        String query = "SELECT * FROM KhachHang";
        pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (rs.next()) {
            Object[] objKH = new Object[9]; // Số trường dữ liệu của KhachHang
            objKH[0] = model.getRowCount() + 1; // Số thứ tự
            objKH[1] = rs.getInt("MaKhachHang");
            objKH[2] = rs.getString("TenKhachHang");
            objKH[3] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ";
            objKH[4] = rs.getString("DiaChi");
            objKH[5] = rs.getString("SDT");
            objKH[6] = "";
            objKH[7] = rs.getString("GhiChu");
            objKH[8] = rs.getDate("NgaySinh");
            model.addRow(objKH); // Thêm dòng mới vào bảng
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Close the ResultSet, PreparedStatement, and Connection in the finally block
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}



public void removeKhachHang() {
    int selectedRow = TableKhachHang.getSelectedRow();
    if (selectedRow != -1) {
        DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel();
        int realSelectedRow = TableKhachHang.convertRowIndexToModel(selectedRow);

        // Lấy thông tin khách hàng từ bảng
        int maKhachHang = (int) model.getValueAt(realSelectedRow, 1); // Giả sử mã khách hàng là cột thứ hai trong bảng

        // Xóa khách hàng từ cơ sở dữ liệu
        Connection conn = new MyDBConnection().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM KhachHang WHERE MaKhachHang = ?");
            pstmt.setInt(1, maKhachHang);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                model.removeRow(realSelectedRow); // Xóa hàng từ bảng hiển thị
                JOptionPane.showMessageDialog(null, "Khách hàng đã được xóa thành công từ cơ sở dữ liệu!");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa khách hàng từ cơ sở dữ liệu! Không có bản ghi nào bị xóa.");
            }
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi xóa khách hàng từ cơ sở dữ liệu!");
        }
    }
}
public void updateKhachHang() {
    // Lấy chỉ số hàng được chọn trong bảng
    int selectedRow = TableKhachHang.getSelectedRow();      
    if (selectedRow != -1) {
        // Lấy thông tin khách hàng từ bảng
        DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel();
        int realSelectedRow = TableKhachHang.convertRowIndexToModel(selectedRow);
        int maKhachHang = (int) model.getValueAt(realSelectedRow, 1); // Lấy mã khách hàng từ cột thứ hai trong bảng

        // Cập nhật thông tin khách hàng từ các trường nhập liệu trên giao diện
        String tenKhachHang = txtten.getText();
        boolean gioiTinh = rdtnNam.isSelected();
        String diaChi = txtdiachi.getText();
        String soDienThoai = txtsdt.getText();
        String loaiKhachHang = (String) cbxloai.getSelectedItem();
        String ghiChu = txtghichu.getText();
        
        // Lấy mã loại khách hàng từ bảng LoaiKhachHang
        int maLoaiKhachHang = -1;
        Connection conn = null;
        PreparedStatement pstmtLoaiKH = null;
        ResultSet rsLoaiKH = null;
        try {
            conn = new MyDBConnection().getConnection();
            String queryLoaiKH = "SELECT MaLoaiKhachHang FROM LoaiKhachHang WHERE TenLoaiKhachHang = ?";
            pstmtLoaiKH = conn.prepareStatement(queryLoaiKH);
            pstmtLoaiKH.setString(1, loaiKhachHang);
            rsLoaiKH = pstmtLoaiKH.executeQuery();
            if (rsLoaiKH.next()) {
                maLoaiKhachHang = rsLoaiKH.getInt("MaLoaiKhachHang");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rsLoaiKH != null) rsLoaiKH.close();
                if (pstmtLoaiKH != null) pstmtLoaiKH.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        // Nếu không tìm thấy mã loại khách hàng, thông báo lỗi và không thực hiện cập nhật
        if (maLoaiKhachHang == -1) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy mã loại khách hàng cho '" + loaiKhachHang + "'");
            return;
        }
        
        // Cập nhật thông tin trong cơ sở dữ liệu
        Connection connUpdate = null;
        PreparedStatement pstmtUpdate = null;
        try {
            connUpdate = new MyDBConnection().getConnection();
            pstmtUpdate = connUpdate.prepareStatement("UPDATE KhachHang SET TenKhachHang = ?, GioiTinh = ?, DiaChi = ?, SDT = ?, LoaiKhachHang = ?, GhiChu = ? WHERE MaKhachHang = ?");
            pstmtUpdate.setString(1, tenKhachHang);
            pstmtUpdate.setBoolean(2, gioiTinh);
            pstmtUpdate.setString(3, diaChi);
            pstmtUpdate.setString(4, soDienThoai);
            pstmtUpdate.setInt(5, maLoaiKhachHang);
            pstmtUpdate.setString(6, ghiChu);
            pstmtUpdate.setInt(7, maKhachHang);
            int rowsUpdated = pstmtUpdate.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Thông tin khách hàng đã được cập nhật thành công trong cơ sở dữ liệu!");
                
                // Cập nhật lại thông tin trong bảng hiển thị
                model.setValueAt(tenKhachHang, realSelectedRow, 2); 
                model.setValueAt(gioiTinh ? "Nam" : "Nữ", realSelectedRow, 3); 
                model.setValueAt(diaChi, realSelectedRow, 4); 
                model.setValueAt(soDienThoai, realSelectedRow, 5); 
                model.setValueAt(loaiKhachHang, realSelectedRow, 6); 
                model.setValueAt(ghiChu, realSelectedRow, 7); 
            } else {
                JOptionPane.showMessageDialog(null, "Không có thông tin nào được cập nhật trong cơ sở dữ liệu!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi cập nhật thông tin khách hàng trong cơ sở dữ liệu!");
        } finally {
            // Đóng PreparedStatement và Connection
            try {
                if (pstmtUpdate != null) pstmtUpdate.close();
                if (connUpdate != null) connUpdate.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public void reset() {
    // Đặt lại giá trị của các thành phần nhập liệu
    txtma.setText("1");
    txtten.setText("Phúc");
    txtdiachi.setText("");
    txtsdt.setText("0367348026");
    txtghichu.setText("");
    
    // Đặt lại giá trị của các radio button
    rdtnNam.setSelected(true); // Đặt lại giá trị mặc định cho radio button Nam
    rdtnNu.setSelected(false); // Đặt lại giá trị mặc định cho radio button Nữ

    // Đặt lại giá trị của JComboBox
    cbxloai.setSelectedIndex(0); // Đặt lại giá trị mặc định cho JComboBox

    // Xóa tất cả các dòng trong bảng
    DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel();
    model.setRowCount(0);
}

 

  private void ShowdulieuKhachHang() {
    DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel();
    Connection conn = new MyDBConnection().getConnection();
    try {
        String queryKH = "SELECT KhachHang.*, LoaiKhachHang.TenLoaiKhachHang " +
                 "FROM KhachHang  " +
                "INNER JOIN LoaiKhachHang ON KhachHang.LoaiKhachHang = LoaiKhachHang.MaLoaiKhachHang";
                 

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(queryKH);
        while (rs.next()) {
            Object objKH[] = new Object[11]; // Thêm một cột mới cho ngày sinh
            objKH[0] = model.getRowCount() + 1;
            objKH[1] = rs.getInt("MaKhachHang");
            objKH[2] = rs.getString("TenKhachHang");
            int gioiTinh = rs.getInt("GioiTinh");
            objKH[3] = (gioiTinh == 1) ? "Nam" : "Nữ"; // Chuyển đổi giới tính từ số sang chuỗi "Nam" hoặc "Nữ"
            objKH[4] = rs.getString("DiaChi");
            objKH[5] = rs.getString("SDT");
            objKH[6] = rs.getString("TenLoaiKhachHang");
            objKH[7] = rs.getString("GhiChu");
            // Lấy ngày sinh từ ResultSet và chuyển đổi sang định dạng String
            Date ngaySinh = rs.getDate("NgaySinh");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ngaySinhStr = sdf.format(ngaySinh);
            objKH[8] = ngaySinhStr;
            model.addRow(objKH);
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

        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableKhachHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lsloai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ThemKhachHang = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtten = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        txtghichu = new javax.swing.JTextField();
        rdtnNam = new javax.swing.JRadioButton();
        rdtnNu = new javax.swing.JRadioButton();
        cbxloai = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtngaysinh = new javax.swing.JTextField();

        TableKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Khách Hàng", "Tên Khách hàng", "giới tính", "Địa chỉ", "SĐT", "Loại Khách Hàng", "Ghi chú", "Ngày Sinh"
            }
        ));
        TableKhachHang.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TableKhachHangAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TableKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableKhachHang);

        jLabel1.setText("Mã KH");

        txtma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmaMouseClicked(evt);
            }
        });
        txtma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên KH");

        jLabel3.setText("giới Tính");

        jLabel4.setText("Địa chỉ");

        jLabel5.setText("SĐT");

        lsloai.setText("Loại Khách Hàng");

        jLabel7.setText("Ghi chú");

        ThemKhachHang.setText("Thêm");
        ThemKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThemKhachHangMouseClicked(evt);
            }
        });
        ThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemKhachHangActionPerformed(evt);
            }
        });

        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xóa");
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

        rdtnNam.setText("Nam");

        rdtnNu.setText("Nữ");
        rdtnNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdtnNuActionPerformed(evt);
            }
        });

        cbxloai.setSelectedIndex(-1);
        cbxloai.setToolTipText("");

        jLabel6.setText("Ngày Sinh");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtma, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(txtten)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdtnNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdtnNu))
                    .addComponent(txtngaysinh))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lsloai)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsdt)
                            .addComponent(txtdiachi)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 288, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtghichu, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ThemKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtten, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdtnNam)
                                    .addComponent(rdtnNu))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtghichu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(28, 28, 28)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lsloai)
                                    .addComponent(cbxloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaActionPerformed

    private void ThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemKhachHangActionPerformed
        // TODO add your handling code here:
         addKhachHang();
         displayKhachHang();
    }//GEN-LAST:event_ThemKhachHangActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateKhachHang();
        displayKhachHang();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        reset();
        displayKhachHang();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void rdtnNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdtnNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdtnNuActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        removeKhachHang();
        displayKhachHang();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TableKhachHangAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TableKhachHangAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TableKhachHangAncestorAdded

    private void txtmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmaMouseClicked
        // TODO add your handling code here:
        txtma.setEditable(false);
    }//GEN-LAST:event_txtmaMouseClicked

    private void TableKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableKhachHangMouseClicked
       int viTriDongVuaBam = TableKhachHang.getSelectedRow();
    
    if (viTriDongVuaBam != -1) { // Kiểm tra xem người dùng đã chọn một dòng trong bảng chưa
        // Lấy giá trị từ dòng được chọn trong bảng và hiển thị thông tin tương ứng
        DefaultTableModel model = (DefaultTableModel) TableKhachHang.getModel();
        int realSelectedRow = TableKhachHang.convertRowIndexToModel(viTriDongVuaBam);
        
        // Lấy thông tin từ bảng và hiển thị trong các ô văn bản hoặc các thành phần khác
        String maKhachHang = model.getValueAt(realSelectedRow, 1).toString();
        String tenKhachHang = model.getValueAt(realSelectedRow, 2).toString();
        String diaChi = model.getValueAt(realSelectedRow, 4).toString();
        String ngaySinhStr = model.getValueAt(realSelectedRow, 8).toString(); // Giả sử cột 4 chứa ngày sinh
        String soDienThoai = model.getValueAt(realSelectedRow, 5).toString();
        String gioiTinh = model.getValueAt(realSelectedRow, 3).toString();
        String loaiKhachHang = model.getValueAt(realSelectedRow, 6).toString(); // Giả sử cột 7 chứa chức vụ
        String ghiChu = model.getValueAt(realSelectedRow, 7).toString(); // Giả sử cột 9 chứa chú thích
        // Tiếp tục lấy các thông tin cần thiết từ bảng
        
        // Hiển thị thông tin lấy được lên các ô văn bản hoặc các thành phần khác trên giao diện
        txtma.setText(maKhachHang);
        txtten.setText(tenKhachHang);
        txtdiachi.setText(diaChi);
        txtngaysinh.setText(ngaySinhStr);
        txtsdt.setText(soDienThoai);
        // Hiển thị giới tính
        if (gioiTinh.equals("Nam")) {
            rdtnNam.setSelected(true);
            rdtnNu.setSelected(false);
        } else {
            rdtnNam.setSelected(false);
            rdtnNu.setSelected(true);
        }
        // Hiển thị chức vụ
        cbxloai.setSelectedItem(loaiKhachHang);
        // Hiển thị ngày vào làm
        // Hiển thị chú thích
        txtghichu.setText(ghiChu);
        // Tiếp tục hiển thị các thông tin cần thiết khác lên giao diện
    }
    }//GEN-LAST:event_TableKhachHangMouseClicked

    private void ThemKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThemKhachHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ThemKhachHangMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableKhachHang;
    private javax.swing.JButton ThemKhachHang;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbxloai;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lsloai;
    private javax.swing.JRadioButton rdtnNam;
    private javax.swing.JRadioButton rdtnNu;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtghichu;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txtten;
    // End of variables declaration//GEN-END:variables
}

