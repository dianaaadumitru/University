<?php
include ('connection.php');

$conn = OpenConnection();
$query = "SELECT * FROM phplab.logreport";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result)) {
    echo "<table style='margin-left: auto;margin-right: auto;'>";
    echo "<tr>";
    echo "<th>ID</th>";
    echo "<th>Author</th>";
    echo "<th>message</th>";
    echo "<th>type</th>";
    echo "<th>severity</th>";
    echo "<th>posted_on</th>";
    echo "</tr>";

    while ($row = mysqli_fetch_array($result)) {
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
