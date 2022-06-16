<!DOCTYPE html>
<html lang="en">
<head>
    <script type="text/javascript" src="main.js"></script>
    <link rel="stylesheet" type="text/css" href="main.css">
    <title>Add</title>
</head>

<body>
<div>
    <p>Add a new asset</p>
    <form method="post">
        <input type="text" name="nameAdd" id="nameAdd" placeholder="name"><br/><br/>
        <input type="text" name="descriptionAdd" id="descriptionAdd" placeholder="description"><br/><br/>
        <input type="text" name="valueAdd" id="valueAdd" placeholder="value"><br/><br/>
        <input id="add" type="submit" name="add" value="Add asset">
        <button type="button" onclick="location.href='./user.php'">Go back</button>
<!--        <button id="add" type="submit" name="add" onclick="addAsset()">Add</button><br/>-->
    </form>
</div>
</body>
</html>

<?php
//echo "Invalid Data";
include ('connection.php');
session_start();
if (isset($_SESSION['id'])){
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];


}

if (isset($_POST['add'])) {
$conn = OpenConnection();
$name = $_POST['nameAdd'];
$description = $_POST['descriptionAdd'];
$value = $_POST['valueAdd'];

if ( $name == '' || $description == '' || $value < 0  || is_numeric($value) != 1)
echo '<div>Invalid Data</div>';
else {

//        $query = "INSERT INTO phplab.logreport(`id`, `userID`, `name`, `description`, `value`, `posted_on`) VALUES ($id,$userId,'$name','$description','$value','$posted_on')";
$query = "INSERT INTO assets.assets( `userid`, `name`, `description`, `value`) VALUES (?, ?, ?, ?)";

if($stmt = mysqli_prepare($conn, $query)){
mysqli_stmt_bind_param($stmt, "issi",  $userId, $name, $description, $value);
mysqli_stmt_execute($stmt);
echo "<div>Asset added</div>";
} else {
echo "<div>error</div>";
}

}

CloseConnection($conn);
}

?>

