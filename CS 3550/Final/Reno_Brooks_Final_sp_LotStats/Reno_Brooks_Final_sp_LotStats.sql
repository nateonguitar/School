--Find the owner first and last name, and the make and model of all cars in a particular lot. 
USE SUPRA


--Re-Creating sp_LotStats
-- check to see if sp_LotStats exists and drop it if it does
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE SPECIFIC_NAME = 'sp_LotStats')
	DROP PROCEDURE sp_LotStats;
GO

--Create sp_LotStats with one input, FolioID. 
CREATE PROCEDURE sp_LotStats
	@LotID		tinyint
AS
BEGIN

	--Variable declaration. 
	DECLARE @ErrMessage			varchar(max)
	DECLARE @LotNum				DECIMAL(5,2)
	DECLARE @LotNumCash			DECIMAL(5,2)
	DECLARE @LotNumPrice		smallmoney
	--DECLARE @LotTot				DECIMAL(5,2)
	--DECLARE @LotPriceTot		DECIMAL(5,2)

	--Error handling. Make sure that the folio actually exists.
	IF NOT EXISTS
		(
		 SELECT LotID 
		 FROM Lot 
		 WHERE LotID = @LotID
		)
	BEGIN
		SET @ErrMessage = ('"' + CONVERT(varchar, @LotID) + '" is not a valid Lot. Please try again.')
		RAISERROR (@ErrMessage, -1, -1, @LotID)
		RETURN -1
	END
	
	
	--Selecting then necessary information to display once.
	SET @LotNum = 
		(SELECT COUNT(*)
		 FROM Valet
		 WHERE LotID = @LotID)

	SET @LotNumPrice = 
		(SELECT LotPrice
		 FROM Lot
		 WHERE LotID = @LotID)

	SET @LotNumCash = CAST(@LotNumPrice AS decimal) * @LotNum;

	DECLARE @Tot  DECIMAL(5,2) = (SELECT COUNT(*) FROM Valet);

	-- @PriceTotal needs to equal (each of the lots cost times number of valets) all added up for each lot
	DECLARE @PriceTot	smallmoney	=(SELECT SUM(t1.NumberOfValetsTimesLotPrice)
	FROM (SELECT l.LotID, COUNT(ValetID) as NumberOfValetsInThisLot, COUNT(ValetID) * LotPrice AS NumberOfValetsTimesLotPrice
	FROM Valet v
		JOIN Lot l
		ON v.LotID = l.LotID
	GROUP BY l.LotID, LotPrice) t1);

	PRINT 'This lot accounts for ' + CONVERT(varchar, CAST(@LotNum/@Tot*100 AS decimal(4,2))) + '% of the spots taken.'
	PRINT 'This lot accounts for ' + CONVERT(varchar, CAST(@LotNumCash/@PriceTot*100 AS decimal(4,2))) + '% of money generated.'


END 
GO

EXEC sp_LotStats
		@LotID = 4