--ASSIGMENT 2


DELETE FROM MenuItem

INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (1,'Caffee Latte',12,280)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (2,'Cappuccino',11.5,280)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (3,'Tea',9,400)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (4,'Cheese Cake',14,300)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (5,'Muffin',6,150)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (6,'Lemonade',16,500)
INSERT INTO MenuItem(itemID,name,price,quantity) VALUES (7,'Carrot Cake',14.5,350)

SELECT * FROM MenuItem

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

DELETE FROM Cats

INSERT INTO Cats(catId, name, age) VALUES (1, 'Larry', 2)
INSERT INTO Cats(catId, name, age) VALUES (2, 'Jerry', 1)
INSERT INTO Cats(catId, name, age) VALUES (3, 'Marry', 4)
INSERT INTO Cats(catId, name, age) VALUES (4, 'Sushi', 2)
INSERT INTO Cats(catId, name, age) VALUES (5, 'Cotton', 3)
INSERT INTO Cats(catId, name, age) VALUES (6, 'Bella', 1)
INSERT INTO Cats(catId, name, age) VALUES (7, 'Lily', 6)
INSERT INTO Cats(catId, name, age) VALUES (8, 'Milo', 4)
INSERT INTO Cats(catId, name, age) VALUES (9, 'Leo', 5)
INSERT INTO Cats(catId, name, age) VALUES (10, 'Jack', 2)

SELECT * FROM Cats

DELETE FROM Order_Details

INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (1,3,2,1);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (1,2,2,1);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (2,5,4,2);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (2,5,3,2);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (3,1,1,2);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (3,7,1,2);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (4,2,3,5);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (5,2,4,4);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (5,5,1,4);
INSERT INTO Order_Details(orderID,itemID,quantity,BarristaID) VALUES (6,7,3,4);

SELECT *FROM Order_Details

DELETE FROM Reservations

INSERT INTO Reservations(reservID,customerID,date,tableID) VALUES (1,2,'2020-09-20 20:00',1)
INSERT INTO Reservations(reservID,customerID,date,tableID) VALUES (2,2,'2020-10-18 20:30',2)
INSERT INTO Reservations(reservID,customerID,date,tableID) VALUES (3,3,'2020-09-20 16:00',6)
INSERT INTO Reservations(reservID,customerID,date) VALUES (4,1,'2020-11-08 17:45')
INSERT INTO Reservations(reservID,customerID,date) VALUES (5,2,'2020-04-10 22:30')

SELECT * FROM Reservations

DELETE FROM Allergens

INSERT INTO Allergens(allergID,name) VALUES (1,'eggs')
INSERT INTO Allergens(allergID,name) VALUES (2,'milk')
INSERT INTO Allergens(allergID,name) VALUES (3,'nuts')
INSERT INTO Allergens(allergID,name) VALUES (4,'raisin')
INSERT INTO Allergens(allergID,name) VALUES (5,'gluten')

select * from Allergens

DELETE FROM Allergens_MenuItem

INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (2,1)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (2,2)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (2,4)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (1,5)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (4,5)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (5,5)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (4,4)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (3,7)
INSERT INTO Allergens_MenuItem(allergID,itemID) VALUES (4,7)

SELECT * FROM Allergens_MenuItem

DELETE FROM Cats_That_Are_Given_To_Adoption

INSERT INTO Cats_That_Are_Given_To_Adoption(adoptID,catID) VALUES(1,1)
INSERT INTO Cats_That_Are_Given_To_Adoption(adoptID,catID) VALUES(2,4)
INSERT INTO Cats_That_Are_Given_To_Adoption(adoptID,catID) VALUES(3,5)
INSERT INTO Cats_That_Are_Given_To_Adoption(adoptID,catID) VALUES(4,9)

SELECT * FROM Cats_That_Are_Given_To_Adoption

DELETE FROM Cats_Reserved_For_Adoption

INSERT INTO Cats_Reserved_For_Adoption(catID,customerID) VALUES (2,7)
INSERT INTO Cats_Reserved_For_Adoption(catID,customerID) VALUES (6,5)
INSERT INTO Cats_Reserved_For_Adoption(catID,customerID) VALUES (7,1)

SELECT * FROM Cats_Reserved_For_Adoption

DELETE FROM Order_DineIn

INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (1, 7, 1, 1, 41.0, 5.0, 'cash')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (2, 2, 2, 6, 42.0, 2.25, 'card')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (3, 8, 2,4, 26.5, 5.50,'voucher')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,tips,paymentMethod) VALUES (6, 7, 4, 1, 43.50, 4.0,'card')
INSERT INTO Order_DineIn(orderID,customerID,BarristaID,tableID,price,paymentMethod) VALUES (5, 4, 4, 2, 52.0, 'voucher')

SELECT * FROM Order_DineIn



DELETE FROM MenuItem
WHERE price >= 12
SELECT * FROM MenuItem

DELETE FROM Customer
WHERE name LIKE '%Andrei%'
SELECT * FROM Customer

DELETE FROM Cats

UPDATE Cats
SET age = age + 1
WHERE age BETWEEN 1 AND 3
SELECT * FROM Cats

DELETE FROM Order_Details

UPDATE Order_Details
SET quantity = quantity - 1
WHERE BarristaID = 1
SELECT *FROM Order_Details


DELETE FROM Reservations

UPDATE Reservations
SET
	tableID = 9
WHERE
	tableID IS NULL
SELECT * FROM Reservations



--UNION with OR: select all the allergens that appear in Muffin or CheeseCake
SELECT DISTINCT A.name
FROM Allergens_MenuItem AM, MenuItem M, Allergens A
WHERE A.allergID = AM.allergID AND AM.itemID = M.itemID AND (M.name = 'Muffin' OR M.name = 'Cheese Cake')


--UNION with UNION: select all menuItems that are in dine-in orders paid by card or voucher
SELECT M.name
FROM Order_DineIn D, Order_Details O, MenuItem M
WHERE M.itemID = O.ItemID AND O.orderID = D.OrderID AND D.paymentMethod = 'card'
UNION
SELECT M2.name
FROM Order_DineIn D2, Order_Details O2, MenuItem M2
WHERE M2.itemID = O2.ItemID AND O2.orderID = D2.OrderID AND D2.paymentMethod = 'voucher'


--INTERSECTION with INTERSECT: select all allergens that appear in both Muffin and CheeseCake
SELECT A.name
FROM Allergens A, MenuItem M, Allergens_MenuItem AM
WHERE A.allergID=AM.allergID AND AM.itemID=M.itemID AND M.name='Muffin'
INTERSECT
SELECT A2.name
FROM Allergens A2, MenuItem M2, Allergens_MenuItem AM2
WHERE A2.allergID=AM2.allergID AND AM2.itemID=M2.itemID AND M2.name='Cheese Cake'


--INTERSECTION with INTERSECT: select all customers who have paid dine-in orders with both cash and card, as well as the total sum paid with card(price+tips) (arithmetic expression)
SELECT DISTINCT C.name,O.price+O.tips as totalPaid
FROM Customer C, Order_DineIn O
WHERE C.customerID=O.customerID AND O.paymentMethod='card' AND O.customerID IN
(
SELECT O2.customerID
FROM Order_DineIn O2
WHERE O2.paymentMethod='cash'
)


--DIFFERENCE with NOT IN: select all allergens that are in Muffin, but not in Cheese Cake
SELECT A.name
FROM Allergens A, MenuItem M, Allergens_MenuItem AM
WHERE A.allergID=AM.allergID AND AM.itemID=M.itemID AND M.name='Muffin' AND A.allergID NOT IN
(
SELECT A2.allergID
FROM Allergens A2, MenuItem M2, Allergens_MenuItem AM2
WHERE A2.allergID=AM2.allergID AND AM2.itemID=M2.itemID AND M2.name='Cheese Cake'
)


--DIFFERENCE with EXCEPT: select all customers that reserved a cat for adoption but didn't dined in
SELECT C.name
FROM Customer C, Cats_Reserved_For_Adoption CRA
WHERE C.customerID = CRA.customerID
EXCEPT
SELECT DISTINCT C2.name
FROM Customer C2, Order_DineIn O
WHERE C2.customerID = O.customerID


--INNER JOIN: 


--FULL JOIN: 


--LEFT JOIN:


--RIGHT JOIN:


--IN operator in WHERE clause and a subclause: select all cats that are reserved for adoption
SELECT C.name
FROM Cats C
WHERE C.catId IN
(
	SELECT CRA.catID	
	FROM Cats_Reserved_For_Adoption CRA
)


