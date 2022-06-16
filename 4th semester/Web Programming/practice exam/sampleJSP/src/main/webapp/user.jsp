<%@ page import="ro.ubb.sampleJSP.Domain.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="ro.ubb.sampleJSP.DB.DBManager" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
<%
    DBManager dbm = new DBManager();
    List<Project> projects = dbm.getAllProjects();
%>
    <p style='text-align: center'>All projects</p>
    <table style='margin-left: auto;margin-right: auto;'>
        <tr>
            <th>ID</th>
            <th>ProjectManagerID</th>
            <th>name</th>
            <th>description</th>
            <th>members</th>
        </tr>
<%
            for(Project project: projects) {
%>
        <tr>
            <td><%=project.getId()%></td>
            <td><%=project.getProjectManagerID()%></td>
            <td><%=project.getName()%></td>
            <td><%=project.getDescription()%></td>
            <td><%=project.getMembers()%></td>
        </tr>
<%
            }
%>
    </table>
    <br/><br/>

    <%
        if (session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            List<Project> yourProjects = dbm.getUsersProjects(username);
    %>
    <p style='text-align: center'>Your projects</p>
    <table style='margin-left: auto;margin-right: auto;'>
        <tr>
            <th>name</th>
        </tr>
        <%
            for(Project project: yourProjects) {
        %>
        <tr>
            <td><%=project.getName()%></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>

    <br/><br/>
    <p>Add developer to another projects</p>
    <form action="PartialAddController" method="post">
        User: <input type="text" name="userAdd"> <br><br>
        Project: <input type="text" name="projectAdd"> <br><br>
        <input class="button" type="submit" value="Submit"/>
    </form>

    <form action="ConfirmAddController" method="post">
        <input class="button" type="submit" value="Add all"/>
    </form>

    <br><br><br>
    <a href="index.jsp"><button>Go back</button></a>
</div>
</body>
</html>
