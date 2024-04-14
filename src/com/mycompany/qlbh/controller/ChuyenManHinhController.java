 package com.mycompany.qlbh.controller;
import com.mycompany.qlbh.bean.DanhMucBean;
import com.mycompany.qlbh.view.HoaDonJPanel;
import com.mycompany.qlbh.view.KhachHangPanel;
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
    private JPanel root; 
    private String kinSelected = "";
    private List<DanhMucBean> listDanhMuc;

    public ChuyenManHinhController(JPanel root) {
        this.root = root;
    }

     
    
    public void setDashboard(JPanel jpnItem, JLabel jlbItem) {
       kinSelected = "TrangChinh";
       jpnItem.setBackground(new Color(96, 100, 191));
       jlbItem.setBackground(new Color(96, 100, 191));
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
      private String kind;

      private JPanel jpnItem;
      private JLabel jlbItem;

      public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
           this.kind = kind;
           this.jpnItem = jpnItem;
           this.jlbItem = jlbItem;
      }

      @Override
      public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuJPanel1();
                    break;
                case "HoaDon":
                    node = new HoaDonJPanel();
                    break;
                case "SanPham":
                    node = new SanPhamJPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangPanel();
                    break;    
                default:
                    break;
           }
       root.removeAll();
       root.setLayout(new BorderLayout());
       root.add(node);
       root.validate();
       root.repaint();
        setChangeBackground(kind);
      }

      @Override
      public void mousePressed(MouseEvent e) {//Xử lý sự kiện khi chuột được giữ.
           kinSelected = kind;
           jpnItem.setBackground(new Color(96, 100, 191));
           jlbItem.setBackground(new Color(96, 100, 191));
      }

      @Override
      public void mouseReleased(MouseEvent e) {//Xử lý sự kiện khi chuột được thả.

      }

      @Override
      public void mouseEntered(MouseEvent e) {//Xử lý sự kiện khi chuột nhập vào.
          jpnItem.setBackground(new Color(96, 100, 191));
          jlbItem.setBackground(new Color(96, 100, 191));
      }

      @Override
      public void mouseExited(MouseEvent e) {//Xử lý sự kiện khi chuột rời khỏi.
          if (!kinSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(76, 175, 80));
                jlbItem.setBackground(new Color(76, 175, 80));
          }
      }

        private void setChangeBackground(String kind) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

}
    
}
