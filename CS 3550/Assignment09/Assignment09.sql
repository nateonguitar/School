--Assignment 09 DISTRIBUTED QUERIES
--Nathan Brooks March 23th, 2016

/*
	TRAMS, Inc is merging with another company in Thailand, which owns 3 Properties. 
	Their database is called Thai_HOBS (Hotel On-Line Booking System), and their 
	tables/relationships are a little different than our TRAMS system.
 
	Since you don't have time to convert/merge their existing data into your 
	TRAMS database, you will use a linked server and distributed ad-hoc queries 
	against their remote data source.

	Your assignment is to create a script (which MUST BE ran from a LOCAL 
	instance of SQL Server).
*/

PRINT
'******************************************************************************
1.  
Start your script so you create YourLastName_TRAMS on a LOCAL instance of SQL 
server (do not bother with paths and log files - just issue the standard single 
line command CREATE DATABASE (without parameters), as I will be running this on 
my LOCAL machine), and using my MODEL to establish the size and location of 
the database and where it is physically stored.
******************************************************************************';
GO

USE Master

IF EXISTS (SELECT * FROM sysdatabases WHERE name='Brooks_TRAMS')
DROP DATABASE Brooks_TRAMS

GO   

CREATE DATABASE Brooks_TRAMS

GO


PRINT
'******************************************************************************
2.  
Remember to populate your local database with the sample data files.  

I suggest putting the sample data files in a folder called 
c:\stage on your machine (actually, you can put them anywhere on your machine, 
but when I run your script on my local machine, it will be looking 
for the c:\stage directory). 
******************************************************************************'

GO

USE Brooks_TRAMS

--!!!!!! Create Tables !!!!!!
-- tables without FKs
CREATE TABLE Person
(
	PersonID			int					NOT NULL	IDENTITY(1,1),
	PersonFirst			nvarchar(50)		NOT NULL,
	PersonLast			nvarchar(50)		NOT NULL,
	PersonAddress		varchar(200)		NOT NULL,
	PersonCity			varchar(50)			NOT NULL,
	PersonState			char(2)				NULL,
	PersonPostalCode	varchar(10)			NOT NULL,
	PersonCountry		varchar(20)			NOT NULL,
	PersonPhone			varchar(20)			NOT NULL,
	PersonEmail			varchar(200)		NOT NULL
)

CREATE TABLE Amenity
(
	AmenityID			smallint			NOT NULL	IDENTITY(1,1),
	AmenityDescription	varchar(50)			NOT NULL
)

CREATE TABLE UnitType
(
	UnitTypeID			tinyint				NOT NULL	IDENTITY(1,1),
	UnitTypeDescription varchar(20)			NOT NULL
)

CREATE TABLE TaxLocation
(
	TaxLocationID		smallint			NOT NULL	IDENTITY(1,1),
	TaxCounty			varchar(50)			NOT NULL,
	TaxState			char(2)				NOT NULL
)

CREATE TABLE TransCategory
(
	TransCategoryID		smallint			NOT NULL	IDENTITY(1,1),
	TransCategoryDescription varchar(50)	NOT NULL,
	TransTaxType		char(1)				NOT NULL
)

CREATE TABLE Unit
(
	UnitID				smallint			NOT NULL,
	UnitNumber			varchar(5)			NOT NULL,
	PropertyID			smallint			NULL,
	UnitTypeID			tinyint				NOT NULL
)

CREATE TABLE TaxRate
(
	TaxID				int					NOT NULL	IDENTITY(1,1),
	TaxRate				decimal(5,3)		NOT NULL,
	TaxType				char(1)				NOT NULL,
	TaxDescription		varchar(50)			NOT NULL,
	TaxStartDate		date				NOT NULL,
	TaxEndDate			date				NULL,
	TaxLocationID		smallint			NOT NULL
)

CREATE TABLE Folio
(
	FolioID				int					NOT NULL	IDENTITY(1,1),
	FolioStatus			char(1)				NOT NULL,
	FolioRate			smallmoney			NOT NULL,
	FolioCheckInDate	smalldatetime		NOT NULL,
	FolioCheckOutDate	smalldatetime		NOT NULL,
	UnitID				smallint			NOT NULL,
	ReservationID		int					NOT NULL
)

