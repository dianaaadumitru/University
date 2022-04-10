<?php
session_start();
session_destroy();
?>

<!DOCTYPE html>
<html>
<head>
    <title>Logout page</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <form action = "index.html">
        <input type="submit" name="redirect" value = "Go back to home">
    </form>
</div>
</body>
</html>