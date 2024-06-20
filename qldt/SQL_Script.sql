create database if not exists Project_OOP;

use Project_OOP;

drop database project_oop;

-- Bảng Student
CREATE TABLE IF NOT EXISTS Student (
    StudentID VARCHAR(50) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Address VARCHAR(100),
    PhoneNumber VARCHAR(15),
    Email VARCHAR(50),
    Gender ENUM('Male', 'Female', 'Other') NOT NULL
);

-- Bảng Lecturers
CREATE TABLE IF NOT EXISTS Lecturer (
    LecturerID VARCHAR(50) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(15),
    Email VARCHAR(50),
    Gender ENUM('Male', 'Female', 'Other') NOT NULL
);

INSERT INTO lecturer
	(LecturerID, FirstName, LastName, PhoneNumber, Email, Gender)
VALUES
	("GV-001", "Trần Đăng", "Hoan", "0945823857", "trandanghoan@phenikaa-uni.edu.vn", "Male"),
    ("GV-002", "Trịnh", "Thành", "0945823873", "trinhthanh@phenikaa-uni.edu.vn", "Male");

-- Bảng Course
CREATE TABLE IF NOT EXISTS Course (
    CourseID VARCHAR(50) PRIMARY KEY,
    NameCourse VARCHAR(100) NOT NULL,
    TuitionFee DECIMAL(10, 2) NOT NULL,
	Credits INT NOT NULL
);

INSERT INTO course (CourseID, NameCourse, TuitionFee, Credits)
VALUES
	("C-001", "Đại số tuyến tính", 2100000, 3),
    ("C-002", "Giải tích", 2100000, 3),
    ("C-003", "Vật lý 1", 2100000, 3),
    ("C-004", "Ngôn ngữ lập trình C", 2450000, 3),
    ("C-005", "Pháp luật đại cương", 1400000, 2),
    ("C-006", "Toán rời rạc", 2100000, 3),
    ("C-007", "Lập trình cho trí tuệ nhân tạo", 2450000, 3),
    ("C-008", "Cấu trúc dữ liệu và giải thuật", 2450000, 3),
    ("C-009", "Kiến trúc máy tính", 2450000, 3),
    ("C-010", "Cơ sở dữ liệu", 2450000, 3),
    ("C-011", "Mạng máy tính", 1400000, 2),
    ("C-012", "Khai phá dữ liệu", 1400000, 2),
    ("C-013", "Hệ điều hành", 1400000, 2),
    ("C-014", "Nhập môn trí tuệ nhân tạo", 2450000, 3),
    ("C-015", "Phương pháp số học cho học máy", 2100000, 3),
    ("C-016", "Lập trình hướng đối tượng", 2450000, 3);

-- Bảng ClassSection
CREATE TABLE IF NOT EXISTS ClassSection (
    ClassSectionID VARCHAR(50) PRIMARY KEY,
    ClassSectionName VARCHAR(100) NOT NULL,
    CourseID VARCHAR(50),
    LecturerID VARCHAR(50),
    Enrolled INT,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (LecturerID) REFERENCES Lecturer(LecturerID)
);

INSERT INTO classsection (ClassSectionID, CourseID, LecturerID, ClassSectionName)
VALUES
	("CS-016.N01", "C-016", "GV-001", "Lập trình hướng đối tượng (N01)"),
    ("CS-016.N01.TH01", "C-016", "GV-001", "Lập trình hướng đối tượng (N01.TH01)"),
    ("CS-016.N01.TH02", "C-016", "GV-001", "Lập trình hướng đối tượng (N01.TH02)"),
    ("CS-016.N02", "C-016", "GV-001", "Lập trình hướng đối tượng (N02)"),
    ("CS-016.N02.TH01", "C-016", "GV-001", "Lập trình hướng đối tượng (N02.TH01)"),
    ("CS-016.N02.TH02", "C-016", "GV-001", "Lập trình hướng đối tượng (N02.TH02)");

