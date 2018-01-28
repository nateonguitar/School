--Assignment 07 learning User Defined Functions
--Nathan Brooks March 6th, 2016
--
-- In this assignment, you will continue to develop data components 
-- for the TRAMS database.  The purpose of these user defined functions 
-- are to help in planning vacancies, reservations, and revenue for the 
-- various properties.  Use your freshly created (and populated) database 
-- on TITAN:  YOURLASTNAME_TRAMS to run your script against.
-- 
-- I usually used PRINT statements to comment my work so I can 
-- see my comments in the output as well as in the code

USE BROOKS_TRAMS

GO


PRINT 
'**********************************************************************************************
	1.  
	Write a user-defined function named dbo.GetLodgingTaxRate which accepts a PropertyID 
	and date. The function will return the taxrate (as decimal) for the PropertyID where the 
	taxtype = "L" (for Lodging Tax), and the date falls between the start and end dates of 
	the taxrate found.  Note:  If there is not a lodging type tax rate for the property 
	(or the date is out of range), return 0 for the taxrate.
**********************************************************************************************'

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
'**********************************************************************************************
	2.
	Write a user-defined function named dbo.CalculateDeposit which accepts a UnitRateID, and 
	Check-in Date.  The function will return smallmoney.  The calculated deposit is the 
	Current Unit Rate * (1+dbo.GetLodgingTaxRate(PropertyID, CheckinDate).  Note:  This 
	function must call function #1.
**********************************************************************************************'
GO

IF OBJECT_ID (N'dbo.CalculateDeposit', N'FN') IS NOT NULL
DROP FUNCTION dbo.CalculateDeposit;

GO

CREATE FUNCTION dbo.CalculateDeposit
       (
       @UnitRateID int,
       @checkInDate date
       )
RETURNS smallmoney
AS
	BEGIN
		

		-- get the PropertyID to pass into dbo.GetLodgingTaxRate()
		DECLARE @PropertyID int = (SELECT PropertyID FROM UnitRate WHERE UnitRateID = @UnitRateID)

		-- get the @currentUnitRate from the check-in date and the UnitRateID to use in some math later
		DECLARE @currentUnitRate smallmoney = 
			(SELECT UnitRate
			FROM UnitRate
			WHERE @checkInDate BETWEEN UnitRateBeginDate AND UnitRateEndDate
			AND UnitRateID = @UnitRateID)
			
			IF(@currentUnitRate IS NULL)
			BEGIN
				SET @currentUnitRate = 0
			END
			
		DECLARE @result smallmoney = @currentUnitRate * (1 + dbo.GetLodgingTaxRate(@PropertyID, @checkInDate))

		return @result
	END



GO

PRINT
'**********************************************************************************************
	The following will demonstrate the above functions....
**********************************************************************************************'

GO

PRINT
'**********************************************************************************************
	3A.
	Make a new Reservation using the following information:
		ResDate = Today
		ResStatus = "A"
		Check-in Date = "15 Aug 2015"
		Nights = 3
		Quoted Rate = dbo.CalculateDeposit
		Deposit Paid = dbo.CalculateDeposit
		CC Auth = "3A Reults"
		Unit Rate ID = 13
		Person ID = 7
**********************************************************************************************'
GO

INSERT INTO Reservation (ResDate, ResStatus, ResCheckInDate, ResNights, ResQuotedRate, ResDepositPaid, ResCCAuth, UnitRateID, PersonID)
VALUES (GetDate(), 'A', '15 Aug, 2015', 3, dbo.CalculateDeposit(13, '15 Aug 2015'), dbo.CalculateDeposit(13, '15 Aug 2015'), '3A Results', 13, 7);

GO
PRINT
'**********************************************************************************************
	3B.
	Make a new Reservation using the following information:
		ResDate = Today
		ResStatus = "A"
		Check-in Date = "27 Aug 2015"
		Nights = 3
		Quoted Rate = dbo.CalculateDeposit
		Deposit Paid = dbo.CalculateDeposit
		CC Auth = "3B Reults"
		Unit Rate ID = 21
		Person ID = 8
**********************************************************************************************'
GO

INSERT INTO Reservation (ResDate, ResStatus, ResCheckInDate, ResNights, ResQuotedRate, ResDepositPaid, ResCCAuth, UnitRateID, PersonID)
VALUES (GetDate(), 'A', '27 Aug 2015', 3, dbo.CalculateDeposit(13, '27 Aug 2015'), dbo.CalculateDeposit(13, '27 Aug 2015'), '3B Reults', 21, 8);

GO
PRINT
'**********************************************************************************************
	3C.
	Make a new Reservation using the following information:
		ResDate = Today
		ResStatus = "A"
		Check-in Date = "25 Sep 2015"
		Nights = 3
		Quoted Rate = dbo.CalculateDeposit
		Deposit Paid = dbo.CalculateDeposit
		CC Auth = "3C Reults"
		Unit Rate ID = 29
		Person ID = 9
**********************************************************************************************'
GO

INSERT INTO Reservation (ResDate, ResStatus, ResCheckInDate, ResNights, ResQuotedRate, ResDepositPaid, ResCCAuth, UnitRateID, PersonID)
VALUES (GetDate(), 'A', '25 Sep 2015', 3, dbo.CalculateDeposit(13, '25 Sep 2015'), dbo.CalculateDeposit(13, '25 Sep 2015'), '3C Reults', 29, 9);

GO
PRINT
'**********************************************************************************************
	3D.
	SELECT * FROM Reservation to show the results
**********************************************************************************************'
GO

SELECT * FROM Reservation

GO
PRINT
'**********************************************************************************************
	4A.
	Write a user-defined function named dbo.CalculateCancellationFees that accepts a 
	reservationID and CancellationDate, and returns a TABLE with the ReservationID, Original 
	Check in Date, Deposit Paid, and Cancellation Charge (determined below).  

	Cancellation Policies:

	Regardless of property or season, all cancellations made with more than 
	30 days’ notice are entitled to a 100% refund of the deposit paid (less a $25 administration fee).
	14-30 days’ cancellation notice (75% refund, less a $25 administration fee)
	8-13 days’ cancellation notice (50% refund, less a $25 administration fee)
	7 days of less cancellation notice – No refund
**********************************************************************************************'
GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'CalculateCancellationFees')
	DROP FUNCTION CalculateCancellationFees;

