USE FRY_TriggerHotel
GO
--1 
Create Trigger tr_HotelHasTaxRate ON Hotels 
AFTER INSERT, UPDATE 
AS 
BEGIN
	DECLARE @TaxID smallint 
	IF Exists ( SELECT 'This text does not matter' 
				FROM Inserted i 
				LEFT JOIN TaxRates t ON i.taxlocationid = t.taxlocationid 
				WHERE t.taxlocationid is NULL ) 
	BEGIN 
		SELECT @TaxID = (SELECT TaxlocationId FROM Inserted) RAISERROR (60005,10,1,@TaxId) 
		ROLLBACK 
	END 
END

--********1A Example. Tries to insert 77 as the tax location id******************** 

Insert into Hotels 
values (3504, 'Test Hotel 4', 'Address', 'City', 'UT', 'Country', 'Zip', 'Rating', NULL, 77) 


Insert into Hotels 
values (3504, 'Test Hotel 4', 'Address', 'City', 'UT', 'Country', 'Zip', 'Rating', NULL, 3) 

GO

UPDATE HOTELS SET TaxLocationID = 88 WHERE TaxLocationID = 3

GO
CREATE TRIGGER tr_Tax_Rate_Assigned_to_Hotel ON TaxRates 
AFTER DELETE 
AS 
IF EXISTS (SELECT 'Anything can go here' 
                          FROM Deleted d 
						  JOIN Hotels h ON d.TaxLocationID = h.TaxLocationID ) 
BEGIN Raiserror ('Cannot Delete a Tax Rate, because it is assigned to a hotel.', 16, 1) 
ROLLBACK TRAN 
END 
GO
--********Example 2A and 2B******************** 
SELECT * FROM TaxRates

DELETE FROM TaxRates WHERE taxlocationID = 2 

DELETE FROM TaxRates WHERE taxlocationID = 4 

--3 
GO
CREATE VIEW vw_Hotel As 
SELECT HotelID, HotelName, HotelAddress, HotelCity, HotelState, HotelCountry, TaxLocationID 
FROM Hotels 
GO
--4 Not a trigger example, but to set up for #5 

SELECT * FROM vw_Hotel 
GO

INSERT INTO vw_Hotel VALUES (5700, 'View Hotel', 'Address', 'City', 'UT', 'USA', 2) --Zip missing causes error
GO
SELECT * from Hotels 

--5 BECAUSE #4 doesn't work, we're using an INSTEAD OF TRIGGER to modify code on behalf of user. 
GO
CREATE TRIGGER tr_Insert_Into_HotelView ON vw_Hotel 

INSTEAD OF INSERT 
AS 
BEGIN 
IF (SELECT COUNT (*) FROM Inserted) > 0 
INSERT INTO Hotels 
SELECT i.hotelid, i.hotelName, i.hoteladdress, i.hotelcity, i.hotelstate, 
          i.hotelcountry, '84123', NULL, NULL, i.taxlocationid 
FROM Inserted i 
END 

--6 (Same thing we tried to do in #4, but this time the trigger is set which inserts the zip on behalf of user) 

INSERT INTO vw_Hotel --again 
VALUES (5700, 'View Hotel', 'Address', 'City', 'UT', 'USA', 2) SELECT * from Hotels 

-- Columns_Updated 
GO

SELECT * FROM TaxRates 

ALTER TABLE TaxRates 
Add Comments varchar(30) 
GO

CREATE Trigger tr_Columnupdated ON TaxRates AFTER INSERT, UPDATE, DELETE 
AS 

IF COLUMNS_UPDATED() | 6 > 0 Update TaxRates Set Comments = 'Description OR Rate' 
IF COLUMNS_UPDATED() & 6 = 6 Update TaxRates Set Comments = 'Description & Rate Only' 




SELECT * FROM TaxRates 
GO
Update TaxRates Set TaxRate = 2.4, TaxDescription = 'test' where taxlocationid = 1 
SELECT * From TaxRates 

Update TaxRates Set TaxRate = 6.64 where taxlocationid = 1 

SELECT * From TaxRates 



--Only works with 8 columns or less. 
-- https://msdn.microsoft.com/en-us/library/ms186329.aspx
--More than 8 see: http://support.microsoft.com/kb/232195

GO

ALTER Trigger tr_Columnupdated ON TaxRates 
AFTER INSERT, UPDATE, DELETE 

AS
   if update (TaxRate)
   UPDATE TAXRATES Set Comments = 'Single Column Example' 

GO

Update TaxRates Set TaxRate = 6.64 where taxlocationid = 1 

SELECT * From TaxRates 