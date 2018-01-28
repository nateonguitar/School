USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_OnCheckoutDateSetGenerateTotalBillCalled')
DROP TRIGGER tr_OnCheckoutDateSetGenerateTotalBillCalled

GO


--When the ValetTimeOut is changed to not null, then you need 
CREATE TRIGGER  tr_OnCheckoutDateSetGenerateTotalBillCalled
ON Valet
AFTER INSERT, UPDATE
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


	IF(@ValetTimeOut IS NOT NULL)
	BEGIN
		EXEC sp_GenerateBill
			@CustomerID = @CustomerID
	END

END



GO

PRINT 'Updating a row in valet so that the end date is no longer null.'
INSERT 
INTO Valet
VALUES(1 ,2, GETDATE(), GETDATE(), 10, 'Nissan', 'Altima', 'N', 'A', '22')


PRINT 'Inserting a row in valet with an end date thtat is not null.'
INSERT 
INTO Valet
VALUES(1 ,2, GETDATE(), GETDATE(), 11, 'Kia', 'Forte', 'N', 'A', '24')

PRINT 'Inserting a row valet with a null end date to show that this does nothing.'
INSERT 
INTO Valet
VALUES(1 ,2, GETDATE(), NULL, 12, 'Pontiac', 'Grand Prix', 'N', 'A', '23')


use SUPRA
SELECT * 
FROM Valet
ORDER BY LotID, SpotID