USE SUPRA

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_ShowCustomerInfo')
	DROP PROCEDURE sp_ShowCustomerInfo
GO

--Create sp_ShowCustomerInfo with one input, CustomerID. 
--This will call the two above strored procedures. It shows all of the discounts a customer has,
--Then prints the lot information corresponding to them, and then prints all the amenities and the
--costs for the CarCarePackage. 
CREATE PROCEDURE sp_ShowCustomerInfo
	  @CustomerID   	    int
AS
BEGIN

	EXEC sp_GenerateBill
		@CustomerID = @CustomerID

	PRINT ''
	PRINT ''
	PRINT 'LOT DETAILS:'
	PRINT ''

	DECLARE @LotID tinyint
	DECLARE @LotPrice smallmoney
	DECLARE @LotName varchar(20)

	SET @LotID=
		(SELECT l.LotID
		 FROM Lot l
			JOIN Valet v
			ON v.LotID = l.LotID
				JOIN Customer c
				ON c.CustomerID = v.CustomerID
		 WHERE c.CustomerID = @CustomerID)
		 
	SET @LotPrice=
		(SELECT l.LotPrice
		 FROM Lot l
			JOIN Valet v
			ON v.LotID = l.LotID
				JOIN Customer c
				ON c.CustomerID = v.CustomerID
		 WHERE c.CustomerID = @CustomerID)

	SET @LotName=
		(SELECT l.LotName
		 FROM Lot l
			JOIN Valet v
			ON v.LotID = l.LotID
				JOIN Customer c
				ON c.CustomerID = v.CustomerID
		 WHERE c.CustomerID = @CustomerID)
		 
		 


	--Print them 
	
		PRINT 'LotName: '+ CHAR(9) + CONVERT(varchar, @LotName)
		PRINT 'LotID: ' + CHAR(9) + CONVERT(varchar, @LotID)
		PRINT 'LotPrice: ' + CHAR(9) + '$' + CONVERT(varchar, @LotPrice)
		PRINT ''


	EXEC sp_GetAmenityInfo
		@CustomerID = @CustomerID

END
GO







EXEC
sp_ShowCustomerInfo
	@CustomerID = 3

GO

PRINT ''
PRINT ''
PRINT ''
PRINT ''
PRINT ''

EXEC
sp_ShowCustomerInfo
	@CustomerID = 4

GO