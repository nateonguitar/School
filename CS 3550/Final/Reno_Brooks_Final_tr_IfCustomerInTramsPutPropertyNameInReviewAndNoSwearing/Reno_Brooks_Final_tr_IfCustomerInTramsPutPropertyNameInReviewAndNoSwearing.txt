-- instead of on insert into review get the name of the property they're staying at and put it at the start.  Also clean out any swear words!

USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_IfCustomerInTramsPutPropertyNameInReviewAndNoSwearing')
	DROP TRIGGER tr_IfCustomerInTramsPutPropertyNameInReviewAndNoSwearing;

GO

CREATE TRIGGER tr_IfCustomerInTramsPutPropertyNameInReviewAndNoSwearing ON Review
AFTER INSERT
AS
BEGIN
	DECLARE @ReviewID int;
	DECLARE @PropertyID smallint;
	DECLARE @ReviewText varchar(max);
	DECLARE @ReviewRating tinyint;
	DECLARE @Flag char(1);
	DECLARE @CustomerID int;
	DECLARE @PropertyName varchar(50);


	SELECT 
		@ReviewID = i.ReviewID,
		@ReviewText = i.ReviewText, 
		@ReviewRating = i.ReviewRating, 
		@Flag = i.Flag
	FROM inserted i

	IF @ReviewText LIKE '%shit%' OR @ReviewText LIKE '%bitch%' OR @ReviewText LIKE '%fuck%'
	BEGIN
		RAISERROR(N'
		@@@@@@@@
		@@@@@@@@ Swear words are not allowed!  Please be grown up about your complaints!
		@@@@@@@@
		', 16, 1);
		ROLLBACK
		RETURN
	END 
	
	SET @CustomerID = (	SELECT TOP 1 c.CustomerID
						FROM Review r
							JOIN ValetReview vr
							ON r.ReviewID = vr.ReviewID
							JOIN Customer c
							ON vr.CustomerID = c.CustomerID);



	--Grab the property ID associated with a review

SET @PropertyID = (
SELECT TOP 1 PropertyID
FROM Review r
 JOIN ValetReview vr
 ON r.ReviewID = vr.ReviewID
  JOIN Customer c
  ON vr.CustomerID = c.CustomerID
   JOIN Valet v
   ON v.CustomerID = c.CustomerID 
    JOIN Lot l
    ON l.Lotid = v.LotID
WHERE c.CustomerID = @CustomerID
)
	 
	-- see if the reviewing customer has a Valet
	IF(SELECT TOP 1 c.CustomerID
	FROM Review r
		JOIN ValetReview vr
		ON r.ReviewID = vr.ReviewID
		JOIN Customer c
		ON vr.CustomerID = c.CustomerID
		JOIN Valet v
		ON c.CustomerID = v.CustomerID
	WHERE c.CustomerID = @CustomerID
		AND ValetID IS NOT NULL) IS NOT NULL
	BEGIN
		-- if so, tack on the Property's Name from Trams to the start of their review, like this:
		-- "I hated this place" becomes
		-- "Mountain Meadows: I hated this place"
		SET @PropertyName = (SELECT PropertyName
		FROM OPENROWSET(
			'MSDASQL', 
			'DRIVER={SQL Server};SERVER=titan.cs.weber.edu,10433;UID=Thai_User;PWD=Thai_Test', 

			'Select * FROM
			 RENO_TRAMS.dbo.Property
			 ') WHERE PropertyID = @PropertyID);

		SET @ReviewText = @PropertyName + ': ' + @ReviewText;

		UPDATE Review
		SET ReviewText = @ReviewText
		WHERE ReviewID = @ReviewID
	END
END

GO

INSERT INTO Review
VALUES('Hi there', 2, 'X')

INSERT INTO ValetReview(CustomerID, ReviewID)
VALUES(14, @@Identity);

SELECT * FROM Review

INSERT INTO Review
VALUES('Hi shit there', 2, 'X')

INSERT INTO ValetReview(CustomerID, ReviewID)
VALUES(14, @@Identity);