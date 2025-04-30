package View;

import controller.NguoiDungController;
import model.NguoiDung;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class NhanVienView extends JFrame {
    private JTextArea textArea;
    private SimpleDateFormat dateFormat;
    private JButton btnRefresh;

    public NhanVienView() {
        setTitle("QUẢN LÝ NHÂN VIÊN");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadNhanVienData();
    }

    private void initComponents() {
        // Main panel với border và padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Panel tiêu đề
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel lblTitle = new JLabel("DANH SÁCH NHÂN VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        // Khởi tạo textArea với font và màu đẹp hơn
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Định dạng ngày tháng
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Panel nút refresh
        JPanel buttonPanel = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(144, 238, 144));
        btnRefresh.addActionListener(e -> loadNhanVienData());
        buttonPanel.add(btnRefresh);

        // Thêm các component vào main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadNhanVienData() {
        try {
            List<NguoiDung> ds = NguoiDungController.getDanhSachNhanVien();
            StringBuilder sb = new StringBuilder();
            
            // Header
            sb.append(String.format("%-5s %-25s %-15s %-12s\n", 
                "ID", "Họ và tên", "SĐT", "Ngày sinh"));
            sb.append("------------------------------------------------------------\n");
            
            // Dữ liệu
            for (NguoiDung nd : ds) {
                String ngaySinhStr = (nd.getNgaySinh() != null) 
                    ? dateFormat.format(nd.getNgaySinh()) 
                    : "N/A";
                
                sb.append(String.format("%-5d %-25s %-15s %-12s\n",
                    nd.getId(),
                    nd.getHoTen(),
                    nd.getSdt() != null ? nd.getSdt() : "N/A",
                    ngaySinhStr));
            }
            
            textArea.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu nhân viên: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NhanVienView view = new NhanVienView();
            view.setVisible(true);
        });
    }
}