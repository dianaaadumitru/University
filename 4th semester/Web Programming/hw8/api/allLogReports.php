<?php

    require 'connection.php';

    $logs = [];
    $sql = "select * from logreport";

    if($result = mysqli_query($con, $sql))
    {
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
    } else {
        http_response_code(404);
    }