--IN operator in WHERE clause and two subclauses: select all allergens that appear in menu items that cost more than 14
SELECT TOP 5 A.name
FROM Allergens A
WHERE A.allergID IN
(
	SELECT AM.allergID
	FROM Allergens_MenuItem AM
	WHERE AM.itemID IN
	(
		SELECT M.itemID
		FROM MenuItem M
		WHERE M.price>=14
	)
)



--EXISTS operator and a subquery in the WHERE clause: select customers with reservations before 2020-10-01 00:00:00 (condition with AND)
SELECT DISTINCT C.name
FROM Customer C
WHERE EXISTS 
(
SELECT *
FROM Reservations R
WHERE R.customerID=C.customerID AND R.date<='2020-10-01 00:00:00'
)


--EXISTS operator and a subquery in the WHERE clause: select waiters who have served orders where the price was greater than 43 (condition with AND)
SELECT B.name
FROM Barrista B
WHERE EXISTS
(
SELECT *
FROM Order_DineIn O
WHERE O.BarristaID=B.BarristaID AND O.price>=43
)


--subquery in the FROM clause: select all menu items whose prices are higher than the average of all menu items, as well as the difference between their price and the average (arithmetic expression)
SELECT M.name, M.price, M.price-AveragePrice AS diffAvg
FROM (SELECT AVG(price) AS AveragePrice FROM MenuItem) AS A, MenuItem M
WHERE M.price>=AveragePrice


--subquery in the FROM clause: select all customers whose phone numbers end in 7
SELECT C.name, C.phoneNumber
FROM (SELECT * FROM Customer WHERE phoneNumber LIKE '%7') AS CP, Customer C
WHERE C.customerID = CP.customerID


--GROUP BY: select the minimum price of a dine-in order, where the orders have been grouped by their payment method
SELECT MIN(O.price) AS minPrice,O.paymentMethod
FROM Order_DineIn O
GROUP BY O.paymentMethod


--GROUP BY and HAVING: select the number of dine-in orders at each table, where the table has been used for at least 2 orders
SELECT COUNT(*) AS timesUsed,O.tableID
FROM Order_DineIn O
GROUP BY O.tableID
HAVING COUNT(*)>=2


--GROUP BY and HAVING with subquery: select the maximum price for a dine-in order grouped by the table, where each table has been reserved before
SELECT MAX(O.price) AS maxPrice,O.tableID
FROM Order_DineIn O
GROUP BY O.tableID
HAVING O.tableID IN (SELECT DISTINCT tableID FROM Reservations)



--GROUP BY and HAVING with subquery:


--ANY introduces subquery in WHERE clause: select all customers whose rating is smaller than any rating of any barrista
SELECT C.name,C.rating
FROM Customer C
WHERE C.rating < ANY (SELECT rating FROM Barrista)
ORDER BY C.rating

--rewritten with max
SELECT C.name,C.rating
FROM Customer C
WHERE C.rating < (SELECT MAX(rating) FROM Barrista)
ORDER BY C.rating


--ALL introduces subquery in WHERE clause: select all customers whose rating is smaller than all rating of all barrista
SELECT C.name,C.rating
FROM Customer C
WHERE C.rating < ALL (SELECT rating FROM Barrista)
ORDER BY C.rating

--rewritten with min
SELECT C.name,C.rating
FROM Customer C
WHERE C.rating < (SELECT MIN(rating) FROM Barrista)
ORDER BY C.rating


--ANY introduces subquery in WHERE clause: select all customers that have reservations
SELECT C.name
FROM Customer C
WHERE C.customerID = ANY (SELECT R.customerID FROM Reservations R)

--rewritten with IN:
SELECT C.name
FROM Customer C
WHERE C.customerID IN (SELECT R.customerID FROM Reservations R)


--ALL introduces subquery in WHERE clause: select all cats that are not given to adoption
SELECT C.name
FROM Cats C
WHERE C.catId <> ALL (SELECT CGA.catId from Cats_That_Are_Given_To_Adoption CGA)

--rewritten with NOT IN:
SELECT C.name
FROM Cats C
WHERE C.catId NOT IN (SELECT CGA.catId from Cats_That_Are_Given_To_Adoption CGA)



--!!!FOLOSESTE 
--			- NOT DE 2 ORI
--			- OR O DATA
--			- TOP O DATA
--			- JOIN URILE
--			- GROUP BY - mai trebe unul
