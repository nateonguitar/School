USE SUPRA

/*
Name: CalculateTax
Description: 

Finds the tax for an inputted item. Similar to Calculate Tax in TRAMS. 
This will actually link through the Lot table, which each car has a part of, 
will call traverse to TRAMS, get the tax for the tax area the lot is in, 
then return this tax rate, then calculate tax for a charge. 

This helps management not get locked up by the IRS.

Input Parameters:	PropertyID (smallint), Price (smallmoney)
Returns: Tax (smallmoney)
*/

IF OBJECT_ID (N'dbo.fn_CalculateTax', N'FN') IS NOT NULL
DROP FUNCTION dbo.fn_CalculateTax;

GO

CREATE FUNCTION dbo.fn_CalculateTax
(
	@PropertyID smallint,
	@price smallmoney
)
RETURNS smallmoney
AS
	BEGIN
		DECLARE @result smallmoney;
		DECLARE @taxRate decimal(5,3);

		SET @taxRate = (SELECT TaxRate
			FROM OPENQUERY(
				TITAN_TRAMS, 
				'SELECT TaxStartDate, TaxEndDate, PropertyID, TaxRate, TaxType
				FROM RENO_TRAMS.dbo.Property pr
					JOIN RENO_TRAMS.dbo.TaxLocation tl
					ON pr.TaxLocationID = tl.TaxLocationID
					JOIN RENO_TRAMS.dbo.TaxRate tr
					ON tl.TaxLocationID = tr.TaxLocationID
				WHERE TaxType = ''L''
					AND TaxEndDate IS NULL
				') 
			WHERE PropertyID = @PropertyID
		);

		-- convert to a percentage
		SET @taxRate = @taxRate / 100;

		IF @taxRate IS NOT NULL
		BEGIN
			SET @result = @price * @taxRate;
		END
		ELSE
		BEGIN
			SET @result = 0;
		END

		RETURN @result;
	END
GO

-- TRY IT OUT


DECLARE @LotID					smallint =		4;
DECLARE @PropertyIDofThisLot	smallint =		(SELECT PropertyID FROM Lot WHERE lotID = @LotID);
DECLARE @priceOfThisLot			smallmoney =	(SELECT LotPrice FROM Lot WHERE PropertyID = @PropertyIDofThisLot);

DECLARE @TaxToApply smallmoney = dbo.fn_CalculateTax(@PropertyIDofThisLot, @PriceOfThisLot);

DECLARE @Total smallmoney = @TaxToApply + @priceOfThisLot;

PRINT 'Price of this lot:';
PRINT  @priceOfThisLot;
print '';
PRINT 'Tax to apply: ';
PRINT @TaxToApply;
print '';
PRINT 'Total: ';
PRINT @Total;
