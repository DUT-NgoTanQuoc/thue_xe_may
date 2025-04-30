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

    @Override
    public String toString() {
        return ten + " - " + cccd;
    }
}
