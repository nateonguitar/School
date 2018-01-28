DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS specimen;
DROP TABLE IF EXISTS species;
DROP TABLE IF EXISTS images;
 -- 5eae491e5c0c17a = puppy
CREATE TABLE users (
	userID 					int				NOT NULL 	AUTO_INCREMENT,
	userFirstName 			varchar(25)		NOT NULL,
	userLastName			varchar(25)		NOT NULL,
	userPhoneNumber			varchar(10)		NULL,
	userRole				tinyint,
	userInactive			tinyint,
	userPassword			varchar(100),
	userEmail				varchar(100),
	PRIMARY KEY (userID)
);

CREATE TABLE specimen (
	specimenID				int				NOT NULL	AUTO_INCREMENT,
	specimenSpecies			varchar(100)	NOT NULL,
	specimenGenus			varchar(100)	NOT NULL,
	specimenFamily			varchar(100)	NOT NULL,
	specimenAuthor			varchar(100)	NULL,
	specimenVariety			varchar(100)	NULL,
	specimenVarietyAuthor 	varchar(100) 	NOT NULL,
	specimenCounty			varchar(100)	NULL,
	specimenState			varchar(100)	NULL,
	specimenCountry			varchar(100)	NULL,
	specimenGeneralLocation varchar(500) 	NULL,
	specimenLatitude 		float(10,6)		NULL,
	specimenLongitude 		float(10,6) 	NULL,
	specimenTownship 		varchar(100)	NULL,
	specimenRange			varchar(100)	NULL,
	specimenSection			varchar(100)	NULL,
	specimenPlantCommunity	varchar(100)	NULL,
	specimenSoilMoisture	varchar(100)	NULL,
	specimenSoilTexture		varchar(100)	NULL,
	specimenSoilColor		varchar(100)	NULL,
	specimenElevation		varchar(100)	NULL,
	specimenDate			datetime		NOT NULL,
	specimenCollectedBy		varchar(100)	NULL,
	specimenCollectorNum	int				NULL,
	specimenAcquisitionNum	int				NULL,
	specimenEthnobotanicalUse varchar(500)	NULL,
	specimenNotes			varchar(500)	NULL,
	PRIMARY KEY (specimenID)
);

CREATE TABLE images (
	imageID					int				NOT NULL	AUTO_INCREMENT,
	imagePath				varchar(500)	NOT NULL,
	specimenID				int				NOT NULL, -- FK to specimen table
	PRIMARY KEY(imageID)
);

ALTER TABLE images 
	ADD CONSTRAINT fk_specimenIDreferencesSpecimenTable
	FOREIGN KEY ( specimenID )
	REFERENCES specimen( specimenID ) 
	ON UPDATE CASCADE 
	ON DELETE CASCADE;