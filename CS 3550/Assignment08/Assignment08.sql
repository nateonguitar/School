--Assignment 08 learning User Defined Functions
--Nathan Brooks March 16th, 2016
--
-- Practicing Triggers!!!
-- 
-- I usually used PRINT statements to comment my work so I can 
-- see my comments in the output as well as in the code

USE BROOKS_TRAMS

GO

PRINT 
'*********************************************************************
stored procedure sp_InsertPerson which calls sp_InsertReservation (from HW6)
*********************************************************************';

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

PRINT
'*********************************************************************
stored procedure sp_ProduceBill (from HW6)
*********************************************************************';

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

PRINT
'*********************************************************************
the function dbo.fn_GetLodgingTaxRate (from HW 7)
*********************************************************************';

GO

IF OBJECT_ID (N'dbo.GetLodgingTaxRate', N'FN') IS NOT NULL
DROP FUNCTION dbo.GetLodgingTaxRate;

GO

CREATE FUNCTION dbo.GetLodgingTaxRate
       (
       @PropertyID int,
       @date date
       )
RETURNS decimal(7, 5)
AS
	BEGIN
		-- @result won't be changed if the query returns NULL
		DECLARE @result decimal(7,5) = 0

		DECLARE @testResult decimal(7,5) = (
			SELECT TOP 1 TaxRate
			FROM TaxRate tr
				JOIN TaxLocation tl
				ON tr.TaxLocationID = tl.TaxLocationID
				JOIN Property pr
				ON tl.TaxLocationID = pr.TaxlocationID
			WHERE PropertyID = @PropertyID
				AND @date BETWEEN TaxStartDate AND
				CASE 
					WHEN TaxEndDate IS NULL THEN GetDate()
					WHEN TaxEndDate IS NOT NULL THEN TaxEndDate
				END
			ORDER BY TaxRate DESC
		)
		
		IF (@testResult) IS NOT NULL
				BEGIN
					SET @result = @testResult
				END
              
		RETURN @result / 100
	END
GO

PRINT
'*********************************************************************
the function dbo.fn_GetQuotedRate (from HW 7)
*********************************************************************';

GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'fn_GetQuotedRate')
	DROP FUNCTION fn_GetQuotedRate;

GO

CREATE FUNCTION dbo.fn_GetQuotedRate
	(
	@PropertyID			int,
	@BeginDate			date,
	@EndDate			date,
	@UnitTypeID			INT
	)
	RETURNS @ValidRates TABLE (
		MaximumUnitRate		smallmoney	NOT NULL,
		UnitTypeDescription varchar(200)	NOT NULL,
		UnitRateDescription	varchar(200)	NOT NULL
		)
	AS
		BEGIN


			DECLARE @MaximumUnitRate smallmoney = (
				SELECT TOP 1 ur.UnitRate
				FROM UnitRate ur
					JOIN UnitType ut
					ON ur.UnitTypeID = ut.UnitTypeID
					JOIN Property pr
					ON ur.PropertyID = pr.PropertyID
				WHERE ut.UnitTypeID = @UnitTypeID
					AND pr.PropertyID = @PropertyID
					AND (UnitRateEndDate > @BeginDate) AND (UnitRateBeginDate < @EndDate)
				ORDER BY ur.UnitRate DESC
			)

			IF (@MaximumUnitRate IS NULL)
				BEGIN
				SET @MaximumUnitRate = 0
				END
		
			-- will be set to an error message if no description is found
			DECLARE @UnitTypeDescription varchar(200) = (
				SELECT UnitRateDescription
				FROM UnitRate
				WHERE UnitRateBeginDate < @EndDate
					AND UnitRateEndDate > @BeginDate
					AND UnitRate = 319
			)

			IF (@UnitTypeDescription IS NULL)
				BEGIN
				SET @UnitTypeDescription = 'No UnitTypeDescription available'
				END

			-- will be set to an error message if no description is found
			DECLARE @UnitRateDescription varchar(200) = (
				SELECT UnitRateDescription
				FROM UnitRate ur
					JOIN UnitType ut
					ON ur.UnitTypeID = ut.UnitTypeID
					JOIN Property pr
					ON ur.PropertyID = pr.PropertyID
				WHERE ut.UnitTypeID = @UnitTypeID
					AND pr.PropertyID = @PropertyID
					AND UnitRate = @MaximumUnitRate
			)

			IF (@UnitRateDescription IS NULL)
				BEGIN
				SET @UnitRateDescription = 'No UnitRateDescription available'
				END


				-- insert the values that were found into the table to be returned!!!
			INSERT INTO @ValidRates (MaximumUnitRate, UnitTypeDescription, UnitRateDescription)
				VALUES(
					@MaximumUnitRate, 
					@UnitTypeDescription,
					@UnitRateDescription)
				RETURN 
			END

