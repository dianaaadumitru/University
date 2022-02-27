CREATE TABLE TableA
(
	aid INT PRIMARY KEY,
	a2 INT UNIQUE,
	name VARCHAR(20)
)
GO

CREATE TABLE TableB
(
	bid INT PRIMARY KEY,
	b2 INT
)
GO

CREATE TABLE TableC
(
	cid INT PRIMARY KEY,
	aid INT FOREIGN KEY REFERENCES TableA(aid),
	bid INT FOREIGN KEY REFERENCES TableB(bid)
)
GO

SELECT * FROM TableA
SELECT * FROM TableB
SELECT * FROM TableC
GO


DECLARE @i INT=0
DECLARE @fk1 INT
DECLARE @fk2 INT
WHILE @i<10
BEGIN
	INSERT INTO TableA VALUES (@i,@i*2,CONCAT('test',RAND()*@i))
	INSERT INTO TableB VALUES (@i,RAND()*@i)

	SET @fk1=(SELECT TOP 1 aid FROM TableA ORDER BY NEWID())
	SET @fk2=(SELECT TOP 1 bid FROM TableB ORDER BY NEWID())

	INSERT INTO TableC VALUES (@i,@fk1,@fk2)

	SET @i=@i+1
END
GO

DELETE FROM TableC
DELETE FROM TableB
DELETE FROM TableA


--a)
--clustered index scan
SELECT *
FROM  TableA 
ORDER BY aid DESC

--clustered index seek
SELECT * 
FROM TableA 
WHERE aid>5

--nonclustered index scan
SELECT a2 
FROM TableA 
ORDER BY a2


--nonclustered index seek
SELECT a2 
FROM TableA 
WHERE a2=2

--key lookup
SELECT name,a2 
FROM TableA 
WHERE a2=4


--b)

GO 
DROP INDEX Idx_NC_b2 ON TableB
GO

--without creating said index, the execution plan is a clustered index scan 0.003293

SELECT *
FROM TableB
WHERE b2 = 2

CREATE NONCLUSTERED INDEX Idx_NC_b2 ON TableB(b2)
go
--now, the execution plan will show a nonclustered index seek, which would be more efficient 0.0032864


--c.
--this will use the previously created index and split the cost...
CREATE VIEW cView
AS 
	SELECT *
	FROM TableA a
	INNER JOIN TableB b  ON a.a2=b.b2
	--INNER JOIN TableC c ON a.aid = c.aid

GO
SELECT * FROM cView


GO

CREATE OR ALTER VIEW C_View AS
SELECT TableA.aid, TableB.bid, TableC.cid, TableA.a2, TableB.b2, TableA.name
FROM TableC
JOIN TableA ON TableA.aid = TableC.aid
JOIN TableB ON TableB.bid = TableC.bid
GO

SELECT * FROM C_View

CREATE NONCLUSTERED INDEX Idx_NC_c2 ON TableC (aid, bid)

DROP INDEX Idx_NC_c2 ON TableC