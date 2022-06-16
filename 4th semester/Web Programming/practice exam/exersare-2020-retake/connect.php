<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "test";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
//echo "Connected successfully";


function OpenConnection(): mysqli
{
    $host = "localhost";
    $username = "root";
    $password = "";
    $db = "test";

    return mysqli_connect($host, $username, $password, $db);
}

function CloseConnection(mysqli $con)
{
    $con->close();
}




