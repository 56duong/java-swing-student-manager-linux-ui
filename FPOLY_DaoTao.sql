
CREATE DATABASE FPOLY_DaoTao;
USE FPOLY_DaoTao;



CREATE TABLE Student (
	StudentID NVARCHAR(5) NOT NULL,
	StudentName NVARCHAR(50) NOT NULL,
	Email NVARCHAR(89),
	PhoneNumber CHAR(10),
	Sex CHAR(1) NOT NULL, -- 1: Nam, 2: Nu
	Address NVARCHAR(50),
	CONSTRAINT PK_Student
        PRIMARY KEY (StudentID)
);

CREATE TABLE Test (
	TestID NVARCHAR(5) NOT NULL,
	TestName NVARCHAR(30),
	TestDate DATE,
	CONSTRAINT PK_Test
        PRIMARY KEY (TestID)
);

CREATE TABLE Grade (
	TestID NVARCHAR(5) NOT NULL,
	StudentID NVARCHAR(5) NOT NULL,
	TAGrade FLOAT,
	TinHocGrade FLOAT,
	GDTCGrade FLOAT,
	CONSTRAINT CHK_TAGrade
		CHECK (TAGrade BETWEEN 0 AND 10),
	CONSTRAINT CHK_TinHocGrade
		CHECK (TinHocGrade BETWEEN 0 AND 10),
	CONSTRAINT CHK_GDTCGrade
		CHECK (GDTCGrade BETWEEN 0 AND 10),
	CONSTRAINT PK_Grade
        PRIMARY KEY (TestID, StudentID),
	CONSTRAINT FK_Grade_Test
        FOREIGN KEY (TestID)
        REFERENCES Test(TestID)
		ON DELETE CASCADE,
	CONSTRAINT FK_Grade_Student
        FOREIGN KEY (StudentID)
        REFERENCES Student(StudentID)
);

CREATE TABLE [User] (
	UserID NVARCHAR(5) NOT NULL,
	UserName NVARCHAR(50) NOT NULL,
	Password NVARCHAR(50) NOT NULL,
	Role CHAR(1) NOT NULL -- 1: Giang vien, 2: Can bo dao tao, 3: admin
	CONSTRAINT PK_User
        PRIMARY KEY (UserID)
);



INSERT INTO [User] VALUES
	('US001', 'giangvien1', '123456', '1'),
    ('US002', 'giangvien2', '123456', '1'),
    ('US003', 'canbodt1', '123456', '2'),
    ('US004', 'canbodt2', '123456', '2'),
    ('US005', 'canbodt3', '123456', '2'),
    ('US006', 'admin', 'admin', '3');

INSERT INTO Student VALUES 
    ('ST001', N'Nguyễn Văn An', 'annv@fpt.edu.vn', '0987654321', '1', N'12 Lý Thường Kiệt, Quận 1, TP.HCM'),
    ('ST002', N'Nguyễn Thị Bình', 'binhnt@fpt.edu.vn', '0912345678', '2', N'42 Trần Hưng Đạo, Quận 2, TP.HCM'),
    ('ST003', N'Đỗ Văn Dương', 'duongdv@fpt.edu.vn', '0918765432', '1', N'36 Lê Lợi, Quận 3, TP.HCM'),
    ('ST004', N'Lê Thị Dung', 'dunglt@fpt.edu.vn', '0965432187', '2', N'90 Bà Triệu, Quận 5, TP.HCM'),
    ('ST005', N'Phạm Văn Cường', 'cuongpv@fpt.edu.vn', '0987123456', '1', N'68 Nguyễn Huệ, Quận 1, TP.HCM'),
	('ST006', N'Nguyễn Thị Huệ', 'huent@fpt.edu.vn', '0932109876', '2', N'23 Trần Phú, Đà Nẵng'),
    ('ST007', N'Lê Văn Huy', 'huylv@fpt.edu.vn', '0912233445', '1', N'6 Hoàng Diệu, Huế'),
    ('ST008', N'Phan Thị Mai', 'maipt@fpt.edu.vn', '0987654321', '2', N'78 Tô Hiến Thành, Quận 10, TP.HCM'),
    ('ST009', N'Đặng Văn Minh', 'minhdv@fpt.edu.vn', '0976543210', '1', N'2 Nguyễn Thị Minh Khai, Quận 1, TP.HCM'),
    ('ST010', N'Nguyễn Đình Nghĩa', 'nghiantd@fpt.edu.vn', '0945678910', '1', N'5 Lý Tự Trọng, Quận 1, TP.HCM'),
    ('ST011', N'Trần Thị Oanh', 'oanhtt@fpt.edu.vn', '0965432198', '2', N'14 Võ Thị Sáu, Quận 3, TP.HCM'),
    ('ST012', N'Phùng Văn Phương', 'phuongpv@fpt.edu.vn', '0934567890', '1', N'30 Nguyễn Trãi, Quận 5, TP.HCM'),
    ('ST013', N'Ngô Thị Quỳnh', 'quynhnt@fpt.edu.vn', '0912345678', '2', N'8 Nguyễn Du, Huế'),
	('ST014', N'Lê Văn Sơn', 'sonlv@fpt.edu.vn', '0978654321', '1', N'15 Phan Chu Trinh, Quận 1, TP.HCM'),
	('ST015', N'Nguyễn Thị Tâm', 'tamnt@fpt.edu.vn', '0943216789', '2', N'20 Lê Duẩn, Quận 1, TP.HCM'),
	('ST016', N'Đỗ Văn Thắng', 'thangdv@fpt.edu.vn', '0981234567', '1', N'50 Cách Mạng Tháng Tám, Quận 1, TP.HCM'),
	('ST017', N'Lê Thị Thu', 'thult@fpt.edu.vn', '0965432109', '2', N'25 Hùng Vương, Đà Nẵng'),
	('ST018', N'Phan Thị Trang', 'trangpt@fpt.edu.vn', '0932109876', '2', N'90 Lý Thường Kiệt, Quận 1, TP.HCM'),
	('ST019', N'Võ Văn Tuấn', 'tuavn@fpt.edu.vn', '0912233445', '1', N'12 Nguyễn Huệ, Huế'),
	('ST020', N'Nguyễn Thị Uyên', 'uyennt@fpt.edu.vn', '0987654321', '2', N'8 Lê Lai, Quận 5, TP.HCM');

