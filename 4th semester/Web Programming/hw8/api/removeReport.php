<?php

require 'connection.php';

$id = ($_GET['id'] !== null && (int)$_GET['id'] > 0)? mysqli_real_escape_string($con, (int)$_GET['id']): false;
$userId = ($_GET['userID'] !== null && (int)$_GET['userID'] > 0)? mysqli_real_escape_string($con, (int)$_GET['userID']): false;

$sql = "DELETE FROM logreport WHERE id='$id' and userID='$userId'";

if(mysqli_query($con, $sql))
{
    http_response_code(204);
}
else
{
    return http_response_code(422);
}