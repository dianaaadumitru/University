<%@ page import="ro.ubb.assetsJSP.DB.DBManager" %>
<%@ page import="ro.ubb.assetsJSP.Domain.Asset" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<%
    if (session.getAttribute("username") != null) {
        String username = (String) session.getAttribute("username");
%>
<p1><div>Hello <%=username%></div></p1>
<%
        DBManager dbm = new DBManager();
        List<Asset> assets = dbm.getUserAssets(username);

%>
<p style='text-align: center'>Your assets</p>";
<table style='margin-left: auto;margin-right: auto;'>
<tr>
<th>ID</th>
<th>name</th>
<th>description</th>
<th>value</th>
</tr>
<%

        for (Asset asset: assets) {
            if(asset.getValue()>10){
%>
<tr style="color:red;">
    <td><%=asset.getId()%></td>
    <td><%=asset.getName()%></td>
    <td><%=asset.getDescription()%></td>
    <td><%=asset.getValue()%></td>
</tr>
<%
            } else {
%>
    <tr>
        <td><%=asset.getId()%></td>
        <td><%=asset.getName()%></td>
        <td><%=asset.getDescription()%></td>
        <td><%=asset.getValue()%></td>
    </tr>
<%
            }
        }
    }

%>

</body>
</html>
