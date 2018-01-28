USE BROOKS_TRAMS

GO


BEGIN TRANSACTION -- for a rollback later

PRINT '------------------------------------------------------------------------------------
   Beginning #1 - Displaying all the UnitRateIDs, UnitRates, UnitTypeDescriptions, 
   and the UnitTypeIDs valid on 7 July 2015 at propertyID 11000
------------------------------------------------------------------------------------';

GO

SELECT distinct ur.UnitRateID, ur.UnitRate, ut.UnitTypeDescription, ur.UnitTypeID
FROM UnitType ut
	JOIN UnitRate ur
	ON ut.UnitTypeID = ur.UnitTypeID
	JOIN Reservation r
	ON ur.UnitRateID = r.UnitRateID
WHERE PropertyID = 11000
	AND UnitRateBeginDate < '2015-07-07'
	AND UnitRateEndDate > '2015-07-07';

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #2 - Finding the lodging TaxRate (type L) for propertyID 11000
   valid on 7 July 2015
-----------------------------------------------------------------------------------';

GO

SELECT distinct TaxRate
FROM UnitRate ur
	JOIN Property pr
	ON ur.PropertyID = pr.PropertyID
	JOIN TaxLocation tl
	ON pr.TaxLocationID = tl.TaxLocationID
	JOIN TaxRate tr
	ON tl.taxLocationID = tr.taxLocationID
WHERE TaxType = 'L'
	AND ur.PropertyID = 11000
	AND TaxStartDate <= 'July 7 2015'
	AND TaxEndDate >= 'July 7 2015';

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #3  - Inserting myself into the Person table
             #3B - Creating a reservation for myself at the Grand Oasis this summer.
			 #3C - SELECT * FROM RESERVATION to show the changes.
-----------------------------------------------------------------------------------';

GO

INSERT INTO Person (PersonFirst,		PersonLast,		PersonAddress,		PersonCity,		PersonState, 
					PersonPostalCode,	PersonCountry,	PersonPhone,		PersonEmail)
			VALUES ('Nathan',			'Brooks',		'2907 Adams Ave',	'Ogden',		'UT',
					84403,				'USA',			'435-225-3728',		'nathanbrooks1@mail.weber.edu');


GO

-- I wasn't sure what my PersonID would be so I wrote a subquery using 'my phone number' to find it.
INSERT INTO Reservation 
		(ResDate,			ResStatus,	ResCheckInDate,	ResNights,	ResQuotedRate,
		ResDepositPaid,		ResCCAuth,	UnitRateID,		PersonID)
VALUES	(GetDate(),			'A',		'7 July 2015',	3,			(209.95 * 11.25),
		(209.95 * 11.25),	'Pending',	15,				(SELECT PersonID
														 FROM Person 
														 WHERE PersonPhone = '435-225-3728'));	

GO

SELECT * 
FROM RESERVATION;

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #4a - Updating Zen Garden Resort"s pricing for all units:
					High season rate down 5%
					Standard season rate up 5%
             #4B - SELECT * FROM UNITRATE WHERE PropertyID = 16000 to show the changes.
-----------------------------------------------------------------------------------';

GO

-- down 5%
UPDATE UnitRate
SET UnitRate = FLOOR(UnitRate * 0.95)
WHERE PropertyID = 16000
	AND UnitRateDescription = 'High Season Rate';

-- up 5%
UPDATE UnitRate
SET UnitRate = CEILING(UnitRate * 1.05)
WHERE PropertyID = 16000
	AND UnitRateDescription = 'Standard Rate';

GO

SELECT *
FROM UnitRate
WHERE PropertyID = 16000;

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #5. Displaying the property name, unity type dscription, and rental rack rate
                 based on today"s date of all studio and 1 bedroom units (type 1 and 2)
				 with a price at or below $199.00 per night
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_5]') IS NOT NULL
	DROP VIEW [View_5]

GO

CREATE VIEW [View_5] 
	(UnitRate, UnitRateBeginDate, UnitRateEndDate, UnitTypeDescription, PropertyName, UnitTypeID) 
AS
SELECT	UnitRate, UnitRateBeginDate, UnitRateEndDate, UnitTypeDescription, PropertyName, ut.UnitTypeID
FROM UnitRate ur
	JOIN UnitType ut
	ON ur.UnitTypeID = ut.UnitTypeID
	JOIN Property pr
	ON ur.PropertyID = pr.PropertyID;

GO

SELECT PropertyName, UnitTypeDescription, '$' + CONVERT(varchar(12), UnitRate, 1) AS UnitRate
FROM [View_5]
WHERE UnitRateBeginDate < GetDate()
	AND UnitRateEndDate > GetDate()
	AND UnitTypeID IN (1, 2)
	AND UnitRate < 199;

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #6. For each property, list the (first word only of the) property name and the 
count of units in each of those properties that have a “Refrigerator” of "Fridge"
(any type) as a unit amenity. Hint: Use Substring/Len/PatIndex Function(s).
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_6]') IS NOT NULL
	DROP VIEW [View_6]

GO

CREATE VIEW [View_6] 
	(UnitID, PropertyName, AmenityDescription)
