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

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }
    public String getTenXe() { return tenXe; }
    public void setTenXe(String tenXe) { this.tenXe = tenXe; }
    public String getLoaiXe() { return loaiXe; }
    public void setLoaiXe(String loaiXe) { this.loaiXe = loaiXe; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public int getGiaThue() { return giaThue; }
    public void setGiaThue(int giaThue) { this.giaThue = giaThue; }
    
    @Override
    public String toString() {
        return bienSo + " - " + tenXe + " (" + loaiXe + ")";
    }

}
