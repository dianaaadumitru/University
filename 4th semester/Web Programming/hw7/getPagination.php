<?php
include('connection.php');


if (isset($_GET['pageno'])) {
    $pageno = $_GET['pageno'];
} else {
    $pageno = 1;
}

$logsPerPage = 4;

$offset = ($pageno - 1) * $logsPerPage;

$conn = OpenConnection();

$totalLogs = 'SELECT COUNT(*) FROM phplab.logreport';
$result = $conn->query($totalLogs);
$totalRows = mysqli_fetch_array($result)[0];

$totalPages = ceil($totalRows / $logsPerPage);

$query = "SELECT * FROM phplab.logreport LIMIT $offset, $logsPerPage" ;
$result2 = mysqli_query($conn, $query);

if (mysqli_num_rows($result2)) {
    echo "<table style='margin-left: auto;margin-right: auto;'>";
    echo "<tr>";
    echo "<th>ID</th>";
    echo "<th>Author</th>";
    echo "<th>message</th>";
    echo "<th>type</th>";
    echo "<th>severity</th>";
    echo "<th>posted_on</th>";
    echo "</tr>";

    while ($row = mysqli_fetch_array($result2)) {
        echo "<tr>";
        echo "<th>".$row['id']."</th>";
        echo "<th>".$row['userID']."</th>";
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

?>


