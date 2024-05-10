package com.mycompany.qlbh.controller;

import com.mycompany.qlbh.bean.DanhMucBean;
import com.mycompany.qlbh.view.AboutMe;
import com.mycompany.qlbh.view.DangNhapJDialog;
import com.mycompany.qlbh.view.HoaDonJPanel;
import com.mycompany.qlbh.view.KhachHangPanel;
import com.mycompany.qlbh.view.MainJFrame;
import com.mycompany.qlbh.view.NhanVienPanel;
import com.mycompany.qlbh.view.SanPhamJPanel;
import com.mycompany.qlbh.view.TrangChuJPanel1;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ChuyenManHinhController {
    private final JPanel root; 
    private String kindSelected = "";
    private List<DanhMucBean> listDanhMuc;

    public ChuyenManHinhController(JPanel root) {
        this.root = root;
    }

    public void setDashboard(JPanel jpnItem, JLabel jlbItem) {
       kindSelected = "TrangChu";
       jpnItem.setBackground(new Color(153,153,153));
       jlbItem.setBackground(new Color(153,153,153));
       JPanel node = new TrangChuJPanel1();
       root.removeAll();
       root.setLayout(new BorderLayout());
       root.add(node);
       root.validate();
       root.repaint();
    }

    public void setEvent(List<DanhMucBean> listDanhMuc) {
        this.listDanhMuc = listDanhMuc;
        for (DanhMucBean item : listDanhMuc) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {
        private JPanel node;
        private final String kind;

        private final JPanel jpnItem;
        private final JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu" -> node = new TrangChuJPanel1();
                case "HoaDon" -> node = new HoaDonJPanel();
                case "SanPham" -> node = new SanPhamJPanel();
                case "KhachHang" -> node = new KhachHangPanel();
                case "NhanVien" -> node = new NhanVienPanel();
                case "AboutMe"  -> node = new AboutMe();             
                default -> {
                }
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(153,153,153));
            jlbItem.setBackground(new Color(153,153,153));
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(153,153,153));
            jlbItem.setBackground(new Color(153,153,153));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(242, 242, 242));
                jlbItem.setBackground(new Color(242, 242, 242));
            }
        }

        private void setChangeBackground(String kind) {
            
            for (DanhMucBean item : listDanhMuc) {
                if (item.getKind().equals(kind)) {
                    item.getJpn().setBackground(new Color(153,153,153));
                    item.getJlb().setBackground(new Color(153,153,153));
                } else {
                    item.getJpn().setBackground(new Color(242, 242, 242));
                    item.getJlb().setBackground(new Color(242, 242, 242));
                }
            }
        }
    }
}
