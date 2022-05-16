<?php

include("connection.php");

$logs = [];
$postdata = file_get_contents("php://input");
$formData = json_decode($postdata);
$filter = filter_var($formData->Filter, FILTER_SANITIZE_FULL_SPECIAL_CHARS);

//echo "123  ", $filter;

if ($filter != 'choose type') {
    $result = mysqli_query($con, "select * from logreport where type = '$filter'");
} else {
    $result = mysqli_query($con, "SELECT * FROM logreportn");
}

$cr = 0;
while ($row = mysqli_fetch_assoc($result)) {
    $logs[$cr]['id'] = $row['id'];
    $logs[$cr]['userID'] = $row['userID'];
    $logs[$cr]['message'] = $row['message'];
    $logs[$cr]['type'] = $row['type'];
    $logs[$cr]['severity'] = $row['severity'];
    $logs[$cr]['posted_on'] = $row['posted_on'];
    $cr++;
}

echo json_encode(['data' => $logs]);
$count = mysqli_num_rows($result);


