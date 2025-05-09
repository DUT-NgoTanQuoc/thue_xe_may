package model;

import java.util.Date;

public class NguoiDung {
    private int id;
    private String tenDangNhap, hoTen, vaiTro, sdt, matKhau;
    private Date ngaySinh;

    public NguoiDung() {}

    public NguoiDung(int id, String tenDangNhap, String hoTen, String vaiTro, String sdt, Date ngaySinh) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getHoTen() { return hoTen; }
    public String getVaiTro() { return vaiTro; }
    public String getSdt() { return sdt; }
    public Date getNgaySinh() { return ngaySinh; }
    public String getMatKhau() { return matKhau; }

    public void setId(int id) { this.id = id; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    @Override
    public String toString() {
        return id + " - " + hoTen + " (" + vaiTro + ")";
    }
}