GO

PRINT
'*********************************************************************
0A.  Just like you did in HW 6, add yourself as a new person, and create 
a new Reservation calling sp_InsertPerson.  

Note: For the data you insert,  Use 7/21/2015 for Check-in Date, 
3 nights (essentially checking out on 24 July).
*********************************************************************';

GO

DECLARE @TodaysDate datetime = CAST(GETDATE() AS SMALLDATETIME);

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
	@NewResCheckInDate	= '7/21/2015',
	@NewResNights		= 3,
	@NewResQuotedRate	= 249.95,
	@NewResDepositPaid	= 249.95,
	@NewResCCAuth		= 'Pending',
	@NewUnitRateID		= 15


GO

PRINT
'*********************************************************************
0B.  Select * From Person and Select * From Reservation to show the results 

(exactly like HW6)
*********************************************************************';

GO

SELECT * FROM person;

SELECT * FROM reservation;

GO

PRINT
'*********************************************************************
Now for the new stuff...

1.  
Write a trigger named tr_UnitIDMustExist on the FOLIO table 
(after INSERT or UPDATE) that checks to make sure the UnitID is a 
valid UnitID (essentially enforcing referential integrity between the 
Folio and Unit Tables).  
 
If an improper UnitID is inserted, then raise an error message 
and rollback the transaction.
*********************************************************************';
 
GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_UnitIDMustExist')
	DROP TRIGGER tr_UnitIDMustExist;

GO

