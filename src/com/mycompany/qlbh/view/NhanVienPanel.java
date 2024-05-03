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
    List<String> ChucVu = new ArrayList<>();
    List <NhanVien> NhanVien = new ArrayList<>();
    /**
     * Creates new form NhanVienPanel
     */
    public NhanVienPanel() {
        initComponents();
        initComponents();
        buttonGroup1.add(rbtnNam);
        buttonGroup1.add(rbtnNu);
        ChucVu.add("1");
        ChucVu.add("2");
        ChucVu.add("3");
        ChucVu.add("4");
        ChucVu.add("5");
        ChucVu.add("6");
        cbxChucVu.removeAllItems();
        for(String loai : ChucVu){
            cbxChucVu.addItem(loai);
        }
        ShowdulieuNhanVien();
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
            Object[] objNV = new Object[11]; // Thêm một cột mới cho tên chức vụ
            objNV[0] = TableNhanVien.getRowCount() + 1; // Số thứ tự
            objNV[1] = rs.getInt("MaNhanVien");
            objNV[2] = rs.getString("TenNhanVien");
            objNV[3] = rs.getString("DiaChi");
            objNV[4] = rs.getDate("NgaySinh"); // Ngày sinh
            objNV[5] = rs.getString("SoDT"); // Đổi "SĐT" thành "SoDT"
            objNV[6] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ";
            objNV[7] = rs.getString("TenChucVu"); // Tên chức vụ từ bảng ChucVu
            objNV[8] = rs.getDate("NgayVaoLam"); // Ngày vào làm
            objNV[9] = rs.getString("GhiChu"); // Ghi chú
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
        return; // Không thực hiện thêm nhân viên nếu có trường nhập liệu còn trống
    }
    
    String tenNhanVien = txtten.getText();
    boolean gioiTinh = rbtnNam.isSelected(); // Kiểm tra nút radio cho giới tính Nam
    String diaChi = txtdiachi.getText();
    String soDienThoai = txtsdt.getText();
    String chucVu = (String) cbxChucVu.getSelectedItem();
    String ghiChu = txtchuthich.getText();
    
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
    
    try {
        conn = new MyDBConnection().getConnection();
        
        // SQL query for inserting data into NhanVien table without joining to ChucVu table
        String query = "INSERT INTO NhanVien (TenNhanVien, GioiTinh, DiaChi, SoDT, ChucVu, NgaySinh, NgayVaoLam, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        pstmt = conn.prepareStatement(query);
        
        // Set values for parameters in the SQL query
        pstmt.setString(1, tenNhanVien);
        pstmt.setBoolean(2, gioiTinh); // Chuyển đổi giá trị boolean thành kiểu dữ liệu bit
        pstmt.setString(3, diaChi);
        pstmt.setString(4, soDienThoai);
        pstmt.setString(5, chucVu);
        pstmt.setDate(6, new java.sql.Date(ngaySinh.getTime())); // Chuyển đổi ngày sinh thành kiểu dữ liệu SQL Date
        
        // Chuyển đổi ngày vào làm từ String thành kiểu dữ liệu SQL Date
        String ngayVaoLamStr = txtngayvaolam.getText();
        SimpleDateFormat sdfNgayVaoLam = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date ngayVaoLamUtil = sdfNgayVaoLam.parse(ngayVaoLamStr);
        pstmt.setDate(7, new java.sql.Date(ngayVaoLamUtil.getTime())); 
        
        pstmt.setString(8, ghiChu);
        
        // Execute the SQL query
        pstmt.executeUpdate();
        
        // Close the PreparedStatement
        pstmt.close();
        
        // Notify user that the data has been added successfully
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
        
        // Clear input fields after adding data
        clearInputFields();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên");
    } catch (ParseException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ngày vào làm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
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
    txtma.setText("");
    txtten.setText("");
    txtdiachi.setText("");
    txtngayvaolam.setText("");
    txtsdt.setText("");
    txtngaysinh.setText("");
    cbxChucVu.setSelectedIndex(-1);
    txtchuthich.setText("");
}
public void removeNhanVien() {
    int selectedRow = TableNhanVien.getSelectedRow();
    if (selectedRow != -1) {
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        int realSelectedRow = TableNhanVien.convertRowIndexToModel(selectedRow);

        // Lấy thông tin khách hàng từ bảng
        int maNhanVien = (int) model.getValueAt(realSelectedRow, 1); // Giả sử mã khách hàng là cột thứ hai trong bảng

        // Xóa khách hàng từ cơ sở dữ liệu
        Connection conn = new MyDBConnection().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NhanVien WHERE MaNhanVien = ?");
            pstmt.setInt(1, maNhanVien);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                model.removeRow(realSelectedRow); // Xóa hàng từ bảng hiển thị
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
    // Lấy chỉ số hàng được chọn trong bảng
    int selectedRow = TableNhanVien.getSelectedRow();      
    if (selectedRow != -1) {
        // Lấy thông tin nhân viên từ bảng
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        int realSelectedRow = TableNhanVien.convertRowIndexToModel(selectedRow);
        int maNhanVien = (int) model.getValueAt(realSelectedRow, 1); // Lấy mã nhân viên từ cột thứ hai trong bảng

        // Cập nhật thông tin nhân viên từ các trường nhập liệu trên giao diện
        String tenNhanVien = txtten.getText();
        boolean gioiTinh = rbtnNam.isSelected();
        String diaChi = txtdiachi.getText();
        String soDienThoai = txtsdt.getText();
        String chucVu = (String) cbxChucVu.getSelectedItem(); // Lấy thông tin chức vụ từ combobox
        // Lấy ngày sinh từ JTextField và chuyển đổi sang định dạng Date
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
        // Lấy ngày vào làm từ JTextField và chuyển đổi sang định dạng Date
        String ngayVaoLamStr = txtngayvaolam.getText();
        Date ngayVaoLam = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ngayVaoLam = sdf.parse(ngayVaoLamStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ngày vào làm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
            return; // Thoát khỏi phương thức nếu ngày vào làm không hợp lệ
        }
        String ghiChu = txtchuthich.getText();
        
        // Cập nhật thông tin trong cơ sở dữ liệu
        Connection conn = new MyDBConnection().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE NhanVien SET TenNhanVien = ?, GioiTinh = ?, DiaChi = ?, SoDT = ?, ChucVu = ?, NgaySinh = ?, NgayVaoLam = ?, GhiChu = ? WHERE MaNhanVien = ?");
            pstmt.setString(1, tenNhanVien);
            pstmt.setBoolean(2, gioiTinh);
            pstmt.setString(3, diaChi);
            pstmt.setString(4, soDienThoai);
            pstmt.setString(5, chucVu);
            pstmt.setDate(6, new java.sql.Date(ngaySinh.getTime())); // Chuyển đổi ngày sinh thành kiểu dữ liệu SQL Date
            pstmt.setDate(7, new java.sql.Date(ngayVaoLam.getTime())); // Chuyển đổi ngày vào làm thành kiểu dữ liệu SQL Date
            pstmt.setString(8, ghiChu);
            pstmt.setInt(9, maNhanVien);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Thông tin nhân viên đã được cập nhật thành công trong cơ sở dữ liệu!");
                
                // Cập nhật lại thông tin trong bảng hiển thị
                model.setValueAt(tenNhanVien, realSelectedRow, 2); 
                model.setValueAt(gioiTinh ? "Nam" : "Nữ", realSelectedRow, 3); 
                model.setValueAt(diaChi, realSelectedRow, 4); 
                model.setValueAt(soDienThoai, realSelectedRow, 5); 
                model.setValueAt(chucVu, realSelectedRow, 6); 
                model.setValueAt(ngaySinh, realSelectedRow, 7); 
                model.setValueAt(ngayVaoLam, realSelectedRow, 8); 
                model.setValueAt(ghiChu, realSelectedRow, 9); 
            } else {
                JOptionPane.showMessageDialog(null, "Không có thông tin nào được cập nhật trong cơ sở dữ liệu!");
            }
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi cập nhật thông tin nhân viên trong cơ sở dữ liệu!");
        }
    }
}
public void resetNhanVien() {
    // Đặt lại giá trị của các thành phần nhập liệu
    txtma.setText("");
    txtten.setText("");
    txtdiachi.setText("Thái Bình");
    txtsdt.setText("0367348026");
    txtngaysinh.setText("2003-1-1");
    txtngayvaolam.setText("2003-1-1");
    txtchuthich.setText("");
    
    // Đặt lại giá trị của các radio button
    rbtnNam.setSelected(true); // Đặt lại giá trị mặc định cho radio button Nam
    rbtnNu.setSelected(false); // Đặt lại giá trị mặc định cho radio button Nữ

    // Đặt lại giá trị của JComboBox
    cbxChucVu.setSelectedIndex(0); // Đặt lại giá trị mặc định cho JComboBox

    // Xóa tất cả các dòng trong bảng
    DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
    model.setRowCount(0); // Xóa tất cả các dòng trong bảng
}