INSERT INTO Test VALUES
	('TE001', N'Test 1', NULL),
	('TE002', N'Test 2', NULL),
	('TE003', N'Test 3', NULL);

INSERT INTO Grade (TestID, StudentID, TAGrade, TinHocGrade, GDTCGrade) VALUES 
	('TE001', 'ST001', 9.5, 8.2, 7.8),
	('TE002', 'ST001', 6.4, 4.5, 2.1),
	('TE001', 'ST002', 8.7, 7.5, 9.2),
	('TE002', 'ST002', 7.8, 8.9, 6.5),
	('TE001', 'ST003', 9.1, 9.5, 8.8),
	('TE002', 'ST003', 4.7, 5.6, 7.8),
	('TE001', 'ST004', 8.3, 7.6, 9.9),
	('TE002', 'ST004', 7.9, 8.4, 6.1),
	('TE001', 'ST005', 10, 9.9, 9.8),
	('TE002', 'ST005', 9.5, 8.2, 7.8),
	('TE001', 'ST006', 6.4, 4.5, 2.1),
	('TE002', 'ST006', 7.8, 8.9, 6.5),
	('TE001', 'ST007', 9.1, 9.5, 8.8),
	('TE002', 'ST007', 8.3, 7.6, 9.9),
	('TE001', 'ST008', 4.7, 5.6, 7.8),
	('TE002', 'ST008', 7.9, 8.4, 6.1),
	('TE001', 'ST009', 5.5, 4.2, 3.8),
	('TE002', 'ST009', 8.7, 7.5, 9.2),
	('TE001', 'ST010', 3.4, 5.5, 2.1),
	('TE002', 'ST010', 7.8, 8.9, 6.5),
	('TE001', 'ST011', 9.5, 8.2, 7.8),
	('TE002', 'ST011', 6.4, 4.5, 2.1),
	('TE001', 'ST012', 8.7, 7.5, 9.2),
	('TE002', 'ST012', 7.8, 8.9, 6.5),
	('TE001', 'ST013', 9.1, 9.5, 8.8),
	('TE002', 'ST013', 4.7, 5.6, 7.8),
	('TE001', 'ST014', 8.3, 7.6, 9.9),
	('TE002', 'ST014', 7.9, 8.4, 6.1),
	('TE001', 'ST015', 10, 9.9, 9.8),
	('TE002', 'ST015', 9.5, 8.2, 7.8),
	('TE001', 'ST016', 7, 7.9, 5.8),
	('TE002', 'ST016', 8.5, 9.2, 7.5),
	('TE001', 'ST017', 6, 9.8, 8.8),
	('TE002', 'ST017', 9.1, 8.9, 7.6),
	('TE001', 'ST018', 10, 8.9, 9.6),
	('TE002', 'ST018', 8.5, 8.9, 7.9),
	('TE001', 'ST019', 6, 4.9, 6.8),
	('TE002', 'ST019', 9.1, 7.2, 8.8),
	('TE001', 'ST020', 4, 3.9, 4.8),
	('TE002', 'ST020', 9.5, 9.2, 9.8);



--insert trong bang Test thi se tu dong insert vao bang Grade (cho nhung sinh vien hien tai)
GO
CREATE OR ALTER TRIGGER TR_Test_I
ON Test
INSTEAD OF INSERT
AS
BEGIN
	--inserted into Test table
	INSERT INTO Test SELECT * FROM inserted;

	--insert into Grade table
	DECLARE @temp TABLE(ID INT IDENTITY(1,1), StudentID NVARCHAR(5));

	INSERT INTO @temp
		SELECT StudentID
		FROM Student;

	DECLARE @min INT, @max INT;

	SELECT @min = MIN(ID), @max = MAX(ID)
	FROM @temp;

	WHILE(@min <= @max) 
	BEGIN
		DECLARE @studentID NVARCHAR(5) = (SELECT StudentID FROM @temp WHERE ID = @min);
		INSERT INTO Grade VALUES((SELECT TestID FROM inserted), @studentID, NULL, NULL, NULL);
		
		SET @min = @min + 1;
	END
END

INSERT INTO Test VALUES
	('TE003', N'Test 3', NULL);



--delete trong Test thi delete trong Grade
GO
CREATE OR ALTER TRIGGER TR_Test_IOD
ON Test
INSTEAD OF DELETE
AS
BEGIN
	DELETE FROM Grade WHERE TestID IN (SELECT TestID FROM deleted);
	DELETE FROM Test WHERE TestID IN (SELECT TestID FROM deleted);
END



--delete trong Student thi delete trong Grade
GO
CREATE OR ALTER TRIGGER TR_Student_IOD
ON Student
INSTEAD OF DELETE
AS
BEGIN
	DELETE FROM Grade WHERE StudentID IN (SELECT StudentID FROM deleted);
	DELETE FROM Student WHERE StudentID IN (SELECT StudentID FROM deleted);
END

