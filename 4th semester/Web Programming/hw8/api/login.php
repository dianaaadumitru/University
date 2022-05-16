<?php

include("connection.php");

$user = [];
$postData = file_get_contents('php://input');

$request = json_decode($postData);

$username = mysqli_real_escape_string($con, $request->Username);
$password = mysqli_real_escape_string($con, $request->Password);

$sql = "SELECT * FROM loggers WHERE username='$username' AND password='$password'";
$result = mysqli_query($con, $sql);

$cr = 0;
while($row = mysqli_fetch_assoc($result)) {
    $user[$cr]['id'] = $row['id'];
    $user[$cr]['username'] = $row['username'];
    $user[$cr]['password'] = $row['password'];
    $cr++;
}
echo json_encode(['data'=> $user]);
