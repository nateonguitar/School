USE SUPRA

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_ShowDiscountsForACustomer')
	DROP PROCEDURE sp_ShowDiscountsForACustomer
GO

--Create sp_ShowDiscountsForACustomer with one input, CustomerID. 
CREATE PROCEDURE sp_ShowDiscountsForACustomer
	  @CustomerID   	    int
AS
BEGIN

	--Variable declaration. 
	DECLARE @ErrMessage			varchar(max)
	DECLARE @GuestName			varchar(200)

	DECLARE @DiscountPercent		decimal(4,2)
	DECLARE @DiscountDescription	varchar(50)

	--Error handling. Make sure that the customer actually exists.
	IF NOT EXISTS
		(
		 SELECT CustomerID 
		 FROM Customer 
		 WHERE CustomerID = @CustomerID
		)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar, @CustomerID) + '" is not a valid Customer. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @CustomerID)
		RETURN -1
	END
	
	
	--Selecting then necessary information to display once.
	SELECT @GuestName = CONCAT(CustomerLast, ', ', CustomerFirst)	
	FROM Customer
	WHERE CustomerID = @CustomerID


	--Displaying the selected one time display information. 
	PRINT '					   	Customer Details'
	PRINT '===================================================================='
	PRINT ''
	PRINT 'Name: ' + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @GuestName)
		PRINT 'CustomerID:' + CHAR(9) + CHAR(9) + CONVERT(varchar, @CustomerID)


	--Printing cost details. 
	PRINT ''
	PRINT ''
	PRINT '							Discount Details'
	PRINT '===================================================================='

	--Error message if necessary
	IF NOT EXISTS(SELECT CustomerID FROM Customer WHERE CustomerID = @CustomerID)
	BEGIN
		PRINT 'No details for this customer.'
		RETURN 
	END

	PRINT ''
	PRINT 'DISCOUNT DETAILS:'
	PRINT ''

	--Declaration of the cursor to select results for Discount Details. 
	DECLARE DiscountCursor CURSOR FOR
	SELECT DiscountDescription, DiscountPercent
	FROM Discount d
		JOIN ValetDiscount vd
		ON d.DiscountCode= vd.DiscountCode
			JOIN Valet v
			ON v.ValetID =vd.ValetID
				JOIN Customer c
				ON c.CustomerID =v.CustomerID
	WHERE c.CustomerID = @CustomerID
	OPEN DiscountCursor

	-- Fetch First Time
	FETCH NEXT FROM DiscountCursor
	INTO @DiscountDescription, @DiscountPercent

	IF(@DiscountDescription IS NULL)
	BEGIN
		PRINT 'No discounts, sorry.'
		CLOSE DiscountCursor
		DEALLOCATE DiscountCursor
		RETURN
	END

	--When the fetching is done from that row of data, 
	WHILE @@FETCH_STATUS = 0


	--Print them 
	BEGIN
		PRINT 'DiscountDescription: '+ CHAR(9) + @DiscountDescription
		PRINT 'DiscountPercent: ' + CHAR(9) + CHAR(9) + CONVERT(varchar, @DiscountPercent * 100) + '%'
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM DiscountCursor
		INTO @DiscountDescription, @DiscountPercent

	END
	
 
	CLOSE DiscountCursor
	DEALLOCATE DiscountCursor --to avoid memory leaks

END 
GO



EXEC sp_ShowDiscountsForACustomer
	@CustomerID = 8
