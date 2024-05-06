CREATE DATABASE QLySanPham
Go
USE QLySanPham
Go
CREATE TABLE HoaDon(
	MaHoaDon int IDENTITY(1,1) NOT NULL,
	MaKhachHang int NOT NULL,
	MaNhanVien int NOT NULL,
	NgayLapHoaDon date NOT NULL CONSTRAINT [DF_HoaDon_NgayLapHoaDon]  DEFAULT (getdate()),
	TongTien money NOT NULL,
	GhiChu nvarchar(255) NULL CONSTRAINT [DF_HoaDon_GhiChu]  DEFAULT (''),
    CONSTRAINT PK_HoaDon PRIMARY KEY (MaHoaDon ASC) 
);
GO

CREATE TABLE ChiTietHoaDon(
	MaCTHD int IDENTITY(1,1) NOT NULL,
	MaHoaDon int NOT NULL,
	MaSanPham int NOT NULL,
	SoLuong int NOT NULL,
	TongTien money NOT NULL,
	GhiCh nvarchar(255) NULL,
 CONSTRAINT [PK_ChiTietHoaDon] PRIMARY KEY (MaCTHD ASC)
);
GO
CREATE TABLE KhachHang (
    MaKhachHang int IDENTITY(1,1) NOT NULL,
    TenKhachHang nvarchar(50) NOT NULL,
    NgaySinh date NOT NULL,
    GioiTinh bit NOT NULL,
    DiaChi nvarchar(50) NOT NULL,
    SDT varchar(11) NOT NULL,
    LoaiKhachHang int NOT NULL,
    GhiChu nchar(10) NULL,
    CONSTRAINT PK_KhachHang PRIMARY KEY (MaKhachHang ASC)
);

Go
CREATE TABLE LoaiKhachHang (
    MaLoaiKhachHang int IDENTITY(1,1) NOT NULL,
    TenLoaiKhachHang nvarchar(50) NOT NULL,
	GhiChu nvarchar(255) NULL
	CONSTRAINT PK_LoaiKhachHang PRIMARY KEY (MaLoaiKhachHang ASC)
);
Go
CREATE TABLE LoaiSanPham (
    MaLoaiSanPham int IDENTITY(1,1) NOT NULL,
    TenLoaiSanPham nvarchar(50) NOT NULL,
	CONSTRAINT PK_LoaiSanPham PRIMARY KEY (MaLoaiSanPham ASC)
);
Go
CREATE TABLE NhanVien (
    MaNhanVien int IDENTITY(1,1) NOT NULL,
    TenNhanVien nvarchar(50) NOT NULL,
    NgaySinh date NOT NULL,
    GioiTinh bit NOT NULL,
    NgayVaoLam date NOT NULL,
    ChucVu int NOT NULL,
    DiaChi nvarchar(50) NOT NULL,
    SoDT varchar(11) NOT NULL,
    GhiChu nvarchar(255) NULL,
    CONSTRAINT PK_NhanVien PRIMARY KEY CLUSTERED (MaNhanVien ASC),
)

GO
CREATE TABLE HangSanXuat (
    MaHangSanXuat int IDENTITY(1,1) NOT NULL ,
    TenHangSanXuat nvarchar(100) NOT NULL
	CONSTRAINT PK_HangSanXuat PRIMARY KEY (MaHangSanXuat ASC)
);
Go
CREATE TABLE SanPham (
    MaSanPham int IDENTITY(1,1) NOT NULL, 
    TenSanPham nvarchar(50) NOT NULL,
    MaLoaiSanPham int NOT NULL,
    MaHangSanXuat int NOT NULL,
    GiaNhap money NOT NULL,
    GiaBan money NOT NULL,
    TonKho int NOT NULL,
    ChuThich nvarchar(255) NULL CONSTRAINT DF_SanPham_ChuThich DEFAULT (''),
	CONSTRAINT PK_SanPham PRIMARY KEY (MaSanPham ASC),
);
Go
CREATE TABLE NhaPhanPhoi (
    MaNhaPhanPhoi int IDENTITY(1,1) NOT NULL,
    TenNhaPhanPhoi nvarchar(50) NOT NULL,
    DiaChi nvarchar(50) NOT NULL,
    SDT varchar(11) NOT NULL,
    Email varchar(100) NOT NULL,
    ChuThich text NULL,
    CONSTRAINT PK_NhaPhanPhoi PRIMARY KEY  (MaNhaPhanPhoi ASC)
);
Go
CREATE TABLE PhieuNhap (
    MaPhieuNhap int IDENTITY(1,1) NOT NULL,
    MaNhanVien int NOT NULL,
    MaNhaPhanPhoi int NOT NULL,
    TongTien money NOT NULL,
    NgayNhap date NOT NULL,
    ChuThich nvarchar(255) NULL CONSTRAINT DF_PhieuNhap_ChuThich DEFAULT (''),
    CONSTRAINT PK_PhieuNhap PRIMARY KEY  (MaPhieuNhap ASC)
);
Go

