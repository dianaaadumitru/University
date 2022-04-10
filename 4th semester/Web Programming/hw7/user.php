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
    <script src="main.js"></script>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <h3>Welcome <?php echo $username; ?>. </h3>
</div>

<div>
    <form action="user.php" method="post">
        <input id="view" type="submit" name="view" value="View your Reports">
    </form>
    <br>
</div>

<?php
if (isset($_POST['view'])) {
    $conn = OpenConnection();
    $query = "SELECT * FROM phplab.logreport WHERE userID='$userId'";
    $result = mysqli_query($conn, $query);
    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>Your reports</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>message</th>";
        echo "<th>type</th>";
        echo "<th>severity</th>";
        echo "<th>posted_on</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['message']."</th>";
            echo "<th>".$row['type']."</th>";
            echo "<th>".$row['severity']."</th>";
            echo "<th>".$row['posted_on']."</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    }
    CloseConnection($conn);
}
?>

<div>
<form action="user.php" method="post">
    <input type="text" name="id" placeholder="id">
    <input id="delete" type="submit" name="delete" value="Delete log report">
</form>
<br>
</div>

<?php
if (isset($_POST['delete'])) {
    $conn = OpenConnection();
    $id = $_POST['id'];

    $query = "DELETE FROM phplab.logreport WHERE id='$id' AND userID='$userId'";

    $conn->query($query);

    header('location:user.php');
    CloseConnection($conn);
}
?>


<br>
<div>
    <form action="logout.php">
        <input type="submit" name="logout" value="Logout">
    </form>
</div>
<br>

</body>
</html>