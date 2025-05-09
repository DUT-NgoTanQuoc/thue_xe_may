package View;

import controller.NguoiDungController;
import model.NguoiDung;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NhanVienView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private NguoiDungController controller;

    public NhanVienView() {
        controller = new NguoiDungController();

        setTitle("Quản lý nhân viên");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255)); // Màu nền nhẹ

        // Top panel: Search + buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(240, 248, 255));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton btnSearch = createStyledButton("Tìm", new Color(70, 130, 180));
        JButton btnAdd = createStyledButton("Thêm", new Color(34, 139, 34));
        JButton btnEdit = createStyledButton("Sửa", new Color(255, 165, 0));
        JButton btnDelete = createStyledButton("Xóa", new Color(220, 20, 60));

        topPanel.add(new JLabel("Tìm kiếm:"));
        topPanel.add(searchField);
        topPanel.add(btnSearch);
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên đăng nhập", "Họ tên", "SĐT", "Ngày sinh"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setGridColor(new Color(200, 200, 200));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);

        // Tùy chỉnh tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));

        // Bottom panel: Refresh + Exit buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(new Color(240, 248, 255));
        JButton btnRefresh = createStyledButton("Refresh", new Color(30, 144, 255));
        JButton btnExit = createStyledButton("Thoát", new Color(128, 128, 128));
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnExit);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();

        // Search event
        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            List<NguoiDung> list = controller.searchNhanVien(keyword);
            loadTable(list);
        });

        // Add event
        btnAdd.addActionListener(e -> {
            NguoiDung nd = showNhanVienForm(null);
            if (nd != null) {
                nd.setVaiTro("nhan_vien");
                boolean success = controller.insertNhanVien(nd);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Edit event
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String tenDangNhap = tableModel.getValueAt(selectedRow, 1).toString();
                String hoTen = tableModel.getValueAt(selectedRow, 2).toString();
                String sdt = tableModel.getValueAt(selectedRow, 3).toString();
                String ngaySinhStr = tableModel.getValueAt(selectedRow, 4).toString();
                Date ngaySinh = ngaySinhStr.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);

                NguoiDung nd = new NguoiDung(id, tenDangNhap, hoTen, "nhan_vien", sdt, ngaySinh);
                NguoiDung updated = showNhanVienForm(nd);
                if (updated != null) {
                    updated.setId(id);
                    updated.setVaiTro("nhan_vien");
                    boolean success = controller.updateNhanVien(updated);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete event
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String hoTen = tableModel.getValueAt(selectedRow, 2).toString();
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa nhân viên '" + hoTen + "'?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = controller.deleteNhanVien(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Refresh event
        btnRefresh.addActionListener(e -> {
            searchField.setText(""); // Xóa ô tìm kiếm
            loadData();
            JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Exit event
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn thoát?", 
                "Xác nhận thoát", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Double-click to edit
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnEdit.doClick();
                }
            }
        });

        setVisible(true);
    }

    private void loadData() {
        List<NguoiDung> list = controller.getAllNhanVien();
        loadTable(list);
    }

    private void loadTable(List<NguoiDung> list) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (NguoiDung nd : list) {
            tableModel.addRow(new Object[]{
                nd.getId(),
                nd.getTenDangNhap(),
                nd.getHoTen(),
                nd.getSdt(),
                nd.getNgaySinh() != null ? sdf.format(nd.getNgaySinh()) : ""
            });
        }
    }

    private NguoiDung showNhanVienForm(NguoiDung nd) {
        JTextField tfTenDangNhap = new JTextField(20);
        JTextField tfHoTen = new JTextField(20);
        JTextField tfSdt = new JTextField(20);
        JTextField tfNgaySinh = new JTextField("yyyy-MM-dd", 20);
        JPasswordField tfMatKhau = new JPasswordField(20);

        // Cải thiện giao diện cho các trường nhập liệu
        tfTenDangNhap.setFont(new Font("Arial", Font.PLAIN, 14));
        tfHoTen.setFont(new Font("Arial", Font.PLAIN, 14));
        tfSdt.setFont(new Font("Arial", Font.PLAIN, 14));
        tfNgaySinh.setFont(new Font("Arial", Font.PLAIN, 14));
        tfMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));

        // Nếu đang sửa, điền thông tin hiện tại
        if (nd != null) {
            tfTenDangNhap.setText(nd.getTenDangNhap());
            tfHoTen.setText(nd.getHoTen());
            tfSdt.setText(nd.getSdt());
            tfNgaySinh.setText(nd.getNgaySinh() != null ? new SimpleDateFormat("yyyy-MM-dd").format(nd.getNgaySinh()) : "");
            tfMatKhau.setText(""); // Không hiển thị mật khẩu hiện tại
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            nd == null ? "Thêm nhân viên" : "Sửa nhân viên",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        panel.setBackground(new Color(245, 245, 245));

        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        JLabel lblHoTen = new JLabel("Họ tên:");
        JLabel lblSdt = new JLabel("Số điện thoại:");
        JLabel lblNgaySinh = new JLabel("Ngày sinh (yyyy-MM-dd):");

        lblTenDangNhap.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHoTen.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSdt.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNgaySinh.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblTenDangNhap);
        panel.add(tfTenDangNhap);
        panel.add(lblMatKhau);
        panel.add(tfMatKhau);
        panel.add(lblHoTen);
        panel.add(tfHoTen);
        panel.add(lblSdt);
        panel.add(tfSdt);
        panel.add(lblNgaySinh);
        panel.add(tfNgaySinh);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, panel, 
                nd == null ? "Thêm nhân viên" : "Sửa nhân viên", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            // Kiểm tra dữ liệu đầu vào
            String tenDangNhap = tfTenDangNhap.getText().trim();
            String matKhau = new String(tfMatKhau.getPassword()).trim();
            String hoTen = tfHoTen.getText().trim();
            String sdt = tfSdt.getText().trim();
            String ngaySinhStr = tfNgaySinh.getText().trim();

            if (tenDangNhap.isEmpty() || hoTen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập và họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (nd == null && matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống khi thêm mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (!sdt.isEmpty() && !sdt.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (10-11 số)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                Date ngaySinh = null;
                if (!ngaySinhStr.isEmpty() && !ngaySinhStr.equals("yyyy-MM-dd")) {
                    ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
                }

                NguoiDung resultNd = new NguoiDung();
                resultNd.setTenDangNhap(tenDangNhap);
                resultNd.setHoTen(hoTen);
                resultNd.setSdt(sdt.isEmpty() ? null : sdt);
                resultNd.setNgaySinh(ngaySinh);
                resultNd.setVaiTro("nhan_vien");
                if (!matKhau.isEmpty()) {
                    resultNd.setMatKhau(matKhau); // Chỉ đặt mật khẩu nếu người dùng nhập
                }

                return resultNd;
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (định dạng: yyyy-MM-dd)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NhanVienView::new);
    }
}