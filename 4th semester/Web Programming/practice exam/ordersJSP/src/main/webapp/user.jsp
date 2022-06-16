<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 9:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <form action="AddProductController" method="post">
        Name: <input type="text" name="product"> <br><br>
        Description: <input type="text" name="description"> <br><br>
        <input class="button" type="submit" value="Add product"/>
    </form>
    <br/><br/>
    <p>Display all products which have a name starting with: </p>
    <form action="DisplayProductsController" method="post">
        <input type="text" name="startsWith"> <br><br>
        <input class="button" type="submit" value="Display products"/>
    </form>
    <br/><br/>
    <a href="index.jsp"><button>Go back</button></a>
</div>

</body>
</html>
