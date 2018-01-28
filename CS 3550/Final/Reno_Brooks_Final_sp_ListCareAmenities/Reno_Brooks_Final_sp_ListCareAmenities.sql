/*
Name: sp_ListCareAmenities

Description: Will use a CURSOR to loop through all of the amenities associated with the inputted CCPackageID. 
CCPackageID and CCPackageName will be displayed only once, but their amenities will be listed, 
CCAmenityID, CCAmenityDescription, and CCAmenityPrice will be listed for each different amenity 
associated with the Package.

Input Parameters:	CCPackageID

Returns: A displayed report
*/
USE SUPRA

GO

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_ListCareAmenities')
	DROP PROCEDURE sp_ListCareAmenities;

GO

CREATE PROC sp_ListCareAmenities
	@CCPackageID tinyint
AS
BEGIN
	DECLARE @CCPackageName varchar(50);
	DECLARE @CCAmenityID tinyint;
	DECLARE @CCAmenityDescription varchar(100);
	DECLARE @CCAmenityPrice smallmoney;
	DECLARE @IDandNameString varchar(100);

	-- if you put in an invalid number it will stop and display an error message
	IF NOT EXISTS(
		SELECT CCPackageID, CCPackageName
		FROM CarCarePackage
		WHERE CCPackageID = @CCPackageID)
	BEGIN
		DECLARE @error varchar(400) = 'The Car Care Package ID "' + CAST(@CCPackageID AS varchar(5)) + '" does not exist.';
		PRINT @error
		RETURN
	END

	-- valid number!!!
	-- set what we know so far
	SET @CCPackageName = (SELECT CCPackageName FROM CarCarePackage WHERE CCPackageID = @CCPackageID);	SET @IDandNameString = '	CCPackageID: ' + CAST(@CCPackageID AS varchar(5)) + ' - CCPackageName: ' + @CCPackageName + '	----------------------------------------';	print @IDandNameString;

	-- Use a cursor to go through each result and display them individually
	DECLARE CCAmenityCursor CURSOR FOR
		SELECT cca.CCAmenityID, CCAmenityDescription, CCAmenityPrice
		FROM CarCarePackage ccp
			JOIN CCPackageAmenity ccpa
			ON ccp.CCPackageID = ccpa.CCPackageID
			JOIN CCAmenity cca
			ON ccpa.CCAmenityID = cca.CCAmenityID
		WHERE ccp.CCPackageID = @CCPackageID;

	OPEN CCAmenityCursor

	FETCH NEXT FROM CCAmenityCursor
	INTO @CCAmenityID, @CCAmenityDescription, @CCAmenityPrice

	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT '	CCAmenityID:			' + CAST(@CCAmenityID AS varchar(5))
		PRINT '	CCAmenityDescription:	' + @CCAmenityDescription
		PRINT '	CCAmenityPrice:			' + CAST(@CCAmenityPrice AS varchar(5))
		PRINT ''
      
		-- Fetch Again
		FETCH NEXT FROM CCAmenityCursor
		INTO @CCAmenityID, @CCAmenityDescription, @CCAmenityPrice

	END

	CLOSE CCAmenityCursor
	DEALLOCATE CCAmenityCursor

END

GO

EXEC sp_ListCareAmenities 
	@CCPackageID =  2