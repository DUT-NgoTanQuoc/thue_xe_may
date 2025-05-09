package View;

import controller.HoaDonController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoaDonView extends JFrame {
    private JTextField tfXeID, tfNguoiDungID, tfKhachHangID, tfSoNgay;
    private JButton btnTaoHoaDon, btnHuy;

    public HoaDonView() {
        setTitle("Tạo Hóa đơn thuê xe");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lb1 = new JLabel("ID Xe:");
        lb1.setFont(font);
        tfXeID = new JTextField();
        tfXeID.setFont(font);

        JLabel lb2 = new JLabel("ID Nhân viên:");
        lb2.setFont(font);
        tfNguoiDungID = new JTextField();
        tfNguoiDungID.setFont(font);

        JLabel lb3 = new JLabel("ID Khach Hang:");
        lb3.setFont(font);
        tfKhachHangID = new JTextField();
        tfKhachHangID.setFont(font);

        JLabel lb4 = new JLabel("Số ngày thuê:");
        lb4.setFont(font);
        tfSoNgay = new JTextField();
        tfSoNgay.setFont(font);

        btnTaoHoaDon = new JButton("Tạo Hóa đơn");
        btnTaoHoaDon.setFont(font);
        btnTaoHoaDon.setBackground(new Color(60, 130, 220));
        btnTaoHoaDon.setForeground(Color.WHITE);

        btnHuy = new JButton("Huỷ");
        btnHuy.setFont(font);
        btnHuy.setBackground(new Color(200, 60, 60));
        btnHuy.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(lb1, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(tfXeID, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(lb2, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(tfNguoiDungID, gbc);
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(lb3, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(tfKhachHangID, gbc);
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(lb4, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(tfSoNgay, gbc);

        // Panel chứa 2 nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnTaoHoaDon);

        gbc.gridx = 1; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(buttonPanel, gbc);

        setContentPane(mainPanel);

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
                        xeId, nguoiDungId,khachHangId,
                        now.format(fmt),
                        ketThuc.format(fmt),
                        soNgay
                );

                if (result) {
                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
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

        btnHuy.addActionListener(e -> dispose());
    }

   // public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new HoaDonView().setVisible(true));
    //}
}