CREATE TABLE Property
(
	PropertyID			smallint			NOT NULL,
	PropertyName		varchar(50)			NOT NULL,
	PropertyAddress		varchar(200)		NOT NULL,
	PropertyCity		varchar(50)			NOT NULL,
	PropertyState		char(2)				NOT NULL,
	PropertyPostalCode	varchar(10)			NOT NULL,
	PropertyCountry		varchar(20)			NOT NULL,
	PropertyPhone		varchar(20)			NOT NULL,
	PropertyMgmtFee		decimal(4,2)		NOT NULL,
	PropertyWebAddress	varchar(100)		NULL,
	TaxLocationID		smallint			NULL
)

CREATE TABLE Reservation
(
	ReservationID		int					NOT NULL	IDENTITY(1,1),
	ResDate				smalldatetime		NOT NULL,
	ResStatus			char(1)				NOT NULL,
	ResCheckInDate		date				NOT NULL,
	ResNights			tinyint				NOT NULL,
	ResQuotedRate		smallmoney			NOT NULL,
	ResDepositPaid		smallmoney			NOT NULL,
	ResCCAuth			varchar(25)			NOT NULL,
	UnitRateID			smallint			NOT NULL,
	PersonID			int					NOT NULL
)

CREATE TABLE UnitRate
(
	UnitRateID			smallint			NOT NULL	IDENTITY(1,1),
	UnitRate			smallmoney			NOT NULL,
	UnitRateBeginDate	date				NOT NULL,
	UnitRateEndDate		date				NOT NULL,
	UnitRateDescription varchar(50)			NULL,
	UnitRateActive		bit					NOT NULL,
	PropertyID			smallint			NOT NULL,
	UnitTypeID			tinyint				NOT NULL
)

CREATE TABLE FolioTransaction
(
	TransID				bigint				NOT NULL	IDENTITY(1,1),
	TransDate			datetime			NOT NULL,
	TransAmount			smallmoney			NOT NULL,
	TransDescription	varchar(50)			NOT NULL,
	TransCategoryID		smallint			NOT NULL,
	FolioID				int					NOT NULL
)

CREATE TABLE PropertyAmenity
(
	AmenityID			smallint			NOT NULL,
	PropertyID			smallint			NOT NULL
)

CREATE TABLE UnitAmenity
(
	AmenityID			smallint			NOT NULL,
	UnitID				smallint			NOT NULL
)

CREATE TABLE UnitOwner
(
	UnitID				smallint			NOT NULL,
	PersonID			int					NOT NULL,
	OwnerStartDate		date				NOT NULL,
	OwnerEndDate		date				NULL
)


-- !!!!!! constraints !!!!!!
-- Primary Keys
ALTER TABLE Person
	ADD CONSTRAINT PK_PersonID
	PRIMARY KEY(PersonID)

ALTER TABLE Amenity
	ADD CONSTRAINT PK_AmenityID
	PRIMARY KEY(AmenityID)

ALTER TABLE UnitType
	ADD CONSTRAINT PK_UnitTypeID
	PRIMARY KEY(UnitTypeID)

ALTER TABLE TaxLocation
	ADD CONSTRAINT PK_TaxLocationID
	PRIMARY KEY(TaxLocationID)

ALTER TABLE TransCategory
	ADD CONSTRAINT PK_TransCategoryID
	PRIMARY KEY(TransCategoryID)

ALTER TABLE Unit
	ADD CONSTRAINT PK_UnitID
	PRIMARY KEY(UnitID)

ALTER TABLE TaxRate
	ADD CONSTRAINT PK_TaxID
	PRIMARY KEY(TaxID)

ALTER TABLE Folio
	ADD CONSTRAINT PK_FolioID
	PRIMARY KEY(FolioID)

ALTER TABLE Property
	ADD CONSTRAINT PK_PropertyID
	PRIMARY KEY(PropertyID)

ALTER TABLE Reservation
	ADD CONSTRAINT PK_ReservationID
	PRIMARY KEY(ReservationID)

ALTER TABLE UnitRate
	ADD CONSTRAINT PK_UnitRateID
	PRIMARY KEY(UnitRateID)

ALTER TABLE FolioTransaction
	ADD CONSTRAINT PK_FolioTransactionID
	PRIMARY KEY(TransID)

