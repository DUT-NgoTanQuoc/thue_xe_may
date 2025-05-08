package View;

import controller.KhachHangController;
import model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhachHangView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private KhachHangController controller;

    public KhachHangView() {
        controller = new KhachHangController();
        setTitle("Quản lý Khách Hàng");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"ID", "CCCD", "Tên", "Địa chỉ", "SĐT", "Email"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnLoad = new JButton("Load");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnLoad);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
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
        btnLoad.addActionListener(e->
        {
        	loadData();
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<KhachHang> list = controller.getAll();
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{kh.getId(), kh.getCccd(), kh.getTen(), kh.getDiaChi(), kh.getSdt(), kh.getEmail()});
        }
    }

    private void openForm(KhachHang kh) {
        JFrame form = new JFrame(kh == null ? "Thêm Khách Hàng" : "Sửa Khách Hàng");
        form.setSize(400, 300);
        form.setLocationRelativeTo(null);
        form.setLayout(new GridLayout(7, 2));

        JTextField tfCCCD = new JTextField(kh != null ? kh.getCccd() : "");
        JTextField tfTen = new JTextField(kh != null ? kh.getTen() : "");
        JTextField tfDiaChi = new JTextField(kh != null ? kh.getDiaChi() : "");
        JTextField tfSdt = new JTextField(kh != null ? kh.getSdt() : "");
        JTextField tfEmail = new JTextField(kh != null ? kh.getEmail() : "");

        form.add(new JLabel("CCCD:")); form.add(tfCCCD);
        form.add(new JLabel("Tên:")); form.add(tfTen);
        form.add(new JLabel("Địa chỉ:")); form.add(tfDiaChi);
        form.add(new JLabel("SĐT:")); form.add(tfSdt);
        form.add(new JLabel("Email:")); form.add(tfEmail);

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Huỷ");
        form.add(btnSave); form.add(btnCancel);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KhachHangView().setVisible(true));
    }
}
