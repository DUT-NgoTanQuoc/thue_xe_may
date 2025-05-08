	package controller;
	
	import model.*;
	import java.sql.*;
	import java.util.*;
	
	public class AuthController {
	
	    public static int kiemTraDangNhap(String tenDangNhap, String matKhau) {
	        String sql = "SELECT * FROM nguoi_dung WHERE ten_dang_nhap = ? AND mat_khau = ?";
	        
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	            
	            stmt.setString(1, tenDangNhap);
	            stmt.setString(2, matKhau);
	            
	            try (ResultSet rs = stmt.executeQuery()) {
	            	 if (rs.next()) {
	            		 if (rs.getString("vai_tro").equals("admin"))
	                    	 {
	                    	 	return 1;
	                    	 } 
	                    	 else 
	                    		 {return 2;}
	                 }
	            	 return 0;
	            }
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }
	}
