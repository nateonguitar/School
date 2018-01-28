DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Auctions;
DROP TABLE IF EXISTS AuctionItems;
DROP TABLE IF EXISTS Images;

CREATE TABLE Users (
	userID 					int				NOT NULL 	AUTO_INCREMENT,
	userFirstName 			varchar(25)		NOT NULL,
	userLastName			varchar(25)		NOT NULL,
	userPhoneNumber			varchar(20)		NULL,
	userRole				tinyint			NULL,		-- allows for admins or other levels of users, null can be basic user
	userBanned				tinyint			NULL,		-- we can ban an email/username, just flag to 1 and check against it
	userPassword			varchar(100)	NULL,		-- should be hashed, they should generate their own password if null
	userUsername			varchar(100)	NOT NULL,	-- should check if unique before allowing insert
	userEmail				varchar(100)	NOT NULL,				
	PRIMARY KEY (userID)
);

CREATE TABLE Auctions (
	auctionID				int				NOT NULL	AUTO_INCREMENT,
	userID					int				NOT NULL,	-- FK to User table -- user is the owner of this auction
	auctionCreationDateTime	DATETIME		NOT NULL,	-- can use date("Y-m-d H:i:s") in PHP to get 2009-12-23 00:00:00
														-- MySQL function NOW() is the exact same ^ can pass between PHP and MySQL
	
	auctionExpireDateTime	DATETIME		NOT NULL,	
	AuctionDescription		varchar(300)	NULL,
	PRIMARY KEY (auctionID)
);

CREATE TABLE AuctionItems (
	auctionItemID			int				NOT NULL	AUTO_INCREMENT,
	auctionItemMinimumBid	numeric(15,4)	NOT NULL,   -- some currencies in the world have more than 2 decimal places
	auctionItemCurrentBid	numeric(15,4)	NOT NULL,
	auctionItemMinIncrement numeric(15,4)	NOT NULL,
	auctionItemDescription  varchar(500)	NULL,
	auctionID				int				NOT NULL,	-- FK to Auctions table
	userID					int				NULL,		-- the user who has the current highest bid
	
	PRIMARY KEY(auctionItemID)
);

CREATE TABLE Images (
	imageID					int				NOT NULL	AUTO_INCREMENT,
	imagePath				varchar(500)	NOT NULL,
	auctionItemID			int				NOT NULL, -- FK to AuctionItem table
	PRIMARY KEY(imageID)
);

ALTER TABLE Auctions
	ADD CONSTRAINT FK_userID_References_UsersTable
	FOREIGN KEY ( userID )
	REFERENCES Users ( userID )
	ON UPDATE CASCADE
	ON DELETE CASCADE;
	
ALTER TABLE AuctionItems
	ADD CONSTRAINT FK_auctionID_References_AuctionsTable
	FOREIGN KEY ( AuctionID )
	REFERENCES Auctions ( AuctionID )
	ON UPDATE CASCADE
	ON DELETE CASCADE;
	
ALTER TABLE Images 
	ADD CONSTRAINT FK_auctionItemID_References_AuctionItemsTable
	FOREIGN KEY ( auctionItemID )
	REFERENCES AuctionItems( auctionItemID ) 
	ON UPDATE CASCADE 
	ON DELETE CASCADE;