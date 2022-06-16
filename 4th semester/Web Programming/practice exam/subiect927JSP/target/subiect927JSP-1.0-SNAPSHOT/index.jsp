<%@ page import="ro.ubb.subiect927JSP.DB.DBManager" %>
<%@ page import="ro.ubb.subiect927JSP.Domain.Document" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/16/2022
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <p>Add keyword: </p>
    <form action="AddKeywordController" method="post">
        key: <input type="text" name="keyAdd"> <br><br>
        value: <input type="text" name="valueAdd"> <br><br>
        <input class="button" type="submit" value="Add"/>
    </form>

    <br><br>
    <p>Display all documents which have a title containing: </p>
    <form action="DisplayDocumentsController" method="post">
        <input type="text" name="contains"> <br><br>
        <input class="button" type="submit" value="Display documents"/>
    </form>
    <br/><br/>

    <%

        if (session.getAttribute("containsText") != null) {
            String text = (String) session.getAttribute("containsText");
            DBManager dbm = new DBManager();
            List<Document> documents = dbm.displayDocuments(text);
    %>
    <p style='text-align: center'>Documents</p>
    <table style='margin-left: auto;margin-right: auto;'>
        <tr>
            <th>ID</th>
            <th>name</th>
            <th>description</th>
        </tr>
            <%
            int index = 0;
        for(Document document: documents) {
            if ( index % 2 == 0) {
%>
        <tr style="color: blueviolet">
            <td><%=document.getId()%></td>
            <td><%=document.getTitle()%></td>
            <td><%=document.getListOfTemplates()%></td>
        </tr>
            <%
            } else {
              %>
        <tr>
            <td><%=document.getId()%></td>
            <td><%=document.getTitle()%></td>
            <td><%=document.getListOfTemplates()%></td>
        </tr>
            <%
            }
            index += 1;
        }

    }
%>    
</div>
</body>
</html>
