package View;

import controller.HoaDonController;
import model.HoaDon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HoaDonMainView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnXoa;

    public HoaDonMainView() {
        setTitle("Quản lý Hóa Đơn");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"ID", "Xe ID", "Nhân viên ID", "Khách hàng ID", "Ngày bắt đầu", "Ngày kết thúc", "Số ngày"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        btnThem = new JButton("Thêm Hóa Đơn");
        btnXoa = new JButton("Xoá Hóa Đơn");

        JPanel panelButton = new JPanel();
        panelButton.add(btnThem);
        panelButton.add(btnXoa);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HoaDonMainView().setVisible(true));
    }
}
