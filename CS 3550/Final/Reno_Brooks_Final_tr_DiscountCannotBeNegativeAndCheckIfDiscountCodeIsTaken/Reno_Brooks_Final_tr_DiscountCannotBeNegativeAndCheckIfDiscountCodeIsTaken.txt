USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_DiscountCannotBeNegativeAndCheckIfDiscountCodeIsTaken')
	DROP TRIGGER tr_DiscountCannotBeNegativeAndCheckIfDiscountCodeIsTaken;

GO

CREATE TRIGGER tr_DiscountCannotBeNegativeAndCheckIfDiscountCodeIsTaken ON Discount
INSTEAD OF INSERT
AS
BEGIN	
	DECLARE @DiscountPercent decimal(2,2) = (SELECT DiscountPercent FROM Inserted);
	DECLARE @DiscountCode char(1) = (SELECT DiscountCode FROM Inserted);
	DECLARE @DiscountDescription varchar(50) = (SELECT DiscountDescription FROM Inserted);
	DECLARE @Problems int = 0;
	
	PRINT @DiscountCode;

	IF @DiscountPercent < 0
	BEGIN
		RAISERROR(N'
		@@@@@@@@
		@@@@@@@@ A discount cannot be negative, this would make their bill greater in the end.
		@@@@@@@@
		', 16, 1);
		ROLLBACK
		RETURN;
	END 

	IF  (	SELECT DiscountCode 
			FROM Discount 
			WHERE DiscountCode = @DiscountCode) = @DiscountCode
	BEGIN
		PRINT 'done'
		RAISERROR(N'
		@@@@@@@@
		@@@@@@@@ The discount code you chose is already taken.  Choose a new letter.
		@@@@@@@@
		', 16, 1);
		ROLLBACK
		RETURN;
	END

	INSERT INTO Discount(DiscountCode, DiscountDescription, DiscountPercent)
	VALUES(@DiscountCode, @DiscountDescription, @DiscountPercent);
	RETURN;
END

GO

-- A isn't taken yet, positive discount under 100%, should insert fine
INSERT INTO Discount(DiscountCode, DiscountDescription, DiscountPercent)
			  VALUES('A', 'Marvel Super Hero Discount', 0.45)

-- 2 is taken, should                 kick back an error
INSERT INTO Discount(DiscountCode, DiscountDescription, DiscountPercent)
			  VALUES('2', 'George Washington Memorial Discount', 0.2)

-- Negative discount, B is not taken, kick back an error
INSERT INTO Discount(DiscountCode, DiscountDescription, DiscountPercent)
			  VALUES('B', 'Marvel Super Hero Discount', -0.1)

DELETE FROM Discount
WHERE DiscountCode = '2';

SELECT * FROM Discount WHERE DiscountCode = 2;

SELECT DiscountCode FROM Discount WHERE DiscountCode = 2