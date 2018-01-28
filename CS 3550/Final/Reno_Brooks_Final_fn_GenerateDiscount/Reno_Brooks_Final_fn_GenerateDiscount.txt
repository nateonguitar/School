USE SUPRA

IF OBJECT_ID (N'dbo.fn_GenerateDiscount', N'FN') IS NOT NULL
DROP FUNCTION dbo.fn_GenerateDiscount;

GO

CREATE FUNCTION dbo.fn_GenerateDiscount
(
	@CustomerID int
)
RETURNS decimal(5,3)
AS
	BEGIN
		DECLARE @CustomerPhone varchar(20) = (SELECT CustomerPhone FROM Customer WHERE CustomerID = @CustomerID);
		DECLARE @ValetTimeIn smalldatetime = (SELECT ValetTimeIn FROM Valet WHERE CustomerID = @CustomerID);
		DECLARE @TotalDiscountPercentage decimal(5,3);
		SET @TotalDiscountPercentage = (SELECT SUM(DiscountPercent)
										FROM Valet v
											JOIN ValetDiscount vd
											ON v.ValetID = vd.ValetID
											JOIN Discount d
											ON vd.DiscountCode = d.DiscountCode
											WHERE CustomerID = @CustomerID)
		IF @TotalDiscountPercentage IS NULL SET @TotalDiscountPercentage = 0;

		-- add a 5% discount for anyone who has an active reservation with TRAMS
		IF (SELECT TOP 1 PersonPhone 
			FROM (SELECT * FROM OPENQUERY(TITAN_TRAMS,   
				'SELECT PersonPhone, ResDate, DATEADD(day, ResNights, ResCheckInDate) AS ResCheckOutDate, ResNights
				 FROM RENO_TRAMS.dbo.Reservation re 
					JOIN RENO_TRAMS.dbo.Person pe
					ON re.PersonID = pe.PersonID') 
			WHERE PersonPhone = @CustomerPhone 
				AND @ValetTimeIn BETWEEN ResDate AND ResCheckOutDate) t1) -- a good one to test 200-524-5397 
		IS NOT NULL
		BEGIN
			SET @TotalDiscountPercentage = @TotalDiscountPercentage + 0.05;
		END;
		
		IF @TotalDiscountPercentage > 1 SET @TotalDiscountPercentage = 1;

		RETURN @TotalDiscountPercentage;
	END
GO

--------------------------
-- TRY IT OUT
--------------------------

SELECT CustomerID,  dbo.fn_GenerateDiscount(CustomerID) AS DiscountPercentage, CustomerFirst + ' ' + CustomerLast AS Name
FROM Customer