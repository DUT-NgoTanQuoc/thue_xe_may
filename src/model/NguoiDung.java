package model;
import java.util.Date;

public class NguoiDung {
    private int id;
    private String tenDangNhap, hoTen, vaiTro, sdt;
    private Date ngaySinh;
    
    public NguoiDung () {
    	
    }
    public NguoiDung(int id, String tenDangNhap, String hoTen, String vaiTro, String sdt, Date ngaySinh) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
    }
    public void setId(int id) { this.id = id; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    // Thêm các getter
    public int getId() {
        return id;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public String getVaiTro() {
        return vaiTro;
    }
    
    public String getSdt() {
        return sdt;
    }
    
    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public Date getNgaySinh () {
    	return ngaySinh;
    }
    @Override
    public String toString() {
        return id + " - " + hoTen + " (" + vaiTro + ")";
    }
	
}
