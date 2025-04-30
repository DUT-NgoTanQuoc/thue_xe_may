package model;

public class XeMay {
    private int id;
    private String bienSo, tenXe, loaiXe, trangThai;
    private int giaThue;
    
    public XeMay ( ) {
    	
    }
    
    
    public XeMay(int id, String bienSo, String tenXe, String loaiXe, String trangThai, int giaThue) {
        this.id = id;
        this.bienSo = bienSo;
        this.tenXe = tenXe;
        this.loaiXe = loaiXe;
        this.trangThai = trangThai;
        this.giaThue = giaThue;
    }
 // Thêm các getter vào class XeMay
    public int getId() { return id; }
    public String getBienSo() { return bienSo; }
    public String getTenXe() { return tenXe; }
    public String getLoaiXe() { return loaiXe; }
    public String getTrangThai() { return trangThai; }
    public int getGiaThue() { return giaThue; }
    @Override
    public String toString() {
        return bienSo + " - " + tenXe + " (" + loaiXe + ")";
    }
}
