package View;

import model.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import controller.HoaDonController;
import controller.KhachHangController;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HoaDonView extends JFrame {
    private JTextField tfBienSo, tfLoaiXe, tfTenXe, tfGiaThue;
    private JTextField tfCCCD, tfTen, tfDiaChi, tfSDT, tfEmail, tfNguoiTaoDon;
    private JTextField tfNgayThue, tfNgayTra, tfSoNgay, tfTongTien;
    private JButton btnHuy, btnTao, btnCapNhat;
    private JCheckBox chkKhachHangCu;
    int idKhachHang,idXe,idNguoiDung;

    public HoaDonView() {
        setTitle("Tạo Hóa Đơn");
        setSize(950, 700); // Tăng kích thước để đảm bảo hiển thị đủ
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

        tfBienSo = createStyledTextField(fieldFont, false);
        tfLoaiXe = createStyledTextField(fieldFont, false);
        tfTenXe = createStyledTextField(fieldFont, false);
        tfGiaThue = createStyledTextField(fieldFont, false);

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

        tfCCCD = createStyledTextField(fieldFont, true);
        tfTen = createStyledTextField(fieldFont, true);
        tfDiaChi = createStyledTextField(fieldFont, true);
        tfSDT = createStyledTextField(fieldFont, true);
        tfEmail = createStyledTextField(fieldFont, true);
        tfNguoiTaoDon = createStyledTextField(fieldFont, true);

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

        tfNgayThue = createStyledTextField(fieldFont, true);
        tfNgayTra = createStyledTextField(fieldFont, false);
        tfSoNgay = createStyledTextField(fieldFont, true);
        tfTongTien = createStyledTextField(fieldFont, false);

        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tfNgayThue.setText(today);

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

        // Panel chứa checkbox và nút Cập nhật (bên trái)
        JPanel panelLeftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelLeftFooter.setBackground(new Color(250, 250, 250));
        
        chkKhachHangCu = new JCheckBox("Khách hàng cũ");
        chkKhachHangCu.setEnabled(false); // Vô hiệu hóa tương tác
        chkKhachHangCu.setFocusable(false); // Ẩn hiệu ứng focus
        chkKhachHangCu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        chkKhachHangCu.setSelected(false);
        
        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCapNhat.setBackground(new Color(0, 120, 215));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnCapNhat.setPreferredSize(new Dimension(120, 35));
        
        panelLeftFooter.add(chkKhachHangCu);
        panelLeftFooter.add(Box.createHorizontalStrut(10));
        panelLeftFooter.add(btnCapNhat);
        panelFooter.add(panelLeftFooter, BorderLayout.WEST);

        // Panel chứa nút Huỷ và Tạo đơn (bên phải)
        JPanel panelRightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnHuy = createStyledButton("Huỷ", new Color(220, 53, 53), buttonFont);
        btnTao = createStyledButton("Tạo đơn", new Color(0, 120, 215), buttonFont);
        
        panelRightFooter.add(btnHuy);
        panelRightFooter.add(btnTao);
        panelFooter.add(panelRightFooter, BorderLayout.EAST);

        mainPanel.add(panelFooter, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        btnHuy.addActionListener(e -> dispose());

        // ================= CÁC SỰ KIỆN =================
        // Sự kiện tính toán tự động
        tfSoNgay.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            
            private void calculateTotal() {
                try {
                    int soNgay = Integer.parseInt(tfSoNgay.getText().trim());
                    int giaThue = Integer.parseInt(tfGiaThue.getText().trim());
                    if (soNgay > 0 && giaThue > 0) {
                        int tong = soNgay * giaThue;
                        tfTongTien.setText(String.valueOf(tong));
                        updateNgayTra(soNgay);
                    }
                } catch (NumberFormatException ex) {
                    tfTongTien.setText("");
                    tfNgayTra.setText("");
                }
            }
            
            private void updateNgayTra(int soNgay) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date ngayThueDate = sdf.parse(tfNgayThue.getText());
                    long newTime = ngayThueDate.getTime() + (long) soNgay * 24 * 60 * 60 * 1000;
                    tfNgayTra.setText(sdf.format(new Date(newTime)));
                } catch (Exception ex) {
                    tfNgayTra.setText("");
                }
            }
        });

        // Sự kiện tìm khách hàng theo CCCD
        tfCCCD.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String cccd = tfCCCD.getText().trim();
                if (!cccd.isEmpty()) {
                    KhachHangController khController = new KhachHangController();
                    KhachHang kh = khController.timKhachHangTheoCCCD(cccd);
                    if (kh != null) {
                        tfTen.setText(kh.getTen());
                        tfDiaChi.setText(kh.getDiaChi());
                        tfSDT.setText(kh.getSdt());
                        tfEmail.setText(kh.getEmail());
                        idKhachHang = kh.getId();
                        chkKhachHangCu.setSelected(true);
                    } else {
                        tfTen.setText("");
                        tfDiaChi.setText("");
                        tfSDT.setText("");
                        tfEmail.setText("");
                        chkKhachHangCu.setSelected(false);
                    }
                }
            }
        });
        btnCapNhat.addActionListener(e -> {
            if (chkKhachHangCu.isSelected()) {
                // Validate dữ liệu trước khi cập nhật
                if (tfCCCD.getText().trim().isEmpty() || tfTen.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "CCCD và Họ tên không được để trống", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    KhachHangController controller = new KhachHangController();
                    KhachHang kh = new KhachHang();
                    kh.setId(idKhachHang);
                    kh.setCccd(tfCCCD.getText().trim());          
                    kh.setTen(tfTen.getText().trim());            
                    kh.setDiaChi(tfDiaChi.getText().trim());      
                    kh.setSdt(tfSDT.getText().trim());            
                    kh.setEmail(tfEmail.getText().trim());
                    
                    boolean result = controller.updateKhachHang(kh);
                    
                    if (result) {
                        JOptionPane.showMessageDialog(this, 
                            "Cập nhật thông tin thành công", 
                            "Thành công", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Cập nhật không thành công", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Lỗi khi cập nhật: " + ex.getMessage(), 
                        "Lỗi hệ thống", 
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn 'Khách hàng cũ' để cập nhật", 
                    "Thông báo", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        btnTao.addActionListener(e -> {
            try {
                // ========= 1. KIỂM TRA THÔNG TIN BẮT BUỘC =========
                if (tfCCCD.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "CCCD không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (tfSoNgay.getText().trim().isEmpty() || Integer.parseInt(tfSoNgay.getText()) <= 0) {
                    JOptionPane.showMessageDialog(this, "Số ngày thuê phải > 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // ========= 2. XỬ LÝ KHÁCH HÀNG =========
                KhachHangController khController = new KhachHangController();
                String cccd = tfCCCD.getText().trim();
                
                // Nếu là KH mới (checkbox không được tick)
                if (!chkKhachHangCu.isSelected()) {
                    // Kiểm tra xem CCCD đã tồn tại chưa
                    if (khController.timKhachHangTheoCCCD(cccd) != null) {
                        JOptionPane.showMessageDialog(this, 
                            "CCCD đã tồn tại. Vui lòng tick 'Khách hàng cũ'", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Thêm KH mới nếu chưa tồn tại
                    KhachHang khachMoi = new KhachHang();
                    khachMoi.setCccd(cccd);
                    khachMoi.setTen(tfTen.getText().trim());
                    khachMoi.setDiaChi(tfDiaChi.getText().trim());
                    khachMoi.setSdt(tfSDT.getText().trim());
                    khachMoi.setEmail(tfEmail.getText().trim());
                    
                    if (!khController.addKhachHang(khachMoi)) {
                        throw new Exception("Không thể thêm khách hàng mới");
                    }
                }

                // ========= 3. LẤY THÔNG TIN ĐỂ TẠO HÓA ĐƠN =========
                int khachHangId = KhachHangController.timKhachHangIdTheoCCCD(cccd);
                if (khachHangId == -1) {
                    throw new Exception("Không tìm thấy thông tin khách hàng");
                }

                // Giả định các thông tin khác (cần điều chỉnh theo thực tế)
               
                
                // ========= 4. GỌI CONTROLLER TẠO HÓA ĐƠN =========
                boolean result = HoaDonController.taoHoaDon(
                   idXe,
                    idNguoiDung,
                    khachHangId,
                    tfNgayThue.getText(), // ngày bắt đầu
                    tfNgayTra.getText(),  // ngày kết thúc
                    Integer.parseInt(tfSoNgay.getText()) // số ngày thuê
                );

                // ========= 5. XỬ LÝ KẾT QUẢ =========
                if (result) {
                    JOptionPane.showMessageDialog(this, 
                        "Tạo hóa đơn thành công!", 
                        "Thành công", 
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Đóng cửa sổ sau khi tạo thành công
                } else {
                    throw new Exception("Không thể tạo hóa đơn");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Số ngày không hợp lệ", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi database: " + ex.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi: " + ex.getMessage(), 
                    "Lỗi hệ thống", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        btnHuy.addActionListener(e -> {
            this.dispose(); // Chỉ đóng form hiện tại (HoaDonView)
        });
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

    private JTextField createStyledTextField(Font font, boolean editable) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setEditable(editable);
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

    // Constructor khi truyền vào thông tin xe
    public HoaDonView(XeMay xe, NguoiDung nguoiDung) {
        this();
        if (xe != null) {
            tfBienSo.setText(xe.getBienSo());
            tfLoaiXe.setText(xe.getLoaiXe());
            tfTenXe.setText(xe.getTenXe());
            tfGiaThue.setText(String.valueOf(xe.getGiaThue()));
            idXe = xe.getId();
        }
        
        if (nguoiDung != null) {
            tfNguoiTaoDon.setText(nguoiDung.getHoTen()); // Hiển thị tên người tạo đơn
            idNguoiDung = nguoiDung.getId(); // Lưu lại ID người dùng
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HoaDonView view = new HoaDonView();
            view.setVisible(true);
        });
    }
}