CREATE TABLE ChucVu (
    MaChucVu int IDENTITY(1,1) NOT NULL,
    TenChucVu nvarchar(50) NOT NULL,
    GhiChu nvarchar(255) NULL CONSTRAINT DF_ChucVu_GhiChu DEFAULT (''),
    CONSTRAINT PK_ChucVu PRIMARY KEY (MaChucVu ASC)
);
Go
CREATE TABLE Quyen (
    MaQuyen int IDENTITY(1,1) NOT NULL,
    TenQuyen nvarchar(50) NOT NULL,
    ChuThich nvarchar(255) NULL CONSTRAINT DF_Quyen_ChuThich DEFAULT (''),
    CONSTRAINT PK_Quyen PRIMARY KEY CLUSTERED (MaQuyen ASC)
);
Go
CREATE TABLE Users (
    ID int IDENTITY(1,1) NOT NULL,
    MaNhanVien int NOT NULL,
    TenDangNhap varchar(50) NOT NULL,
    Password varchar(50) NOT NULL,
    Quyen int NOT NULL,
    ChuThich nvarchar(255) NULL,
    CONSTRAINT PK_TaiKhoan PRIMARY KEY (ID ASC)
);


SET IDENTITY_INSERT QLySanPham.dbo.KhachHang OFF;
SET IDENTITY_INSERT [dbo].[LoaiKhachHang] on;
INSERT dbo.LoaiKhachHang (MaLoaiKhachHang, TenLoaiKhachHang, GhiChu) 
VALUES (1, N'vip', N'giảm 10%');
INSERT dbo.LoaiKhachHang (MaLoaiKhachHang, TenLoaiKhachHang, GhiChu) 
VALUES (2, N'thường', N'giảm 0%');
INSERT dbo.LoaiKhachHang (MaLoaiKhachHang, TenLoaiKhachHang, GhiChu) 
VALUES (3, N'sinh viên', N'giảm 5%');
SET IDENTITY_INSERT LoaiKhachHang off;

SET IDENTITY_INSERT KhachHang ON 
INSERT KhachHang (MaKhachHang, TenKhachHang, Ngaysinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu) 
VALUES (3, N'Nguyễn Thanh ', CAST(N'1996-03-02' AS Date), 0, N'Thái Bình', N'093283283', 1, N'');
INSERT dbo.KhachHang (MaKhachHang, TenKhachHang, Ngaysinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu) 
VALUES (7, N'Nguyễn Đình Giáp', CAST(N'2004-09-02' AS Date), 1, N'Đà Sơn', N'0847294254', 3, N'');
INSERT dbo.KhachHang (MaKhachHang, TenKhachHang, Ngaysinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu) 
VALUES (12, N'đức cường', CAST(N'1980-04-10' AS Date), 0, N'Hà Nội', N'09746281', 3, N'mới thêm');
INSERT dbo.KhachHang (MaKhachHang, TenKhachHang, Ngaysinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu) 
VALUES (14, N'Trần Ngọc Hà', CAST(N'2004-04-09' AS Date), 0, N'Hà Nội', N'0987342', 2, N'');
SET IDENTITY_INSERT KhachHang OFF;

SET IDENTITY_INSERT QLySanPham.dbo.NhanVien OFF;
SET IDENTITY_INSERT dbo.ChucVu ON;
INSERT dbo.ChucVu (MaChucVu, TenChucVu, GhiChu) 
VALUES (1, N'Giám Đốc', N''),
       (2, N'Thu Ngân', N''),
       (3, N'Kế Toán', N''),
       (4, N'Bảo Vệ', N''),
       (5, N'Lễ Tân', N''),  
       (7, N'Quản Lý ', N''),
       (8, N'Lao Công', N'')
SET IDENTITY_INSERT dbo.ChucVu OFF;

