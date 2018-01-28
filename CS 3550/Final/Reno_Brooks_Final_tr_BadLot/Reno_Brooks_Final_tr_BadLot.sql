USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_BadLot')
	DROP TRIGGER tr_BadLot;

GO

CREATE TRIGGER tr_BadLot ON Valet
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @Problems int = 0;
	DECLARE @InsertedLotID tinyint = (SELECT LotID FROM Inserted);
	DECLARE @InsertedIsMotorcycle char(1) = (SELECT IsMotorcycle FROM Inserted);
	DECLARE @LotTypeTryingToInsertInto char(1) = (	SELECT LotType 
													FROM Lot
													WHERE LotID = @InsertedLotID);
	
	IF UPDATE (IsMotorcycle)
	BEGIN
		-- IsMotorcycle can be Y/N
		-- LotType can be      C/M for Car/Motorcycle
		-- if Valet.IsMotorcycle == Y
		-- Make sure Lot.LotType == M for Motorcycle
		IF @InsertedIsMotorcycle = 'Y' AND @LotTypeTryingToInsertInto != 'M'
		BEGIN
			RAISERROR(N'
			@@@@@@@@
			@@@@@@@@ Not allowed to insert a Motorcycle into a Car lot.
			@@@@@@@@
			', 16, 1);
			ROLLBACK
		END 
		
		
		-- if Valet.IsMotorcycle == N 
		-- Make sure Lot.LotType == C for Any
		IF @InsertedIsMotorcycle = 'N' AND @LotTypeTryingToInsertInto != 'C'
		BEGIN
			RAISERROR(N'
			@@@@@@@@
			@@@@@@@@ Not allowed to insert a Car into a Motorcycle lot.
			@@@@@@@@
			', 16, 1);
			ROLLBACK
		END


		RETURN
	END
END

GO

INSERT INTO Valet(
	LotID, IsMotorcycle, CCPackageID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, ValetStatus, SpotID
)
VALUES(
	7,			-- known Motorcycle lot
	'N',        -- Not a motorcycle
	1,          
	GETDATE(),
	NULL,
	10,
	'SomeMake',
	'SomeModel',
	'A',
	'1'
)


INSERT INTO Valet(
	LotID, IsMotorcycle, CCPackageID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, ValetStatus, SpotID
)
VALUES(
	6,			-- known Car lot
	'Y',        -- Is a motorcycle
	1,          
	GETDATE(),
	NULL,
	10,
	'SomeMake',
	'SomeModel',
	'A',
	'1'
)
