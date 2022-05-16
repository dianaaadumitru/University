<?php

require 'connection.php';

$types = [];
$sql = "SELECT DISTINCT  type FROM logreport";

if($result = mysqli_query($con, $sql))
{
    $cr = 0;
    while($row = mysqli_fetch_assoc($result)) {
        $types[$cr]['type'] = $row['type'];
        $cr++;
    }
    echo json_encode(['data'=> $types]);
} else {
    http_response_code(404);
}