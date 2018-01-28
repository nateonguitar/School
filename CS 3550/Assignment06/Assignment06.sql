--Assignment 06 learning stored procedures and cursors
--Nathan Brooks February 24th, 2016
--
-- This script start to develop data components for the TRAMS Database. 
-- The purpose of the system is to help in planning vacancies, 
-- reservations, and revenue for Fry Hotels, Inc
--
-- I usually used PRINT statements to comment my work so I could 
-- see my comments in the output as well as in the code

USE BROOKS_TRAMS

GO

PRINT '-------------------------------------------------------------------------------------------------
	1. 
	
	Write a stored procedure named sp_InsertPerson that can be used to insert a new row of 
	data (guest) into the Person Table AND also insert a reservation of that new person (using 
	the @@identity of the new PersonID created). This will require a separate procedure named 
	sp_InsertReservation (that is called from within sp_InsertPerson after the new PersonID is 
	determined). 
-------------------------------------------------------------------------------------------------'

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertReservation')
	DROP PROCEDURE sp_InsertReservation;

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertPerson')
	DROP PROCEDURE sp_InsertPerson;

GO

CREATE PROC sp_InsertReservation
	@ResDate			smalldatetime,
	@ResStatus			char(1),
	@ResCheckInDate		date,
	@ResNights			tinyint,
	@ResQuotedRate		smallmoney,
	@ResDepositPaid		smallmoney,
	@ResCCAuth			varchar(25),
	@UnitRateID			smallint,
	@PersonID			int
AS
BEGIN

	INSERT INTO Reservation(
		ResDate,
		ResStatus,
		ResCheckInDate,
		ResNights,
		ResQuotedRate,
		ResDepositPaid,
		ResCCAuth,
		UnitRateID,
		PersonID
		)
	VALUES(
		@ResDate,
		@ResStatus,
		@ResCheckInDate,
		@ResNights,
		@ResQuotedRate,
		@ResDepositPaid,
		@ResCCAuth,
		@UnitRateID,
		@PersonID
		);	

END

GO

CREATE PROC sp_InsertPerson
	@PersonFirst		nvarchar(50),
	@PersonLast			nvarchar(50),
	@PersonAddress		varchar(200),
	@PersonCity			varchar(50),
	@PersonState		char(2) = NULL,
	@PersonPostalCode	varchar(10),
	@PersonCountry		varchar(20),
	@PersonPhone		varchar(20),
	@PersonEmail		varchar(200),
	
	@NewResDate			smalldatetime,
	@NewResStatus		char(1),
	@NewResCheckInDate	date,
	@NewResNights		tinyint,
	@NewResQuotedRate	smallmoney,
	@NewResDepositPaid	smallmoney,
	@NewResCCAuth		varchar(25),
	@NewUnitRateID		smallint

AS
BEGIN

	INSERT INTO Person (
		PersonFirst,
		PersonLast,
		PersonAddress,
		PersonCity,
		PersonState,
		PersonPostalCode,
		PersonCountry,
		PersonPhone,
		PersonEmail
		)
	VALUES (
		@PersonFirst,
		@PersonLast,
		@PersonAddress,
		@PersonCity,
		@PersonState,
		@PersonPostalCode,
		@PersonCountry,
		@PersonPhone,
		@PersonEmail
		);

	DECLARE @NewPersonID  int = @@identity;

	EXEC sp_InsertReservation
		@ResDate			= @NewResDate,
		@ResStatus			= @NewResStatus,
		@ResCheckInDate		= @NewResCheckInDate,
		@ResNights			= @NewResNights,
		@ResQuotedRate		= @NewResQuotedRate,
		@ResDepositPaid		= @NewResDepositPaid,
		@ResCCAuth			= @NewResCCAuth,
		@UnitRateID			= @NewUnitRateID,

		@PersonID			= @NewPersonID;

END

GO


GO

PRINT '

-------------------------------------------------------------------------------------------------
    1A. Demonstrate this procedure by adding myself as a new person, using sp_InsertPerson.
-------------------------------------------------------------------------------------------------'

GO


DECLARE @TodaysDate datetime
SET @TodaysDate = CAST(GETDATE() AS SMALLDATETIME);

EXEC sp_InsertPerson
	@PersonFirst		= 'Nathan',
	@PersonLast			= 'Brooks',
	@PersonAddress		= '2907 Adams Ave',
	@PersonCity			= 'Ogden',
	@PersonState		= 'UT',
	@PersonPostalCode	= 84403,
	@PersonCountry		= 'USA',
	@PersonPhone		= '435-225-3728',
	@PersonEmail		= 'nathanbrooks1@mail.weber.edu',
	
	@NewResDate			= @TodaysDate,
	@NewResStatus		= 'A',
	@NewResCheckInDate	= '7 July 2015',
	@NewResNights		= 3,
	@NewResQuotedRate	= 249.95,
	@NewResDepositPaid	= 249.95,
	@NewResCCAuth		= 'Pending',
	@NewUnitRateID		= 15


