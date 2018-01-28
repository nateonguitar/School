-- make sure to use my database
USE FRY_FARMS

GO

-- #1 - sp_InsertDiscount

-- check to see if sp_InsertDiscount exists
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertDiscount')
	DROP PROCEDURE sp_InsertDiscount;
GO

-- create procedure
CREATE PROCEDURE sp_InsertDiscount
	  @DiscountDescription		varchar(50)
	, @DiscountExpiration		smalldatetime
	, @DiscountRules			varchar(100) = NULL
	, @DiscountPercent			decimal(4,2) = NULL
	, @DiscountAmount			smallmoney = NULL
	, @DiscountID				smallint OUTPUT
AS
BEGIN

	INSERT INTO Discount (DiscountDescription, DiscountExpiration, DiscountRules, DiscountPercent, DiscountAmount)
	VALUES (
	  @DiscountDescription	
	, @DiscountExpiration		
	, @DiscountRules			
	, @DiscountPercent			
	, @DiscountAmount)

	SELECT @DiscountID = @@IDENTITY; --new DiscountID as OUTPUT

END
GO

-- #1A - test by adding myself
DECLARE @NewDiscountID smallint;

EXEC sp_InsertDiscount 
	  @DiscountDescription		= 'A special CS 3550 Rate'
	, @DiscountExpiration		= '12/31/2016'
	, @DiscountRules			= 'Must be a current student'
	, @DiscountPercent			= NULL
	, @DiscountAmount			= 35.50
	, @DiscountID               = @NewDiscountID		OUTPUT;

PRINT 'New Discount ID for assigned is ' + CONVERT(VARCHAR(10),@NewDiscountID)

-- #1B - check full table to see if record was added.
SELECT *
FROM Discount

GO

-- #2 - sp_InsertRoomTypes

-- check to see if both procedures exist
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertRoomTypes')
	DROP PROCEDURE sp_InsertRoomTypes;
GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertRoom')
	DROP PROCEDURE sp_InsertRoom;
GO

CREATE PROCEDURE sp_InsertRoom
	  @HotelRoomTypeID		smallint
	, @RoomNumber			varchar(5)
	, @RoomDescription		varchar(200)
	, @RoomSmoking			bit
	, @RoomBedConfiguration	varchar(30)
	, @HotelID				smallint
AS
BEGIN

	INSERT INTO Room(HotelRoomTypeID, RoomNumber, RoomDescription, RoomSmoking, RoomBedConfiguration, HotelID)
	VALUES (@HotelRoomTypeID
	, @RoomNumber
	, @RoomDescription
	, @RoomSmoking
	, @RoomBedConfiguration
	, @HotelID);

END
GO

CREATE PROCEDURE sp_InsertRoomTypes
	  @RoomType				varchar(20)
	, @RoomTypeRackRate		smallmoney
	, @RoomTypeDescription	varchar(200)	= NULL
	, @NewRoomNumber		varchar(5)
	, @NewRoomDescription	varchar(200)
	, @NewRoomSmoking		bit
	, @NewRoomBedConfiguration	varchar(30)
	, @NewHotelID			smallint
AS
BEGIN

	INSERT INTO HotelRoomType(RoomType, RoomTypeRackRate, RoomTypeDescription)
	VALUES (@RoomType
	, @RoomTypeRackRate
	, @RoomTypeDescription);

	EXEC sp_InsertRoom
		  @HotelRoomTypeID	= @@IDENTITY
		, @RoomNumber		= @NewRoomNumber
		, @RoomDescription	= @NewRoomDescription
		, @RoomSmoking		= @NewRoomSmoking
		, @RoomBedConfiguration = @NewRoomBedConfiguration
		, @HotelID			= @NewHotelID;

END
GO

-- #2A - add MasterSuite, Room351 for Weber Towers, $199/night
EXEC sp_InsertRoomTypes
	  @RoomType					= 'MasterSuite'
	, @RoomTypeRackRate			= 199.00
	, @RoomTypeDescription		= 'Master Suite - Rack Rate'
	, @NewRoomNumber			= 351
	, @NewRoomDescription		= 'A new suite overlooking the Great Salt Lake'
	, @NewRoomSmoking			= 0
	, @NewRoomBedConfiguration	= '2 King - 2 Queens'
	, @NewHotelID				= 2300