SET IDENTITY_INSERT dbo.NhanVien ON;
INSERT NhanVien (MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, NgayVaoLam, ChucVu, DiaChi, SoDT, GhiChu) 
VALUES (1, N'Phạm Thế Huy', CAST(N'2005-12-25' AS Date), 1, CAST(N'2024-01-01' AS Date), 1, N'51- 102 Nguyễn Tất Thành', N'01293223225', N''),
       (3, N'Phạm Văn Toàn', CAST(N'2004-01-02' AS Date), 0, CAST(N'2024-03-05' AS Date), 2, N'chưa có', N'01293223225', N'chưa có'),
       (6, N'Nguyễn Trúc Nhân', CAST(N'1999-06-10' AS Date), 1, CAST(N'2024-12-03' AS Date), 5, N'Hà Nội ', N'091425635', N''),
       (8, N'Phạm Băng', CAST(N'1896-03-02' AS Date), 0, CAST(N'2024-05-04' AS Date), 5, N'việt nam', N'01652343643', N''),
       (10, N'Nguyễn Thanh Tùng', CAST(N'1994-07-05' AS Date), 1, CAST(N'2024-09-07' AS Date), 1, N'Thái Bình', N'09090909090', N'')
SET IDENTITY_INSERT dbo.NhanVien off;

SET IDENTITY_INSERT [dbo].[HoaDon] ON;
INSERT [dbo].[HoaDon] ([MaHoaDon], [MaKhachHang], [MaNhanVien], [NgayLapHoaDon], [TongTien], [GhiChu]) VALUES 
(7, 3, 10, CAST(N'2024-03-03' AS Date), 30790000.0000, N''),
(13, 7, 6, CAST(N'2024-02-03' AS Date), 4790000.0000, N'huy'),
(17, 12, 1, CAST(N'2024-01-03' AS Date), 1300000.0000, N''),
(20, 14, 1, CAST(N'2024-02-06' AS Date), 15200000.0000, N''),
(1021, 3, 1, CAST(N'2024-02-07' AS Date), 52400000.0000, N''),
(1022, 7, 8, CAST(N'2024-02-03' AS Date), 2760000.0000, N''),
(1023, 12, 3, CAST(N'2024-02-02' AS Date), 7890000.0000, N''),
(1024, 3, 3, CAST(N'2024-02-05' AS Date), 7890000.0000, N'');
SET IDENTITY_INSERT [dbo].[HoaDon] off;


SET IDENTITY_INSERT [dbo].[LoaiSanPham] ON;
INSERT [dbo].[LoaiSanPham] ([MaLoaiSanPham], [TenLoaiSanPham]) VALUES 
(1, N'Điện thoại phổ thông'),
(2, N'Smartphone'),
(3, N'Tablet'),
(4, N'SmartWatch'),
(5, N'Phụ Kiện'),
(9, N'Linh Kiện');
SET IDENTITY_INSERT [dbo].[LoaiSanPham] OFF;

SET IDENTITY_INSERT [dbo].[HangSanXuat] ON;
INSERT [dbo].[HangSanXuat] ([MaHangSanXuat], [TenHangSanXuat]) VALUES 
(1, N'SamSung'),
(2, N'LG'),
(3, N'HTC'),
(4, N'Sonny'),
(5, N'HuaWei'),
(6, N'Xiaomi'),
(7, N'Lenovo'),
(8, N'Bkav'),
(9, N'Gionne');
SET IDENTITY_INSERT [dbo].[HangSanXuat] OFF;

SET IDENTITY_INSERT [dbo].[SanPham] ON;
INSERT [dbo].[SanPham] ([MaSanPham], [TenSanPham], MaLoaiSanPham, MaHangSanXuat, [GiaNhap], [GiaBan], [TonKho], [ChuThich]) VALUES 
(1, N'sony m4 aqua', 1, 4, 7390000.0000, 7890000.0000, 34, N''),
(3, N'htc 10', 2, 3, 1800000.0000, 1890000.0000, 100, N''),
(4, N'Samsung s7 edge', 2, 1, 1300000.0000, 1300000.0000, 10, N''),
(5, N'iphone 6', 3, 5, 20000000.0000, 21000000.0000, 100, N''),
(6, N'Samsung Galaxy A5', 2, 1, 700000.0000, 7490000.0000, 100, N''),
(7, N'Samsung s7 edge', 2, 1, 1500000.0000, 1800000.0000, 10, N''),
(8, N'iphone 6 plus', 9, 6, 150000.0000,200000.0000,100,N'')
SET IDENTITY_INSERT [dbo].[SanPham] OFF;

