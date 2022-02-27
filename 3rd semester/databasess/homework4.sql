


-- -a view with a SELECT statement operating on one table;
CREATE OR ALTER VIEW ViewBarrista
AS
	SELECT * FROM Barrista
GO



-- -a view with a SELECT statement operating on at least 2 tables. (customers that dinedIn and how much did they paid)
CREATE OR ALTER VIEW View2Tables
AS
	SELECT Cu.name, O.price
	FROM Customer Cu
	INNER JOIN Order_DineIn O
	ON O.customerID = Cu.customerID

GO

SELECT *FROM View2Tables
Go


-- -a view with a SELECT statement that has a GROUP BY clause and operates on at least 2 tables.
CREATE OR ALTER VIEW ViewGroupBy
AS
	SELECT COUNT(O.orderID) as NoOfOrders,O.BarristaID
	FROM Order_DineIn O
	GROUP BY O.BarristaID
	HAVING O.BarristaID IN (SELECT B.BarristaID FROM Barrista B)

GO

SELECT *FROM ViewGroupBy

DELETE FROM Views 
INSERT INTO Views VALUES ('ViewBarrista'),('View2Tables'),('ViewGroupBy')

SELECT * FROM Views


DELETE FROM Tables
INSERT INTO Tables VALUES ('Barrista'),('Order_DineIn'),('Customer')
GO


SELECT * FROM Tables


DELETE FROM Tests
INSERT INTO Tests VALUES ('test_10'),('test_100'),('test_1000'),('test_5000')
GO


SELECT * FROM Tests


DELETE FROM TestTables
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (1,1,10,1)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (1,2,10,2)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (1,3,10,3)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (1,4,10,4)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (2,1,100,1)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (2,2,100,2)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (2,3,100,3)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (2,4,100,4)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (3,1,1000,1)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (3,2,1000,2)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (3,3,1000,3)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (3,4,1000,4)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (4,1,5000,1)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (4,2,5000,2)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (4,3,5000,3)
INSERT INTO TestTables(TestId, TableID, NoOfRows, Position) VALUES (4,4,5000,4)


SELECT * FROM TestTables


DELETE FROM TestViews
INSERT INTO TestViews(TestID,ViewID) VALUES (1,1)
INSERT INTO TestViews(TestID,ViewID) VALUES (1,2)
INSERT INTO TestViews(TestID,ViewID) VALUES (1,3)
INSERT INTO TestViews(TestID,ViewID) VALUES (2,1)
INSERT INTO TestViews(TestID,ViewID) VALUES (2,2)
INSERT INTO TestViews(TestID,ViewID) VALUES (2,3)
INSERT INTO TestViews(TestID,ViewID) VALUES (3,1)
INSERT INTO TestViews(TestID,ViewID) VALUES (3,2)
INSERT INTO TestViews(TestID,ViewID) VALUES (3,3)
INSERT INTO TestViews(TestID,ViewID) VALUES (4,1)
INSERT INTO TestViews(TestID,ViewID) VALUES (4,2)
INSERT INTO TestViews(TestID,ViewID) VALUES (4,3)
GO

SELECT *FROM TestViews
GO


-- select view
CREATE OR ALTER PROCEDURE select_view @view_name VARCHAR(30) 
AS
BEGIN
	IF @view_name='ViewBarrista'
	BEGIN
		SELECT * FROM ViewBarrista
	END

	IF @view_name='View2Tables'
	BEGIN
		SELECT * FROM View2Tables
	END

	IF @view_name='ViewGroupBy'
	BEGIN
		SELECT * FROM ViewGroupBy
	END

	ELSE
		BEGIN
			PRINT 'There is no view with this name'
			RETURN 1
		END
END

GO




-- delete table
CREATE OR ALTER PROCEDURE delete_table @no_of_rows INT, @table_name VARCHAR(30)
AS
BEGIN
	DECLARE @nr_existing_rows INT

	IF @table_name='Barrista'
	BEGIN
	IF (SELECT COUNT(*) FROM Barrista)<@no_of_rows
		BEGIN
			PRINT ('Too many rows to delete')
			RETURN 1
		END
	ELSE
		BEGIN
			SET @nr_existing_rows = (SELECT MAX(BarristaID) FROM Barrista) - @no_of_rows

			DELETE FROM Barrista
			WHERE BarristaID > @nr_existing_rows
		END
	END


	ELSE IF @table_name = 'Order_DineIn'
	BEGIN
	IF (SELECT COUNT(*) FROM Order_DineIn) < @no_of_rows
		BEGIN
			PRINT ('Too many rows to delete')
			RETURN 1
		END
		ELSE
		BEGIN
			SET @nr_existing_rows = (SELECT MAX(orderID) FROM Order_DineIn) - @no_of_rows

			DELETE FROM Order_DineIn
			WHERE orderID > @nr_existing_rows
		END
	END

	ELSE IF @table_name = 'Customer'
	BEGIN	
	IF (SELECT COUNT(*) FROM Customer) < @no_of_rows
		BEGIN
			PRINT ('Too many rows to delete')
			RETURN 1
		END

	ELSE
		BEGIN
			SET @nr_existing_rows = (SELECT MAX(customerID) FROM Customer) - @no_of_rows
			DELETE FROM Customer
			WHERE customerID > @nr_existing_rows
	
		END
	END

	ELSE
	BEGIN
		PRINT('Not a valid table name')
		RETURN 1
	END
