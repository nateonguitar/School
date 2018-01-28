USE Master

IF EXISTS (SELECT * FROM sysdatabases WHERE name='TabSite3')
DROP DATABASE TabSite3

GO   

CREATE DATABASE TabSite3

ON PRIMARY

(
NAME = 'TabSite3',
FILENAME = 'C:\Stage\TabSite3.mdf', 
--FILENAME LOCAL
SIZE = 12MB, --500 kb, 500, TB, GB
MAXSIZE = 50MB,
FILEGROWTH = 10% -- 5MB 
)

LOG ON

(
NAME = 'TabSite3_log',
FILENAME = 'C:\Stage\TabSite3.ldf',
SIZE = 1200KB, --10% for Read, otherwise 25%
MAXSIZE= 5MB,
FILEGROWTH = 500KB
) 

GO

-- With the Brooks_PurpleBox database now created, switch to it and begin 
-- creating the individual tables for the database

USE TabSite3

--!!!!!! Create Tables !!!!!!
-- tables without FKs
-- for some reason User was not a valid table name
-- it forced me to use another name
CREATE TABLE Person
(
	personID			int				NOT NULL		IDENTITY(1,1),
	personFirstName		varchar(30)		NOT NULL,
	personLastName		varchar(30)		NOT NULL,
	personPassword		varchar(30)		NULL,
	personAdmin			bit				NOT NULL,
	personBanned		bit				NOT NULL,
	personAddr			varchar(50)		NULL,
	personCity			varchar(50)		NULL,
	personState			char(2)			NULL,
	personZip			char(5)			NULL
)

CREATE TABLE Song
(
	songID				int				NOT NULL		IDENTITY(1,1),
	songTitle			varchar(200)	NOT NULL,
	songText			varchar(max)	NULL,
	songArranger		varchar(200)	NULL,           -- on upload will copy username from person
	songUploadDate		smalldatetime	NOT NULL
)

CREATE TABLE Composer
(
	composerID			int				NOT NULL		IDENTITY(1,1),
	composerName		varchar(200)	NOT NULL
)

CREATE TABLE SongComposer
(
	songComposerID		int				NOT NULL		IDENTITY(1,1),
	composerID			int				NOT NULL,
	songID				int				NOT NULL
)

ALTER TABLE Person
	ADD CONSTRAINT PK_PersonID
	PRIMARY KEY(personID)

ALTER TABLE Song
	ADD CONSTRAINT PK_SongID
	PRIMARY KEY(songID)

ALTER TABLE Composer
	ADD CONSTRAINT PK_ComposerID
	PRIMARY KEY(composerID)

ALTER TABLE SongComposer
	ADD CONSTRAINT PK_SongIDComposerID
	PRIMARY KEY(composerID, songID)

ALTER TABLE SongComposer
	ADD
	CONSTRAINT FK_ComposerIDRefersToComposer
	FOREIGN KEY(composerID) REFERENCES Composer(composerID)
	ON UPDATE Cascade
	ON DELETE Cascade,

	CONSTRAINT FK_SongIDRefersToSong
	FOREIGN KEY(songID) REFERENCES Song(songID)
	ON UPDATE Cascade
	ON DELETE Cascade



-- bulk insert

BULK INSERT Person			FROM 'C:\Stage\TabSitePerson.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT Composer		FROM 'C:\Stage\TabSiteComposer.txt'		WITH (FIELDTERMINATOR='|')
BULK INSERT Song			FROM 'C:\Stage\TabSiteSong.txt'			WITH (FIELDTERMINATOR='|')
BULK INSERT SongComposer	FROM 'C:\Stage\TabSiteSongComposer.txt'	WITH (FIELDTERMINATOR='|')

go

SELECT composerName, s.songID
FROM Composer c
	JOIN SongComposer sc
	ON c.composerID = sc.composerID
	JOIN Song s
	ON sc.songID = s.songID
	WHERE songTitle = 'Example 2 Title'
