package View;

import controller.XeMayController;
import model.XeMay;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class XeMayView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public XeMayView() {
        setTitle("Danh sách Xe Máy");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Tạo model cho bảng
        String[] columnNames = {"ID", "Biển số", "Tên xe", "Loại xe", "Trạng thái", "Giá thuê/ngày"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Đặt màu cho các dòng xen kẽ
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                
                // Đổi màu theo trạng thái
                if (column == 4) { // Cột trạng thái
                    String status = (String) value;
                    if (status.equals("dang_thue")) {
                        c.setBackground(new Color(255, 200, 200));
                    } else if (status.equals("bi_hong")) {
                        c.setBackground(new Color(255, 255, 150));
                    }
                }
                
                return c;
            }
        });

        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Thêm nút làm mới
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadData());
        JPanel panel = new JPanel();
        panel.add(btnRefresh);
        add(panel, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<XeMay> dsXe = XeMayController.getAll();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (XeMay xe : dsXe) {
            // Chuyển đổi trạng thái sang tiếng Việt
            String trangThai = "";
            switch (xe.getTrangThai()) {
                case "san_sang": trangThai = "Sẵn sàng"; break;
                case "dang_thue": trangThai = "Đang thuê"; break;
                case "bi_hong": trangThai = "Bị hỏng"; break;
                default: trangThai = xe.getTrangThai();
            }

            Object[] rowData = {
                xe.getId(),
                xe.getBienSo(),
                xe.getTenXe(),
                xe.getLoaiXe(),
                trangThai,
                String.format("%,d VND", xe.getGiaThue())
            };
            model.addRow(rowData);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            XeMayView view = new XeMayView();
//            view.setVisible(true);
//        });
//    }
}