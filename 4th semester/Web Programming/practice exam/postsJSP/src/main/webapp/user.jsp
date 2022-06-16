<%@ page import="ro.ubb.posts.DB.DBManager" %>
<%@ page import="ro.ubb.posts.Domain.Post" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 6/15/2022
  Time: 3:48 PM
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
<%
    if (session.getAttribute("username") != null) {
        String username = (String) session.getAttribute("username");
%>
<p1><div>Hello <%=username%></div></p1>
<%
        DBManager dbm = new DBManager();
        List<Post> posts = dbm.getAllPosts();
        if (posts.size() > 0) {
%>
    <p style='text-align: center'>All posts</p>
    <table style='margin-left: auto;margin-right: auto;'>
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>topicId</th>
            <th>Text</th>
            <th>Date</th>
        </tr>
<%
        } else {
%>
    <p><div>there are no posts in db</div></p>
<%
        }
        for(Post post: posts) {
%>
    <tr>
        <td><%=post.getId()%></td>
        <td><%=post.getUser()%></td>
        <td><%=post.getTopicId()%></td>
        <td><%=post.getText()%></td>
        <td><%=post.getDate()%></td>
    </tr>

<%
        }
%>
</table>
<%

    }
%>

    <p>Update a post: </p>

    <form action="UserController" method="post">
        <input id="postIdUpdate" name="postIdUpdate" placeholder="postId"><br/>
        <input id="topicIdUpdate" name="topicIdUpdate" placeholder="topicId"><br/>
        <input id="textUpdate" name="textUpdate" placeholder="text"><br/>
        <input class="button" type="submit" value="Update"/>
    </form>
    <br/><br/>

    <p>Add a post: </p>
    <form action="AddController" method="post">
        <input id="topicNameAdd" name="topicNameAdd" placeholder="topicName"><br/>
        <input id="textAdd" name="textAdd" placeholder="text"><br/>
        <input class="button" type="submit" value="Add"/>
    </form>
    <br/><br/>
</div>
</body>
</html>
