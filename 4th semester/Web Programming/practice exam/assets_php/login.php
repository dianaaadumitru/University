<?php

session_start();

if (isset($_POST['login'])) {
    echo 'pressed login';
    $username = $_POST['username'];
    $password = $_POST['password'];

    $con = mysqli_connect('localhost', 'root', '', 'assetsdb');
    $sql = "select id, username, password from users where username='$username' and password='$password' limit 1";
    $query = $con->query($sql);
    if($query) {
        $row = mysqli_fetch_row($query);
        echo $row[0];
        $dbUserId = $row[0];
        $dbUsername = $row[1];
        $dbPassword = $row[2];

        if ($username == $dbUsername && $password == $dbPassword && $username != '') {
            $_SESSION['username'] = $username;
            $_SESSION['id'] = $dbUserId;

            header('Location: mainPage.php');
        }
        else {
            echo "incorrect credentials";
        }
    }


    $con->close();
}

?>


<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div  class="col-md-2 col-md-offset-5">
    <h1>Login</h1>
    <form method="post">
        <input type="text" name = "username" class="form-control" placeholder="Enter username">
        <br>
        <input type="password" name="password" class="form-control" placeholder="Enter password here">
        <br>
        <input class="btn btn-primary btn-block" type="submit" name="login" value="Login">
    </form>
</div>

</body>
</html>