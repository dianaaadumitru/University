<?php
session_start();
if (isset($_POST['submit'])) {
    include ('connection.php');
    $username = $_POST['username'];
    $conn = OpenConnection();
    $_SESSION['username'] = $username;

    header('Location: user.php');


    CloseConnection($conn);
}
?>



<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <h1>Login</h1>
    <form method="post" action="login.php">
        <input type="text" name = "username" placeholder="Enter username">
        <br><br>
        <input type="submit" name="submit" value="Login">
    </form>
</div>
</body>
</html>


