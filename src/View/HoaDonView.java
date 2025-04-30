package View;

import controller.HoaDonController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoaDonView extends JFrame {
    private JTextField tfXeID, tfNguoiDungID, tfKhachHangID, tfSoNgay;
    private JButton btnTaoHoaDon;

    public HoaDonView() {
        setTitle("Tạo Hóa đơn thuê xe");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("ID Xe:"));
        tfXeID = new JTextField();
        add(tfXeID);

        add(new JLabel("ID Nhân viên:"));
        tfNguoiDungID = new JTextField();
        add(tfNguoiDungID);

        add(new JLabel("ID Khách hàng:"));
        tfKhachHangID = new JTextField();
        add(tfKhachHangID);

        add(new JLabel("Số ngày thuê:"));
        tfSoNgay = new JTextField();
        add(tfSoNgay);

        btnTaoHoaDon = new JButton("Tạo Hóa đơn");
        add(btnTaoHoaDon);

        btnTaoHoaDon.addActionListener(e -> {
            try {
                int xeId = Integer.parseInt(tfXeID.getText());
                int nguoiDungId = Integer.parseInt(tfNguoiDungID.getText());
                int khachHangId = Integer.parseInt(tfKhachHangID.getText());
                int soNgay = Integer.parseInt(tfSoNgay.getText());

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime ketThuc = now.plusDays(soNgay);
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                boolean result = HoaDonController.taoHoaDon(
                    xeId, nguoiDungId, khachHangId,
                    now.format(fmt),
                    ketThuc.format(fmt),
                    soNgay
                );

                if (result) {
                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
                    // Reset các trường nhập liệu
                    tfXeID.setText("");
                    tfNguoiDungID.setText("");
                    tfKhachHangID.setText("");
                    tfSoNgay.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thất bại khi tạo hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
}
