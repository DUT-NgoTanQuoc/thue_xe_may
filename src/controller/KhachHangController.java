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
