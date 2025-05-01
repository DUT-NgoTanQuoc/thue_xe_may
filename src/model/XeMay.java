package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XeMay {
    private int id;
    private String bienSo, tenXe, loaiXe, trangThai;
    private int giaThue;

    public XeMay() {}

    public XeMay(int id, String bienSo, String tenXe, String loaiXe, String trangThai, int giaThue) {
        this.id = id;
        this.bienSo = bienSo;
        this.tenXe = tenXe;
        this.loaiXe = loaiXe;
        this.trangThai = trangThai;
        this.giaThue = giaThue;
    }

    public int getId() { return id; }
    public String getBienSo() { return bienSo; }
    public String getTenXe() { return tenXe; }
    public String getLoaiXe() { return loaiXe; }
    public String getTrangThai() { return trangThai; }
    public int getGiaThue() { return giaThue; }

    public static List<XeMay> getAll() {
        List<XeMay> list = new ArrayList<>();
        String sql = "SELECT * FROM xe_may";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<XeMay> getByLoai(String loaiXe) {
        List<XeMay> list = new ArrayList<>();
        String sql = "SELECT * FROM xe_may WHERE loai_xe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loaiXe);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<XeMay> getByTrangThai(String trangThai) {
        List<XeMay> list = new ArrayList<>();
        String sql = "SELECT * FROM xe_may WHERE trang_thai = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String toString() {
        return bienSo + " - " + tenXe + " (" + loaiXe + ")";
    }
}
