<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 2:33 PM
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
    <form action="LoginController" method="post">
        Username: <input type="text" name="user"> <br><br>
        Password: <input type="password" name="password"> <br><br>
        <input class="button" type="submit" value="Login"/>
    </form>
</div>
</body>
</html>
