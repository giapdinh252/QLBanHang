/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlbh.controller;

import com.mycompany.qlbh.model.Users;
import com.mycompany.qlbh.service.UsersService;
import com.mycompany.qlbh.service.UsersServiceImpl;
import com.mycompany.qlbh.view.MainJFrame;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ADMIN
 */
public class DangNhapController {
    private Dialog dialog;
    private JButton btnSubmit;
    private JTextField jtfTenDangNhap;
    private JPasswordField jtfMatKhau;
    private JLabel jlbMsg;

    private UsersService taiKhoanService = null;

    public DangNhapController(Dialog dialog, JButton btnSubmit,
            JTextField jtfTenDangNhap, JPasswordField jtfMatKhau, JLabel jlbMsg) {
        this.dialog = dialog;
        this.btnSubmit = btnSubmit;
        this.jtfTenDangNhap = jtfTenDangNhap;
        this.jtfMatKhau = jtfMatKhau;
        this.jlbMsg = jlbMsg;

        taiKhoanService = new UsersServiceImpl();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (jtfTenDangNhap.getText().isEmpty()
                            || jtfMatKhau.getText().isEmpty()) {
                        jlbMsg.setText("Vui lòng nhập tên đăng nhập và mật khẩu!");
                    } else {
                        Users taiKhoan = taiKhoanService.login(jtfTenDangNhap.getText(), jtfMatKhau.getText());
                        if (taiKhoan == null) {
                            jlbMsg.setText("Bạn đã nhập sai thông tin!");
                        } else {
                            MainJFrame frame = new MainJFrame();
                            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            frame.setVisible(true);
                            dialog.dispose();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(153, 153, 153));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(242, 242, 242));
            }
        });
    }
}