-- Bảng StudentClassSection để lưu danh sách sinh viên đăng ký vào mỗi lớp học phần
CREATE TABLE IF NOT EXISTS StudentClassSection (
    StudentID VARCHAR(50),
    ClassSectionID VARCHAR(50),
    PRIMARY KEY (StudentID, ClassSectionID),
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (ClassSectionID) REFERENCES ClassSection(ClassSectionID)
);

-- Bảng StudentCourseProgress để lưu quá trình học của sinh viên
CREATE TABLE IF NOT EXISTS StudentCourseProgress (
    ProgressID INT PRIMARY KEY,
    StudentID VARCHAR(50),
    CourseID VARCHAR(50),
    Status ENUM('InProgress', 'Completed', 'Failed') DEFAULT 'InProgress',
    FinalGrade DECIMAL(4, 2),
    times int default 1,
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

-- Tạo bảng Account để lưu thông tin tài khoản
CREATE TABLE IF NOT EXISTS Account (
    AccountID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    UserType ENUM('Student', 'Lecturer') NOT NULL,
    StudentID VARCHAR(50),
    LecturerID VARCHAR(50),
    CONSTRAINT fk_student FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_lecturer FOREIGN KEY (LecturerID) REFERENCES Lecturer(LecturerID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `account` (`Username`, `Password`, `UserType`, `LecturerID`) 
VALUES 
	('danghoan', '123', 'Lecturer', 'GV-001'),
	('trinhthanh', '123', 'Lecturer', 'GV-002');

-- Bảng Session để lưu thông tin phiên đăng nhập hiện tại
CREATE TABLE IF NOT EXISTS Session (
    AccountID INT primary key,
	UserName VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    UserType ENUM('Student', 'Lecturer') NOT NULL,
    StudentID VARCHAR(50),
    LecturerID VARCHAR(50)
);

-- Bảng Classroom để lưu thông tin về các phòng học
CREATE TABLE IF NOT EXISTS Classroom (
    ClassroomID VARCHAR(50) PRIMARY KEY,
    RoomNumber VARCHAR(50) NOT NULL,
    Building VARCHAR(100) NOT NULL
);

-- Bảng Schedule
CREATE TABLE IF NOT EXISTS Schedule (
    ScheduleID INT AUTO_INCREMENT PRIMARY KEY,
    ClassSectionID VARCHAR(50),
    ClassroomID VARCHAR(50),
    StartTime TIME NOT NULL,
    EndTime TIME NOT NULL,
    DayOfWeek ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
    FOREIGN KEY (ClassSectionID) REFERENCES ClassSection(ClassSectionID),
    FOREIGN KEY (ClassroomID) REFERENCES Classroom(ClassroomID)
);

-- Bảng Payment
CREATE TABLE IF NOT EXISTS Payment (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID VARCHAR(50),
    CourseID VARCHAR(50),
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PaymentStatus ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

-- Bảng Grade
CREATE TABLE IF NOT EXISTS Grade (
    GradeID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID VARCHAR(50),
    ClassSectionID VARCHAR(50),
    Grade_CC DECIMAL(4, 2),
    Grade_Midterm DECIMAL(4, 2),
    Grade_Endterm DECIMAL(4, 2),
    GradeDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (ClassSectionID) REFERENCES ClassSection(ClassSectionID),
    CONSTRAINT uc_student_course_grade UNIQUE (StudentID, ClassSectionID)
);

-- Bảng Email để lưu trữ thông tin email
CREATE TABLE IF NOT EXISTS Email (
    EmailID INT AUTO_INCREMENT PRIMARY KEY,
    SenderID INT,
    ReceiverID INT,
    ClassSectionID VARCHAR(50),
    Tiltle VARCHAR(255) NOT NULL,
    Body TEXT NOT NULL,
    FOREIGN KEY (SenderID) REFERENCES Account(AccountID),
    FOREIGN KEY (ReceiverID) REFERENCES Account(AccountID),
    FOREIGN KEY (ClassSectionID) REFERENCES ClassSection(ClassSectionID)
);