END
GO



--insert table
CREATE OR ALTER PROCEDURE insert_table @no_of_rows INT, @table_name VARCHAR(30)
AS
BEGIN
	DECLARE @input_id INT
	IF @table_name = 'Barrista'
	BEGIN
		SET @input_id = (SELECT MAX(BarristaID) FROM Barrista) + 1
		
		WHILE @no_of_rows > 0
		BEGIN
			INSERT INTO Barrista(BarristaID, name) VALUES (@input_id, 'Abc Def')

			SET @input_id = @input_id + 1
			SET @no_of_rows = @no_of_rows - 1
		END
	END


	ELSE IF @table_name='Order_DineIn'
	BEGIN
		SET @input_id = (SELECT MAX(orderID) FROM Order_DineIn) + 1

		DECLARE @fk1 INT
		SET @fk1=(SELECT TOP 1 customerID FROM Customer)

		DECLARE @fk2 INT
		SET @fk2=(SELECT TOP 1 BarristaID FROM Barrista)


		WHILE @no_of_rows > 0
		BEGIN
			print @input_id
			print @no_of_rows
			INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price) VALUES(@input_id,@fk1,@fk2,1,0.0)

			SET @input_id=@input_id+1
			SET @no_of_rows=@no_of_rows-1
		END
	END

	ELSE IF @table_name='Customer'
	BEGIN
		SET @input_id = (SELECT MAX(customerID) FROM Customer) + 1
		
		DECLARE @fk INT
		SET @fk=(SELECT TOP 1 BarristaID FROM Barrista)
		WHILE @no_of_rows > 0
		BEGIN
			INSERT INTO Customer(customerID,barristaID,name,rating,address,phoneNumber) VALUES(@input_id,@fk,'abc',1.0,'def','1234567890')

			SET @input_id = @input_id+1
			SET @no_of_rows=@no_of_rows-1
			
			PRINT(@input_id)
		END
	END

	ELSE
	BEGIN
		PRINT('Not a valid table name')
		RETURN 1
	END
END
GO


--main Test
CREATE OR ALTER PROCEDURE mainTest @testID INT
AS
BEGIN
	INSERT INTO TestRuns VALUES ((SELECT Name FROM Tests WHERE TestID=@testID),GETDATE(),GETDATE())
	DECLARE @testRunID INT
	SET @testRunID=(SELECT MAX(TestRunID) FROM TestRuns)

	DECLARE @noOfRows INT
	DECLARE @tableID INT
	DECLARE @tableName VARCHAR(30)
	DECLARE @startAt DATETIME
	DECLARE @endAt DATETIME
	DECLARE @viewID INT
	DECLARE @viewName VARCHAR(30)

	DECLARE testDeleteCursor CURSOR FOR
	SELECT TableID,NoOfRows
	FROM TestTables
	WHERE TestID=@testID
	ORDER BY Position DESC

	OPEN testDeleteCursor

	FETCH NEXT 
	FROM testDeleteCursor
	INTO @tableID,@noOfRows

	WHILE @@FETCH_STATUS=0
	BEGIN
		SET @tableName=(SELECT Name FROM Tables WHERE TableID=@tableID)

		EXEC delete_table @noOfRows,@tableName

		FETCH NEXT 
		FROM testDeleteCursor
		INTO @tableID,@noOfRows
	END

	CLOSE testDeleteCursor
	DEALLOCATE testDeleteCursor

	DECLARE testInsertCursor CURSOR FOR
	SELECT TableID,NoOfRows
	FROM TestTables
	WHERE TestID=@testID
	ORDER BY Position ASC

	OPEN testInsertCursor

	FETCH NEXT 
	FROM testInsertCursor
	INTO @tableID,@noOfRows

	WHILE @@FETCH_STATUS=0
	BEGIN
		SET @tableName=(SELECT Name FROM Tables WHERE TableID=@tableID)

		SET @startAt=GETDATE()
		EXEC insert_table @noOfRows,@tableName
		SET @endAt=GETDATE()

		INSERT INTO TestRunTables VALUES (@testRunID,@tableID,@startAt,@endAt)

		FETCH NEXT 
		FROM testInsertCursor
		INTO @tableID,@noOfRows
	END

	CLOSE testInsertCursor
	DEALLOCATE testInsertCursor

	DECLARE testViewCursor CURSOR FOR
	SELECT ViewID
	FROM TestViews
	WHERE TestID=@testID

	OPEN testViewCursor

	FETCH NEXT 
	FROM testViewCursor
	INTO @viewID

	WHILE @@FETCH_STATUS=0
	BEGIN
		SET @viewName=(SELECT Name FROM Views WHERE ViewID=@viewID)

		SET @startAt=GETDATE()
		EXEC select_view @viewName
		SET @endAt=GETDATE()

		INSERT INTO TestRunViews VALUES (@testRunID,@viewID,@startAt,@endAt)

		FETCH NEXT 
		FROM testViewCursor
		INTO @viewID
	END

	CLOSE testViewCursor
	DEALLOCATE testViewCursor

	UPDATE TestRuns
	SET EndAt=GETDATE()
	WHERE TestRunID=@testRunID

