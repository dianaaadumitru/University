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
    else {
        echo "You don't have any log reports!";
    }

    CloseConnection($conn);
}
?>

<div>
    <form action="user.php" method="post">
<!--        <input type="text" name="idAdd" placeholder="id">-->
        <input type="text" name="messageAdd" placeholder="message">
        <input type="text" name="typeAdd" placeholder="type">
        <input type="text" name="severityAdd" placeholder="severity">
        <input type="text" name="posted_onAdd" placeholder="posted_on">
        <input id="add" type="submit" name="add" value="Add log report">
    </form>
    <br>
</div>

<?php
if (isset($_POST['add'])) {
    $conn = OpenConnection();
//    $id = $_POST['idAdd'];
    $message = $_POST['messageAdd'];
    $type = $_POST['typeAdd'];
    $severity = $_POST['severityAdd'];
    $posted_on = $_POST['posted_onAdd'];


    $idExists = "SELECT id FROM phplab.logreport";

    $res = mysqli_query($conn, $idExists);
    $idValues = Array();

    while ( $row = mysqli_fetch_assoc($res) ) {

        $idValues[] = $row['id'];

    }

    if ( $message == '' || $type == '' || $severity == '' || $posted_on == '')
        echo "Invalid Data";
    else {

//        $query = "INSERT INTO phplab.logreport(`id`, `userID`, `message`, `type`, `severity`, `posted_on`) VALUES ($id,$userId,'$message','$type','$severity','$posted_on')";
        $query = "INSERT INTO phplab.logreport( `userID`, `message`, `type`, `severity`, `posted_on`) VALUES ( ?, ?, ?, ?, ?)";

        if($stmt = mysqli_prepare($conn, $query)){
            mysqli_stmt_bind_param($stmt, "issss",  $userId, $message, $type, $severity, $posted_on);
            mysqli_stmt_execute($stmt);
            echo "Log Report added";
        } else {
            echo "error";
        }

    }


    CloseConnection($conn);
}
?>


<div>
<form action="user.php" method="post">
    <input type="text" name="id" placeholder="id">
    <input id="delete" type="submit" name="delete" value="Delete log report" onclick="return confirm('Are you sure?')">
</form>
<br>
</div>

<?php
if (isset($_POST['delete'])) {
    $conn = OpenConnection();
    $id = $_POST['id'];

    $idExists = "SELECT id FROM phplab.logreport WHERE userID='$userId'";

    $res = mysqli_query($conn, $idExists);
    $idValues = Array();

    while ( $row = mysqli_fetch_assoc($res) ) {

        $idValues[] = $row['id'];

    }

    if ($id == '' || in_array($id, $idValues) != 1)
        echo "Invalid ID";
    else {
        $query = "DELETE FROM phplab.logreport WHERE id=? AND userID=?";

        if($stmt = mysqli_prepare($conn, $query)){
            mysqli_stmt_bind_param($stmt, "ii", $id, $userId);
            mysqli_stmt_execute($stmt);
            echo "Log Report deleted";
        } else {
            echo "error";
        }
    }


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