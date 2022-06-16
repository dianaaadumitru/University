<?php
session_start();

$con = mysqli_connect('localhost', 'root', '', 'assetsdb');
$userId = $_SESSION['id'];
$listToAdd = json_decode(stripslashes($_POST['album']));
foreach ($listToAdd as $asset) {
    $name = $asset[0];
    $description = $asset[1];
    $value = $asset[2];
    $query = "INSERT INTO `assets`(`userId`, `name`, `description`, `value`) VALUES(?,?,?,?)";
    if ($stmt = mysqli_prepare($con, $query)) {
        mysqli_stmt_bind_param($stmt, "issi", $userId, $name, $description, $value);
        mysqli_stmt_execute($stmt);
        echo "asset added!";
    }
}

mysqli_close($con);

?>

