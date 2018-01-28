-- Danyell Reno
-- Nathan Brooks
--
-- SUPRAS creation for Final Project ~ CS 3550


USE Master

IF EXISTS (SELECT * FROM sysdatabases WHERE name='SUPRA')
DROP DATABASE SUPRA

GO   

CREATE DATABASE SUPRA

ON PRIMARY

(
NAME = 'SUPRA',
FILENAME = 'C:\Stage\SUPRA.mdf', 
--FILENAME LOCAL
SIZE = 4MB,
MAXSIZE = 4MB,
FILEGROWTH = 500KB
)

LOG ON

(
NAME = 'SUPRA_log',
FILENAME = 'C:\Stage\SUPRA.ldf',
SIZE = 1200KB, --10% for Read, otherwise 25%
MAXSIZE= 5MB,
FILEGROWTH = 500KB
) 

GO

USE SUPRA

--!!!!!! Create Tables !!!!!!
CREATE TABLE Valet
(
	ValetID				int					NOT NULL	IDENTITY(1,1),
	CCPackageID			tinyint				NULL,
	LotID				tinyint				NOT NULL,
	ValetTimeIn			smalldatetime		NOT NULL,
	ValetTimeOut		smalldatetime		NULL,
	CustomerID			int					NOT NULL,
	CarMake				varchar(25)			NOT NULL,
	CarModel			varchar(25)			NOT NULL,
	IsMotorcycle		char(1)				NOT NULL,
	ValetStatus			char(1)				NOT NULL,
	SpotID				tinyint				NOT NULL
)


CREATE TABLE Customer
(
	CustomerID			int					NOT NULL	IDENTITY(1,1),
	CustomerFirst		varchar(50)			NOT NULL,
	CustomerLast		varchar(50)			NOT NULL,
	CustomerAddress		varchar(200)		NOT NULL,
	CustomerCity		varchar(50)			NOT NULL,
	CustomerState		char(2)				NULL,
	CustomerPostalCode	varchar(10)			NOT NULL,
	CustomerCountry		varchar(20)			NOT NULL,
	CustomerPhone		varchar(10)    		NOT NULL,
	CusomerEmail		varchar(200)		NOT NULL
)

CREATE TABLE ValetReview

(
	ReviewID			int					NOT NULL,
	CustomerID			int					NOT NULL
)

CREATE TABLE Review
(
	ReviewID			int					NOT NULL	IDENTITY(1,1),
	ReviewText			varchar(max)		NULL,
	ReviewRating		tinyint				NULL,
	Flag				char(1)				NULL
)

CREATE TABLE ValetDiscount
(
	DiscountCode		char(1)				NOT NULL,
	ValetID				int					NOT NULL
)

CREATE TABLE Discount
(
	DiscountCode		char(1)				NOT NULL,
	DiscountDescription varchar(50)			NOT NULL,
	DiscountPercent		decimal(2,2)		NOT NULL
)

CREATE TABLE CarCarePackage
(
	CCPackageID			tinyint				NOT NULL IDENTITY(1,1),
	CCPackageName		varchar(50)			NULL
)

CREATE TABLE CCPackageAmenity
(
	CCPackageID			tinyint				NOT NULL,
	CCAmenityID			tinyint				NOT NULL
)

CREATE TABLE CCAmenity
(
	CCAmenityID			 tinyint			NOT NULL	IDENTITY(1,1),
	CCAmenityDescription varchar(100)		NOT NULL,
	CCAmenityPrice		 smallmoney			NOT NULL
)

CREATE TABLE Lot
(
	LotID				tinyint				NOT NULL	IDENTITY(1,1),
	LotName				varchar(20)			NOT NULL,
	LotCapacity			tinyint				NOT NULL,
	PropertyID			smallint			NOT NULL,
	LotType				char(1)				NOT NULL,
	LotPrice			smallmoney			NOT NULL
)

CREATE TABLE LotParkingSpots
(
	SpotID				tinyint				NOT NULL	IDENTITY(1,1),
	LotID				tinyint				NOT NULL,
	SpotNo				tinyint				NOT NULL
	
)