GO

CREATE FUNCTION dbo.CalculateCancellationFees
	(
	@ReservationID int,
	@CancellationDate date
	)
	RETURNS @table TABLE (
		ReservationID		int			NOT NULL,
		OriginalCheckInDate date		NOT NULL,
		DepositPaid			smallmoney	NOT NULL,
		CancellationCharge	smallmoney	NOT NULL
		)
	AS
		BEGIN
			DECLARE @OriginalCheckInDate date = (SELECT ResCheckInDate FROM Reservation WHERE ReservationID = @ReservationID)

			-- CalculateDeposit needs
			--	@UnitRateID int
			--	@checkInDate date
			DECLARE @UnitRateID int = (SELECT UnitRateID FROM Reservation WHERE ReservationID = @ReservationID)

			DECLARE @CheckInDate date = (SELECT ResCheckInDate FROM Reservation WHERE ReservationID = @ReservationID)

			DECLARE @DepositPaid smallmoney = dbo.CalculateDeposit(@UnitRateID, @CheckInDate)

			DECLARE @NumDaysBetween int = DATEDIFF(dd, @CancellationDate, @OriginalCheckInDate)
			
			DECLARE @CancellationCharge smallmoney = 
				CASE
					-- 30 days’ notice are entitled to a 100% refund of the deposit paid (less a $25 administration fee).
					WHEN @NumDaysBetween > 30
						THEN 25

					-- 14-30 days’ cancellation notice (75% refund, less a $25 administration fee)
					WHEN @NumDaysBetween BETWEEN 14 AND 30
						THEN (@DepositPaid * 0.25) + 25

					-- 8-13 days’ cancellation notice (50% refund, less a $25 administration fee)
					WHEN @NumDaysBetween BETWEEN 8 AND 13
						THEN (@DepositPaid * 0.5) + 25

					-- 7 days of less cancellation notice – No refund
					ELSE @DepositPaid
				END
			
			-- round down to nearest penny
			SET @CancellationCharge = (FLOOR((@CancellationCharge * 100)) / 100)

			INSERT INTO @table (ReservationID, OriginalCheckInDate, DepositPaid, CancellationCharge)
			VALUES(
				@ReservationID, 
				@OriginalCheckInDate,
				@DepositPaid,
				@CancellationCharge)
		
			RETURN 
		END
GO

PRINT
'**********************************************************************************************
	4B.
	Demonstrate 4A by passing in the last 3 reservations you created (in 3A, 3B, and 3C), 
	using a cancellation date of 14 Aug 2015.
**********************************************************************************************'
GO

SELECT * FROM dbo.CalculateCancellationFees(46, '14 Aug 2015')
SELECT * FROM dbo.CalculateCancellationFees(47, '14 Aug 2015')
SELECT * FROM dbo.CalculateCancellationFees(48, '14 Aug 2015')

GO
PRINT
'**********************************************************************************************
	5.
		Write a user-defined function called fn_QuotedRate that accepts a begin and end date as 
		well as a PropertyID and UnitTypeID and returns a Table called @ValidRates.  This table should 
		show the MAXIMUM UnitRate (if a date range crosses over multiple seasons/rates) as well as the 
		Unit Type Description,  and an additional field for the Unit Rate Description (or used to hold 
		a custom error message).

		For example:  If a unit has a rate of $100 between 1 Jan and 1 Feb, and $150 between 2 
		Feb and 28 Feb, and the UDF passes in a date range of 15 Jan to 7 Feb, then the $150 
		result would be returned.
**********************************************************************************************'
GO

GO

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'fn_QuotedRate')
	DROP FUNCTION fn_QuotedRate;

GO

CREATE FUNCTION dbo.fn_QuotedRate
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
'*********************************************************************************************
	5A.  
	Demonstrate the Function by passing in the Property ID 10000, Begin Date 1 July 2015,  
	End Date 30 November 2015, and UnitTypeID 4.
**********************************************************************************************'
GO

SELECT * FROM dbo.fn_QuotedRate(10000, '1 July 2015', '30 November 2015', 4)

GO
PRINT
'**********************************************************************************************
	5B.
	Demonstrate the Function by passing in the Property ID 11000, Begin Date 1 July 2015,  
	End Date 30 November 2015, and UnitTypeID 4.
**********************************************************************************************'
GO

SELECT * FROM dbo.fn_QuotedRate(11000, '1 July 2015', '30 November 2015', 4)

GO
PRINT
'**********************************************************************************************
	5C.
	Demonstrate the Function by passing in the Property ID 11000, Begin Date 30 November 2015,  
	End Date 1 July 2015*, and UnitTypeID 4.

	*Yes, these dates are incorrect, because I want you to demonstrate to me how to do 
	"error handling" in your UDF.
**********************************************************************************************'
GO

SELECT * FROM dbo.fn_QuotedRate(11000, '30 November 2015', '1 July 2015', 4)