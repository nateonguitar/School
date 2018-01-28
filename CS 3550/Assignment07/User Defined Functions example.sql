--Example 0 Error Messages

USE MASTER
EXEC sp_addmessage --system level stored procedure
@msgnum = 62219, --Use the last 4 of your W# (they start with 60,000)
@severity = 10,
@msgtext = 'Sorry the CustomerID %s was not found.  Try again.'

GO
 
USE FRY_NORTHWIND
GO

CREATE PROC GetCustomers 
@CustomerID varchar (5)

AS
DECLARE @err_message nvarchar(255)

--if no customers found return error to abort stored procedure
IF NOT EXISTS (Select CustomerID from Customers where CustomerID = @CustomerID) 
	BEGIN	
	RAISERROR (62219, 11, 1, @CustomerID)
	END
ELSE Select * from Customers where CustomerID = @CustomerID

GO

EXEC GetCustomers 'ALFKI'
EXEC GetCustomers 'Fry'



USE MASTER
GO
EXEC sp_dropmessage 62219
GO

--Now on to our functions


USE FRY_NORTHWIND
GO


--Example 1:  Creating a Basic SCALAR Function

IF OBJECT_ID (N'dbo.fn_ExtendedAmount', N'FN') IS NOT NULL
DROP FUNCTION dbo.fn_ExtendedAmount;

GO

CREATE FUNCTION dbo.fn_ExtendedAmount
       (
       @quantity int,
       @unitprice money,
       @discount float
       )
RETURNS money
AS
       BEGIN
              DECLARE @result money
              SET @result = 0
              SET @result = @quantity * @unitprice * (1 - @Discount)
       RETURN @result
       END

--Example 1A:  CALLING the function directly.

SELECT (dbo.fn_ExtendedAmount(6, 4, .4)) as [Order Total]

--Example 1B:  CALLING the function directly (in-line).
 
SELECT OrderID, Sum(dbo.fn_ExtendedAmount(Quantity, UnitPrice, Discount)) AS [Order Total]
FROM [ORDER DETAILS]
GROUP BY OrderID

--Example 2:  Making Example 1B into its own function, to be called by Example 3.
IF OBJECT_ID (N'dbo.fn_OrderTotal', N'FN') IS NOT NULL
DROP FUNCTION dbo.fn_OrderTotal;
GO
CREATE FUNCTION dbo.fn_OrderTotal
       (
       @OrderID int
       )
RETURNS money
AS
       BEGIN
              DECLARE @result money
              SET @result = 0
              SET @result = (SELECT SUM(dbo.fn_ExtendedAmount(Quantity, UnitPrice, Discount)) AS [Order Total]
                           FROM dbo.[Order Details]
                           WHERE OrderID = @OrderID )
       RETURN @result
       END

--EXAMPLE 3:  Making antoher function to call Example 2.
IF OBJECT_ID (N'dbo.fn_CustomerTotal', N'FN') IS NOT NULL
DROP FUNCTION dbo.fn_CustomerTotal;
GO
CREATE FUNCTION dbo.fn_CustomerTotal
       (
       @CustomerID varchar(10)
       )
RETURNS money
AS
       BEGIN
              DECLARE @result money
              SET @result = 0
              SET @result = (SELECT SUM(dbo.fn_OrderTotal(OrderID))
                           FROM ORDERS
                           WHERE CustomerID = @CustomerID)
       RETURN @result
       END

--Example 4:  Putting it all together....


Select DISTINCT CompanyName, dbo.fn_CustomerTotal(Orders.CustomerID) AS [Customer Total]
FROM Customers
INNER JOIN Orders ON Customers.CustomerID = Orders.CustomerID


--Example 5:  A Returned Table (Note the TOP 100 ROWS).
IF EXISTS(SELECT name FROM sys.objects WHERE name = N'fn_CustomerList')
DROP FUNCTION fn_CustomerList;
GO

CREATE FUNCTION dbo.fn_CustomerList
(@prefix varchar(40))
RETURNS TABLE 
AS
RETURN (SELECT TOP 100 
CustomerID, CompanyName, @prefix AS Prefix
FROM dbo.Customers
WHERE (CompanyName LIKE @prefix + '%')
ORDER BY CompanyName )

--Example 5A:  Selecting from the Table....

Select CompanyName From dbo.fn_CustomerList ('An')


--Example 6:   
IF EXISTS(SELECT name FROM sys.objects WHERE name = N'fn_GetBigOrders')
DROP FUNCTION fn_GetBigOrders;
GO

CREATE FUNCTION dbo.fn_GetBigOrders()
RETURNS @BigOrders TABLE
(

	[OrderID] [int] NOT NULL ,
	[Total] [smallmoney]   NULL 

	
)
AS
BEGIN
INSERT INTO @BigOrders SELECT DISTINCT OrderId, UnitPrice*Quantity FROM [Order Details]
RETURN
END
GO

Select * from GetBigOrders() where Total <100 

--Example 7

IF OBJECT_ID ('[dbo].[fx_dosomething]', 'TF') IS NOT NULL
  drop function [dbo].[fx_dosomething];
GO

create FUNCTION dbo.fx_dosomething ( @x int )
returns @t table (debug varchar(100), x2 int)
as
begin
 declare @debug varchar(100)
 set @debug = '1 record updated';

 declare @x2 int
 set @x2 = 123456;

 insert into @t values (@debug, @x2)
 return 
end
go

select * from fx_dosomething(123456)