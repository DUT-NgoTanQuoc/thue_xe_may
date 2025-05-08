package model;

import java.sql.Timestamp;

public class HoaDon {
    public int id, xeId, nguoiDungId, khachHangId;
    public Timestamp batDau, ketThuc;
    public long tongTien;

    public HoaDon () {
    	
    }
    public HoaDon(int id,int xeId, int nguoiDungId, int khachHangId, Timestamp batDau, Timestamp ketThuc, long tongTien) {
        this.id=id;
    	this.xeId = xeId;
        this.nguoiDungId = nguoiDungId;
        this.khachHangId = khachHangId;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.tongTien = tongTien;
    }
    public int getXeId() {
        return xeId;
    }
    
    public int getId()
    {
    	return id;
    }
    public int getNguoiDungId() {
        return nguoiDungId;
    }

    public int getKhachHangId() {
        return khachHangId;
    }

    public Timestamp getBatDau() {
        return batDau;
    }

    public Timestamp getKetThuc() {
        return ketThuc;
    }

    

    public long getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }
}
