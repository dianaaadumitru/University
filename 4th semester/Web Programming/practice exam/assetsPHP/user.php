<?php
include ('connection.php');
session_start();
if (isset($_SESSION['id'])){
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];

}
else {
    header('Location: login.php');
    die();
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome user</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <h3>Welcome <?php echo $username; ?>. </h3>
</div>

<!--<div>-->
<!--    <form action="user.php" method="post">-->
<!--        <input id="view" type="submit" name="view" value="View your Reports">-->
<!--    </form>-->
<!--    <br>-->
<!--</div>-->

<?php
//if (isset($_POST['view'])) {
    $conn = OpenConnection();
    $query = "SELECT * FROM assets.assets WHERE userid='$userId'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>Your assets</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>name</th>";
        echo "<th>description</th>";
        echo "<th>value</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            if ($row['value'] < 10) {
                echo "<tr style='color: red'>";
                echo "<th>".$row['id']."</th>";
                echo "<th>".$row['name']."</th>";
                echo "<th>".$row['description']."</th>";
                echo "<th>".$row['value']."</th>";
                echo "</tr>";
            } else {
                echo "<tr>";
                echo "<th>".$row['id']."</th>";
                echo "<th>".$row['name']."</th>";
                echo "<th>".$row['description']."</th>";
                echo "<th>".$row['value']."</th>";
                echo "</tr>";
            }

        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "You don't have any assets!";
    }

    CloseConnection($conn);
//}
?>

<div>
    <button type="button" onclick="location.href='add.php'">Add assets</button>
    <br>
</div>

<?php