-- #2B - check results
SELECT *
FROM HotelRoomType
SELECT *
FROM Room

GO

-- #3 - sp_UpdateTaxRates

-- check to see if procedure exists
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_UpdateTaxRates')
	DROP PROCEDURE sp_UpdateTaxRates;
GO

CREATE PROCEDURE sp_UpdateTaxRates
	  @TaxLocationID	smallint
	, @RoomTaxChange	decimal(6,4) --this should be the percent i.e. -5.02 for decrease of 5.02%, we'll change to decimal inside the sproc
    , @SalesTaxChange	decimal(6,4) 
	
AS
 BEGIN

	IF (SELECT RoomTaxRate From TaxRate Where TaxLocationID = @TaxLocationID) != @RoomTaxChange -- Updated
	BEGIN
		UPDATE TAXRATE
		SET RoomTaxRate = @RoomTaxChange
		WHERE TaxLocationID = @TaxLocationID
		
				
	END
	ELSE IF (SELECT SalesTaxRate From TaxRate Where TaxLocationID = @TaxLocationID) != @SalesTaxChange -- Updated
	BEGIN
		UPDATE TAXRATE
		SET SalesTaxRate = @SalesTaxChange
		WHERE TaxLocationID = @TaxLocationID

		
	END

END
GO

-- #3A - Change to 6.66%
EXEC sp_UpdateTaxRates
	  @TaxLocationID	 = 3
	, @RoomTaxChange	= 4.00
	, @SalesTaxChange   = 7.66;

-- #3B - check the results
SELECT *
FROM TaxRate

GO

--4

-- check to see if procedure exists
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_UpdateReservation')
	DROP PROCEDURE sp_UpdateReservation;
GO

CREATE PROCEDURE sp_UpdateReservation
	  @ReservationID	smallint
	, @ReservationDate   varchar(max) = Null
	, @NeworOld          char(1) = 'N'
	
	
AS
 BEGIN 

 DECLARE @OldReservationDate smalldatetime = (SELECT ReservationDate From Reservation Where ReservationID = @ReservationID)
 DECLARE @CheckDate smalldatetime
 DECLARE @ErrMessage  varchar(max)

 BEGIN TRY
		
		SET @CheckDate = CONVERT(smalldatetime, @ReservationDate)
		END TRY
		
		
	
	
	BEGIN CATCH
		SET @ErrMessage = ('"' + @ReservationDate + '" is not a valid check-in date. Please enter a valid date.')
		RAISERROR (@ErrMessage, -1, -1, @ReservationDate)
		RETURN -1
	END CATCH

	UPDATE Reservation SET ReservationDate = CASE
           WHEN @NeworOld = 'N' THEN @CheckDate
		   WHEN @NeworOld = 'O' THEN @OldReservationDate
           ELSE GetDate()
		   END 
		   WHERE ReservationID = @ReservationID
		   
END	
		   
       



-- #4A (demo error)
EXEC sp_UpdateReservation
	  @ReservationID	= 1
	, @ReservationDate =   '11/2015'
	, @NeworOld = 'N'

GO
PRINT''
SELECT * FROM RESERVATION WHERE RESERVATIONID = 1
GO

-- #4B (New Date)
EXEC sp_UpdateReservation
	  @ReservationID	= 1
	, @ReservationDate =   '11/11/2015'
	, @NeworOld = 'N'

GO
PRINT''
SELECT * FROM RESERVATION WHERE RESERVATIONID = 1
GO

-- #4C (Old Date - keeps 11/11)
EXEC sp_UpdateReservation
	  @ReservationID	= 1
	, @ReservationDate =   '12/11/2015'
	, @NeworOld = 'O'

GO
PRINT''
SELECT * FROM RESERVATION WHERE RESERVATIONID = 1
GO

-- #4C (Get Date because 'W' Invalid)
EXEC sp_UpdateReservation
	  @ReservationID	= 1
	, @ReservationDate =   '11/11/2015'
	, @NeworOld = 'W'

GO
PRINT''
SELECT * FROM RESERVATION WHERE RESERVATIONID = 1
GO
-- #5 - sp_ProduceBill