SET IDENTITY_INSERT [dbo].[ChiTietHoaDon] ON;
INSERT [dbo].[ChiTietHoaDon] ([MaCTHD], [MaHoaDon], [MaSanPham], [SoLuong], [TongTien], [GhiChu]) VALUES 
(11, 7, 5, 1, 21000000.0000, N''),
(15, 13, 3, 1, 1890000.0000, N''),
(19, 17, 4, 1, 1300000.0000, N''),
(20, 13, 5, 1, 1100000.0000, N''),
(1020, 1021, 1, 3, 3900000.0000, N''),
(1021, 1021, 1, 5, 6500000.0000, N''),
(1022, 7, 6, 1, 7490000.0000, N''),
(1023, 7, 8, 1, 1200000.0000, N''),
(1024, 7, 3, 1, 1100000.0000, N''),
(1025, 20, 3, 1, 1200000.0000, N''),
(1026, 20, 3, 1, 14000000.0000, N'')
SET IDENTITY_INSERT [dbo].[ChiTietHoaDon] OFF;

SET IDENTITY_INSERT [dbo].[NhaPhanPhoi] ON 
INSERT [dbo].[NhaPhanPhoi] ([MaNhaPhanPhoi], [TenNhaPhanPhoi], [DiaChi], [SDT], [Email], [ChuThich]) 
VALUES 
    (1, N'FPT Trading', N'Hồ Chí Minh', N'098674322', N'motcucgach7796@gmail.com', N''),
    (2, N'docomo', N'Trung Quốc ', N'34657865434', N'dcomo197@gmail.com', N''),
    (8, N'FPTShop', N'Việt Nam', N'34657865434', N'dcomo197@gmail.com', N''),
    (10, N'ZTE', N'Quảng đông Trung quốc', N'01293223225', N'ZTEhuy@gmail.com', N''),
    (11, N'FPTShop', N'Việt Nam', N'34657865434', N'dcomo197@gmail.com', N''),
    (12, N'FPTShop', N'Việt Nam', N'34657865434', N'dcomo197@gmail.com', N'')
SET IDENTITY_INSERT [dbo].[NhaPhanPhoi] OFF

SET IDENTITY_INSERT [dbo].[PhieuNhap] ON 
INSERT [dbo].[PhieuNhap] ([MaPhieuNhap], [MaNhanVien], [MaNhaPhanPhoi], [TongTien], [NgayNhap], [ChuThich]) 
VALUES 
    (1, 1, 2, 100000.0000, CAST(N'2024-04-03' AS Date), N''),
    (2, 3, 2, 100000.0000, CAST(N'2024-01-01' AS Date), N''),
    (3, 6, 2, 100000.0000, CAST(N'2024-02-01' AS Date), N''),
    (7, 8, 2, 100000.0000, CAST(N'2024-04-03' AS Date), N''),
    (10, 10, 2, 0.0000, CAST(N'2024-03-02' AS Date), N''),
    (11, 3, 2, 0.0000, CAST(N'2024-02-02' AS Date), N'')
SET IDENTITY_INSERT [dbo].[PhieuNhap] OFF

SET IDENTITY_INSERT [dbo].[Quyen] ON 
INSERT [dbo].[Quyen] ([MaQuyen], [TenQuyen], [ChuThich]) 
VALUES 
    (1, N'Quản Trị Viên', N''),
    (2, N'Kế Toán', N''),
    (3, N'Thu Ngân', N''),
    (4, N'Bảo Vệ', N''),
    (5, N'New', N''),
    (6, N'Giám Đốc', N'')
SET IDENTITY_INSERT [dbo].[Quyen] OFF

SET IDENTITY_INSERT [dbo].[Users] ON 
INSERT [dbo].[Users] ([ID], [MaNhanVien], [TenDangNhap], [Password], [Quyen], [ChuThich]) 
VALUES 
    (1, 1, N'admin', N'admin', 1, N'Người có quyền cao nhất'),
    (2, 3, N'giap04', N'giap04', 2, N'giáp kute'),
    (7, 6, N'phuc04', N'phuc04', 5, N'phúc xấu gái')
SET IDENTITY_INSERT [dbo].[Users] OFF



