package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.DoanhThu;

public class DoanhThuController {

    // Thống kê tổng doanh thu và số lượng khách (for the bottom summary)
    public DoanhThu thongKe() {
        DoanhThu doanhThu = new DoanhThu();
        String sql = "SELECT COUNT(*) AS tong_khach, SUM(tong_tien) AS tong_tien FROM hoa_don";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                doanhThu.setSoKhach(rs.getInt("tong_khach"));
                doanhThu.setTongTien(rs.getLong("tong_tien"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doanhThu;
    }

    // Thống kê doanh thu theo ngày trong tháng/năm
    public List<DoanhThu> thongKeTheoNgay(int thang, int nam) {
        List<DoanhThu> danhSachDoanhThu = new ArrayList<>();
        String sql = "SELECT DAY(thoi_gian_bat_dau) AS ngay, COUNT(*) AS tong_khach, SUM(tong_tien) AS tong_tien " +
                     "FROM hoa_don WHERE MONTH(thoi_gian_bat_dau) = ? AND YEAR(thoi_gian_bat_dau) = ? " +
                     "GROUP BY DAY(thoi_gian_bat_dau)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, thang);
            stmt.setInt(2, nam);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DoanhThu doanhThu = new DoanhThu();
                    doanhThu.setNgay(rs.getInt("ngay"));
                    doanhThu.setSoKhach(rs.getInt("tong_khach"));
                    doanhThu.setTongTien(rs.getLong("tong_tien"));
                    danhSachDoanhThu.add(doanhThu);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return danhSachDoanhThu;
    }

    // Thống kê doanh thu theo tháng trong năm
    public List<DoanhThu> thongKeTheoThang(int nam) {
        List<DoanhThu> danhSachDoanhThu = new ArrayList<>();
        String sql = "SELECT MONTH(thoi_gian_bat_dau) AS thang, COUNT(*) AS tong_khach, SUM(tong_tien) AS tong_tien " +
                     "FROM hoa_don WHERE YEAR(thoi_gian_bat_dau) = ? " +
                     "GROUP BY MONTH(thoi_gian_bat_dau)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nam);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DoanhThu doanhThu = new DoanhThu();
                    doanhThu.setThang(rs.getInt("thang"));
                    doanhThu.setSoKhach(rs.getInt("tong_khach"));
                    doanhThu.setTongTien(rs.getLong("tong_tien"));
                    danhSachDoanhThu.add(doanhThu);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return danhSachDoanhThu;
    }

    // Tính số lượng xe "tay ga" và "số" được thuê trong khoảng thời gian chọn
    public double[] tinhTiLeLoaiXe(Date tuNgay, Date denNgay) {
        double[] tiLe = new double[2]; // [0] = xe số, [1] = xe tay ga
        int[] soLuong = new int[2]; // [0] = xe số, [1] = xe tay ga
        int tongSoLuong = 0;

        String sql = "SELECT xm.loai_xe, COUNT(*) AS so_luong " +
                     "FROM hoa_don hd " +
                     "JOIN xe_may xm ON hd.xe_id = xm.id " +
                     "WHERE hd.thoi_gian_bat_dau >= ? AND hd.thoi_gian_ket_thuc <= ? " +
                     "GROUP BY xm.loai_xe";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new java.sql.Timestamp(tuNgay.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(denNgay.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String loaiXe = rs.getString("loai_xe");
                    int soLuongXe = rs.getInt("so_luong");

                    if ("so".equalsIgnoreCase(loaiXe)) {
                        soLuong[0] = soLuongXe;
                    } else if ("tay ga".equalsIgnoreCase(loaiXe)) {
                        soLuong[1] = soLuongXe;
                    }
                    tongSoLuong += soLuongXe;
                }
            }

            // Tính tỉ lệ phần trăm
            if (tongSoLuong > 0) {
                tiLe[0] = (soLuong[0] * 100.0) / tongSoLuong; // xe số
                tiLe[1] = (soLuong[1] * 100.0) / tongSoLuong; // xe tay ga
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tiLe;
    }
}