-- check to see if procedure exists
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_ProduceBill')
	DROP PROCEDURE sp_ProduceBill;
GO

CREATE PROCEDURE sp_ProduceBill
	  @ReservationID	smallint
AS
BEGIN

	--declare variables for ease of printing later
	DECLARE @ErrMessage		varchar(max)
	DECLARE @GuestName		varchar(200)
	DECLARE @GuestAddress	varchar(200)
	DECLARE @GuestPhone		varchar(20)
	DECLARE @GuestEmail		varchar(30)
	DECLARE @GuestComments	varchar(200)

	DECLARE @ReservationDetailID smallint
	DECLARE @RoomNumber		varchar(5)
	DECLARE @QuotedRate		smallmoney
	DECLARE @CheckinDate	smalldatetime
	DECLARE @Nights			tinyint
	DECLARE @RDStatus		char
	DECLARE @Comments		varchar(200)


	IF NOT EXISTS(SELECT ReservationID FROM Reservation WHERE ReservationID = @ReservationID)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar,@ReservationID) + '" is not a valid reservation. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @ReservationID)
		RETURN -1
	END
	
	SELECT @GuestName = CONCAT(GuestLast,',',GuestFirst)	
	, @GuestAddress = GuestAddress1
		+ CASE WHEN GuestAddress2 IS NOT NULL THEN CHAR(13) + CHAR(10) + GuestAddress2 ELSE '' END
		+ CHAR(13) + CHAR(10) + GuestCity
		+ CASE WHEN GuestState IS NOT NULL THEN ', ' + GuestState ELSE '' END
		+ ' ' + GuestPostalCode
		+ CHAR(13) + CHAR(10) + GuestCountry
	, @GuestPhone = GuestPhone
	, @GuestEmail = GuestEmail
	, @GuestComments = GuestComments
	FROM Reservation R
	INNER JOIN CreditCard CC ON CC.CreditCardID = R.CreditCardID
	INNER JOIN Guest G ON G.GuestID = CC.GuestID
	WHERE ReservationID = @ReservationID;

	PRINT 'Guest Details:'
	PRINT @GuestName
	PRINT @GuestAddress
	PRINT @GuestPhone
	IF (@GuestEmail IS NOT NULL)
		PRINT @GuestEmail
	IF (@GuestComments IS NOT NULL)
		PRINT @GuestComments

	PRINT ''
	PRINT 'Reservation Details:'

	IF NOT EXISTS(SELECT ReservationID FROM ReservationDetail WHERE ReservationID = @ReservationID)
	BEGIN
		PRINT 'No details for this reservation.'
		RETURN
	END
	
	DECLARE ResDetailCursor CURSOR FOR
	SELECT ReservationDetailID, RoomNumber, QuotedRate
	, CheckinDate, Nights, RDStatus, Comments
	FROM ReservationDetail RD
	INNER JOIN Room R ON R.RoomID = RD.RoomID
	WHERE RD.ReservationID = @ReservationID

	OPEN ResDetailCursor

	-- Fetch First Time
	FETCH NEXT FROM ResDetailCursor
	INTO @ReservationDetailID, @RoomNumber, @QuotedRate
	, @CheckinDate, @Nights, @RDStatus, @Comments

	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT 'Room Number:     ' + @RoomNumber
		PRINT 'Quoted Rate:     ' + FORMAT(@QuotedRate,'$#,0.00')
		PRINT 'Check-in Date:   ' + CONVERT(varchar,@CheckinDate,101)
		PRINT 'Nights:          ' + CONVERT(varchar,@Nights)
		PRINT 'Detail Status:   ' + @RDStatus
		IF (@GuestComments IS NOT NULL)
			PRINT 'Comments:        ' + @Comments
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM ResDetailCursor
		INTO @ReservationDetailID, @RoomNumber, @QuotedRate
		, @CheckinDate, @Nights, @RDStatus, @Comments

	END
 
	CLOSE ResDetailCursor
	DEALLOCATE ResDetailCursor

END
GO

-- #5A - test
-- test for error check
EXEC sp_ProduceBill
	@ReservationID = 99;
	
	EXEC sp_ProduceBill
	@ReservationID = 3;

	EXEC sp_ProduceBill
	@ReservationID = 15;

GO