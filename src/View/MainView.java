package View;

import javax.swing.*;
import java.awt.event.*;

public class MainView extends JFrame {
    public MainView() {
        setTitle("Quản Lý Thuê Xe Máy");
        setSize(400, 300);
        setLayout(null);

        JButton btnNV = new JButton("Xem Nhân Viên");
        btnNV.setBounds(100, 30, 200, 30);
        add(btnNV);
        btnNV.addActionListener(e -> new NhanVienView());

        JButton btnXM = new JButton("Xem Xe Máy");
        btnXM.setBounds(100, 70, 200, 30);
        add(btnXM);
        btnXM.addActionListener(e -> new XeMayView());

        JButton btnHD = new JButton("Tạo Hóa Đơn");
        btnHD.setBounds(100, 110, 200, 30);
        add(btnHD);
        btnHD.addActionListener(e -> new HoaDonView());

        JButton btnDT = new JButton("Xem Doanh Thu");
        btnDT.setBounds(100, 150, 200, 30);
        add(btnDT);
        btnDT.addActionListener(e -> new DoanhThuView());

        setVisible(true);
        
//        public static  main (String arg[]) {
//        	new MainView().setVisible(true);
//        }
    }
}
