package controller;

import model.DBConnection;
import model.NguoiDung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungController {

    // Lấy danh sách tất cả nhân viên
    public List<NguoiDung> getAllNhanVien() {
        List<NguoiDung> nhanVienList = new ArrayList<>();
        String query = "SELECT * FROM nguoi_dung WHERE vai_tro = 'nhan_vien'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                nhanVienList.add(mapResultSetToNguoiDung(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }

        return nhanVienList;
    }

    // Tìm kiếm nhân viên theo họ tên hoặc số điện thoại
    public List<NguoiDung> searchNhanVien(String keyword) {
        List<NguoiDung> result = new ArrayList<>();
        String query = "SELECT * FROM nguoi_dung WHERE vai_tro = 'nhan_vien' AND (ho_ten LIKE ? OR sdt LIKE ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToNguoiDung(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }

        return result;
    }
    public NguoiDung getNguoiDungById(int id) {
        String query = "SELECT * FROM nguoi_dung WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNguoiDung(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy người dùng theo ID: " + e.getMessage());
        }

        return null;
    }

    // Thêm mới nhân viên
    public boolean insertNhanVien(NguoiDung nd) {
        String query = "INSERT INTO nguoi_dung (ten_dang_nhap, mat_khau, ho_ten, vai_tro, sdt, ngay_sinh) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nd.getTenDangNhap());
            stmt.setString(2, nd.getMatKhau() != null ? nd.getMatKhau() : "123456"); // Mật khẩu mặc định nếu null
            stmt.setString(3, nd.getHoTen());
            stmt.setString(4, nd.getVaiTro());
            stmt.setString(5, nd.getSdt());
            stmt.setDate(6, nd.getNgaySinh() != null ? new java.sql.Date(nd.getNgaySinh().getTime()) : null);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
        }

        return false;
    }

    // Cập nhật thông tin nhân viên
    public boolean updateNhanVien(NguoiDung nd) {
        String query = "UPDATE nguoi_dung SET ten_dang_nhap = ?, mat_khau = COALESCE(?, mat_khau), ho_ten = ?, vai_tro = ?, sdt = ?, ngay_sinh = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nd.getTenDangNhap());
            stmt.setString(2, nd.getMatKhau()); // Nếu matKhau là null, giữ nguyên mật khẩu cũ
            stmt.setString(3, nd.getHoTen());
            stmt.setString(4, nd.getVaiTro());
            stmt.setString(5, nd.getSdt());
            stmt.setDate(6, nd.getNgaySinh() != null ? new java.sql.Date(nd.getNgaySinh().getTime()) : null);
            stmt.setInt(7, nd.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }

        return false;
    }

    // Xóa nhân viên theo ID
    public boolean deleteNhanVien(int id) {
        String query = "DELETE FROM nguoi_dung WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
        }

        return false;
    }

    // Phương thức hỗ trợ: ánh xạ kết quả từ ResultSet sang đối tượng NguoiDung
    private NguoiDung mapResultSetToNguoiDung(ResultSet rs) throws SQLException {
        NguoiDung nd = new NguoiDung();
        nd.setId(rs.getInt("id"));
        nd.setTenDangNhap(rs.getString("ten_dang_nhap"));
        nd.setMatKhau(rs.getString("mat_khau"));
        nd.setHoTen(rs.getString("ho_ten"));
        nd.setVaiTro(rs.getString("vai_tro"));
        nd.setSdt(rs.getString("sdt"));
        Date ngaySinh = rs.getDate("ngay_sinh");
        if (ngaySinh != null) {
            nd.setNgaySinh(ngaySinh);
        }
        return nd;
    }
    public NguoiDung layNguoiDung(String tenDangNhap, String matKhau) {
        String query = "SELECT * FROM nguoi_dung WHERE ten_dang_nhap = ? AND mat_khau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NguoiDung nd = new NguoiDung();
                    nd.setId(rs.getInt("id"));
                    nd.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    nd.setMatKhau(rs.getString("mat_khau"));
                    nd.setHoTen(rs.getString("ho_ten"));
                    nd.setVaiTro(rs.getString("vai_tro"));
                    nd.setSdt(rs.getString("sdt"));
                    nd.setNgaySinh(rs.getDate("ngay_sinh"));
                    return nd;
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy người dùng: " + e.getMessage());
        }

        return null;
    } 
}