END
GO

EXEC mainTest 1
EXEC mainTest 2
EXEC mainTest 3
EXEC mainTest 4


SELECT * FROM Tests
SELECT * FROM Tables
SELECT * FROM TestTables
SELECT * FROM Views 
SELECT * FROM TestViews 
SELECT * FROM TestRuns
SELECT * FROM TestRunTables 
SELECT * FROM TestRunViews


DELETE FROM TestRuns
DELETE FROM TestRunTables
DELETE FROM TestRunViews


DROP TABLE TestViews
DROP TABLE TestRunTables
DROP TABLE TestRunViews
DROP TABLE TestTables
DROP TABLE Tests
DROP TABLE Tables
DROP TABLE Views
DROP TABLE TestRuns



SELECT *FROM Barrista
SELECT *FROM Order_DineIn
SELECT *FROM Customer



DELETE FROM Barrista

INSERT INTO Barrista(BarristaID,name,rating,salary,phoneNumber) VALUES (1,'Popa Ana',4.5,1200.0,'0749376287')
INSERT INTO Barrista(BarristaID,name,rating,salary,phoneNumber) VALUES (2,'Andrei Paul',4.8,1350.0,'0765234287')
INSERT INTO Barrista(BarristaID,name,rating,salary,phoneNumber) VALUES (3,'Ionescu Octavian',3.2,1075.5,'0749372987')
INSERT INTO Barrista(BarristaID,name,rating,salary,phoneNumber) VALUES (4,'Popescu Ioana',4.2,1135.5,'0765218801')
INSERT INTO Barrista(BarristaID,name,rating,salary,phoneNumber) VALUES (5,'Munteanu Matei',3.7,1100.0,'0733320114')

SELECT * FROM Barrista

DELETE FROM Customer

INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (1,'Lovinescu Andrei',4.5,'Fabricii de Zahar 14',0745690021, 1)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (2,'Prisacaru Teodora',3.7,'Nicolae Grigorescu 4',0722759407, 3)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (3,'Andone Roxana',4.3,'Dumbrava Rosie 8',0729504102, 1)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (4,'Marin George',4.8,'Dunarii 20',0734257007, 2)
INSERT INTO Customer(customerID,name,rating,address, barristaID) VALUES (5,'Dornescu Andrei',2.1,'Intre Lacuri 16', 2)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (6,'Pop Robert',3.8,'Grivitei 63', 0744427220, 4)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (7,'Draghici Ioana',2.8,'13 Decembrie 111',077022580, 5)
INSERT INTO Customer(customerID,name,rating,address,phoneNumber, barristaID) VALUES (8,'Luca Andra',4.9,'1 Mai 2',0741415592, 5)

SELECT * FROM Customer



DELETE FROM Order_DineIn

INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (1, 7, 1, 1, 41.0, 5.0, 'cash')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (2, 2, 2, 6, 42.0, 2.25, 'card')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (3, 8, 2, 4, 26.5, 5.50,'voucher')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (6, 7, 4, 1, 43.50, 4.0,'card')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,paymentMethod) VALUES (5, 4, 4, 2, 52.0, 'voucher')

SELECT * FROM Order_DineIn


DROP TABLE Order_DineIn
CREATE TABLE Order_DineIn
(
	orderID SMALLINT NOT NULL PRIMARY KEY,
	customerID SMALLINT NOT NULL,
	BarristaID SMALLINT NOT NULL,
	tableID SMALLINT,
	price DECIMAL(6,2) NOT NULL,
	tips SMALLINT,
	paymentMethod VARCHAR(30),
	FOREIGN KEY(BarristaID) REFERENCES Barrista(BarristaID) ON DELETE CASCADE,
	FOREIGN KEY(customerID) REFERENCES Customer(customerID) ON DELETE CASCADE
)



DROP TABLE Customer
CREATE TABLE Customer
(
	customerID SMALLINT NOT NULL,
	barristaID SMALLINT NOT NULL,
	name VARCHAR(30) NOT NULL,
	rating DECIMAL(3,2),
	address VARCHAR(50),
	phoneNumber CHAR(10),
	PRIMARY KEY(customerID),
)

