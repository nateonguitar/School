USE MASTER


-- If the database already exists, drop it  
IF EXISTS(SELECT name 
		FROM sys.databases WHERE name='FRY_TRIGGERHOTEL')  
DROP DATABASE FRY_TRIGGERHOTEL  
GO

CREATE DATABASE FRY_TRIGGERHOTEL
ON
	(NAME = 'FRY_TRIGGERHOTEL',
	FILENAME = 'c:\stage\FRY_TRIGGERHOTEL.mdf',
	SIZE = 4MB,
	MAXSIZE = 4MB,
	FILEGROWTH = 1MB)
LOG ON
	(NAME = 'FRY_HOTELLOG',
	FILENAME = 'c:\stage\FRY_TRIGGERHOTELLOG.ldf',
	SIZE = 1MB,
	MAXSIZE = 2MB,
	FILEGROWTH = 500KB)

GO

USE FRY_TRIGGERHOTEL

CREATE TABLE Hotels
(
	HotelID			SMALLINT			NOT NULL,
	HotelName		varchar(26)			NOT NULL,
	HotelAddress	varchar(21)			NOT NULL,
	HotelCity		varchar(16)			NOT NULL,
	HotelState		char(2)				NOT NULL,
	HotelCountry	varchar(22)			NOT NULL,
	HotelPostalCode	varchar(10)			NOT NULL,
	HotelRating		varchar(7),
	HotelPicture	varchar(30),
	TaxLocationID	SMALLINT			NOT NULL
)

CREATE TABLE TaxRates
(
	TaxLocationID		SMALLINT IDENTITY(1,1)		NOT NULL,
	TaxDescription		varchar(30)					NOT NULL,
	TaxRate				FLOAT					    NOT NULL
)


Insert into Hotels
values (3500, 'Test Hotel', 'Address', 'City', 'UT', 'Country', 'Zip', 'Rating', NULL, 1)

Insert into Hotels
values (3501, 'Test Hotel 2', 'Address', 'City', 'UT', 'Country', 'Zip', 'Rating', NULL, 2)

Insert into Hotels
values (3502, 'Test Hotel 3', 'Address', 'City', 'UT', 'Country', 'Zip', 'Rating', NULL, 2)

INSERT into TaxRates
Values ('test Tax1', 5.5)

INSERT into TaxRates
Values ('test Tax2', 7.5)

INSERT into TaxRates
Values ('test Tax3', 7.5)

INSERT into TaxRates
Values ('test Tax4', 7.5)

EXEC sp_addmessage 60005, 10, 'Error.  Sorry, TaxLocationID %d was not a valid tax location to insert into the hotel table.'

PRINT 'HERE ARE THE HOTELS'
PRINT ''
Select * From Hotels

PRINT 'HERE ARE THE RATES'
PRINT ''

Select * from TaxRates