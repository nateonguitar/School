USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_JdmUpgrade') 
DROP TRIGGER tr_JdmUpgrade

GO

CREATE TRIGGER tr_JdmUpgrade
ON Valet
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
	DECLARE @UpgradeCCPack	tinyint
	
	SELECT @ValetID = i.ValetID, @CCPackageID = i.CCPackageID, @LotID = i.LotID, @ValetTimeIn = i.ValetTimeIn, @ValetTimeOut = i.ValetTimeOut,
		   @CustomerID = i.CustomerID, @CarMake = i.CarMake, @CarModel = i.CarModel, @IsMotorcycle= i.IsMotorcycle, @ValetStatus = i.ValetStatus, 
		   @SpotID = i.SpotID
	FROM inserted i

	SET @UpgradeCCPack = 4


  --If Toyota Supra
  IF(@CarMake = 'Toyota' AND @CarModel = 'Supra')
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
    VALUES(@UpgradeCCPack, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
  RETURN
  
  END


  --If Nissan GTR
  IF(@CarMake = 'Nissan' AND @CarModel = 'GTR')
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
    VALUES(@UpgradeCCPack, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
  RETURN
  
  END


  --If Subaru WRX STI
  IF(@CarMake = 'Subaru' AND @CarModel = 'WRX STI')
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
    VALUES(@UpgradeCCPack, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
  RETURN
  
  END

  --If Mazda RX-7
  IF(@CarMake = 'Mazda' AND @CarModel = 'RX-7')
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
    VALUES(@UpgradeCCPack, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
  RETURN
  
  END


  --If Honda NSX
  IF(@CarMake = 'Honda' AND @CarModel = 'NSX')
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
    VALUES(@UpgradeCCPack, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)
  RETURN
  
  END

  --Default Behaviour
  INSERT 
  INTO Valet(CCPackageID, LotID, ValetTimeIn, ValetTimeOut, CustomerID, CarMake, CarModel, IsMotorcycle, ValetStatus, SpotID)
  VALUES(@CCPackageID, @LotID, @ValetTimeIn, @ValetTimeOut, @CustomerID, @CarMake, @CarModel, @IsMotorcycle, @ValetStatus, @SpotID)


END

GO



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