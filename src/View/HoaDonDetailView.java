package View;

import model.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import controller.KhachHangController;
import controller.NguoiDungController;
import controller.XeMayController;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class HoaDonDetailView extends JFrame {
    private JTextField tfBienSo, tfLoaiXe, tfTenXe, tfGiaThue;
    private JTextField tfCCCD, tfTen, tfDiaChi, tfSDT, tfEmail, tfNguoiTaoDon;
    private JTextField tfNgayThue, tfNgayTra, tfSoNgay, tfTongTien;
    private JButton btnHuy;
    int idKhachHang, idXe, idNguoiDung;

    public HoaDonDetailView() {
        setTitle("Chi Tiết Hóa Đơn");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // ================= PHẦN THÔNG TIN XE =================
        JPanel leftMainPanel = new JPanel();
        leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));

        JPanel panelXe = createStyledPanel("Thông tin xe", labelFont);
        panelXe.setLayout(new GridLayout(4, 2, 8, 12));
        panelXe.setPreferredSize(new Dimension(400, 180));

        tfBienSo = createReadOnlyTextField(fieldFont);
        tfLoaiXe = createReadOnlyTextField(fieldFont);
        tfTenXe = createReadOnlyTextField(fieldFont);
        tfGiaThue = createReadOnlyTextField(fieldFont);

        panelXe.add(createStyledLabel("Biển số:", labelFont));
        panelXe.add(tfBienSo);
        panelXe.add(createStyledLabel("Loại xe:", labelFont));
        panelXe.add(tfLoaiXe);
        panelXe.add(createStyledLabel("Tên xe:", labelFont));
        panelXe.add(tfTenXe);
        panelXe.add(createStyledLabel("Giá thuê/ngày:", labelFont));
        panelXe.add(tfGiaThue);

        leftMainPanel.add(panelXe);
        leftMainPanel.add(Box.createVerticalStrut(15));

        // ================= PHẦN THÔNG TIN KHÁCH HÀNG =================
        JPanel panelKhachHang = createStyledPanel("Thông tin khách hàng", labelFont);
        panelKhachHang.setLayout(new GridLayout(6, 2, 8, 12));
        panelKhachHang.setPreferredSize(new Dimension(400, 250));

        tfCCCD = createReadOnlyTextField(fieldFont);
        tfTen = createReadOnlyTextField(fieldFont);
        tfDiaChi = createReadOnlyTextField(fieldFont);
        tfSDT = createReadOnlyTextField(fieldFont);
        tfEmail = createReadOnlyTextField(fieldFont);
        tfNguoiTaoDon = createReadOnlyTextField(fieldFont);

        panelKhachHang.add(createStyledLabel("CCCD:", labelFont));
        panelKhachHang.add(tfCCCD);
        panelKhachHang.add(createStyledLabel("Họ tên:", labelFont));
        panelKhachHang.add(tfTen);
        panelKhachHang.add(createStyledLabel("Địa chỉ:", labelFont));
        panelKhachHang.add(tfDiaChi);
        panelKhachHang.add(createStyledLabel("SĐT:", labelFont));
        panelKhachHang.add(tfSDT);
        panelKhachHang.add(createStyledLabel("Email:", labelFont));
        panelKhachHang.add(tfEmail);
        panelKhachHang.add(createStyledLabel("Người tạo đơn:", labelFont));
        panelKhachHang.add(tfNguoiTaoDon);

        leftMainPanel.add(panelKhachHang);
        mainPanel.add(leftMainPanel, BorderLayout.CENTER);

        // ================= PHẦN THÔNG TIN THUÊ XE =================
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 0));

        JPanel panelThoiGian = createStyledPanel("Thời gian thuê", labelFont);
        panelThoiGian.setLayout(new GridLayout(4, 2, 8, 10));
        panelThoiGian.setPreferredSize(new Dimension(280, 150));

        tfNgayThue = createReadOnlyTextField(fieldFont);
        tfNgayTra = createReadOnlyTextField(fieldFont);
        tfSoNgay = createReadOnlyTextField(fieldFont);
        tfTongTien = createReadOnlyTextField(fieldFont);

        panelThoiGian.add(createStyledLabel("Ngày thuê:", labelFont));
        panelThoiGian.add(tfNgayThue);
        panelThoiGian.add(createStyledLabel("Ngày trả:", labelFont));
        panelThoiGian.add(tfNgayTra);
        panelThoiGian.add(createStyledLabel("Số ngày:", labelFont));
        panelThoiGian.add(tfSoNgay);
        panelThoiGian.add(createStyledLabel("Tổng tiền:", labelFont));
        panelThoiGian.add(tfTongTien);

        rightPanel.add(panelThoiGian);
        rightPanel.add(Box.createVerticalGlue());
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // ================= PHẦN NÚT BẤM DƯỚI CÙNG =================
        JPanel panelFooter = new JPanel(new BorderLayout());
        panelFooter.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelFooter.setBackground(new Color(250, 250, 250));

        JPanel panelRightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnHuy = createStyledButton("Huỷ", new Color(220, 53, 53), buttonFont);
        panelRightFooter.add(btnHuy);
        panelFooter.add(panelRightFooter, BorderLayout.EAST);

        mainPanel.add(panelFooter, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        btnHuy.addActionListener(e -> dispose());
    }

    // ================= CÁC PHƯƠNG THỨC HỖ TRỢ =================
    private JPanel createStyledPanel(String title, Font font) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            title, TitledBorder.LEFT, TitledBorder.TOP, font, new Color(70, 70, 70)));
        panel.setBackground(new Color(250, 250, 250));
        return panel;
    }

    private JTextField createReadOnlyTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setEditable(false);
        textField.setEnabled(true);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return textField;
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(70, 70, 70));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public HoaDonDetailView(int xeId, int nguoiDungId, int khachHangId, 
                           Timestamp batDau, Timestamp ketThuc, long tongTien) {
        this();
        
        XeMay xe = XeMayController.getXeById(xeId);
        NguoiDungController nguoidungcontroller= new  NguoiDungController();
        NguoiDung nguoiDung = nguoidungcontroller.getNguoiDungById(nguoiDungId);
        KhachHang khachHang = KhachHangController.getKhachHangById(khachHangId);

        if (xe != null) {
            tfBienSo.setText(xe.getBienSo());
            tfLoaiXe.setText(xe.getLoaiXe());
            tfTenXe.setText(xe.getTenXe());
            tfGiaThue.setText(String.format("%,d VND", xe.getGiaThue()));
        }

        if (khachHang != null) {
            tfCCCD.setText(khachHang.getCccd());
            tfTen.setText(khachHang.getTen());
            tfDiaChi.setText(khachHang.getDiaChi());
            tfSDT.setText(khachHang.getSdt());
            tfEmail.setText(khachHang.getEmail());
        }

        if (nguoiDung != null) {
            tfNguoiTaoDon.setText(nguoiDung.getHoTen());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (batDau != null) {
            tfNgayThue.setText(sdf.format(batDau));
        }
        if (ketThuc != null) {
            tfNgayTra.setText(sdf.format(ketThuc));
        }

        if (batDau != null && ketThuc != null) {
            long diff = ketThuc.getTime() - batDau.getTime();
            long soNgay = diff / (1000 * 60 * 60 * 24);
            tfSoNgay.setText(String.valueOf(soNgay));
        }

        tfTongTien.setText(String.format("%,d VND", tongTien));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HoaDonDetailView view = new HoaDonDetailView();
            view.setVisible(true);
        });
    }
}