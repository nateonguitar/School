USE SUPRA

--Re-Creating sp_GetCarsInLot
-- check to see if sp_GetCarsInLot exists and drop it if it does
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_GetCarsInLot')
	DROP PROCEDURE sp_GetCarsInLot;
GO

--Create sp_GetCarsInLot with one input, FolioID. 
CREATE PROCEDURE sp_GetCarsInLot
	  @LotID		   	    tinyint
AS
BEGIN

	--Variable declaration. 
	DECLARE @ErrMessage			varchar(max)
	DECLARE @LotName			varchar(20)
	DECLARE @LotCapacity		tinyint

	DECLARE @GuestName			varchar(200)
	DECLARE @CarMake			varchar(25)
	DECLARE @CarModel			varchar(25)
	DECLARE @SpotID				tinyint



	--Error handling. Make sure that the folio actually exists.
	IF NOT EXISTS
		(
		 SELECT LotID 
		 FROM Lot 
		 WHERE LotID = @LotID
		)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar, @LotID) + '" is not a valid Lot. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @LotID)
		RETURN -1
	END
	
	
	--Selecting then necessary information to display once.
	SELECT @LotID = LotID	
	, @LotName = LotName
	, @LotCapacity = LotCapacity
	FROM Lot
	WHERE LotID = @LotID;
	
	--Displaying the selected one time display information. 
	PRINT 'Lot Details:'
	PRINT ''
	PRINT 'LotID: ' + CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @LotID)
	PRINT 'LotName: ' + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @LotName)
	PRINT 'LotCapacity: ' + CHAR(9) + CHAR(9) + CONVERT(varchar, @LotCapacity)
	PRINT ''
	--Print the stats
	EXEC sp_LotStats
		@LotID = @LotID

	--Printing transaction details. 
	PRINT ''
	PRINT ''
	PRINT 'Vehicles Parked in this Lot:'
	PRINT ''

	--Error message if there are no folio transactions for the folioID given.  
	IF NOT EXISTS(SELECT LotID FROM Valet WHERE LotID = @LotID)
	BEGIN
		PRINT 'No details for this reservation.'
		RETURN 
	END


	--Declaration of the cursor to select results for Transaction Details. 
	DECLARE ParkedCursor CURSOR FOR
	SELECT CustomerLast + ', '+  CustomerFirst, CarMake, CarModel, SpotID
	FROM Customer c
		JOIN Valet v
		ON c.CustomerID = v.CustomerID
	WHERE LotID = @LotID

	OPEN ParkedCursor

	-- Fetch First Time
	FETCH NEXT FROM ParkedCursor
	INTO @GuestName, @CarMake, @CarModel, @LotCapacity

	--When the fetching is done from that row of data, 
	WHILE @@FETCH_STATUS = 0

	--Print them 
	BEGIN
		PRINT 'Guest: '+ CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + @GuestName
		PRINT 'Make: ' + CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @CarMake)
		PRINT 'Model: ' + CHAR(9) + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @CarModel)
		PRINT 'Capacity: ' + CHAR(9) + CHAR(9) + CHAR(9) + CONVERT(varchar, @LotCapacity)	
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM ParkedCursor
		INTO @GuestName, @CarMake, @CarModel, @LotCapacity

	END
 
	CLOSE ParkedCursor
	DEALLOCATE ParkedCursor --to avoid memory leaks

END
GO





EXEC sp_GetCarsInLot
	@LotID = 7
