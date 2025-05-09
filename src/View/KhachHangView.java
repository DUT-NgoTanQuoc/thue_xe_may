package View;

import controller.KhachHangController;
import model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class KhachHangView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private KhachHangController controller;

    public KhachHangView() {
        controller = new KhachHangController();
        setTitle("Quản lý Khách Hàng");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        tableModel = new DefaultTableModel(
                new String[]{"ID", "CCCD", "Tên", "Địa chỉ", "SĐT", "Email"}, 0
        );
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnAdd = new JButton(" Thêm");
        JButton btnEdit = new JButton(" Sửa");
        JButton btnDelete = new JButton(" Xoá");
        JButton btnLoad = new JButton(" Load");
        JButton btnExit = new JButton(" Thoát");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        Color primary = new Color(0, 123, 255);
        Color danger = new Color(220, 53, 69);
        Color success = new Color(40, 167, 69);
        Color warning = new Color(255, 193, 7);

        btnAdd.setFont(btnFont); btnAdd.setBackground(primary); btnAdd.setForeground(Color.WHITE);
        btnEdit.setFont(btnFont); btnEdit.setBackground(warning); btnEdit.setForeground(Color.BLACK);
        btnDelete.setFont(btnFont); btnDelete.setBackground(danger); btnDelete.setForeground(Color.WHITE);
        btnLoad.setFont(btnFont); btnLoad.setBackground(success); btnLoad.setForeground(Color.WHITE);
        btnExit.setFont(btnFont); btnExit.setBackground(Color.GRAY); btnExit.setForeground(Color.WHITE);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnExit);
        add(buttonPanel, BorderLayout.SOUTH);

        loadData();

        btnAdd.addActionListener(e -> openForm(null));

        btnEdit.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                KhachHang kh = new KhachHang();
                kh.setId((int) tableModel.getValueAt(selected, 0));
                kh.setCccd((String) tableModel.getValueAt(selected, 1));
                kh.setTen((String) tableModel.getValueAt(selected, 2));
                kh.setDiaChi((String) tableModel.getValueAt(selected, 3));
                kh.setSdt((String) tableModel.getValueAt(selected, 4));
                kh.setEmail((String) tableModel.getValueAt(selected, 5));
                openForm(kh);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
            }
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                int id = (int) tableModel.getValueAt(selected, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteKhachHang(id);
                    loadData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xoá.");
            }
        });

        btnLoad.addActionListener(e -> loadData());

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<KhachHang> list = controller.getAll();
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{
                    kh.getId(),
                    kh.getCccd(),
                    kh.getTen(),
                    kh.getDiaChi(),
                    kh.getSdt(),
                    kh.getEmail()
            });
        }
    }

    private void openForm(KhachHang kh) {
        JFrame form = new JFrame(kh == null ? "Thêm Khách Hàng" : "Sửa Khách Hàng");
        form.setSize(400, 350);
        form.setLocationRelativeTo(null);
        form.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JTextField tfCCCD = new JTextField(kh != null ? kh.getCccd() : "");
        JTextField tfTen = new JTextField(kh != null ? kh.getTen() : "");
        JTextField tfDiaChi = new JTextField(kh != null ? kh.getDiaChi() : "");
        JTextField tfSdt = new JTextField(kh != null ? kh.getSdt() : "");
        JTextField tfEmail = new JTextField(kh != null ? kh.getEmail() : "");

        formPanel.add(new JLabel("CCCD:")); formPanel.add(tfCCCD);
        formPanel.add(new JLabel("Tên:")); formPanel.add(tfTen);
        formPanel.add(new JLabel("Địa chỉ:")); formPanel.add(tfDiaChi);
        formPanel.add(new JLabel("SĐT:")); formPanel.add(tfSdt);
        formPanel.add(new JLabel("Email:")); formPanel.add(tfEmail);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSave = new JButton(" Lưu");
        JButton btnCancel = new JButton(" Huỷ");

        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(220, 53, 69));
        btnCancel.setForeground(Color.WHITE);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        form.add(formPanel, BorderLayout.CENTER);
        form.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            KhachHang khMoi = new KhachHang();
            khMoi.setCccd(tfCCCD.getText());
            khMoi.setTen(tfTen.getText());
            khMoi.setDiaChi(tfDiaChi.getText());
            khMoi.setSdt(tfSdt.getText());
            khMoi.setEmail(tfEmail.getText());

            boolean success;
            if (kh == null) {
                success = controller.addKhachHang(khMoi);
            } else {
                khMoi.setId(kh.getId());
                success = controller.updateKhachHang(khMoi);
            }

            if (success) {
                form.dispose();
                loadData();
            } else {
                JOptionPane.showMessageDialog(form, "Lưu thất bại");
            }
        });

        btnCancel.addActionListener(e -> form.dispose());

        form.setVisible(true);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new KhachHangView().setVisible(true));
    // }
}
