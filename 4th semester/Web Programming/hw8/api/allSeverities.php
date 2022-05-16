<?php

require 'connection.php';

$severities = [];
$sql = "SELECT DISTINCT  severity FROM logreport";

if($result = mysqli_query($con, $sql))
{
    $cr = 0;
    while($row = mysqli_fetch_assoc($result)) {
        $severities[$cr]['severity'] = $row['severity'];
        $cr++;
    }
    echo json_encode(['data'=> $severities]);
} else {
    http_response_code(404);
}