AS
SELECT un.UnitID, PropertyName, am.AmenityDescription
FROM Amenity am
	JOIN UnitAmenity ua
	ON am.AmenityID = ua.AmenityID
	JOIN Unit un
	ON ua.UnitID = un.UnitID
	JOIN UnitType ut
	ON un.UnitTypeID = ut.UnitTypeID
	JOIN UnitRate ur
	ON ut.UnitTypeID = ur.UnitTypeID
	JOIN Property pr
	ON ur.PropertyID = pr.PropertyID

GO

SELECT SUBSTRING(PropertyName,1,(CHARINDEX(' ',PropertyName + ' ')-1)) AS PropertyNameFirstWord, 
	COUNT(*) AS NumUnitsWithFridge
FROM [View_6]
WHERE LOWER(AmenityDescription) LIKE '%fridge%'
	OR LOWER(AmenityDescription) LIKE '%refrigerator%'
GROUP BY PropertyName;

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #7. Displaying the property name, the average length of stay for each
   property, for reservation check in dates that are in june.
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_7]') IS NOT NULL
	DROP VIEW [View_7]

GO

CREATE VIEW [View_7] 
	(PropertyName, ResCheckInDate, ResNights)
AS
SELECT PropertyName, ResCheckInDate, ResNights
FROM Property pr
	JOIN UnitRate ur
	ON pr.PropertyID = ur.PropertyID
	JOIN Reservation re
	ON ur.UnitRateID = re.UnitRateID

GO


	-- comment out the WHERE clause to see the rest of the properties' averages
	-- I used SUM and COUNT to make sure my math looked right
SELECT 
	PropertyName, 
	SUM(ResNights), 
	COUNT(*),
	CAST(CAST(SUM(ResNights) AS DECIMAL(10,2)) / CAST(COUNT(*) AS DECIMAL(10, 2)) AS DECIMAL(10, 2))
FROM [View_7]
WHERE DatePart(month, ResCheckInDate) = 6
GROUP BY PropertyName
ORDER BY PropertyName;

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #8. List the people, unit type descriptions, arrival dates, and departure dates
   for all reservations in which the arrival date is a Monday-Thursday.
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_8]') IS NOT NULL
	DROP VIEW [View_8]

GO

CREATE VIEW [View_8]
	(PersonFirst, PersonLast, UnitTypeDescription, ResCheckInDate, ResNights)
AS
SELECT PersonFirst, PersonLast, UnitTypeDescription, ResCheckInDate, ResNights 
FROM Person pe
	JOIN Reservation re
	ON pe.PersonID = re.PersonID
	JOIN UnitRate ur
	ON re.UnitRateID = ur.UnitRateID
	JOIN UnitType ut
	ON ur.UnitTypeID = ut.UnitTypeID

GO

SELECT 
	PersonLast + ', ' + PersonFirst AS PersonName, 
	UnitTypeDescription, 
	FORMAT(ResCheckInDate, 'MMM dd yyyy', 'en-US' ) AS CheckInDate, 
	--DATENAME(dw, ResCheckInDate),
	FORMAT(DATEADD(day, ResNights, ResCheckInDate), 'MMM dd yyyy', 'en-US' ) AS CheckOutDate
FROM [View_8]
WHERE DATENAME(dw, ResCheckInDate) IN ('Monday', 'Tuesday', 'Wednesday', 'Thursday');

GO



PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #9. Displaying the sum of all the 2015 folio transactions grouped by 
TransCategoryDescription and by Month.
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_9]') IS NOT NULL
	DROP VIEW [View_9]

GO

CREATE VIEW [View_9]
	(TransDate, TransCategoryDescription, TransAmount)
AS
SELECT TransDate, TransCategoryDescription, TransAmount
FROM TransCategory tc
	JOIN FolioTransaction ft
	ON tc.TransCategoryID = ft.TransCategoryID

GO

SELECT 
	DatePart(month, TransDate) AS Month,
	SUM(TransAmount) AS SumTransAmts2015, 
	TransCategoryDescription
FROM [View_9]
WHERE YEAR(TransDate) = 2015
GROUP BY TransCategoryDescription, DatePart(month, TransDate)
ORDER BY SUM(TransAmount) DESC;

GO

PRINT '';
PRINT '-----------------------------------------------------------------------------------
   Beginning #10. Displaying property name and property management fee total
   for each property in may 2015
-----------------------------------------------------------------------------------';

GO

IF OBJECT_ID('[View_10]') IS NOT NULL
	DROP VIEW [View_10]

GO

CREATE VIEW [View_10]
	(PropertyName, PropertyMgmtFee, TransAmount, TransTaxType, FolioCheckInDate)
AS
SELECT PropertyName, PropertyMgmtFee, TransAmount, TransTaxType, FolioCheckInDate
FROM Property pr
	JOIN UnitRate ur
	ON pr.PropertyID = ur.PropertyID
	JOIN Reservation re
	ON ur.UnitRateID = re.UnitRateID
	JOIN Folio fo
	ON re.ReservationID = fo.ReservationID
	JOIN FolioTransaction ft
	ON fo.FolioID = ft.FolioID
	JOIN TransCategory tc
	ON ft.TransCategoryID = tc.TransCategoryID

GO

SELECT PropertyName, format((sum(TransAmount) * (PropertyMgmtFee) / 100) + sum(TransAmount), 'c', 'en-us')
FROM [View_10]
WHERE TransTaxType = 'L'
	AND YEAR(FolioCheckInDate) = 2015
GROUP BY PropertyName, PropertyMgmtFee;

GO

ROLLBACK