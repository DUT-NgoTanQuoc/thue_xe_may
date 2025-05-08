package model;

public class DoanhThu {
    private long tongDoanhThu;
    private int soLuongKhach;
    private double tiLeXeGa;
    private double tiLeXeSo;
    private int soKhach;
    private long tongTien;
    private int ngay;  // For daily stats
    private int thang; // For monthly stats

    public DoanhThu(long tongDoanhThu, int soLuongKhach, double tiLeXeGa, double tiLeXeSo) {
        this.tongDoanhThu = tongDoanhThu;
        this.soLuongKhach = soLuongKhach;
        this.tiLeXeGa = tiLeXeGa;
        this.tiLeXeSo = tiLeXeSo;
    }

    public DoanhThu() {
    }

    public long getTongDoanhThu() { return tongDoanhThu; }
    public int getSoLuongKhach() { return soLuongKhach; }
    public double getTiLeXeGa() { return tiLeXeGa; }
    public double getTiLeXeSo() { return tiLeXeSo; }
    public int getSoKhach() { return soKhach; }
    public void setSoKhach(int soKhach) { this.soKhach = soKhach; }
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
    public int getNgay() { return ngay; }
    public void setNgay(int ngay) { this.ngay = ngay; }
    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }
}