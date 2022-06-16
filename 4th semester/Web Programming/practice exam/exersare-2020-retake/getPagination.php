
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagination</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"> 
</head>



<?php

include('connect.php');
session_start();
if (isset($_SESSION['id'])){
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];

}
else {
    header('Location: login.php');
    die();
}




if (isset($_GET['pageno'])) {
    $pageno = $_GET['pageno'];
} else {
    $pageno = 1;
}

$logsPerPage = 5;   //how many entries

$offset = ($pageno - 1) * $logsPerPage;

$conn = OpenConnection();

$totalLogs = 'SELECT COUNT(*) FROM files ';
$result = $conn->query($totalLogs);
$totalRows = mysqli_fetch_array($result)[0];

$totalPages = ceil($totalRows / $logsPerPage);

$query = "SELECT * FROM files WHERE userID='$userId' LIMIT $offset, $logsPerPage" ;
$result2 = mysqli_query($conn, $query);

if (mysqli_num_rows($result2)) {
    echo  "<!DOCTYPE html>";
    echo " <script src='https://kit.fontawesome.com/c9c2cadf89.js' crossorigin='anonymous'></script>";
    echo " <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>";
    echo "<table  class='table' style='margin-left: auto;margin-right: auto;' >";
    echo "<tr>";
    echo "<th>ID</th>";
    echo "<th>userid</th>";
    echo "<th>filename</th>";
    echo "<th>filepath</th>";
    echo "<th>size</th>";
    echo "</tr>";

    while ($row = mysqli_fetch_array($result2)) {
        echo "<tr>";
        echo "<th>".$row['id']."</th>";
        echo "<th>".$row['userid']."</th>";
        echo "<th>".$row['filename']."</th>";
        echo "<th>".$row['filepath']."</th>";
        echo "<th>".$row['size']."</th>";
        echo "</tr>";
    }
    echo "</table>";
    echo "</br>";
}
CloseConnection($conn);

?>


