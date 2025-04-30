package controller;

import model.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class NguoiDungController {
    public List<NguoiDung> getAll() {
        List<NguoiDung> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM nguoi_dung")) {
            while (rs.next()) {
                NguoiDung nd = new NguoiDung(); // gán các field
                list.add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<NguoiDung> getDanhSachNhanVien() {
        List<NguoiDung> danhSach = new ArrayList<>();
        String sql = "SELECT id, ten_dang_nhap, ho_ten, vai_tro, sdt, ngay_sinh FROM nguoi_dung WHERE vai_tro = 'nhan_vien'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                NguoiDung nv = new NguoiDung();
                nv.setId(rs.getInt("id"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setVaiTro(rs.getString("vai_tro"));
                nv.setSdt(rs.getString("sdt"));
                
                // Xử lý ngày sinh có thể null
                Date ngaySinh = rs.getDate("ngay_sinh");
                if (!rs.wasNull()) {
                    nv.setNgaySinh(ngaySinh);
                }
                
                danhSach.add(nv);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
            // Có thể throw custom exception hoặc trả về danh sách rỗng tùy yêu cầu
        }
        
        return danhSach;
    }
}
