<?php
include ('connection.php');
session_start();
if (isset($_SESSION['id'])){
    $userId = $_SESSION['id'];
//    $username = $_SESSION['username'];

}

if (isset($_POST['add'])) {
    $conn = OpenConnection();
    $name = $_POST['nameAdd'];
    $description = $_POST['descriptionAdd'];
    $value = $_POST['valueAdd'];

    if ( $name == '' || $description == '' || $value == '')
        echo "Invalid Data";
    else {

//        $query = "INSERT INTO phplab.logreport(`id`, `userID`, `name`, `description`, `value`, `posted_on`) VALUES ($id,$userId,'$name','$description','$value','$posted_on')";
        $query = "INSERT INTO assets.assets( `userid`, `name`, `description`, `value`) VALUES (?, ?, ?, ?)";

        if($stmt = mysqli_prepare($conn, $query)){
            mysqli_stmt_bind_param($stmt, "issi",  $userId, $name, $description, $value, $posted_on);
            mysqli_stmt_execute($stmt);
            echo "Asset added";
        } else {
            echo "error";
        }

    }

    CloseConnection($conn);
}
