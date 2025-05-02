CREATE DATABASE  thue_xe_may;
USE thue_xe_may;
-- Bảng người dùng
CREATE TABLE nguoi_dung (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) NOT NULL UNIQUE,
    mat_khau VARCHAR(255) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    vai_tro ENUM('admin', 'nhan_vien') NOT NULL DEFAULT 'nhan_vien',
    sdt VARCHAR(20),
    ngay_sinh DATE
);

-- Bảng khách hàng
CREATE TABLE khach_hang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cccd VARCHAR(20) NOT NULL UNIQUE,
    ten_khach VARCHAR(100) NOT NULL,
    dia_chi VARCHAR(255),
    sdt VARCHAR(20),
    email VARCHAR(100)
);

-- Bảng xe máy

CREATE TABLE xe_may (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bien_so VARCHAR(20) NOT NULL UNIQUE,
    ten_xe VARCHAR(100) NOT NULL,
    loai_xe VARCHAR(50),
    trang_thai ENUM('san_sang', 'dang_thue', 'bi_hong') DEFAULT 'san_sang',
    gia_thue_theo_ngay INT NOT NULL
);

-- Bảng hóa đơn
CREATE TABLE hoa_don (
    id INT AUTO_INCREMENT PRIMARY KEY,
    xe_id INT NOT NULL,
    nguoi_dung_id INT NOT NULL,
    khach_hang_id INT NOT NULL,
    thoi_gian_bat_dau DATETIME NOT NULL,
    thoi_gian_ket_thuc DATETIME NOT NULL,
    tong_tien LONG NOT NULL,
    FOREIGN KEY (xe_id) REFERENCES xe_may(id),
    FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id),
    FOREIGN KEY (khach_hang_id) REFERENCES khach_hang(id)
);
-- Thêm dữ liệu mẫu

-- Người dùng
INSERT INTO nguoi_dung (ten_dang_nhap, mat_khau, ho_ten, vai_tro, sdt, ngay_sinh) VALUES
('admin1', '123', 'Ngo Tan Quoc', 'admin', '0905000001', '1988-01-15'),
('nv01', '456', 'Nguyen Van Nhuan', 'nhan_vien', '0911111111', '1995-04-20'),
('nv02', '456', 'Tran Thi Hoa', 'nhan_vien', '0922222222', '1996-05-25'),
('nv03', '456', 'Le Van Hieu', 'nhan_vien', '0933333333', '1993-07-10'),
('nv04', '456', 'Le Minh Tam', 'nhan_vien', '0905000021', '1988-03-15');

-- Khách hàng
INSERT INTO khach_hang (cccd, ten_khach, dia_chi, sdt, email) VALUES
('012345678901', 'Nguyen Van A', '123 Đường ABC, Quận 1', '0905123456', 'vana@gmail.com'),
('012345678902', 'Tran Thi B', '456 Đường DEF, Quận 3', '0905987654', 'thib@gmail.com'),
('012345678903', 'Le Van C', '789 Đường GHI, Quận 5', '0911222333', 'vanc@gmail.com');

-- Xe máy
INSERT INTO xe_may (bien_so, ten_xe, loai_xe, trang_thai, gia_thue_theo_ngay) VALUES
('43C1-12345', 'Honda SH', 'tay ga', 'san_sang', 200000),
('43C1-67890', 'Yamaha Sirius', 'so', 'dang_thue', 150000),
('43C1-99999', 'Vision 2023', 'tay ga', 'bi_hong', 180000),
('43C1-88888', 'Honda Wave Alpha', 'so', 'san_sang', 120000),
('43C1-77777', 'Lead 125', 'tay ga', 'san_sang', 190000),
('43C1-66666', 'Exciter 150', 'so', 'dang_thue', 220000);
INSERT INTO xe_may (bien_so, ten_xe, loai_xe, trang_thai, gia_thue_theo_ngay) VALUES
('43C1-55555', 'Air Blade 2023', 'tay ga', 'san_sang', 210000),
('43C1-44444', 'Winner X', 'so', 'dang_thue', 230000),
('43C1-33333', 'Janus', 'tay ga', 'san_sang', 170000),
('43C1-22222', 'Wave RSX', 'so', 'san_sang', 130000),
('43C1-11111', 'Grande', 'tay ga', 'bi_hong', 190000),
('43C1-00000', 'Future 125', 'so', 'dang_thue', 160000),
('43C1-23456', 'Vision 2022', 'tay ga', 'san_sang', 180000),
('43C1-34567', 'SH Mode', 'tay ga', 'san_sang', 195000),
('43C1-45678', 'Sirius Fi', 'so', 'bi_hong', 140000),
('43C1-56789', 'Lead 2022', 'tay ga', 'san_sang', 185000);



-- Hóa đơn
INSERT INTO hoa_don (xe_id, nguoi_dung_id, khach_hang_id, thoi_gian_bat_dau, thoi_gian_ket_thuc, tong_tien) VALUES
(1, 1, 1, '2025-04-25 10:00:00', '2025-04-27 10:00:00', 2 * 200000),
(2, 2, 2, '2025-04-24 08:00:00', '2025-04-25 08:00:00', 1 * 150000),
(4, 3, 3, '2025-04-23 13:00:00', '2025-04-24 13:00:00', 1 * 120000),
(5, 2, 1, '2025-04-22 09:00:00', '2025-04-24 09:00:00', 2 * 190000),
(6, 4, 2, '2025-04-21 07:00:00', '2025-04-22 07:00:00', 1 * 220000);
