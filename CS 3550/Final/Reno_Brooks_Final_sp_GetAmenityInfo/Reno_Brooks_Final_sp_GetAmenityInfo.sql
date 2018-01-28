USE SUPRA

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_GetAmenityInfo')
	DROP PROCEDURE sp_GetAmenityInfo
GO

--Create sp_GetAmenityInfo with one input, CustomerID. 
CREATE PROCEDURE sp_GetAmenityInfo
	  @CustomerID   	    int
AS
BEGIN
	
	DECLARE @CCAmenityDescription	varchar(100)
	DECLARE @CCAmenityPrice			smallmoney
	DECLARE @CCPackageID			tinyint


	SET @CCPackageID =
		(SELECT CCPackageID
		 FROM Valet
		 WHERE CustomerID = @CustomerID)


	PRINT ''
	PRINT ''
	PRINT 'AMENITY DETAILS:'
	PRINT ''
	PRINT 'CCPackageID:' + CHAR(9) + CONVERT(varchar, @CCPackageID)
	PRINT 'Which has the following services:'
	PRINT ''



	--Declaration of the cursor to select results for Amenity Details. 
	DECLARE AmenityCursor CURSOR FOR
	SELECT CCAmenityDescription, CCAmenityPrice
	FROM CCAmenity cca
		JOIN CCPackageAmenity ccpa
		ON cca.CCAmenityID = ccpa.CCAmenityID
			JOIN CarCarePackage ccp
			ON ccp.CCPackageID = ccpa.CCPackageID
	WHERE ccp.CCPackageID = @CCPackageID


	OPEN AmenityCursor

	-- Fetch First Time
	FETCH NEXT FROM AmenityCursor
	INTO @CCAmenityDescription, @CCAmenityPrice

	--When the fetching is done from that row of data, 
	WHILE @@FETCH_STATUS = 0

	--Print them 
	BEGIN
		PRINT 'CCAmenityDescription: '+ CHAR(9) + @CCAmenityDescription
		PRINT 'CCAmenityPrice: ' + CHAR(9) + CHAR(9) + '$' + CONVERT(varchar, @CCAmenityPrice)
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM AmenityCursor
		INTO @CCAmenityDescription, @CCAmenityPrice

	END
 
	CLOSE AmenityCursor
	DEALLOCATE AmenityCursor --to avoid memory leaks



END
GO


EXEC sp_GetAmenityInfo
	@CustomerID = 4