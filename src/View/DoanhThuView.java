package View;

import controller.DoanhThuController;
import model.DoanhThu;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DoanhThuView extends JFrame {
    private JComboBox<String> comboLoai, comboThang, comboNam;
    private JButton btnLoc;
    private JLabel lblDoanhThu, lblKhach, lblXeThue;
    private JPanel panelBieuDo, panelPieChart;
    private DoanhThuController controller;

    public DoanhThuView() {
        controller = new DoanhThuController();
        setTitle("Coffee Home - Thống Kê Doanh Thu");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(40, 44, 52));
        headerPanel.setBounds(0, 0, 1000, 80);
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel lblTitle = new JLabel("DOANH THU THEO THỜI GIAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 20, 400, 40);
        headerPanel.add(lblTitle);

        // Filters
        JLabel lblLoai = new JLabel("Thống kê theo:");
        lblLoai.setBounds(20, 100, 100, 25);
        lblLoai.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblLoai);

        comboLoai = new JComboBox<>(new String[]{"Ngày", "Tháng"});
        comboLoai.setBounds(120, 100, 100, 30);
        comboLoai.setFont(new Font("Arial", Font.PLAIN, 14));
        add(comboLoai);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setBounds(240, 100, 50, 25);
        lblNam.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblNam);

        comboNam = new JComboBox<>(new String[]{"2024", "2025"});
        comboNam.setBounds(290, 100, 100, 30);
        comboNam.setFont(new Font("Arial", Font.PLAIN, 14));
        add(comboNam);

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setBounds(410, 100, 50, 25);
        lblThang.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblThang);

        comboThang = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        comboThang.setBounds(460, 100, 100, 30);
        comboThang.setFont(new Font("Arial", Font.PLAIN, 14));
        comboThang.setEnabled(false);
        add(comboThang);

        btnLoc = new JButton("Lọc");
        btnLoc.setBounds(580, 100, 80, 30);
        btnLoc.setFont(new Font("Arial", Font.BOLD, 14));
        btnLoc.setBackground(new Color(88, 101, 242));
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setFocusPainted(false);
        add(btnLoc);

        // Histogram Panel
        panelBieuDo = new JPanel();
        panelBieuDo.setBounds(20, 150, 600, 350);
        panelBieuDo.setBackground(Color.WHITE);
        panelBieuDo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(panelBieuDo);

        // Pie Chart Panel
        panelPieChart = new JPanel();
        panelPieChart.setBounds(630, 150, 350, 350);
        panelPieChart.setBackground(Color.WHITE);
        panelPieChart.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(panelPieChart);

        // Summary Panel (at the bottom)
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBounds(20, 520, 960, 100);
        summaryPanel.setBackground(new Color(245, 245, 245));
        summaryPanel.setLayout(null);
        add(summaryPanel);

        lblDoanhThu = new JLabel("Tổng doanh thu: 0 (000đ)");
        lblDoanhThu.setBounds(20, 20, 300, 30);
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
        lblDoanhThu.setForeground(new Color(40, 44, 52));
        summaryPanel.add(lblDoanhThu);

        lblKhach = new JLabel("Tổng lượng khách: 0");
        lblKhach.setBounds(20, 50, 300, 30);
        lblKhach.setFont(new Font("Arial", Font.BOLD, 16));
        lblKhach.setForeground(new Color(40, 44, 52));
        summaryPanel.add(lblKhach);

        lblXeThue = new JLabel("Xe đã thuê: 0");
        lblXeThue.setBounds(340, 35, 300, 30);
        lblXeThue.setFont(new Font("Arial", Font.BOLD, 16));
        lblXeThue.setForeground(new Color(40, 44, 52));
        summaryPanel.add(lblXeThue);

        // Initial summary stats
        updateSummary();

        // Event Listeners
        btnLoc.addActionListener(e -> thongKe());

        comboLoai.addActionListener(e -> {
            comboThang.setEnabled(comboLoai.getSelectedItem().equals("Ngày"));
        });
    }

    private void updateSummary() {
        DoanhThu dt = controller.thongKe();
        lblDoanhThu.setText("Tổng doanh thu: " + dt.getTongTien() + " (đ)");
        lblKhach.setText("Tổng lượng khách: " + dt.getSoKhach());
        lblXeThue.setText("Xe đã thuê: 9"); // Hardcoded as per the image
    }

    private void thongKe() {
        String loai = (String) comboLoai.getSelectedItem();
        int nam = Integer.parseInt((String) comboNam.getSelectedItem());

        if ("Ngày".equals(loai)) {
            int thang = Integer.parseInt((String) comboThang.getSelectedItem());
            List<DoanhThu> danhSachDoanhThu = controller.thongKeTheoNgay(thang, nam);
            showHistogram(danhSachDoanhThu, true);
            // Calculate bike type percentages for the selected month
            Date tuNgay = Date.valueOf(nam + "-" + thang + "-01");
            Date denNgay = Date.valueOf(nam + "-" + thang + "-31");
            double[] tiLe = controller.tinhTiLeLoaiXe(tuNgay, denNgay);
            showPieChart(tiLe);
        } else {
            List<DoanhThu> danhSachDoanhThu = controller.thongKeTheoThang(nam);
            showHistogram(danhSachDoanhThu, false);
            // Calculate bike type percentages for the selected year
            Date tuNgay = Date.valueOf(nam + "-01-01");
            Date denNgay = Date.valueOf(nam + "-12-31");
            double[] tiLe = controller.tinhTiLeLoaiXe(tuNgay, denNgay);
            showPieChart(tiLe);
        }
    }

    private void showHistogram(List<DoanhThu> danhSachDoanhThu, boolean isDaily) {
        panelBieuDo.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Initialize all days (1-31) or months (1-12) with 0
        int maxIndex = isDaily ? 31 : 12;
        for (int i = 1; i <= maxIndex; i++) {
            dataset.addValue(0, "Doanh thu", String.valueOf(i));
        }

        // Update with actual data
        for (DoanhThu dt : danhSachDoanhThu) {
            String label = isDaily ? String.valueOf(dt.getNgay()) : String.valueOf(dt.getThang());
            dataset.setValue(dt.getTongTien(), "Doanh thu", label);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                isDaily ? "Doanh thu theo ngày" : "Doanh thu theo tháng",
                isDaily ? "Ngày" : "Tháng",
                "Doanh thu (000đ)",
                dataset
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getCategoryPlot().setBackgroundPaint(new Color(245, 245, 245));
        chart.getCategoryPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);
        chart.getCategoryPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 99, 132));

        // Customize the x-axis to show labels in increments (e.g., 5, 10, 15, ...)
        org.jfree.chart.axis.CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
        int categoryCount = dataset.getColumnCount();
        if (isDaily && categoryCount > 10) { // Adjust threshold as needed
            // Show only every 5th label by setting others to empty
            for (int i = 0; i < categoryCount; i++) {
                int day = Integer.parseInt(dataset.getColumnKey(i).toString());
                if (day % 5 != 0) {
                    domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 0)); // Invisible font size
                    domainAxis.setTickLabelPaint(dataset.getColumnKey(i).toString(), new Color(0, 0, 0, 0)); // Transparent
                } else {
                    domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
                    domainAxis.setTickLabelPaint(dataset.getColumnKey(i).toString(), Color.BLACK);
                }
            }
            domainAxis.setCategoryMargin(0.1); // Reduce overlap
            domainAxis.setLowerMargin(0.01);
            domainAxis.setUpperMargin(0.01);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 350));
        panelBieuDo.add(chartPanel);
        panelBieuDo.validate();
    }

    private void showPieChart(double[] tiLe) {
        panelPieChart.removeAll();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Xe tay ga (" + String.format("%.1f%%", tiLe[1]) + ")", tiLe[1]);
        dataset.setValue("Xe số (" + String.format("%.1f%%", tiLe[0]) + ")", tiLe[0]);

        JFreeChart chart = ChartFactory.createPieChart(
                "TỈ LỆ LOẠI XE",
                dataset,
                true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setSectionPaint("Xe tay ga (" + String.format("%.1f%%", tiLe[1]) + ")", new Color(88, 101, 242));
        plot.setSectionPaint("Xe số (" + String.format("%.1f%%", tiLe[0]) + ")", new Color(255, 159, 64));

        ChartPanel chartPanel = new ChartPanel(chart );
        chartPanel.setPreferredSize(new Dimension(350, 350));
        panelPieChart.add(chartPanel);
        panelPieChart.validate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoanhThuView().setVisible(true));
    }
}