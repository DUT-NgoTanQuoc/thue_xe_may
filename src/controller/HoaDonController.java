package controller;

import model.*;
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

public class HoaDonController {
    public static List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM hoa_don")) {
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getInt("id"),
                        rs.getInt("xe_id"),
                        rs.getInt("nguoi_dung_id"),
                        rs.getInt("khach_hang_id"),
                        rs.getTimestamp("thoi_gian_bat_dau"),
                        rs.getTimestamp("thoi_gian_ket_thuc"),
                        rs.getLong("tong_tien")
                    );
// gán các field
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static long tinhTongDoanhThu() {
        long tongDoanhThu = 0;
        String sql = "SELECT SUM(tong_tien) AS tong FROM hoa_don";
        
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                tongDoanhThu = rs.getLong("tong");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tính tổng doanh thu: " + e.getMessage());
            e.printStackTrace();
        }
        
        return tongDoanhThu;
    }
    public static boolean taoHoaDon(int xeId, int nguoiDungId, int khachHangId, 
            String batDauStr, String ketThucStr, int soNgay) {
    	if (!kiemTraTrangThaiXe(xeId, "san_sang")) {
            // Nếu xe không sẵn sàng (có thể là "dang_thue" hoặc "hong"), không thể tạo hóa đơn
            JOptionPane.showMessageDialog(null, "Xe không sẵn sàng để thuê.", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    	
    	String sql = "INSERT INTO hoa_don (xe_id, nguoi_dung_id, khach_hang_id, " +
    				"thoi_gian_bat_dau, thoi_gian_ket_thuc, tong_tien) " +
    				"VALUES (?, ?, ?, ?, ?, ?)";

	try (Connection con = DBConnection.getConnection();
	PreparedStatement stmt = con.prepareStatement(sql)) {

// Tính toán tổng tiền dựa trên số ngày và giá thuê xe
		long giaThue = layGiaThueXe(xeId); // Cần thêm hàm này
		long tongTien = giaThue * soNgay;

// Set các tham số cho câu lệnh SQL
		stmt.setInt(1, xeId);
		stmt.setInt(2, nguoiDungId);
		stmt.setInt(3, khachHangId);
		stmt.setString(4, batDauStr);
		stmt.setString(5, ketThucStr);
		stmt.setLong(6, tongTien);

// Thực thi câu lệnh
		int rowsAffected = stmt.executeUpdate();

// Cập nhật trạng thái xe thành "đang thuê"
		if (rowsAffected > 0) {
			capNhatTrangThaiXe(xeId, "dang_thue"); // Cần thêm hàm này
			return true;
		}
	} catch (SQLException e) {
		System.err.println("Lỗi khi tạo hóa đơn: " + e.getMessage());
		e.printStackTrace();
	}
	return false;
    } 
    private static boolean kiemTraTrangThaiXe(int xeId, String trangThai) {
        String sql = "SELECT COUNT(*) FROM xe WHERE xe_id = ? AND trang_thai = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, xeId);
            stmt.setString(2, trangThai);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;  
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trạng thái xe: " + e.getMessage(), 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;  
    }
    public static int timKhachHangIdTheoCCCD(String cccd) {
        String sql = "SELECT id FROM khach_hang WHERE cccd = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cccd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo CCCD: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }


    
    public static boolean deleteHoaDon(int id) {
        String sql = "DELETE FROM hoa_don WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static long layGiaThueXe(int xeId) throws SQLException {
    	String sql = "SELECT gia_thue_theo_ngay FROM xe_may WHERE id = ?";
    	try (Connection con = DBConnection.getConnection();
    			PreparedStatement stmt = con.prepareStatement(sql)) {
    		stmt.setInt(1, xeId);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			return rs.getLong("gia_thue_theo_ngay");
    		}
    	}
    	throw new SQLException("Không tìm thấy xe với ID: " + xeId);
    }
    
    

    private static void capNhatTrangThaiXe(int xeId, String trangThai) throws SQLException {
    	String sql = "UPDATE xe_may SET trang_thai = ? WHERE id = ?";
    	try (Connection con = DBConnection.getConnection();
    			PreparedStatement stmt = con.prepareStatement(sql)) {
    		stmt.setString(1, trangThai);
    		stmt.setInt(2, xeId);
    		stmt.executeUpdate();
    	}
    }

}
