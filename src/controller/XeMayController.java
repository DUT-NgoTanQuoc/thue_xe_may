package controller;

import model.*;
import java.sql.*;
import java.util.*;

public class XeMayController {
	public static List<XeMay> getAll() {
	    List<XeMay> list = new ArrayList<>();
	    try (Connection con = DBConnection.getConnection();
	         Statement stmt = con.createStatement();
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
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}
