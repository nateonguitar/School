USE SUPRA

IF EXISTS(SELECT name FROM sys.objects WHERE name = N'tr_BadReview')
	DROP TRIGGER tr_BadReview;

GO


CREATE TRIGGER tr_BadReview
ON Review
INSTEAD OF INSERT

AS
BEGIN
	DECLARE @ReviewID		int
	DECLARE @ReviewText		varchar(max)
	DECLARE @ReviewRating	tinyint
	DECLARE @Flag			char(1)

	SELECT @ReviewID = i.ReviewID, @ReviewText = i.ReviewText, @ReviewRating = i.ReviewRating, @Flag = i.Flag
	FROM inserted i
		
	--Default
	SET @Flag = 'X'


  --If the employee rated a 1 or 2, then the flag is set to L so management can look into the bad review. 
  IF(@ReviewRating = 1 OR @ReviewRating =2)
  BEGIN 

  IF(SELECT COUNT(*) FROM inserted) >0
  	INSERT 
	INTO Review(ReviewText, ReviewRating, Flag)
    VALUES(@ReviewText, @ReviewRating, 'L')
  RETURN
  
  END

  INSERT
  INTO Review(ReviewText, ReviewRating, Flag)
  VALUES(@ReviewText, @ReviewRating, @Flag)

END

GO


--Doesn't change this. 
INSERT 
INTO Review
VALUES('Very nice staff, respectful', 5, 'X')


--Changes X to L for staff to look into 
INSERT 
INTO Review
VALUES('I don''t know why this place is still in business...', 1, 'X')


--Changes X to L for staff to look into 
INSERT 
INTO Review
VALUES('Very difficult to find', 2, 'X')


--No change, shows off the ability for handling Spanish reviews
--"the employee spoke Spanish to help me"
INSERT 
INTO Review
VALUES('El empleado podría hablar español para ayudarme.', 5, 'X')


--Changes L to X for a good review (in case of accidental entry.
--Also shows off special character capability
--"I loved their bilingual staff."
INSERT 
INTO Review
VALUES('Me encantó su personal bilingüe.', 5, 'L')


--Show results. 
SELECT *
FROM REVIEW