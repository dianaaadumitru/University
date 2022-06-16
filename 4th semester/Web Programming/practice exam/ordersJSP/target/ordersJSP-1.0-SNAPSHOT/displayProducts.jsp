<%@ page import="ro.ubb.ordersJSP.DB.DBManager" %>
<%@ page import="ro.ubb.ordersJSP.Domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ro.ubb.ordersJSP.Domain.Order" %><%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 10:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Display Products</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
<%

    if (session.getAttribute("startsWith") != null) {
        String text = (String) session.getAttribute("startsWith");
        DBManager dbm = new DBManager();
        List<Product> products = dbm.getProductsStartingWith(text);
%>
<p style='text-align: center'>Products</p>
<table style='margin-left: auto;margin-right: auto;'>
    <tr>
        <th>ID</th>
        <th>name</th>
        <th>description</th>
    </tr>
<%
        for(Product product: products) {
%>
    <tr>
        <td><%=product.getId()%></td>
        <td><%=product.getName()%></td>
        <td><%=product.getDescription()%></td>
    </tr>
<%
        }
    }
%>
    <form action="PartialOrderController" method="post">
        productName: <input type="text" name="productName"> <br><br>
        Quantity: <input type="text" name="quantity"> <br><br>
        <input class="button" type="submit" value="Submit"/>

    </form>
    <br><br>
    <form action="ConfirmOrderController" method="post">
        <input class="button" type="submit" value="Add all"/>
    </form>
    <form action="CancelOrderController" method="post">
        <input class="button" type="submit" value="Add all"/>
    </form>
    <br/><br/>
    <a href="user.jsp"><button>Go back</button></a>
</div>
</body>
</html>
