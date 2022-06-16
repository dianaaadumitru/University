<%@ page import="java.util.ArrayList" %>
<%@ page import="ro.ubb.sampleJSP.Domain.UserToAdd" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 11:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <%
        List<UserToAdd> userToAdd = new ArrayList<>();
        session.setAttribute("partialAdd", userToAdd);
    %>
    <form action="LoginController" method="post">
        Username: <input type="text" name="user"> <br><br>
        <input class="button" type="submit" value="Login"/>
    </form>
</div>
</body>
</html>
