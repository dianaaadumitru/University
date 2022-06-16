<?php

$con = mysqli_connect('localhost', 'root', '', 'assetsdb');
session_start();
if (isset($_SESSION['id'])) {
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];
} else {
    header('Location: login.php');
    die();
}

echo "<h3>Welcome, $username!</h3>";

$sql = "select id, name, description, value from assets where userId='$userId'";

$result = mysqli_query($con, $sql);
if (mysqli_num_rows($result) > 0) {
    echo "<table>";
    echo "<tr>";
    echo "<th>id</th>";
    echo "<th>name</th>";
    echo "<th>description</th>";
    echo "<th>value</th>";
    echo "</tr>";
    while ($row = mysqli_fetch_array($result)) {
        if ($row['value'] > 10) {
            echo "<tr style='background-color: red'>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['description']."</th>";
            echo "<th>".$row['value']."</th>";
        }
        else {
            echo "<tr>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['description']."</th>";
            echo "<th>".$row['value']."</th>";
        }


    }
    echo "</table>";
}



if (isset($_POST['logout'])) {
    session_destroy();
}
?>

<!DOCTYPE html>
<html>
<button onclick="window.location.href = 'addAsset.html';" id="myButton" class="float-left submit-button" >add new asset</button>
<button onclick="window.location.href = 'login.php';" id="myButton" class="float-left submit-button" >Logout</button>

</html>

