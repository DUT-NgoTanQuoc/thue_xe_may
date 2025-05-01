package controller;

import model.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class XeMayController {
    private static final Logger logger = Logger.getLogger(XeMayController.class.getName());

    public static List<XeMay> getAll() {
        List<XeMay> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new SQLException("Database connection is null");
            }
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM xe_may")) {
                while (rs.next()) {
                    XeMay xe = new XeMay(
                        rs.getInt("id"),
                        rs.getString("bien_so"),
                        rs.getString("ten_xe"),
                        rs.getString("loai_xe"),
                        rs.getString("trang_thai"),
                        rs.getInt("gia_thue_theo_ngay")
                    );
                    list.add(xe);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error fetching all XeMay: " + e.getMessage());
        }
        return list;
    }

    public static List<XeMay> getByLoai(String loaiXe) {
        if (loaiXe == null || loaiXe.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<XeMay> list = new ArrayList<>();
        String sql = "SELECT * FROM xe_may WHERE loai_xe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loaiXe);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    XeMay xe = new XeMay(
                        rs.getInt("id"),
                        rs.getString("bien_so"),
                        rs.getString("ten_xe"),
                        rs.getString("loai_xe"),
                        rs.getString("trang_thai"),
                        rs.getInt("gia_thue_theo_ngay")
                    );
                    list.add(xe);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error fetching XeMay by loai: " + e.getMessage());
        }
        return list;
    }

    public static List<XeMay> getByTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<XeMay> list = new ArrayList<>();
        String sql = "SELECT * FROM xe_may WHERE trang_thai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    XeMay xe = new XeMay(
                        rs.getInt("id"),
                        rs.getString("bien_so"),
                        rs.getString("ten_xe"),
                        rs.getString("loai_xe"),
                        rs.getString("trang_thai"),
                        rs.getInt("gia_thue_theo_ngay")
                    );
                    list.add(xe);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error fetching XeMay by trang thai: " + e.getMessage());
        }
        return list;
    }

    public static boolean insert(XeMay xe) {
        String sql = "INSERT INTO xe_may(bien_so, ten_xe, loai_xe, trang_thai, gia_thue_theo_ngay) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, xe.getBienSo());
            stmt.setString(2, xe.getTenXe());
            stmt.setString(3, xe.getLoaiXe());
            stmt.setString(4, "san_sang");
            stmt.setInt(5, xe.getGiaThue());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error inserting XeMay: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(XeMay xe) {
        String sql = "UPDATE xe_may SET bien_so=?, ten_xe=?, loai_xe=?, trang_thai=?, gia_thue_theo_ngay=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, xe.getBienSo());
            stmt.setString(2, xe.getTenXe());
            stmt.setString(3, xe.getLoaiXe());
            stmt.setString(4, xe.getTrangThai());
            stmt.setInt(5, xe.getGiaThue());
            stmt.setInt(6, xe.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error updating XeMay: " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM xe_may WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting XeMay: " + e.getMessage());
            return false;
        }
    }
}