ALTER TABLE PropertyAmenity
	ADD CONSTRAINT PK_PropertyAmenity
	PRIMARY KEY(AmenityID, PropertyID)

ALTER TABLE UnitAmenity
	ADD CONSTRAINT PK_UnitAmenity
	PRIMARY KEY(AmenityID, UnitID)

ALTER TABLE UnitOwner
	ADD CONSTRAINT PK_UnitOwner
	PRIMARY KEY(UnitID, PersonID)

GO

-- Foreign Keys
ALTER TABLE UnitOwner
	ADD
	CONSTRAINT FK_UnitOwnerIsAPerson
	FOREIGN KEY(PersonID) REFERENCES Person(PersonID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_UnitOwnerOwnsAUnit
	FOREIGN KEY(UnitID) REFERENCES Unit(UnitID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE Reservation
	ADD
	CONSTRAINT FK_PersonMakesAReservation
	FOREIGN KEY(PersonID) REFERENCES Person(PersonID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_ReservationOfAUnitHasAUnitRate
	FOREIGN KEY(UnitRateID) REFERENCES UnitRate(UnitRateID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE Unit
	ADD
	CONSTRAINT FK_UnitTypeHasAUnit
	FOREIGN KEY(UnitTypeID) REFERENCES UnitType(UnitTypeID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE UnitRate
	ADD
	CONSTRAINT FK_PropertyHasAUnitRate
	FOREIGN KEY(PropertyID) REFERENCES Property(PropertyID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_UnitTypeHasAUnitRate
	FOREIGN KEY(UnitTypeID) REFERENCES UnitType(UnitTypeID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE UnitAmenity
	ADD
	CONSTRAINT FK_UnitHasAUnitAmenity
	FOREIGN KEY(UnitID) REFERENCES Unit(UnitID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_AmenityHasAUnitAmenity
	FOREIGN KEY(AmenityID) REFERENCES Amenity(AmenityID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE Folio
	ADD
	CONSTRAINT FK_ReservationGeneratesFolio
	FOREIGN KEY(ReservationID) REFERENCES Reservation(ReservationID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE Property
	ADD
	CONSTRAINT FK_PropertyIsLocatedInATaxLocation
	FOREIGN KEY(TaxLocationID) REFERENCES TaxLocation(TaxLocationID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE PropertyAmenity
	ADD
	CONSTRAINT FK_PropertyHasAPropertyAmenity
	FOREIGN KEY(PropertyID) REFERENCES Property(PropertyID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_AmenityHasAPropertyAmenity
	FOREIGN KEY(AmenityID) REFERENCES Amenity(AmenityID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE FolioTransaction
	ADD
	CONSTRAINT FK_TransCategoryHasAFolioTransaction
	FOREIGN KEY(TransCategoryID) REFERENCES TransCategory(TransCategoryID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_FolioContainsFolioTransaction
	FOREIGN KEY(FolioID) REFERENCES Folio(FolioID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE TaxRate
	ADD
	CONSTRAINT FK_TaxLocationHasATaxRate
	FOREIGN KEY(TaxLocationID) REFERENCES TaxLocation(TaxLocationID)
	ON UPDATE Cascade
	ON DELETE Cascade
	
GO

-- check constraints 
ALTER TABLE Reservation
	ADD CONSTRAINT CK_ValidResStatus
	CHECK(UPPER(ResStatus) IN ('A', 'C', 'X'))

ALTER TABLE Folio
	ADD CONSTRAINT CK_ValidFolioStatus
	CHECK(UPPER(FolioStatus) IN ('B', 'C', 'X'))

ALTER TABLE TransCategory
	ADD CONSTRAINT CK_ValidTransTaxType
	CHECK(UPPER(TransTaxType) IN ('N', 'L', 'G', 'F'))

ALTER TABLE TaxRate
	ADD CONSTRAINT CK_ValidTaxType
	CHECK(UPPER(TaxType) IN ('L', 'G', 'F'))



-- this fixed my issue of, when I ran this script more 
-- than once, it wanted me to give the PersonID on insert...
-- even though I dropped the database if it existed....strange
SET IDENTITY_INSERT Person OFF 



-- !!!!!! Populate Tables !!!!!!
BULK INSERT Person			FROM 'C:\Stage\Person.txt'			WITH (FIELDTERMINATOR='|', DATAFILETYPE='widechar')
BULK INSERT Property		FROM 'C:\Stage\Property.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT UnitType		FROM 'C:\Stage\UnitType.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT Unit			FROM 'C:\Stage\Unit.txt'			WITH (FIELDTERMINATOR='|')
BULK INSERT UnitOwner		FROM 'C:\Stage\UnitOwner.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT UnitRate		FROM 'C:\Stage\UnitRate.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT TaxLocation		FROM 'C:\Stage\TaxLocation.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT TaxRate			FROM 'C:\Stage\TaxRate.txt'			WITH (FIELDTERMINATOR='|')
BULK INSERT Amenity			FROM 'C:\Stage\Amenity.txt'			WITH (FIELDTERMINATOR='|')
BULK INSERT PropertyAmenity	FROM 'C:\Stage\PropertyAmenity.txt'	WITH (FIELDTERMINATOR='|')
BULK INSERT UnitAmenity		FROM 'C:\Stage\UnitAmenity.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT TransCategory	FROM 'C:\Stage\TransCategory.txt'	WITH (FIELDTERMINATOR='|')
BULK INSERT Reservation		FROM 'C:\Stage\Reservation.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT Folio			FROM 'C:\Stage\Folio.txt'			WITH (FIELDTERMINATOR='|')
BULK INSERT FolioTransaction FROM 'C:\Stage\FolioTransaction.txt' WITH (FIELDTERMINATOR='|')


GO

PRINT
'******************************************************************************
3. 
In your script, USE MASTER, then add a linkedserver called TITAN_YOURLASTNAME 
(make sure its underscored, not hyphened) that points to the the Titan Server.  

Its important you name this correctly, as I run this on MY local machine, and 
will create a linked server for all students, so I do not want duplication for 
linked server names.
******************************************************************************'

GO

USE MASTER


IF EXISTS(SELECT * FROM sys.servers WHERE name = N'TITAN_Brooks')
BEGIN	
	exec sp_droplinkedsrvlogin 'TITAN_Brooks' , 'DESKTOP-03OFHNJ\theds'	
	exec sp_dropserver 'TITAN_Brooks'
END

Exec sp_addlinkedserver
@server='TITAN_Brooks',
@srvproduct='',
/*this is the product name of the OLE DB data source to add as a linked server.*/
@provider='MSDASQL',
@provstr='DRIVER={SQL Server};SERVER=titan.cs.weber.edu,10433;UID=Thai_User;PWD=Thai_Test;Initial Catalog=Thai_HOBS'

Exec sp_serveroption 'TITAN_BROOKS', 'data access', 'true'
Exec sp_serveroption 'TITAN_BROOKS', 'rpc', 'true'--enables from the REMOTE to LOCAL server
Exec sp_serveroption 'TITAN_BROOKS', 'rpc out', 'true' -- enables from the LOCAL to REMOTE server
Exec sp_serveroption 'TITAN_BROOKS', 'collation compatible', 'true'

GO

PRINT
'******************************************************************************
4. 
Add a linked server login, and map MY* local login id Rich-PC\Richard to the remote 
login: Thai_User (with password: Thai_Test) already created for you in the 
Thai_HOBS database on Titan.  
******************************************************************************';

GO

Exec sp_addlinkedsrvlogin 
@rmtsrvname = 'TITAN_Brooks',
@useself = 'false',
@locallogin = 'DESKTOP-03OFHNJ\theds',
@rmtuser= 'Thai_User',
@rmtpassword = 'Thai_Test'

GO

EXEC sp_configure 'show advanced options', 1
reconfigure
GO

EXEC sp_configure 'Ad Hoc Distributed Queries', 1
reconfigure
GO

PRINT
'******************************************************************************
5. 
Using the Linked Server, perform the following queries on the Thai_HOBS database, 
to determine the layout and contents of these tables on titan.

SELECT * FROM Hotels
SELECT * FROM Bookings
SELECT * FROM Guests
SELECT * FROM Rooms
SELECT * FROM TaxRates
******************************************************************************';

USE Brooks_TRAMS

GO

SELECT * FROM OPENQUERY(TITAN_Brooks, 'SELECT * FROM Hotels');
SELECT * FROM OPENQUERY(TITAN_Brooks, 'SELECT * FROM Bookings');
SELECT * FROM OPENQUERY(TITAN_Brooks, 'SELECT * FROM Guests');
SELECT * FROM OPENQUERY(TITAN_Brooks, 'SELECT * FROM Rooms');
SELECT * FROM OPENQUERY(TITAN_Brooks, 'SELECT * FROM TaxRates');

GO

PRINT
'******************************************************************************
6A. 
Continue your script by USING YOUR DATABASE (not Master) and creating a modified 
version of the stored procedure (sp_InsertPerson) you created in HW 6.  

YOU MUST RENAME this modified stored procedure to sp_InsertPerson_YOURLASTNAME 
(because I will be adding several student stored procedures on MY computer and 
they cannot all have the same name).  

This modified stored procedure should INSERT INTO the Thai_HOBS database instead 
of your own TRAMS database (using Linked Server).
******************************************************************************';

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_InsertPerson_Brooks')
	DROP PROCEDURE sp_InsertPerson_Brooks;

GO

CREATE PROC sp_InsertPerson_Brooks
	@PersonFirst		nvarchar(50),
	@PersonLast			nvarchar(50),
	@PersonAddress		varchar(200),
	@PersonCity			varchar(50),
	@PersonPostalCode	varchar(10)
AS
BEGIN
	-- all the collumns from SELECT * FROM Guest
	--GuestNo, GuestFirstName, GuestLastName, GuestAddress, GuestCity, GuestPostalCode
	INSERT OPENQUERY(TITAN_Brooks, 'SELECT GuestFirstName, GuestLastName, GuestAddress, GuestCity, GuestPostalCode FROM Thai_HOBS.dbo.Guests')
	VALUES(@PersonFirst, @PersonLast, @PersonAddress, @PersonCity, @PersonPostalCode);
END

GO

PRINT
'******************************************************************************
6B. 
Demonstrate this stored procedure by executing it, and adding yourself as a guest 
to the Thai_HOBS database. Note: You may need to set RPC IN/OUT on your linked 
server (I have already done it on Thai_HOBS).
******************************************************************************';

GO

DECLARE @TodaysDate datetime
SET @TodaysDate = CAST(GETDATE() AS SMALLDATETIME);

EXEC sp_InsertPerson_Brooks
	@PersonFirst		= 'Nathan',
	@PersonLast			= 'Brooks',
	@PersonAddress		= '2907 Adams Ave',
	@PersonCity			= 'Ogden',
	@PersonPostalCode	= 84403
GO

PRINT
'******************************************************************************
7. 
Using OpenQuery, Write a statement that SELECTS information containing the 
Property (or Hotel) Name, Room (or Unit) Type, and the formatted and 
converted ($*) rate for all the rooms from each hotel in BOTH databases 

(Hint:  Simple UNION to group two separate query results together and sort 
by Property / Hotel Name).  Since Thai_HOBS does not use dates for their 
rates, do not include them when you do the TRAMS Rates (or else union will 
not work, because the number of columns will be off).  Rather, just filter 
on the rates using an arbitrary date like 1 July 2015.
******************************************************************************';

GO

SELECT Name, UnitType, '$' + CONVERT(varchar(20), Rate) as Rate
FROM 
	(SELECT * FROM OPENQUERY(TITAN_Brooks,  
		'SELECT HotelName AS Name, RoomType AS UnitType, RoomRackRate AS Rate
		FROM Hotels h 
			JOIN Rooms r 
			ON h.HotelNo = r.HotelNo'
	) 
	UNION
	SELECT PropertyName AS Name, UnitTypeDescription AS UnitType, UnitRate AS rate
	FROM Property pr
		JOIN UnitRate ur
		ON pr.PropertyID = ur.PropertyID
		JOIN UnitType ut
		ON ur.UnitTypeID = ut.UnitTypeID) AS t2
ORDER BY Name;

GO

PRINT
'******************************************************************************
8. 
Using OpenRowSet, Write a query that SELECTS the ReservationID (called BookingID 
in the Thai Database), Guest Last Name, Formatted Quoted Rate*, and Check-in Date 
from Both Databases, and presents all the details together, sorted by Guest Last Name.
******************************************************************************';
GO

SELECT ReservationID, LastName, '$' + CONVERT(varchar(20), QuotedRate) as QuotedRate
FROM
(SELECT ReservationID, PersonLast as LastName, ResQuotedRate as QuotedRate
FROM Reservation r
	JOIN Person p
	ON p.PersonID = r.PersonID
UNION
SELECT *
FROM OPENROWSET(
	'MSDASQL', 
	'DRIVER={SQL Server};SERVER=titan.cs.weber.edu,10433;UID=Thai_User;PWD=Thai_Test', 

	'Select BookingID as ReservationID, GuestLastName as LastName, QuotedRate 
	 From Thai_HOBS.dbo.Guests g 
	 JOIN Bookings b
		ON g.GuestNo = b.GuestNo')) t1
ORDER BY LastName;

GO
PRINT
'******************************************************************************
9.  
Finally, USE MASTER AND write a statement to DROP YOUR DATABASE and DROP THE 
LINKED SERVER WITH LOGINS (just replace student name ----- 
sp_dropserver "TITAN_Student Name", "droplogins")
******************************************************************************';

GO

USE MASTER

GO

exec sp_droplinkedsrvlogin 'TITAN_Brooks' , 'Rich-PC\Richard'	
exec sp_dropserver 'TITAN_Brooks'

GO

USE Brooks_TRAMS

GO


PRINT
'

*****************
PART II XML
*****************

******************************************************************************
1. 
Apply OPENXML to this XML document, using Element-Centric mapping. 

Show guestid, guestfirst, guestlast, and Check-in ONLY for each guest. 
******************************************************************************';

GO

DECLARE @idoc int;
DECLARE @xmldoc varchar(4000);

SET @xmldoc = 
'<ROOT>
	<GUEST>
		<GuestID>2331</GuestID>
		<GuestFirst>Braden</GuestFirst>
		<GuestLast>Gramse</GuestLast>
		<RESERVATIONDETAIL>
			<CheckInDate>2016-04-02</CheckInDate>
			<Nights>2</Nights>
		</RESERVATIONDETAIL>
	</GUEST>

	<GUEST>
		<GuestID>2263</GuestID>
		<GuestFirst>Danyell</GuestFirst>
		<GuestLast>Reno</GuestLast>
		<RESERVATIONDETAIL>
			<CheckInDate>2016-04-05</CheckInDate>
			<Nights>2</Nights>
		</RESERVATIONDETAIL>
	</GUEST>

	<GUEST>
		<GuestID>2280</GuestID>
		<GuestFirst>Brady</GuestFirst>
		<GuestLast>Adair</GuestLast>
		<RESERVATIONDETAIL>
			<CheckInDate>2015-06-11</CheckInDate>
			<Nights>3</Nights>
		</RESERVATIONDETAIL>
	</GUEST>
 </ROOT>';

EXEC sp_xml_preparedocument @idoc OUTPUT, @xmldoc


PRINT
'******************************************************************************
2. 
Using the same raw XML above, use OPENXML to Insert the XMLs GuestId, GuestFirst, 
and GuestLast (Plus remaining fake  data values for these required fields 
(Address, City, Postal Code, Country, Phone, E-mail) into your TRAMS Person Table.

Note: You will also need to SET IDENTITY_INSERT ON PersonID, to override the auto 
IDENTITY so you can manually insert the PersonID.
******************************************************************************';


SET IDENTITY_INSERT Person ON --required so you can overwrite Identity

INSERT INTO Person (PersonID, PersonFirst, PersonLast, PersonAddress,     PersonCity, PersonPostalCode, PersonCountry, PersonPhone,    PersonEmail)
SELECT              GuestID,  GuestFirst,  GuestLast,  '123 Dummy Lane', 'City Park', '90210',          'US',          '123-456-7890', 'rock@star.com'
FROM OPENXML (@idoc, '/ROOT/GUEST', 2) --2 is element centric
WITH
(
	GuestID			int			'GuestID',
	GuestFirst		varchar(30) 'GuestFirst',
	GuestLast		varchar(30)	'GuestLast'
)

EXEC sp_xml_removedocument @idoc

GO

PRINT
'******************************************************************************
2A.  
Write a SELECT * FROM Person to prove the inserts took place.
******************************************************************************';

GO

SELECT * FROM Person;

GO