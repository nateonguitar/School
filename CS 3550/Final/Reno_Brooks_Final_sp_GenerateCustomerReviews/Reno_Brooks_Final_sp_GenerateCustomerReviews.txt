USE SUPRA


-- check to see if sp_GenerateCustomerReviews exists and drop it if it does
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_GenerateCustomerReviews')
	DROP PROCEDURE sp_GenerateCustomerReviews
GO

--Create sp_GenerateCustomerReviews with one input, Flag.
--This will generate all of the L or V or X flags 
CREATE PROCEDURE sp_GenerateCustomerReviews
	@Flag	char(1)

AS
BEGIN

	--Variable declaration. 
	DECLARE @ErrMessage			varchar(max)
	DECLARE @GuestName			varchar(200)
	DECLARE @ReviewRating		varchar(5)		 
	DECLARE @ReviewText			varchar(max)


	--Error handling. Make sure that the folio actually exists.
	IF NOT EXISTS
		(
		 SELECT @Flag 
		 FROM Review 
		)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar, @Flag) + '" is not a valid Flag. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @Flag)
		RETURN -1
	END
	
	
	
	--Displaying the selected one time display information. 
	PRINT 'Review Details for Flag ' + @Flag + ':'
	PRINT ''



	--Declaration of the cursor to select results for Transaction Details. 
	DECLARE ReviewCursor CURSOR FOR
	SELECT CustomerFirst + ' ' + SUBSTRING(CustomerLast,1,1) + '.', ReviewRating, ReviewText
	FROM Customer c
		JOIN ValetReview vr
		ON c.CustomerID = vr.CustomerID
			JOIN Review r
			ON r.ReviewID=vr.ReviewID
	WHERE Flag = @Flag

	OPEN ReviewCursor

	-- Fetch First Time
	FETCH NEXT FROM ReviewCursor
	INTO @GuestName, @ReviewRating, @ReviewText

	--When the fetching is done from that row of data, 
	WHILE @@FETCH_STATUS = 0

	--Print them 
	BEGIN
		PRINT 'Reviewer: '+ CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @GuestName)
		PRINT 'Rating: ' + CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @ReviewRating)
		PRINT 'ReviewText: ' + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar(max), @ReviewText)
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM ReviewCursor
		INTO @GuestName, @ReviewRating, @ReviewText

	END
 
	CLOSE ReviewCursor
	DEALLOCATE ReviewCursor --to avoid memory leaks

END
GO

EXEC sp_GenerateCustomerReviews
	@Flag = 'L'


PRINT ''
PRINT ''
PRINT ''
PRINT ''
PRINT ''


EXEC sp_GenerateCustomerReviews
	@Flag = 'V'


PRINT ''
PRINT ''
PRINT ''
PRINT ''
PRINT ''



EXEC sp_GenerateCustomerReviews
	@Flag = 'X'