public void displayNhanVien() {
    DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
    model.setRowCount(0); // Xóa hết dữ liệu trong bảng trước khi hiển thị mới
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = new MyDBConnection().getConnection();
        
        String query = "SELECT MaNhanVien, TenNhanVien, DiaChi, NgaySinh, SoDT, GioiTinh, NgayVaoLam, GhiChu " +
                       "FROM NhanVien";
        
        pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Object[] objNV = new Object[10]; // Mảng để lưu thông tin của một nhân viên
            
            // Đổ dữ liệu từ ResultSet vào mảng objNV
            objNV[0] = model.getRowCount() + 1; // Số thứ tự
            objNV[1] = rs.getInt("MaNhanVien");
            objNV[2] = rs.getString("TenNhanVien");
            objNV[3] = rs.getString("DiaChi");
            objNV[4] = rs.getDate("NgaySinh"); // Ngày sinh
            objNV[5] = rs.getString("SoDT");
            objNV[6] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ"; // Giới tính
            objNV[7] = ""; // Không có thông tin chức vụ nên để trống
            objNV[8] = rs.getDate("NgayVaoLam"); // Ngày vào làm
            objNV[9] = rs.getString("GhiChu"); // Ghi chú
            
            // Thêm một hàng mới vào bảng
            model.addRow(objNV);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Đóng kết nối và các đối tượng Statement và ResultSet
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
        jTable1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

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

        cbxChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                .addContainerGap(192, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("NhanVien", jPanel1);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Chức Vụ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên Chức Vụ", "Ghi Chú"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Nhân Viên");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Nhân Viên", "Tên Nhân Viên", "Chức Vụ"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jLabel12.setText("Mã Chức Vụ");

        jLabel13.setText("Tên Chức Vụ");

        jLabel14.setText("Ghi Chú");

        jButton1.setText("Thêm");

        jButton2.setText("Sửa");

        jButton3.setText("Reset");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(65, 65, 65)
                                .addComponent(jButton2)
                                .addGap(62, 62, 62)
                                .addComponent(jButton3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chức Vụ", jPanel2);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ThêmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThêmActionPerformed
        // TODO add your handling code here:
        addNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_ThêmActionPerformed

    private void txtmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmaMouseClicked
        // TODO add your handling code here:
        txtma.setEditable(false);
    }//GEN-LAST:event_txtmaMouseClicked

    private void XóaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XóaActionPerformed
        // TODO add your handling code here:
        removeNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_XóaActionPerformed

    private void SửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SửaActionPerformed
        // TODO add your handling code here:
        updateNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_SửaActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        // TODO add your handling code here:
        resetNhanVien();
        displayNhanVien();
    }//GEN-LAST:event_ResetActionPerformed

    private void TableNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableNhanVienMouseClicked
        // TODO add your handling code here:
    int viTriDongVuaBam = TableNhanVien.getSelectedRow();
    
    if (viTriDongVuaBam != -1) { // Kiểm tra xem người dùng đã chọn một dòng trong bảng chưa
        // Lấy giá trị từ dòng được chọn trong bảng và hiển thị thông tin tương ứng
        DefaultTableModel model = (DefaultTableModel) TableNhanVien.getModel();
        int realSelectedRow = TableNhanVien.convertRowIndexToModel(viTriDongVuaBam);
        
        // Lấy thông tin từ bảng và hiển thị trong các ô văn bản hoặc các thành phần khác
        String maNhanVien = model.getValueAt(realSelectedRow, 1).toString();
        String tenNhanVien = model.getValueAt(realSelectedRow, 2).toString();
        String diaChi = model.getValueAt(realSelectedRow, 3).toString();
        String ngaySinhStr = model.getValueAt(realSelectedRow, 4).toString(); // Giả sử cột 4 chứa ngày sinh
        String soDienThoai = model.getValueAt(realSelectedRow, 5).toString();
        String gioiTinh = model.getValueAt(realSelectedRow, 6).toString();
        String chucVu = model.getValueAt(realSelectedRow, 7).toString(); // Giả sử cột 7 chứa chức vụ
        String ngayVaoLamStr = model.getValueAt(realSelectedRow, 8).toString(); // Giả sử cột 8 chứa ngày vào làm
        String ghiChu = model.getValueAt(realSelectedRow, 9).toString(); // Giả sử cột 9 chứa chú thích
        // Tiếp tục lấy các thông tin cần thiết từ bảng
        
        // Hiển thị thông tin lấy được lên các ô văn bản hoặc các thành phần khác trên giao diện
        txtma.setText(maNhanVien);
        txtten.setText(tenNhanVien);
        txtdiachi.setText(diaChi);
        txtngaysinh.setText(ngaySinhStr);
        txtsdt.setText(soDienThoai);
        // Hiển thị giới tính
        if (gioiTinh.equals("Nam")) {
            rbtnNam.setSelected(true);
            rbtnNu.setSelected(false);
        } else {
            rbtnNam.setSelected(false);
            rbtnNu.setSelected(true);
        }
        // Hiển thị chức vụ
        cbxChucVu.setSelectedItem(chucVu);
        // Hiển thị ngày vào làm
        txtngayvaolam.setText(ngayVaoLamStr);
        // Hiển thị chú thích
        txtchuthich.setText(ghiChu);
        // Tiếp tục hiển thị các thông tin cần thiết khác lên giao diện
    }
    }//GEN-LAST:event_TableNhanVienMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Reset;
    private javax.swing.JButton Sửa;
    private javax.swing.JTable TableNhanVien;
    private javax.swing.JButton Thêm;
    private javax.swing.JButton Xóa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxChucVu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
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
