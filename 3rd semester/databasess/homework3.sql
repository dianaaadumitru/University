
--a. modify the type of a column
CREATE OR ALTER PROCEDURE changeCustomerRatingType
AS 
BEGIN 
	ALTER TABLE Customer
	ALTER COLUMN rating INT
END
GO

EXEC changeCustomerRatingType
GO


--undo
CREATE OR ALTER PROCEDURE undo_changeCustomerRatingType
AS 
BEGIN 
	ALTER TABLE Customer
	ALTER COLUMN rating DECIMAL(6,2)
END
GO

EXEC undo_changeCustomerRatingType
GO


--b. add / remove a column
CREATE OR ALTER PROCEDURE removeColumnPhoneNumberBarristas
AS
BEGIN
	ALTER TABLE Barrista
	DROP COLUMN phoneNumber
END
GO

EXEC removeColumnPhoneNumberBarristas
GO



--undo
CREATE OR ALTER PROCEDURE undo_removeColumnPhoneNumberBarristas
AS
BEGIN
	ALTER TABLE Barrista
	ADD phoneNumber CHAR(10)
END
GO


EXEC undo_removeColumnPhoneNumberBarristas
GO


--c. add / remove a DEFAULT constraint
CREATE OR ALTER PROCEDURE addDefaultConstraintBarristaRating
AS
BEGIN
	ALTER TABLE Barrista
	ADD CONSTRAINT rating_default
	DEFAULT 1.0 FOR rating
END
GO

EXEC addDefaultConstraintBarristaRating
GO


--undo
CREATE OR ALTER PROCEDURE undo_addDefaultConstraintBarristaRating
AS
BEGIN
	ALTER TABLE Barrista
	DROP CONSTRAINT rating_default
END
GO

EXEC undo_addDefaultConstraintBarristaRating
GO


--INSERT INTO Barrista(barristaID, name, salary, phoneNumber) values (12, 'ABC AAA', 1279, 0754321123)
--SELECT * FROM Barrista



--d. add / remove primary key

CREATE TABLE NEW_TABLE
(
	primKey SMALLINT NOT NULL,
	descr VARCHAR(50),
	CONSTRAINT PK_primKey PRIMARY KEY(primKey)
)
GO

CREATE OR ALTER PROCEDURE dropPrimaryKeyNewTable
AS
BEGIN
	ALTER TABLE NEW_TABLE
	DROP CONSTRAINT PK_primKey
END
GO

EXEC dropPrimaryKeyNewTable
GO


--undo
CREATE OR ALTER PROCEDURE undo_dropPrimaryKeyNewTable
AS
BEGIN
	ALTER TABLE NEW_TABLE
	ADD CONSTRAINT PK_primKey PRIMARY KEY(primKey)
END
GO

EXEC undo_dropPrimaryKeyNewTable
GO



--e. add / remove a candidate key
CREATE OR ALTER PROCEDURE addCandidateKeyReservationIDTable
AS
BEGIN
	ALTER TABLE Reservations
	ADD CONSTRAINT UQ_ID_Table UNIQUE(reservID,tableID)


END
GO

EXEC addCandidateKeyReservationIDTable
GO

--undo 

CREATE OR ALTER PROCEDURE undo_addCandidateKeyReservationIDTable
AS
BEGIN
	ALTER TABLE Reservations
	DROP CONSTRAINT UQ_ID_Table
END
GO

EXEC undo_addCandidateKeyReservationIDTable
GO


--f. add / remove a foreign key
CREATE OR ALTER PROCEDURE removeForeignKeyOrderDetailsBarristaID
AS
BEGIN 
	ALTER TABLE Order_Details
	DROP CONSTRAINT FK_barrista_odID
END
GO

EXEC removeForeignKeyOrderDetailsBarristaID
GO


--undo
CREATE OR ALTER PROCEDURE undo_removeForeignKeyOrderDetailsBarristaID
AS
BEGIN 
	ALTER TABLE Order_Details
	ADD CONSTRAINT FK_barrista_odID FOREIGN KEY (barristaId) REFERENCES Barrista(BarristaID)
END
GO

EXEC undo_removeForeignKeyOrderDetailsBarristaID
GO


--g. create / drop a table
CREATE OR ALTER PROCEDURE createTestTable
AS 
BEGIN 
	CREATE TABLE Test_Table
	(
		testID SMALLINT NOT NULL PRIMARY KEY,
		testMessage VARCHAR(30)
	)
END
GO

EXEC createTestTable
GO

--undo
CREATE OR ALTER PROCEDURE undo_createTestTable
AS 
BEGIN 
	DROP TABLE Test_Table
END
GO

EXEC undo_createTestTable
GO




CREATE TABLE Previous_Versions
(
	storedProcedure VARCHAR(50),
	versionFrom INT,
	versionTo INT,
	PRIMARY KEY(versionFrom,versionTo)
)
GO


INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('changeCustomerRatingType',0,1)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('removeColumnPhoneNumberBarristas',1,2)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('addDefaultConstraintBarristaRating',2,3)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('dropPrimaryKeyNewTable',3,4)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('addCandidateKeyReservationIDTable',4,5)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('removeForeignKeyOrderDetailsBarristaID',5,6)
INSERT INTO Previous_Versions(storedProcedure,versionFrom,versionTo) VALUES ('createTestTable',6,7)


--table2: keeps only the current version

CREATE TABLE Current_Version
(
	currentVersion INT DEFAULT 0
)
GO




--procedure that modifies version
--SELECT currentVersion FROM Current_Version

CREATE OR ALTER PROCEDURE ModifyVersion(@version INT)
AS
	DECLARE @current INT;
	SET @current=(SELECT Current_Version.currentVersion FROM Current_Version)
	DECLARE @proc VARCHAR(100)
	DECLARE @query VARCHAR(100)
	BEGIN
	
		IF @version<0 OR @version>7
		BEGIN
			DECLARE @errorMsg VARCHAR(200)
			SET @errorMsg='Version has to be between 0 and 7'
			PRINT @errorMsg
			RETURN 
		END

		ELSE

		IF @version=@current
		BEGIN
			PRINT 'Already on this version!'
			RETURN 
		END	

		ELSE

		IF (@version > @current)
		BEGIN
			WHILE (@current != @version)
			BEGIN
				SET @proc = (SELECT Previous_Versions.storedProcedure FROM Previous_Versions WHERE Previous_Versions.versionFrom = @current)
				EXEC @proc
				SET @current = @current + 1
				UPDATE Current_Version SET currentVersion = @current
				PRINT 'Updated version'
			END
		END

		ELSE

		BEGIN
			WHILE (@current != @version)
			BEGIN
				SET @proc = (SELECT Previous_Versions.storedProcedure FROM Previous_Versions WHERE Previous_Versions.versionTo = @current)
				SET @query = 'undo_' + @proc
				EXEC @query
				PRINT 'Undo executed'
				SET @current = @current - 1
				UPDATE Current_Version SET currentVersion = @current
				PRINT 'Updated version'
			END
		END


	END
GO


EXEC modifyVersion 0
GO

SELECT * FROM Previous_Versions
SELECT * FROM Current_Version
GO


INSERT INTO Current_Version (currentVersion) VALUES(0)
DELETE FROM Current_Version