GO

PRINT '

-------------------------------------------------------------------------------------------------
    1B.  Select * From Person and Select * From Reservation to show the results.
-------------------------------------------------------------------------------------------------'

GO
SELECT * FROM person;

SELECT * FROM reservation

GO












PRINT '


***********************************************************************


-------------------------------------------------------------------------------------------------
	2. 
	
	Creating a stored procedure named sp_UpdatePrices that can be used to update prices on the UnitRate Table. 
	The procedure will require a PropertyID, and percentage increase (or decrease) parameter to be passed. 
	
	The Proc will reset the ACTIVE unit rates only (based on today’s date) - rounding down to the nearest 
	dollar for price decreases, or rounding up to the nearest dollar for price increases.

	////////////////////////////////////////////////////////////////////////////////////////////////
	In this procedure I used CASE - WHEN - THEN instead of an IF - IF ELSE - ELSE, same logic though
	////////////////////////////////////////////////////////////////////////////////////////////////
-------------------------------------------------------------------------------------------------'

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_UpdatePrices')
	DROP PROCEDURE sp_UpdatePrices;

GO

CREATE PROC sp_UpdatePrices
	@PropertyID			smallint,
	@IncreasePercentage decimal(6,4) -- allows a number like 3.22 or 0.17, will be used as a percent
AS
BEGIN
	UPDATE UnitRate
	SET UnitRate = CASE
		WHEN @IncreasePercentage > 0 
			THEN CEILING( UnitRate * (1 + @IncreasePercentage) )
		WHEN @IncreasePercentage < 0 
			THEN FLOOR  ( UnitRate * (1 - @increasePercentage) )
		ELSE UnitRate
		END
	WHERE PropertyID = @PropertyID
		AND GETDATE() BETWEEN UnitRateBeginDate AND UnitRateEndDate;
END

GO

PRINT '

-------------------------------------------------------------------------------------------------
	2A. Demonstrate: Its summertime in Park City, and Wasatch Mountain Resort wants to decrease its current 
	active rates 6.66% (Rounding down to the nearest dollar) for all their units. Call sp_UpdatePrices to 
	make this happen.
-------------------------------------------------------------------------------------------------'

GO

DECLARE @PropID smallint = (SELECT distinct pr.PropertyID
							FROM UnitRate ur
							JOIN Property pr
								ON ur.propertyID = pr.propertyID
							WHERE PropertyName = 'Wasatch Mountain Resort');



EXEC sp_UpdatePrices
	@PropertyID			= @PropID,
	@IncreasePercentage = 0.0666


GO

PRINT '

-------------------------------------------------------------------
	2B. Select * From UnitRate where PropertyID = 10000 to show the results.
----------------------------------------------------------------------'

GO

Select * From UnitRate where PropertyID = 10000


GO

PRINT '

-------------------------------------------------------------------
	2C. Demonstrate: Calling the exact same sp_UpdatePrices (only supplying a different PropertyID and %), 
	increase the current active rate of all units at Bare Essentials Nudist Resort 5% (rounding up to the 
	nearest dollar).
----------------------------------------------------------------------'

GO

DECLARE @PropID smallint = (SELECT distinct pr.PropertyID
							FROM UnitRate ur
							JOIN Property pr
								ON ur.propertyID = pr.propertyID
							WHERE PropertyName = 'Bare Essentials Nudist Resort');

Select * From UnitRate where PropertyID = 17000

EXEC sp_UpdatePrices
	@PropertyID			= @PropID,
	@IncreasePercentage = 0.05

GO

PRINT '

-------------------------------------------------------------------
	2D. Select * From UnitRate where PropertyID = 17000 to show the results..
----------------------------------------------------------------------'

GO

Select * From UnitRate where PropertyID = 17000

GO














Print '


***********************************************************************


------------------------------------------------------------------------
	3. 
	
	Creating a stored procedure named sp_UpdateResDetail (for the Reservation 
	Table in your DB) that can be used to update a reservation (and any combination 
	of check-in date, number of nights, and/or status). If any of the 3 values are 
	not supplied when the procedure is executed, then these parameters should 
	retain their original value. This stored procedure should include appropriate 
	error handling to give the user a friendly message. For example: Show a 
	friendly message like "Sorry, but "A" is not a valid value for the number 
	of nights. Record not updated." You only need to do one error handling 
	routine - you dont need to account for every possible error.
------------------------------------------------------------------------'

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_UpdateResDetail')
	DROP PROCEDURE sp_UpdateResDetail;

GO

CREATE PROC sp_UpdateResDetail
	@ReservationID		int,
	@ResCheckInDate		date = NULL,
	@ResNights			int = NULL,
	@ResStatus			char(1) = NULL
