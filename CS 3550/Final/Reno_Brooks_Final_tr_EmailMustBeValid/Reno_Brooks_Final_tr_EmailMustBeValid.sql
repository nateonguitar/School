USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_EmailMustBeValid') 
DROP TRIGGER tr_EmailMustBeValid

GO

CREATE TRIGGER tr_EmailMustBeValid
ON Customer
INSTEAD OF INSERT

AS
BEGIN
	DECLARE @CustomerID			int
	DECLARE @CustomerFirst		varchar(50)
	DECLARE @CustomerLast		varchar(50)
	DECLARE @CustomerAddress	varchar(50)
	DECLARE @CustomerCity		varchar(50)
	DECLARE @CustomerState		char(2)
	DECLARE @CustomerPostalCode	varchar(10)
	DECLARE @CustomerCountry	varchar(20)
	DECLARE @CustomerPhone		varchar(20)
	DECLARE @CustomerEmail		varchar(200)


	SELECT @CustomerID= i.CustomerID, @CustomerFirst = i.CustomerFirst, @CustomerLast = i.CustomerLast, 
		   @CustomerAddress = i.CustomerAddress, @CustomerCity = i.CustomerCity, 
		   @CustomerState = i.CustomerState, @CustomerPostalCode= i.CustomerPostalCode,
		   @CustomerCountry = i.CustomerCountry, @CustomerPhone = i.CustomerPhone, @CustomerEmail = i.CusomerEmail
	FROM inserted i

	--this is valid
	IF(@CustomerEmail LIKE '%@%' AND @CustomerEmail LIKE '%.%') 
	BEGIN
		INSERT 
		INTO Customer
		VALUES(@CustomerFirst, @CustomerLast, @CustomerAddress, @CustomerCity, @CustomerState, @CustomerPostalCode, @CustomerCountry, 
			   @CustomerPhone, @CustomerEmail)

		RETURN
	END

	--everything else is nonvalid
	RAISERROR(N'
				The email address ''%s'' is not valid. Please try again.
				', 16, 1, @CustomerEmail)
	ROLLBACK
END

GO


--Before the attempted insert
SELECT *
FROM Customer

GO

--Attempt to insert
PRINT ''

INSERT 
INTO Customer
VALUES('Carrie', 'O''brien', '1158 Bald Eagle Ave', 'Liberty', 'GA', '65897', 'USA', '8059874563', 'carriedoesnthaveanemail')

PRINT ''  

GO

PRINT ''

INSERT 
INTO Customer
VALUES('Jarron', 'Wayman', '2258 N 2550 W', 'West Haven', 'UT', '84075', 'USA', '8015447889', 'badtothebone@gmail')

PRINT ''  

GO

PRINT ''

INSERT 
INTO Customer
VALUES('Josephine', 'Marine', '123 West Mariner Ave', 'Integral', 'AL', '85897', 'USA', '5985586358', 'I.Dont.Have.An.Email')

PRINT ''  

GO



--Show nothing's changed
SELECT *
FROM Customer