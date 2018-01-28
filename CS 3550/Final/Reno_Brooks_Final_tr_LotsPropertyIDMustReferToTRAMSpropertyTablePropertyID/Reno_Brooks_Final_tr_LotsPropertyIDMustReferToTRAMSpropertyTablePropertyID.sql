USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_LotsPropertyIDMustReferToTRAMSpropertyTablePropertyID')
	DROP TRIGGER tr_LotsPropertyIDMustReferToTRAMSpropertyTablePropertyID;

GO

CREATE TRIGGER tr_LotsPropertyIDMustReferToTRAMSpropertyTablePropertyID ON Lot
INSTEAD OF INSERT
AS
BEGIN	
	DECLARE @LotName varchar(20)
	DECLARE @LotCapacity tinyint
	DECLARE @PropertyID smallint
	DECLARE @LotType char(1)
	DECLARE @LotPrice smallmoney


	SELECT 
		@LotName = i.LotName, 
		@LotCapacity = i.LotCapacity, 
		@PropertyID = i.PropertyID,
		@LotType = i.LotType,
		@LotPrice = i.LotPrice

	FROM inserted i

	IF (SELECT PropertyID
		FROM OPENQUERY(
			TITAN_TRAMS, 
			'SELECT *
			FROM RENO_TRAMS.dbo.Property') WHERE PropertyID = @PropertyID) IS NULL
	BEGIN
		RAISERROR(N'
			@@@@@@@@
			@@@@@@@@ You are trying to create a lot that does not have a corresponding PropertyID in TRAMS
			@@@@@@@@
			', 16, 1);
			ROLLBACK
			RETURN
	END 

	INSERT INTO Lot(LotName, LotCapacity, PropertyID, LotType, LotPrice)
	VALUES(@LotName, @LotCapacity, @PropertyID, @LotType, @LotPrice);
	RETURN;
END

GO

INSERT INTO Lot(LotName, LotCapacity, PropertyID, LotType, LotPrice)
	VALUES('Obama', 245, 13000, 'M', 5000);

GO

SELECT * FROM Lot WHERE PropertyID = 13000