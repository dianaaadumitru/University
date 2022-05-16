<?php
session_start();
if (isset($_POST['submit'])) {
    include ('connection.php');
    $username = $_POST['username'];
    $password = $_POST['password'];

    $conn = OpenConnection();
    $sql = "SELECT * FROM phplab.loggers WHERE username='$username' LIMIT 1";
    $query = $conn->query($sql);

    if (mysqli_num_rows($query) == 0) {
        echo "<b><i>Invalid credentials</i><b>";
    } else {
        if ($query) {
            $row = mysqli_fetch_row($query);
            $userId = $row[0];
            $dbUsername = $row[1];
            $dbPassword = $row[2];

            if($username == $dbUsername && $password == $dbPassword) {
                $_SESSION['username'] = $username;
                $_SESSION['id'] = $userId;

                header('Location: user.php');
            }
            else {
                echo "<b><i>Invalid credentials</i><b>";
            }
        }
    }

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
        <input type="password" name="password" placeholder="Enter password">
        <br><br>
        <input type="submit" name="submit" value="Login">
    </form>
</div>
</body>
</html>


