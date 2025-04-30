package View;

import controller.HoaDonController;

import javax.swing.*;
import java.awt.*;

public class DoanhThuView extends JFrame {
    private JLabel lblDoanhThu;

    public DoanhThuView() {
        setTitle("Tổng Doanh thu");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lblDoanhThu = new JLabel("", JLabel.CENTER);
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblDoanhThu, BorderLayout.CENTER);

        hienThiDoanhThu();
    }

    private void hienThiDoanhThu() {
        long tong =  HoaDonController.tinhTongDoanhThu();
        lblDoanhThu.setText("Tổng doanh thu: " + tong + " VND");
    }
}	
