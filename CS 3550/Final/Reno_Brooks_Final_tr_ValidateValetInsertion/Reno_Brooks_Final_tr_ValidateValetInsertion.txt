USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_ValidateValetInsertion')
	DROP TRIGGER tr_ValidateValetInsertion;

GO

CREATE TRIGGER tr_ValidateValetInsertion ON Valet
INSTEAD OF INSERT
AS
BEGIN
	
	DECLARE @ValetID		int
	DECLARE @CCPackageID	tinyint
	DECLARE @LotID			tinyint
	DECLARE @ValetTimeIn	smalldatetime
	DECLARE @ValetTimeOut	smalldatetime
	DECLARE @CustomerID		int
	DECLARE @CarMake		varchar(25)
	DECLARE @CarModel		varchar(25)
	DECLARE @IsMotorcycle	char(1)
	DECLARE @ValetStatus	char(1)
	DECLARE @SpotID			tinyint
	DECLARE @UpgradeCCPack	tinyint = 4 -- to override the CCPackageID if a specific car Make/Model is inserted

	SELECT @ValetID = i.ValetID, @CCPackageID = i.CCPackageID, @LotID = i.LotID, @ValetTimeIn = i.ValetTimeIn, @ValetTimeOut = i.ValetTimeOut,
		   @CustomerID = i.CustomerID, @CarMake = i.CarMake, @CarModel = i.CarModel, @IsMotorcycle= i.IsMotorcycle, @ValetStatus = i.ValetStatus, 
		   @SpotID = i.SpotID
	FROM inserted i

	---------------------------------------------------
	-- Bad Lot 
	---------------------------------------------------
	
	DECLARE @LotTypeTryingToInsertInto char(1) = (	SELECT LotType 
													FROM Lot
													WHERE LotID = @LotID);
	
	IF UPDATE (IsMotorcycle)
	BEGIN
		-- IsMotorcycle can be Y/N
		-- LotType can be      C/M for Car/Motorcycle
		-- if Valet.IsMotorcycle == Y
		-- Make sure Lot.LotType == M for Motorcycle
		IF @IsMotorcycle = 'Y' AND @LotTypeTryingToInsertInto != 'M'
		BEGIN
			RAISERROR(N'
			@@@@@@@@
			@@@@@@@@ Not allowed to insert a Motorcycle into a Car lot.
			@@@@@@@@
			', 16, 1);
			ROLLBACK
			RETURN
		END 
		
		
		-- if Valet.IsMotorcycle == N 
		-- Make sure Lot.LotType == C for Any
		IF @IsMotorcycle = 'N' AND @LotTypeTryingToInsertInto != 'C'
		BEGIN
			RAISERROR(N'
			@@@@@@@@
			@@@@@@@@ Not allowed to insert a Car into a Motorcycle lot.
			@@@@@@@@
			', 16, 1);
			ROLLBACK
			RETURN
		END
	END




	---------------------------------------------------
	-- JdmUpgrade
	---------------------------------------------------

  --If Toyota Supra
  IF
	   (@CarMake = 'Toyota' AND @CarModel = 'Supra') 
	OR (@CarMake = 'Nissan' AND @CarModel = 'GTR')
	OR (@CarMake = 'Subaru' AND @CarModel = 'WRX STI')
	OR (@CarMake = 'Mazda' AND @CarModel = 'RX-7')
	OR (@CarMake = 'Honda' AND @CarModel = 'NSX')
  BEGIN 
	SET @CCPackageID = @UpgradeCCPack -- replace @CCPackageID to be inserted to a 4 if the make/model is correct
  END









  --Default Behaviour - no errors were found
  INSERT 
  INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
  VALUES(@CCPackageID, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
END

GO








INSERT INTO Valet(
	LotID, IsMotorcycle, CCPackageID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, ValetStatus, SpotID
)
VALUES(
	7,			-- known Motorcycle lot    -- should throw an error
	'N',        -- Not a motorcycle
	1, GETDATE(), NULL, 10, 'SomeMake', 'SomeModel', 'A', '1'
)


INSERT INTO Valet(
	LotID, IsMotorcycle, CCPackageID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, ValetStatus, SpotID
)
VALUES(
	6,			-- known Car lot          -- should throw an error
	'Y',        -- Is a motorcycle
	1, GETDATE(), NULL, 10, 'SomeMake', 'SomeModel', 'A', '1'
)


--Adding some fresh Customers and Corresponding Valets


--Should Activate trigger
INSERT
INTO Customer
VALUES ('Joseph', 'Lee', '5751 Baker St.', 'Honolulu', 'HI', '96754', 'US', '8088762153', 'josephlee@hotmail.com')

INSERT 
INTO Valet
VALUES(1, 1, '2015-04-02 07:26:00', '2015-04-03 07:26:00', @@IDENTITY, 'Toyota', 'Supra', 'N', 'X', 8)


--should not activate trigger
INSERT
INTO Customer
VALUES ('Hannah', 'Scharff', '2141 Elmer St.', 'Kaneohe', 'HI', '96734', 'US', '8086789461', 'hannah332@hotmail.com')

INSERT 
INTO Valet
VALUES(1, 1, '2015-04-02 07:26:00', '2015-04-03 07:26:00', @@IDENTITY, 'Toyota', 'Corolla', 'N', 'X', 9)


--Show results
SELECT * 
FROM Valet