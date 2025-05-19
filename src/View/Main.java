package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;  // Import for ActionEvent
import java.awt.event.ActionListener;  // Import for ActionListener

public class Main extends JFrame {
    
    public Main() {
        initUI();
    }
    
    private void initUI() {
        setTitle("Hệ thống Thuê Xe Máy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        createMenuBar();
        createMainPanel();
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Quản lý
        JMenu mnQuanLy = new JMenu("Quản lý");
        mnQuanLy.add(createMenuItem("Quản lý Xe Máy", e -> openXeMayView()));
        mnQuanLy.add(createMenuItem("Quản lý Nhân Viên", e -> openNhanVienView()));
        mnQuanLy.addSeparator();
        mnQuanLy.add(createMenuItem("Thoát", e -> System.exit(0)));
        
        // Menu Giao dịch
        JMenu mnGiaoDich = new JMenu("Giao dịch");
//        mnGiaoDich.add(createMenuItem("Tạo Hóa Đơn", e -> openHoaDonView()));
        mnGiaoDich.add(createMenuItem("Xem Doanh Thu", e -> openDoanhThuView()));
        
        menuBar.add(mnQuanLy);
        menuBar.add(mnGiaoDich);
        
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
        
        // Panel tiêu đề
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 150, 200));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ THUÊ XE MÁY");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        // Panel nội dung
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JLabel lblWelcome = new JLabel("Chào mừng đến với hệ thống");
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(lblWelcome);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void openXeMayView() {
        SwingUtilities.invokeLater(() -> {
            XeMayView view = new XeMayView();
            view.setVisible(true);
        });
    }
    
    private void openNhanVienView() {
        SwingUtilities.invokeLater(() -> {
            NhanVienView view = new NhanVienView();
            view.setVisible(true);
        });
    }
    
    private void openHoaDonView() {
        SwingUtilities.invokeLater(() -> {
            HoaDonView view = new HoaDonView();
            view.setVisible(true);
        });
    }
    
    private void openDoanhThuView() {
        SwingUtilities.invokeLater(() -> {
            DoanhThuView view = new DoanhThuView();
            view.setVisible(true);
        });
    }
    
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
          Main app = new Main();
           app.setVisible(true);
        });
    }
}