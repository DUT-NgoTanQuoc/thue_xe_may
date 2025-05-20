package View;

import controller.XeMayController;
import model.XeMay;
import model.NguoiDung;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class XeMayListView extends JFrame {
    private JTable table;
    private JComboBox<String> cbLoai, cbTrangThai;
    private JButton btnLocLoai, btnLocTrangThai, btnTaoDon, btnSua ;
    private NguoiDung nguoiDung;
    
    public XeMayListView(NguoiDung nd) {  // Thêm tham số người dùng
        setTitle("Danh sách Xe Máy");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.nguoiDung=nd;
        initComponents(nguoiDung);  // Truyền người dùng vào
        initFilters();
        loadData();
       
    }

    private void initComponents(NguoiDung nguoiDung) {
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

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Chỉ giữ lại nút Refresh và thêm nút Tạo đơn
        JButton btnRefresh = new JButton("Refresh");
        btnTaoDon = new JButton("Tạo đơn");
        btnSua = new JButton("Sửa");
        
        // Thiết lập kích thước cho các nút
        Dimension buttonSize = new Dimension(120, 30);
        btnRefresh.setPreferredSize(buttonSize);
        btnTaoDon.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnTaoDon);
        buttonPanel.add(btnSua);

        // Thêm sự kiện
        btnRefresh.addActionListener(e -> loadData());
        btnSua.addActionListener(e -> suaXe());
        btnTaoDon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int idXe = (int) table.getValueAt(selectedRow, 0);
                String bienSo = (String) table.getValueAt(selectedRow, 1);
                String tenXe = (String) table.getValueAt(selectedRow, 2);
                String loaiXe = (String) table.getValueAt(selectedRow, 3);
                int giaThue = Integer.parseInt(table.getValueAt(selectedRow, 5).toString().replaceAll("[^0-9]", ""));
                
                XeMay xe = new XeMay(idXe, bienSo, tenXe, loaiXe, "", giaThue);
                new HoaDonView(xe, nguoiDung).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn xe để tạo đơn", 
                    "Thông báo", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        // Bố trí layout chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void initFilters() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc"));

        cbLoai = new JComboBox<>(new String[]{"Tất cả", "Tay ga", "Số"});
        cbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Sẵn sàng", "Đang thuê", "Bị hỏng"});
        btnLocLoai = new JButton("Lọc theo loại");
        btnLocTrangThai = new JButton("Lọc theo trạng thái");

        // Thiết lập kích thước cho các combobox và nút lọc
        Dimension filterSize = new Dimension(150, 25);
        cbLoai.setPreferredSize(filterSize);
        cbTrangThai.setPreferredSize(filterSize);
        btnLocLoai.setPreferredSize(new Dimension(150, 30));
        btnLocTrangThai.setPreferredSize(new Dimension(170, 30));

        filterPanel.add(new JLabel("Loại xe:"));
        filterPanel.add(cbLoai);
        filterPanel.add(btnLocLoai);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(new JLabel("Trạng thái:"));
        filterPanel.add(cbTrangThai);
        filterPanel.add(btnLocTrangThai);

        add(filterPanel, BorderLayout.NORTH);

        btnLocLoai.addActionListener(e -> {
            String loai = cbLoai.getSelectedItem().toString();
            if (loai.equals("Tất cả")) {
                loadData();
            } else {
                loadData(XeMayController.getByLoai(loai.equals("Tay ga") ? "tay ga" : "so"));
            }
        });

        btnLocTrangThai.addActionListener(e -> {
            String tt = cbTrangThai.getSelectedItem().toString();
            if (tt.equals("Tất cả")) {
                loadData();
            } else {
                String trangThai = "";
                switch (tt) {
                    case "Sẵn sàng": trangThai = "san_sang"; break;
                    case "Đang thuê": trangThai = "dang_thue"; break;
                    case "Bị hỏng": trangThai = "bi_hong"; break;
                }
                loadData(XeMayController.getByTrangThai(trangThai));
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
    private XeMay nhapThongTinXe(XeMay xeCu) {
        JTextField tfBienSo = new JTextField();
        JTextField tfTenXe = new JTextField();
        JComboBox<String> cbLoai = new JComboBox<>(new String[]{"tay ga", "so"});
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"san_sang", "dang_thue", "bi_hong"});
        JTextField tfGia = new JTextField();

        if (xeCu != null) {
            tfBienSo.setText(xeCu.getBienSo());
            tfTenXe.setText(xeCu.getTenXe());
            cbLoai.setSelectedItem(xeCu.getLoaiXe());
            cbTrangThai.setSelectedItem(xeCu.getTrangThai());
            tfGia.setText(String.valueOf(xeCu.getGiaThue()));
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Biển số:"));
        panel.add(tfBienSo);
        panel.add(new JLabel("Tên xe:"));
        panel.add(tfTenXe);
        panel.add(new JLabel("Loại xe:"));
        panel.add(cbLoai);
        panel.add(new JLabel("Trạng thái:"));
        panel.add(cbTrangThai);
        panel.add(new JLabel("Giá thuê/ngày (VND):"));
        panel.add(tfGia);

        int result = JOptionPane.showConfirmDialog(this, panel, 
                xeCu != null ? "Cập nhật thông tin xe" : "Thêm xe mới", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int gia = Integer.parseInt(tfGia.getText());
                return new XeMay(
                        xeCu != null ? xeCu.getId() : 0,
                        tfBienSo.getText(),
                        tfTenXe.getText(),
                        cbLoai.getSelectedItem().toString(),
                        cbTrangThai.getSelectedItem().toString(),
                        gia
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá thuê phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    private void suaXe() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) table.getValueAt(row, 0);
            XeMay xeCu = XeMayController.getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
            if (xeCu != null) {
                XeMay xeMoi = nhapThongTinXe(xeCu);
                if (xeMoi != null && XeMayController.update(xeMoi)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật xe thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe để sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
   // public static void main(String[] args) {
   //     SwingUtilities.invokeLater(() -> {
   //         // Tạo người dùng mẫu (trong thực tế sẽ lấy từ hệ thống đăng nhập)
   //         NguoiDung nguoiDung = new NguoiDung(1, "admin", "Quản lý", "admin", "0123456789", null);
    //        XeMayListView view = new XeMayListView(nguoiDung);
    //        view.setVisible(true);
    //    });
    //}
}