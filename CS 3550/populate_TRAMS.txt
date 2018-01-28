--TRAMS creation script
--Nathan Brooks February 3rd, 2016
--This generates the TRAMS database
--Builds tables
--Adds constraints
--Populates from txt files


USE Master

IF EXISTS (SELECT * FROM sysdatabases WHERE name='Brooks_TRAMS')
DROP DATABASE Brooks_TRAMS

GO   

CREATE DATABASE Brooks_TRAMS

ON PRIMARY

(
NAME = 'Brooks_TRAMS',
FILENAME = 'C:\Stage\Brooks_TRAMS.mdf', 
--FILENAME LOCAL
SIZE = 4MB,
MAXSIZE = 4MB,
FILEGROWTH = 500KB
)

LOG ON

(
NAME = 'Brooks_TRAMS_log',
FILENAME = 'C:\Stage\Brooks_TRAMS.ldf',
SIZE = 1200KB, --10% for Read, otherwise 25%
MAXSIZE= 5MB,
FILEGROWTH = 500KB
) 

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