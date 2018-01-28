USE MASTER


IF EXISTS(SELECT * FROM sys.servers WHERE name = N'SUPRA_TRAMS')
BEGIN	
	exec sp_droplinkedsrvlogin 'TITAN_TRAMS' , 'DESKTOP-03OFHNJ\theds'	
	exec sp_dropserver 'TITAN_TRAMS'
END

Exec sp_addlinkedserver
@server='SUPRA_TRAMS',
@srvproduct='',
/*this is the product name of the OLE DB data source to add as a linked server.*/
@provider='MSDASQL',
@provstr='DRIVER={SQL Server};SERVER=titan.cs.weber.edu,10433;UID=Thai_User;PWD=Thai_Test;Initial Catalog=RENO_TRAMS'

Exec sp_serveroption 'TITAN_TRAMS', 'data access', 'true'
Exec sp_serveroption 'TITAN_TRAMS', 'rpc', 'true'--enables from the REMOTE to LOCAL server
Exec sp_serveroption 'TITAN_TRAMS', 'rpc out', 'true' -- enables from the LOCAL to REMOTE server
Exec sp_serveroption 'TITAN_TRAMS', 'collation compatible', 'true'

GO

Exec sp_addlinkedsrvlogin 
@rmtsrvname = 'SUPRA_TRAMS',
@useself = 'false',
@locallogin = 'DESKTOP-03OFHNJ\theds',
@rmtuser= 'Thai_User',
@rmtpassword = 'Thai_Test'

GO

EXEC sp_configure 'show advanced options', 1
reconfigure

GO

EXEC sp_configure 'Ad Hoc Distributed Queries', 1
reconfigure

GO