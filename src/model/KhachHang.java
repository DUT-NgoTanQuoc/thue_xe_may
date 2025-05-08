package model;

public class KhachHang {
    private int id;
    private String cccd, ten, diaChi, sdt, email;

    public KhachHang(int id, String cccd, String ten, String diaChi, String sdt, String email) {
        this.id = id;
        this.cccd = cccd;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }
    public KhachHang () {
    	
    }
    public void setId(int id) { this.id = id; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    public void setTen(String ten) { this.ten = ten; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setEmail(String email) { this.email = email; }
    
    public int getId() {
        return id;
    }

    public String getCccd() {
        return cccd;
    }

    public String getTen() {
        return ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return ten + " - " + cccd;
    }
}