AS
BEGIN
	IF(@ResStatus NOT IN ('A', 'C', 'X'))
		BEGIN
			PRINT 'ResStatus must be an A, C, or X, Record not updated';
		END
	ELSE
		BEGIN
			UPDATE RESERVATION

			SET ResCheckInDate = CASE 
					WHEN @ResCheckInDate IS NOT NULL 
					THEN @ResCheckInDate 
					ELSE ResCheckInDate
				END,
				ResNights = CASE 
					WHEN @ResNights IS NOT NULL 
					THEN @ResNights
					ELSE ResNights
				END,
				ResStatus = CASE
					WHEN @ResStatus IS NOT NULL
					THEN @ResStatus
					ELSE ResStatus
				END
			WHERE ReservationID = @ReservationID
		END
END

GO

Print '

------------------------------------------------------------------------
	3A. Demonstrate by providing any combination of nights, status, and/or 
	check-in date changes.
------------------------------------------------------------------------'

GO

EXEC sp_UpdateResDetail
	@ReservationID		= 46, -- the one I created for myself
	@ResCheckInDate		= '01/01/1999',
	@ResNights			= 10
	-- @ResStatus left blank on purpose

GO

PRINT '

------------------------------------------------------------------------
	3B. Do it one more time with different combinations.
------------------------------------------------------------------------'

GO

EXEC sp_UpdateResDetail
	@ReservationID		= 46, -- the one I created for myself
	@ResCheckInDate		= '12/12/2012',
	@ResStatus			= 'A'

GO

PRINT '

------------------------------------------------------------------------
	3C. Do it a third time so it triggers your error interception and message 
	display.
------------------------------------------------------------------------'

GO

EXEC sp_UpdateResDetail
	@ReservationID		= 46, -- the one I created for myself
	@ResCheckInDate		= '10/10/2010',
	@ResNights			= 1,
	@ResStatus			= 'w'

GO






PRINT '


***********************************************************************


------------------------------------------------------------------------
	4. 
	
	Write a stored procedure named sp_ProduceBill that accepts the input 
	parameter "FolioID".  In this single SPROC, display the following 
	information ONCE (guest name unit number (not ID), and check-in date, 
	check-out date), then use a cursor to loop through and fetch folio 
	transaction details (transaction description, amount, and date).  
	
	It there are no folio details for the entered folio number, 
	a message should be displayed.

	Demonstrate this stored procedure three times by passing in a different 
	folio # each time, and displaying the results.
------------------------------------------------------------------------';

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_ProduceBill')
	DROP PROCEDURE sp_ProduceBill;

GO

CREATE PROC sp_ProduceBill
	@FolioID int
AS
BEGIN
	DECLARE @PersonFirst		nvarchar(50)
	DECLARE @PersonLast			nvarchar(50) 
	DECLARE @UnitID				smallint
	DECLARE @FolioCheckInDate	smalldatetime
	DECLARE @FolioCheckOutDate	smalldatetime

	IF NOT EXISTS(
		SELECT PersonFirst, PersonLast, UnitID, FolioCheckInDate, FolioCheckOutDate
		FROM Folio fo
			JOIN Reservation re
			ON fo.ReservationID = re.ReservationID
			JOIN Person pe
			ON re.PersonID = pe.PersonID
		WHERE FolioID = @FolioID)
	BEGIN
		PRINT 'No details for this reservation.'
		RETURN
	END

	DECLARE ProduceBillCursor CURSOR FOR
	SELECT PersonFirst, PersonLast, UnitID, FolioCheckInDate, FolioCheckOutDate
	FROM Folio fo
		JOIN Reservation re
		ON fo.ReservationID = re.ReservationID
		JOIN Person pe
		ON re.PersonID = pe.PersonID
	WHERE FolioID = @FolioID

	OPEN ProduceBillCursor

	FETCH NEXT FROM ProduceBillCursor
	INTO @PersonFirst, @PersonLast, @UnitID, @FolioCheckInDate, @FolioCheckOutDate

	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT 'Name:			' + @PersonFirst + ' ' + @PersonLast
		PRINT 'UnitID:			' + CAST(@UnitID AS VARCHAR)
		PRINT 'Check-in Date:	' + CAST(@FolioCheckInDate AS VARCHAR)
		PRINT 'Check-out Date:	' + CAST(@FolioCheckOutDate AS VARCHAR)

		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM ProduceBillCursor
		INTO @PersonFirst, @PersonLast, @UnitID, @FolioCheckInDate, @FolioCheckOutDate

	END

	CLOSE ProduceBillCursor
	DEALLOCATE ProduceBillCursor
END

GO

EXEC sp_ProduceBill 
	@FolioID = 2

EXEC sp_ProduceBill 
	@FolioID = 4

EXEC sp_ProduceBill 
	@FolioID = 200