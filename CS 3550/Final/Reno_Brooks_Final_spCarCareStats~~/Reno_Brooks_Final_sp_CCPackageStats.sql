USE SUPRA

-- check to see if sp_CCPackageStats exists and drop it if it does
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_CCPackageStats')
	DROP PROCEDURE sp_CCPackageStats;
GO

--Create sp_CCPackageStats with one input, CCPackageID. 
CREATE PROCEDURE sp_CCPackageStats
	@CCPackageID		tinyint
AS
BEGIN

	--Variable declaration. 
	DECLARE @ErrMessage			varchar(max)

	DECLARE @PeopleUsingPack	int
	DECLARE @CCPackPrice		smallmoney
	DECLARE @CCPackNet			decimal(15,5)

	DECLARE @TotalValets		int

	--Error handling. Make sure that the CCPackageID actually exists.
	IF NOT EXISTS
		(
		 SELECT CCPackageID 
		 FROM CarCarePackage
		 WHERE CCPackageID = @CCPackageID
		)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar, @CCPackageID) + '" is not a PackageID. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @CCPackageID)
		RETURN -1
	END
	
	
	--Package read in
	SET @CCPackPrice =  
		(SELECT CAST(SUM(CCAmenityPrice) AS smallmoney)
		 FROM CCAmenity cca
				JOIN CCPackageAmenity ccpa
				ON cca.CCAmenityID = ccpa.CCAmenityID
		 WHERE CCPackageID = @CCPackageID)

	SET @PeopleUsingPack = 
		(SELECT COUNT(*)
		 FROM Valet
		 WHERE CCPackageID = @CCPackageID)

	SET @CCPackNet = CAST(@CCPackPrice AS decimal(15,5)) * CAST(@PeopleUsingPack AS decimal(15,5))
	IF(@CCPackNet IS NULL)
		BEGIN 
			Set @CCPackNet = 0
		END


	SET @TotalValets  = (SELECT COUNT(*) FROM Valet)

	DECLARE @TotalRevenueFromAllCCPackages smallmoney = (
		SELECT SUM(CCAmenityPrices * NumberOfValetsThatUsePackage)
		FROM (SELECT ccp.CCPackageID as IDs, SUM(CCAmenityPrice) AS CCAmenityPrices
				FROM CarCarePackage ccp
					JOIN CCPackageAmenity ccpa
					ON ccp.CCPackageID = ccpa.CCPackageID
					JOIN CCAmenity cca
					ON ccpa.CCAmenityID = cca.CCAmenityID
				GROUP BY ccp.CCPackageID) AS PricesForEachPackage
				JOIN (
					SELECT CCPackageID as IDs, COUNT(*) AS NumberOfValetsThatUsePackage
					FROM Valet
					GROUP BY CCPackageID) NumValets
				ON PricesForEachPackage.IDs = NumValets.IDs);

	PRINT 'Package ' + CONVERT(varchar, @CCPackageID) + ' accounts for ' + CONVERT(varchar, CAST(CAST(@PeopleUsingPack AS decimal(15,5))/CAST(@TotalValets AS decimal(15,5))*100 AS decimal(4,2))) + '% of the packages used.'
	PRINT 'Package ' + CONVERT(varchar, @CCPackageID) + ' accounts for ' + CONVERT(varchar, CAST( @CCPackNet/(CAST(@TotalRevenueFromAllCCPackages AS decimal(15,5)))*100 AS decimal(4,2))) + '% of money generated.'

END 
GO


EXEC sp_CCPackageStats
	@CCPackageID = 0

PRINT ''

EXEC sp_CCPackageStats
	@CCPackageID = 1

PRINT ''

EXEC sp_CCPackageStats
	@CCPackageID = 2

PRINT ''	

EXEC sp_CCPackageStats
	@CCPackageID = 3

PRINT ''

EXEC sp_CCPackageStats
	@CCPackageID = 4

PRINT ''

EXEC sp_CCPackageStats
	@CCPackageID = 5

PRINT ''

EXEC sp_CCPackageStats
	@CCPackageID = 6