CREATE TABLE SpotAmenityLookup
(
	SpotID				tinyint				NOT NULL,
	SpotAmenityID		tinyint				NOT NULL
)

CREATE TABLE SpotAmenity
(
	SpotAmenityID		tinyint		NOT NULL	IDENTITY(1,1),
	SpotAmenityDescription varchar(50)	NOT NULL
)


GO

-- !!!!!! constraints !!!!!!
-- Primary Keys
ALTER TABLE Valet
	ADD CONSTRAINT PK_ValetID
	PRIMARY KEY(ValetID)

ALTER TABLE Customer
	ADD CONSTRAINT PK_Customer
	PRIMARY KEY(CustomerID)

ALTER TABLE ValetReview
	ADD CONSTRAINT PK_ValetReview
	PRIMARY KEY(CustomerID, ReviewID)

ALTER TABLE Review
	ADD CONSTRAINT PK_Review
	PRIMARY KEY(ReviewID)

ALTER TABLE ValetDiscount
	ADD CONSTRAINT PK_ValetDiscount
	PRIMARY KEY(ValetID, DiscountCode)

ALTER TABLE Discount
	ADD CONSTRAINT PK_Discount
	PRIMARY KEY(DiscountCode)

ALTER TABLE Lot
	ADD CONSTRAINT PK_Lot
	PRIMARY KEY(LotID)

ALTER TABLE LotParkingSpots
	ADD CONSTRAINT PK_LotParkingSpots
	PRIMARY KEY(SpotID)

ALTER TABLE SpotAmenityLookup
	ADD CONSTRAINT PK_SpotAmenityLookup
	PRIMARY KEY(SpotID, SpotAmenityID)

ALTER TABLE SpotAmenity
	ADD CONSTRAINT PK_SpotAmenity
	PRIMARY KEY(SpotAmenityID)

ALTER TABLE CarCarePackage
	ADD CONSTRAINT PK_CarCarePackage
	PRIMARY KEY(CCPackageID)

ALTER TABLE CCPackageAmenity
	ADD CONSTRAINT PK_CCPackageAmenity
	PRIMARY KEY(CCPackageID, CCAmenityID)

ALTER TABLE CCAmenity
	ADD CONSTRAINT PK_CCAmenity
	PRIMARY KEY(CCAmenityID)

GO

-- Foreign Keys
ALTER TABLE Valet
	ADD
	CONSTRAINT FK_CCPackageIDReferencesCCPackage
	FOREIGN KEY(CCPackageID) REFERENCES CarCarePackage(CCPackageID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_CustomerIDUsedInCustomerTable
	FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_LotIDReferencesLotTable
	FOREIGN KEY(LotID) REFERENCES Lot(LotID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE ValetReview
	ADD
	CONSTRAINT FK_ValetReviewValetIDReferencesValet
	FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_ValetReviewReviewIDReferencesReview
	FOREIGN KEY(ReviewID) REFERENCES Review(ReviewID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE CCPackageAmenity
	ADD
	CONSTRAINT FK_CCPackageIDReferencesCCPackageTable
	FOREIGN KEY(CCPackageID) REFERENCES CarCarePackage(CCPackageID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_CCAmenityIDReferencesCCAmenityTable
	FOREIGN KEY(CCAmenityID) REFERENCES CCAmenity(CCAmenityID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE LotParkingSpots
	ADD
	CONSTRAINT FK_LotIDReferencesLot
	FOREIGN KEY(LotID) REFERENCES Lot(LotID)
	ON UPDATE Cascade
	ON DELETE Cascade

ALTER TABLE SpotAmenityLookup
	ADD
	CONSTRAINT FK_SpotIDReferencesLotParkingSpots
	FOREIGN KEY(SpotID) REFERENCES LotParkingSpots(SpotID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_SpotAmenityIDReferencesLotAmenityTable
	FOREIGN KEY(SpotAmenityID) REFERENCES SpotAmenity(SpotAmenityID)
	ON UPDATE Cascade
	ON DELETE Cascade

GO

USE master