<?php

include("connection.php");

$logs = [];
$postData = file_get_contents('php://input');

$request = json_decode($postData);

$id = mysqli_real_escape_string($con, (int)$request->UserId);


$sql = "SELECT * FROM logreport WHERE userID = '$id'";
$result = mysqli_query($con, $sql);

$cr = 0;
while($row = mysqli_fetch_assoc($result)) {
    $logs[$cr]['id'] = $row['id'];
    $logs[$cr]['userID'] = $row['userID'];
    $logs[$cr]['message'] = $row['message'];
    $logs[$cr]['type'] = $row['type'];
    $logs[$cr]['severity'] = $row['severity'];
    $logs[$cr]['posted_on'] = $row['posted_on'];
    $cr++;
}
echo json_encode(['data'=> $logs]);
