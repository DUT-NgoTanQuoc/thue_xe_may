package View;

import controller.HoaDonController;
import model.HoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class HoaDonMainView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnXoa, btnReload, btnTimKiem, btnThoat;
    private JTextField txtSearch, txtNgayBatDau;
    private JComboBox<String> cbSearchType;

    public HoaDonMainView() {
        setTitle("Quản lý Hóa Đơn");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Xe ID", "Nhân viên ID", "Khách hàng ID", "Ngày bắt đầu", "Ngày kết thúc", "Số Tiền"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(220, 220, 220));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 0, 10));

        btnThem = new JButton(" Thêm Hóa Đơn");
        btnXoa = new JButton(" Xoá Hóa Đơn");
        btnReload = new JButton(" Tải lại");
        btnTimKiem = new JButton(" Tìm kiếm");
        btnThoat = new JButton(" Thoát");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        btnThem.setFont(buttonFont);
        btnXoa.setFont(buttonFont);
        btnReload.setFont(buttonFont);
        btnTimKiem.setFont(buttonFont);
        btnThoat.setFont(buttonFont);

        btnThem.setBackground(new Color(0, 123, 255));
        btnThem.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 53, 69));
        btnXoa.setForeground(Color.WHITE);
        btnReload.setBackground(new Color(40, 167, 69));
        btnReload.setForeground(Color.WHITE);
        btnTimKiem.setBackground(new Color(255, 193, 7));
        btnTimKiem.setForeground(Color.BLACK);
        btnThoat.setBackground(new Color(108, 117, 125));
        btnThoat.setForeground(Color.WHITE);

        cbSearchType = new JComboBox<>(new String[]{"All", "ID Hóa đơn", "ID Khách hàng", "ID Xe"});
        txtSearch = new JTextField(12);
        txtNgayBatDau = new JTextField(10);

        JPanel panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSearch.add(new JLabel("Tìm theo:"));
        panelSearch.add(cbSearchType);
        panelSearch.add(txtSearch);
        panelSearch.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"));
        panelSearch.add(txtNgayBatDau);
        panelSearch.add(btnTimKiem);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelButton.setBorder(new EmptyBorder(0, 10, 10, 10));
        panelButton.add(btnThem);
        panelButton.add(btnXoa);
        panelButton.add(btnReload);
        panelButton.add(btnThoat);

        add(panelSearch, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        loadData();

        btnThem.addActionListener(e -> new HoaDonView().setVisible(true));

        btnXoa.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                int id = (int) tableModel.getValueAt(selected, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = HoaDonController.deleteHoaDon(id);
                    if (success) {
                        loadData();
                        JOptionPane.showMessageDialog(this, "Xoá thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xoá thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xoá.");
            }
        });

        btnReload.addActionListener(e -> loadData());

        btnTimKiem.addActionListener(e -> timKiem());

        btnThoat.addActionListener(e -> dispose());
    }

    private void timKiem() {
        String kieu = (String) cbSearchType.getSelectedItem();
        String tuKhoa = txtSearch.getText().trim().toLowerCase();
        String ngayNhap = txtNgayBatDau.getText().trim();

        LocalDate ngayBatDau = null;
        if (!ngayNhap.isEmpty()) {
            try {
                ngayBatDau = LocalDate.parse(ngayNhap);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Định dạng phải là yyyy-MM-dd");
                return;
            }
        }

        tableModel.setRowCount(0);
        List<HoaDon> danhSach = HoaDonController.getAll();

        for (HoaDon hd : danhSach) {
            boolean match = true;

            if (!kieu.equals("All") && !tuKhoa.isEmpty()) {
                switch (kieu) {
                    case "ID Hóa đơn":
                        match &= String.valueOf(hd.getId()).contains(tuKhoa);
                        break;
                    case "ID Khách hàng":
                        match &= String.valueOf(hd.getKhachHangId()).contains(tuKhoa);
                        break;
                    case "ID Xe":
                        match &= String.valueOf(hd.getXeId()).contains(tuKhoa);
                        break;
                }
            }

            if (ngayBatDau != null) {
                LocalDate batDau = hd.getBatDau().toLocalDateTime().toLocalDate();
                match &= batDau.equals(ngayBatDau);
            }

            if (match) {
                tableModel.addRow(new Object[]{
                        hd.getId(),
                        hd.getXeId(),
                        hd.getNguoiDungId(),
                        hd.getKhachHangId(),
                        hd.getBatDau(),
                        hd.getKetThuc(),
                        hd.getTongTien()
                });
            }
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<HoaDon> danhSach = HoaDonController.getAll();
        for (HoaDon hd : danhSach) {
            tableModel.addRow(new Object[]{
                    hd.getId(),
                    hd.getXeId(),
                    hd.getNguoiDungId(),
                    hd.getKhachHangId(),
                    hd.getBatDau(),
                    hd.getKetThuc(),
                    hd.getTongTien()
            });
        }
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new HoaDonMainView().setVisible(true));
    // }
}
