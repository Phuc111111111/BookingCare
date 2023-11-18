use BookingCare
-- Thêm dữ liệu vào bảng clinic
-- Thêm dữ liệu vào bảng clinic
INSERT INTO clinic (address, name) VALUES
(N'123 Đường Chính', N'Bệnh viện Thành phố'),
(N'456 Đường Sồi', N'Trung tâm Y tế Quận Trung ương'),
(N'789 Đường Phong', N'Trung tâm Y tế Hoàng hôn'),
(N'101 Đường Thông', N'Bệnh viện Đỉnh đồi'),
(N'202 Đường Cây Đinh', N'Trung tâm Y tế Thung lũng'),
(N'303 Đường Cây Tùng', N'Trung tâm Y tế Xanh lục'),
(N'404 Đường Cây Bạch', N'Phòng khám Sông hồ'),
(N'505 Đường Cây Ô', N'Bệnh viện Trung ương'),
(N'606 Đường Cây Tuyết', N'Trung tâm Y tế Vịnh biển'),
(N'707 Đường Dứa', N'Trung tâm Y tế Nhiệt đới');

-- Thêm dữ liệu vào bảng doctor
INSERT INTO doctor (clinic_id, specialization_id, user_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10);

-- Thêm dữ liệu vào bảng patient
INSERT INTO patient (date_of_birth, description, gender, name, user_id) VALUES
('1990-01-01', N'Kiểm tra định kỳ', N'Nam', N'John Doe', 1),
('1985-05-15', N'Vấn đề về dị ứng', N'Nữ', N'Jane Smith', 2),
('1980-11-30', N'Kiểm tra sức khỏe hàng năm', N'Nam', N'Robert Johnson', 3),
('1975-09-22', N'Tư vấn lần đầu', N'Nữ', N'Emily Davis', 4),
('1995-03-10', N'Lịch tái khám', N'Nam', N'Michael Wilson', 5),
('1992-07-05', N'Tư vấn phẫu thuật', N'Nữ', N'Sophia White', 6),
('1988-12-18', N'Kiểm tra nha khoa', N'Nam', N'David Brown', 7),
('1979-08-03', N'Tư vấn chỉnh hình', N'Nữ', N'Olivia Miller', 8),
('1983-04-27', N'Kiểm tra mắt', N'Nam', N'Daniel Taylor', 9),
('1998-06-14', N'Kiểm tra nhi khoa', N'Nữ', N'Ava Martin', 10);

-- Thêm dữ liệu vào bảng roles
INSERT INTO roles (id, name) VALUES
(1, N'ROLE_ADMIN'),
(2, N'ROLE_DOCTOR'),
(3, N'ROLE_USER');

-- Thêm dữ liệu vào bảng specialization
INSERT INTO specialization (description, image, name) VALUES
(N'Tâm lý học', 'tam_ly_hoc.jpg', N'Chuyên gia Tâm lý'),
(N'Dermatology', 'da_lien.jpg', N'Bác sĩ Da liễu'),
(N'Nội tiết', 'noi_tiet.jpg', N'Bác sĩ Nội tiết'),
(N'Điều trị tiêu hóa', 'tieu_hoa.jpg', N'Bác sĩ Tiêu hóa'),
(N'Nội thần kinh', 'than_kinh.jpg', N'Bác sĩ Nội thần kinh'),
(N'Chấn thương xương khớp', 'xuong_khop.jpg', N'Bác sĩ Chấn thương Xương khớp'),
(N'Nhi khoa', 'nhi_khoa.jpg', N'Bác sĩ Nhi khoa'),
(N'Tâm thần học', 'tam_than.jpg', N'Bác sĩ Tâm thần học'),
(N'Chụp X quang', 'x_quang.jpg', N'Bác sĩ Chụp X quang'),
(N'Tiểu đường', 'tieu_duong.jpg', N'Bác sĩ Tiểu đường');

-- Thêm dữ liệu vào bảng users
INSERT INTO users (address, avatar, email, enabled, name, password, phone, reset_password_code, username, verification_code) VALUES
(N'123 Đường Chính', 'avatar1.jpg', 'admin@example.com', 1, N'Người quản trị', 'admin_password', '123456789', NULL, 'admin_user', NULL),
(N'456 Đường Sồi', 'avatar2.jpg', 'doctor1@example.com', 1, N'Bác sĩ Một', 'doctor1_password', '987654321', NULL, 'doctor_one', NULL),
(N'789 Đường Phong', 'avatar3.jpg', 'doctor2@example.com', 1, N'Bác sĩ Hai', 'doctor2_password', '555666777', NULL, 'doctor_two', NULL),
(N'101 Đường Thông', 'avatar4.jpg', 'patient1@example.com', 1, N'Bệnh nhân Một', 'patient1_password', '111222333', NULL, 'patient_one', NULL),
(N'202 Đường Cây Đinh', 'avatar5.jpg', 'patient2@example.com', 1, N'Bệnh nhân Hai', 'patient2_password', '999888777', NULL, 'patient_two', NULL),
(N'303 Đường Cây Tùng', 'avatar6.jpg', 'patient3@example.com', 1, N'Bệnh nhân Ba', 'patient3_password', '444555666', NULL, 'patient_three', NULL),
(N'404 Đường Cây Bạch', 'avatar7.jpg', 'patient4@example.com', 1, N'Bệnh nhân Bốn', 'patient4_password', '333222111', NULL, 'patient_four', NULL),
(N'505 Đường Cây Ô', 'avatar8.jpg', 'patient5@example.com', 1, N'Bệnh nhân Năm', 'patient5_password', '777888999', NULL, 'patient_five', NULL),
(N'606 Đường Cây Tuyết', 'avatar9.jpg', 'patient6@example.com', 1, N'Bệnh nhân Sáu', 'patient6_password', '666555444', NULL, 'patient_six', NULL),
(N'707 Đường Dứa', 'avatar10.jpg', 'patient7@example.com', 1, N'Bệnh nhân Bảy', 'patient7_password', '222333444', NULL, 'patient_seven', NULL);

-- Thêm dữ liệu vào bảng user_role
INSERT INTO user_role (role_id, user_id) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10);

-- Thêm dữ liệu vào bảng schedule
INSERT INTO schedule (date, reason, status, time, clinic_id, doctor_id, patient_id, specialization_id) VALUES

('2023-11-24', N'Tư vấn Tim mạch', 1, '03:20 PM', 5, 5, 8, 1),
('2023-11-25', N'Xét nghiệm chụp X quang', 1, '01:00 PM', 6, 6, 9, 9),
('2023-11-26', N'Tư vấn Tâm thần học', 1, '04:45 PM', 7, 7, 10, 8),
('2023-11-28', N'Tư vấn Nội thần kinh', 1, '02:00 PM', 9, 9, 11, 5),
('2023-11-29', N'Tư vấn Điều trị tiêu hóa', 1, '12:30 PM', 10, 10, 3, 4);


