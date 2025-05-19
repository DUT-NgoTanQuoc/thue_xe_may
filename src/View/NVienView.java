package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;

import model.NguoiDung;

public class NVienView extends JFrame {
    private NguoiDung nguoiDung;

    public NVienView(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
        initUI();
    }

    private void initUI() {
        setTitle("Giao diện Nhân viên - " + nguoiDung.getHoTen());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();
        createMainPanel();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu mnChucNang = new JMenu("Chức năng");
        mnChucNang.add(createMenuItem("Hóa đơn", e -> openHoaDonView()));
        mnChucNang.add(createMenuItem("Khách hàng", e -> openKhachHangView()));
        mnChucNang.add(createMenuItem("XeMáy", e -> openXeMayView())); // <-- Thêm dòng này
        mnChucNang.addSeparator();
        mnChucNang.add(createMenuItem("Đăng xuất", e -> dispose()));

        menuBar.add(mnChucNang);
        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel lblTitle = new JLabel("CHỨC NĂNG DÀNH CHO NHÂN VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        JLabel lblWelcome = new JLabel("Xin chào, " + nguoiDung.getHoTen() + "! Chọn chức năng ở thanh menu phía trên để bắt đầu");
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(lblWelcome);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void openHoaDonView() {
        SwingUtilities.invokeLater(() -> new HoaDonMainView().setVisible(true));
    }
    private void openXeMayView() {
        SwingUtilities.invokeLater(() -> {
            new XeMayListView(nguoiDung).setVisible(true);
        });
    }

    private void openKhachHangView() {
        SwingUtilities.invokeLater(() -> new KhachHangView().setVisible(true));
    }

    // main() chỉ để test tạm thời
    
}
