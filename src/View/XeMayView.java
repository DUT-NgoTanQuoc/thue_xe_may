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
    private JComboBox<String> cbLoai, cbTrangThai;
    private JButton btnLocLoai, btnLocTrangThai;

    public XeMayView() {
        setTitle("Danh sách Xe Máy");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        initFilters();
        loadData();
    }

    private void initComponents() {
        String[] columnNames = {"ID", "Biển số", "Tên xe", "Loại xe", "Trạng thái", "Giá thuê/ngày"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                if (column == 4) {
                    String status = (String) value;
                    if (status.equals("Đang thuê")) {
                        c.setBackground(new Color(255, 200, 200));
                    } else if (status.equals("Bị hỏng")) {
                        c.setBackground(new Color(255, 255, 150));
                    }
                }
                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadData());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnRefresh);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initFilters() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cbLoai = new JComboBox<>(new String[]{"tat_ca", "tay ga", "so"});
        cbTrangThai = new JComboBox<>(new String[]{"tat_ca", "san_sang", "dang_thue", "bi_hong"});
        btnLocLoai = new JButton("Lọc theo loại");
        btnLocTrangThai = new JButton("Lọc theo trạng thái");

        filterPanel.add(new JLabel("Loại xe:"));
        filterPanel.add(cbLoai);
        filterPanel.add(btnLocLoai);
        filterPanel.add(Box.createHorizontalStrut(30));
        filterPanel.add(new JLabel("Trạng thái:"));
        filterPanel.add(cbTrangThai);
        filterPanel.add(btnLocTrangThai);

        add(filterPanel, BorderLayout.NORTH);

        btnLocLoai.addActionListener(e -> {
            String loai = cbLoai.getSelectedItem().toString();
            if (loai.equals("tat_ca")) {
                loadData();
            } else {
                loadData(XeMayController.getByLoai(loai));
            }
        });

        btnLocTrangThai.addActionListener(e -> {
            String tt = cbTrangThai.getSelectedItem().toString();
            if (tt.equals("tat_ca")) {
                loadData();
            } else {
                loadData(XeMayController.getByTrangThai(tt));
            }
        });
    }

    private void loadData() {
        loadData(XeMayController.getAll());
    }

    private void loadData(List<XeMay> dsXe) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (XeMay xe : dsXe) {
            String trangThai = switch (xe.getTrangThai()) {
                case "san_sang" -> "Sẵn sàng";
                case "dang_thue" -> "Đang thuê";
                case "bi_hong" -> "Bị hỏng";
                default -> xe.getTrangThai();
            };

            Object[] row = {
                xe.getId(),
                xe.getBienSo(),
                xe.getTenXe(),
                xe.getLoaiXe(),
                trangThai,
                String.format("%,d VND", xe.getGiaThue())
            };
            model.addRow(row);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new XeMayView().setVisible(true));
//    }
}