CREATE TRIGGER tr_UnitIDMustExist ON Folio
AFTER INSERT, UPDATE
AS
BEGIN
	DECLARE @NewUnitID int = (SELECT UnitID FROM Inserted);
	
	IF @NewUnitID NOT IN (SELECT UnitID FROM Unit)
	BEGIN
		RAISERROR(N'
		@@@@@@@@
		@@@@@@@@ %d was not a valid UnitID, not updated/inserted @@@@@@@@
		@@@@@@@@
		', 16, 1, @NewUnitID);
		ROLLBACK
		RETURN
	END
END

GO

PRINT
'*********************************************************************
1A.  
Demonstrate this trigger by Checking yourself in (creating a new Folio 
entry based on the Reservation you made in #0A above).  
 
Insert into Folio the same values as the Reservation (with status "C" 
checked in), but inserting UnitID = 999.  
 
An error message should display showing UnitID 999 is invalid.
*********************************************************************';

GO
DECLARE @ReservationIDToInsert int = (SELECT TOP 1 ReservationID FROM Reservation ORDER BY ReservationID Desc);


INSERT INTO FOLIO (FolioStatus, FolioRate, FolioCheckInDate, FolioCheckOutDate, UnitID, ReservationID)
VALUES('C', 599.95, '2015-01-08 14:32:00', '2015-01-13 08:24:00', 999, @ReservationIDToInsert);

GO

PRINT
'*********************************************************************
1B.  
Demonstrate the same trigger, using a valid UnitID of your choice.  

You should now have an active Folio linked back to your reservation.  
*********************************************************************';

GO

DECLARE @ReservationIDToInsert int = (SELECT TOP 1 ReservationID FROM Reservation ORDER BY ReservationID Desc);

INSERT INTO FOLIO (FolioStatus, FolioRate, FolioCheckInDate, FolioCheckOutDate, UnitID, ReservationID)
VALUES('C', 599.95, '2015-01-08 14:32:00', '2015-01-13 08:24:00', 10000, @ReservationIDToInsert);

SELECT * FROM FOLIO

GO

PRINT
'*********************************************************************
1C.  
Select * from Folio to show the results. 
*********************************************************************';

GO

SELECT *
FROM Folio;

GO

PRINT
 '*********************************************************************
 1D.  
 Using the FolioID that was created for your reservation, write a couple 
 Insert Statements for the FolioTransaction table so it populates with 
 some sample charges associated with the Folio.  
 
 I dont really care what the Transactions contain (maybe a Food and Beverage, 
 Gym, or Internet Charge), but have at least 2 rows of sample data (for later).
*********************************************************************';

GO

-- get all the folioIDs and order them descending.  The most recent will be the first in the list.
DECLARE @LastFolioID int = (SELECT TOP 1 FolioID FROM Folio ORDER BY FolioID DESC);

INSERT INTO FolioTransaction (
	TransDate,
	TransAmount,
	TransDescription,
	TransCategoryID,
	FolioID)
VALUES(
	'2015-01-08 15:00:00',
	'3.14',
	'the cost of Pi from the minibar',
	3,
	@LastFolioID)

INSERT INTO FolioTransaction (
	TransDate,
	TransAmount,
	TransDescription,
	TransCategoryID,
	FolioID)
VALUES(
	'2015-01-08 16:00:00',
	'5000',
	'Awesome dude refund',
	11,
	@LastFolioID)
	
GO

PRINT
'*********************************************************************
2. 
Write a trigger named tr_UpdateFolio  that will be invoked when the 
Folio table "status" field (column update) is changed.

If Foliostatus is updated to "B" for Billed, trigger two Insert statements 
to (1) INSERT in the FolioTransaction table, the amount for the total 
lodging cost as Transcategory 1 - (normally the FolioRate * number of 
nights stay, but you must also factor in any late checkout fees*). 
 
*Checkout time is Noon on the checkout date. Guest is given a one hour 
grace period to check out. After 1PM (but before 4PM), a 50% surcharge 
is added to the FolioRate. After 4PM, an additional full night
s FolioRate is applied.  (2) The second INSERT statement in the same 
trigger will insert the Lodging Tax* - a separate entry in the 
FolioTransaction table for tax on lodging (Transcategory 2).  

*Use the dbo.GetLodgingTaxRate function from HW 7 to determine the Logding Tax.

Note:  You will need to determine the propertyID to pass to the function by 
joining Folio(unitid) to Unit(UnitID).  You may also modify the function, 
if you wish to make things easier.

If FolioCheckoutDate (column update) is updated, trigger an INSERT in the 
FolioTransaction table.  Hardcode sample insert data using 
	GetDate(), 
	$0 for the TransAmount, 
	"Processing Bill, and getting balance due..." for the description,  
	Transcategory 10 (for payment), 
	and any YOUR folioID (from 1D).

Clue for #2

For Status "x"
IF DATEDIFF (HOUR, GetDate(), @InsertedCheckinDate) =< 48...

For Status "b"
IF DATEDIFF (HOUR, @InsertedCheckinDate + @InsertedNights, GetDate()) >=16 then add 1 to nights
ELSE IF DATEDIFF (HOUR, @InsertedCheckinDate + @InsertedNights, GetDate()) >=13 then add .5 to nights

1300 Hours = 1PM, 1600 HRS = 4PM
*********************************************************************';

GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_UpdateFolio')
	DROP TRIGGER tr_UpdateFolio;

GO
CREATE TRIGGER tr_UpdateFolio ON Folio
AFTER UPDATE
AS
BEGIN
	--//--//-- get all the variables I'll need for later
	DECLARE @InsertedFolioID	int =		(SELECT FolioID	FROM Inserted);
	DECLARE @ReservationID int =			(SELECT ReservationID FROM Folio WHERE FolioID = @InsertedFolioID);
	DECLARE @PropertyID int =				(SELECT distinct ur.PropertyID	
												FROM Folio fo 
													JOIN Reservation re 
													ON fo.ReservationID = re.ReservationID
													JOIN UnitRate ur
													ON re.UnitRateID = ur.UnitRateID
												WHERE re.ReservationID = @ReservationID);
	DECLARE @UnitTypeID int =				(SELECT distinct ur.UnitTypeID	
												FROM Folio fo 
												JOIN Reservation re 
												ON fo.ReservationID = re.ReservationID
												JOIN UnitRate ur
												ON re.UnitRateID = ur.UnitRateID
												WHERE re.ReservationID = @ReservationID);
	DECLARE @FolioStatus varchar(1) =		(SELECT FolioStatus		FROM Inserted);
	DECLARE @InsertedCheckinDate datetime = (SELECT FolioCheckInDate FROM Inserted);
	DECLARE @InsertedCheckOutDate datetime =(SELECT FolioCheckOutDate FROM Inserted);
	DECLARE @InsertedNights decimal(5, 2) = (SELECT ResNights		FROM Reservation WHERE ReservationID = @ReservationID);

	DECLARE @FolioRate			smallmoney;
	DECLARE @TotalLodgingCost	smallmoney;
	DECLARE @TaxPercentage      decimal(7, 5);
	DECLARE @TaxesDue			smallmoney;
	--//--//-- 

	IF UPDATE (FolioStatus)
	BEGIN
		-- If billed after a certain number of hours add late fee by raising the number of nights they stayed
		IF @FolioStatus = 'B'
		BEGIN
			IF DATEDIFF (HOUR, @InsertedCheckinDate + @InsertedNights, GetDate()) >=16 
			BEGIN
				--then add 1 to nights
				SET @InsertedNights = @InsertedNights + 1;
			END
			ELSE IF DATEDIFF (HOUR, @InsertedCheckinDate + @InsertedNights, GetDate()) >=13
			BEGIN
				--then add .5 to nights
				SET @InsertedNights = @InsertedNights + 0.5;
			END

			-- 
			SET @FolioRate		  =	(SELECT MaximumUnitRate 
										FROM dbo.fn_GetQuotedRate(10000, @InsertedCheckinDate, @InsertedCheckoutDate, @UnitTypeID)) -- 319
			SET @TotalLodgingCost =	@FolioRate * @InsertedNights;
			SET @TaxPercentage    = (SELECT dbo.GetLodgingTaxRate(@PropertyID, @InsertedCheckinDate));
			SET @TaxesDue		  = @TotalLodgingCost * @TaxPercentage;

			INSERT INTO FolioTransaction (
				TransDate,
				TransAmount,
				TransDescription,
				TransCategoryID,
				FolioID)
			VALUES (
				GETDATE(),
				@TotalLodgingCost,
				'Cost of lodging + late fees',
				10,  -- Payment
				@InsertedFolioID)

			INSERT INTO FolioTransaction (
				TransDate,
				TransAmount,
				TransDescription,
				TransCategoryID,
				FolioID)
			VALUES (
				GETDATE(),
				@TaxesDue,
				'Taxes due',
				2,  -- Occupancy Tax
				@InsertedFolioID)

		END -- end "B"

		ELSE IF @FolioStatus = 'X'
		BEGIN
			PRINT 'Cancellation Fee if less than 48 hours from check in date';
			IF DATEDIFF (HOUR, GetDate(), @InsertedCheckinDate) <= 48
			BEGIN
				INSERT INTO FolioTransaction (
					TransDate,
					TransAmount,
					TransDescription,
					TransCategoryID,
					FolioID)
				VALUES (
					GETDATE(),
					50,		-- didn't know what the late fee should be so I hard 
							-- coded one in, could easily figure one out if I had the info
					'Late Fee',     
					10,  -- Payment
					@InsertedFolioID)
			END
		END -- end "X"
	END -- end IF UPDATED(FolioStatus)

	IF UPDATE (FolioCheckoutDate)
	BEGIN
		INSERT INTO FolioTransaction (
				TransDate,
				TransAmount,
				TransDescription,
				TransCategoryID,
				FolioID)
			VALUES (
				GETDATE(),
				0,
				'Processing Bill, and getting balance due...',
				10,  -- Payment
				@InsertedFolioID)
	END
END

GO

PRINT
'*********************************************************************
3. 
Write a trigger named tr_GenerateBill that will be invoked when an entry 
is INSERTED into the table FolioTransaction . 
 
If TransCategoryID is 10, meaning "Payment", then call stored procedure 
ProduceBill (from HW 6).
*********************************************************************';

GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_GenerateBill')
	DROP TRIGGER tr_GenerateBill;

GO
CREATE TRIGGER tr_GenerateBill ON FolioTransaction
AFTER INSERT
AS
BEGIN
	DECLARE @TransCategoryID int= (SELECT TransCategoryID FROM Inserted);

	IF @TransCategoryID = 10 -- meaning "Payment"
	BEGIN
		DECLARE @FolioIDFromInsert int = (SELECT FolioID FROM Inserted);

		-- then call stored procedure ProduceBill (from HW 6).
		EXEC sp_ProduceBill
			@FolioID = @FolioIDFromInsert
	END
END

GO

INSERT INTO FolioTransaction (TransDate, TransAmount, TransDescription, TransCategoryID, FolioID)
	VALUES('2015-05-21 07:26:51.060', -448.98, 'Payment', 10, 19)

PRINT
'*********************************************************************
4A. 
Assume today is (July 24, 2015 at 2PM)*. YOU are due to check out today 
(from 1A-1C above). Write an Update Statement to change the status of your 
Folio to "B"illed. (Be careful to include a WHERE clause so ONLY your 
folio is updated).  

Note: This should automatically invoke Trigger 1 
above (factoring in the late charge), which automatically invokes 3 
above, and calls sp_ProduceBill, and produces a bill.

*To simulate the current time as July 24, 2015 at 2PM, you will need 
to hard code the date value  7/24/2015 2:00:00 PM
*********************************************************************';

GO

-- get all the folioIDs and order them descending.  The most recent will be the first in the list.
DECLARE @MyReservationID int = (SELECT TOP 1 ReservationID
									FROM Person pe
									JOIN Reservation re
									ON pe.personID = re.personID
									WHERE pe.personLast = 'Brooks'
									ORDER BY ReservationID DESC);

UPDATE Folio
SET FolioStatus = 'B', FolioCheckInDate = '7/24/2015 2:00:00 PM'
WHERE ReservationID = @MyReservationID;



GO

PRINT
'*********************************************************************
4B. 
Assume today is (July 24, 2015 at 10AM)*. Write an Update Statement 
to change the status of your Folio to "B"illed. 

Note: This should 
automatically invoke Trigger 1 above (NOT factoring in the late charge),
which automatically invokes 3 above, and calls sp_ProduceBill, 
and produces a bill.

*To simulate the current time as July 24, 2015 at 10AM, you will 
need to hard code the date value  7/24/2015 10:00:00 AM
*********************************************************************';

GO

-- get all the folioIDs and order them descending.  The most recent will be the first in the list.
DECLARE @MyReservationID int = (SELECT TOP 1 ReservationID
									FROM Person pe
									JOIN Reservation re
									ON pe.personID = re.personID
									WHERE pe.personLast = 'Brooks'
									ORDER BY ReservationID DESC);

UPDATE Folio
SET FolioStatus = 'B', FolioCheckInDate = '7/24/2015 10:00:00 AM'
WHERE ReservationID = @MyReservationID;

GO

PRINT
'*********************************************************************
4C. 
Assume today is (July 24, 2015 at 6PM)*. Write an Update Statement to 
change the status of your Folio to "B"illed. 

Note: This should automatically invoke Trigger 1 above (factoring in 
the late charge), which automatically invokes 3 above, and calls 
sp_ProduceBill, and produces a bill.

*To simulate the current time as July 24, 2015 at 6PM, you will need 
to hard code the date value  7/24/2015 6:00:00 PM
*********************************************************************';

GO

-- get all the folioIDs and order them descending.  The most recent will be the first in the list.
DECLARE @MyReservationID int = (SELECT TOP 1 ReservationID
									FROM Person pe
									JOIN Reservation re
									ON pe.personID = re.personID
									WHERE pe.personLast = 'Brooks'
									ORDER BY ReservationID DESC);

UPDATE Folio
SET FolioStatus = 'B', FolioCheckInDate = '7/24/2015 6:00:00 PM'
WHERE ReservationID = @MyReservationID;

GO

PRINT
'*********************************************************************
4D. 
Run a SELECT statement on the FolioTransaction Table showing your 
Triggers worked.
*********************************************************************';

GO
DECLARE @MyReservationID int = (SELECT TOP 1 ReservationID
									FROM Person pe
									JOIN Reservation re
									ON pe.personID = re.personID
									WHERE pe.personLast = 'Brooks'
									ORDER BY ReservationID DESC);

SELECT * FROM FolioTransaction WHERE FolioID = (SELECT FolioID 
												FROM Folio
												WHERE ReservationID = @MyReservationID);

GO

PRINT
'*********************************************************************
5. 
Write a trigger called tr_InsertReservationDetail that will automatically 
insert the Quoted Rack Rate (obtained from dbo.fn_GetQuotedRate*) into the 
ResQuotedRate field (in lieu of the users input), when an insert (only) 
is made into the RESERVATION table.  

*getting the PropertyID by joining the UnitRateID from the inserted 
table to the UnitRate (UnitRateID).
*********************************************************************';

GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_InsertReservationDetail')
	DROP TRIGGER tr_InsertReservationDetail;

GO
CREATE TRIGGER tr_InsertReservationDetail ON Reservation
AFTER INSERT
AS
BEGIN
	DECLARE @ReservationID  int			= (SELECT ReservationID FROM Inserted);
	DECLARE @PropertyID		int			= (SELECT PropertyID 
											FROM Inserted i 
											JOIN UnitRate ur 
											ON i.UnitRateID = ur.UnitRateID
											WHERE ur.unitRateID = i.UnitRateID);
	DECLARE @BeginDate		date		= (SELECT UnitRateBeginDate 
											FROM Inserted i 
											JOIN UnitRate ur 
											ON i.UnitRateID = ur.UnitRateID
											WHERE ur.unitRateID = i.UnitRateID);
	DECLARE @EndDate		date		= (SELECT UnitRateEndDate 
											FROM Inserted i 
											JOIN UnitRate ur 
											ON i.UnitRateID = ur.UnitRateID
											WHERE ur.unitRateID = i.UnitRateID);
	DECLARE @UnitTypeID		int			= (SELECT UnitTypeID 
											FROM Inserted i 
											JOIN UnitRate ur 
											ON i.UnitRateID = ur.UnitRateID
											WHERE ur.unitRateID = i.UnitRateID);

	DECLARE @QuotedRackRate smallmoney	= (SELECT MaximumUnitRate 
										FROM dbo.fn_GetQuotedRate(@PropertyID, @BeginDate, @EndDate, @UnitTypeID));
										
	UPDATE Reservation
	SET ResQuotedRate = @QuotedRackRate
	WHERE ReservationID = @ReservationID;
END

GO

PRINT 
'*********************************************************************
5A. 
Demonstrate this trigger by entering a reservation. Go ahead and supply 
quoted rate (any rate). The INSTEAD OF trigger should overwrite 
your entry with the correct Rack Rate.
*********************************************************************';

GO

DECLARE @TodaysDate datetime = CAST(GETDATE() AS SMALLDATETIME);
DECLARE @LastPersonIDEntered int = (SELECT TOP 1 PersonID FROM Person ORDER BY PersonID DESC);

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
		@TodaysDate,
		'A',
		'7/21/2015',
		4,
		100000,
		100000,
		'Pending',
		15,
		@LastPersonIDEntered
		);

GO

PRINT
'*********************************************************************
5B:  
Select * From Reservation to show the system overrode your rate.
*********************************************************************';

GO


Select * From Reservation