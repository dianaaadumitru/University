<?php

require 'connection.php';

$postData = file_get_contents('php://input');

$request = json_decode($postData);

$userId = mysqli_real_escape_string($con, (int)$request->userID);
$message = mysqli_real_escape_string($con, $request->message);
$type = mysqli_real_escape_string($con, $request->type);
$severity = mysqli_real_escape_string($con, $request->severity);
$posted_on = mysqli_real_escape_string($con, $request->posted_on);

    $sql = "INSERT INTO logreport( `userID`, `message`, `type`, `severity`, `posted_on`) VALUES ( '$userId', '$message', '$type', '$severity', '$posted_on')";
    if (mysqli_query($con, $sql)) {
        $logs = [];
        $sql2 = "select * from logreport";

        if($result = mysqli_query($con, $sql2))
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

    } else {

        http_response_code(422);
    }
