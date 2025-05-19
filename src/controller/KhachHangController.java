package controller;

import model.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class KhachHangController {
	public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM khach_hang")) {
            while (rs.next()) {
            	KhachHang kh = new KhachHang();
            	kh.setId(rs.getInt("id"));
                kh.setCccd(rs.getString("cccd"));
                kh.setTen(rs.getString("ten_khach"));
                kh.setDiaChi(rs.getString("dia_chi"));
                kh.setSdt(rs.getString("sdt"));
                kh.setEmail(rs.getString("email"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	public static KhachHang getKhachHangById(int id) {
	    String sql = "SELECT * FROM khach_hang WHERE id = ?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            KhachHang kh = new KhachHang();
	            kh.setId(rs.getInt("id"));
	            kh.setCccd(rs.getString("cccd"));
	            kh.setTen(rs.getString("ten_khach"));
	            kh.setDiaChi(rs.getString("dia_chi"));
	            kh.setSdt(rs.getString("sdt"));
	            kh.setEmail(rs.getString("email"));
	            return kh;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean addKhachHang(KhachHang kh) {
	    String sql = "INSERT INTO khach_hang (cccd, ten_khach, dia_chi, sdt, email) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setString(1, kh.getCccd());
	        stmt.setString(2, kh.getTen());
	        stmt.setString(3, kh.getDiaChi());
	        stmt.setString(4, kh.getSdt());
	        stmt.setString(5, kh.getEmail());
	        
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean deleteKhachHang(int id) {
	    String sql = "DELETE FROM khach_hang WHERE id = ?";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setInt(1, id);
	        int rowsAffected = stmt.executeUpdate();
	        
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public KhachHang timKhachHangTheoCCCD(String cccd) {
	    String sql = "SELECT * FROM khach_hang WHERE cccd = ?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, cccd);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            KhachHang kh = new KhachHang();
	            kh.setId(rs.getInt("id"));
	            kh.setCccd(rs.getString("cccd"));
	            kh.setTen(rs.getString("ten_khach"));
	            kh.setDiaChi(rs.getString("dia_chi"));
	            kh.setSdt(rs.getString("sdt"));
	            kh.setEmail(rs.getString("email"));
	            return kh;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public static int timKhachHangIdTheoCCCD(String cccd) {
	    String sql = "SELECT id FROM khach_hang WHERE cccd = ?";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setString(1, cccd.trim()); // Trim để loại bỏ khoảng trắng thừa
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id"); // Trả về ID nếu tìm thấy
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Lỗi khi tìm ID khách hàng theo CCCD: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return -1; // Trả về -1 nếu không tìm thấy hoặc có lỗi
	}
	public boolean updateKhachHang(KhachHang kh) {
	    String sql = "UPDATE khach_hang SET cccd = ?, ten = ?, dia_chi = ?, sdt = ?, email = ? WHERE id = ?";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        stmt.setString(1, kh.getCccd());
	        stmt.setString(2, kh.getTen());
	        stmt.setString(3, kh.getDiaChi());
	        stmt.setString(4, kh.getSdt());
	        stmt.setString(5, kh.getEmail());
	        stmt.setInt(6, kh.getId()); 
	        
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}



}
