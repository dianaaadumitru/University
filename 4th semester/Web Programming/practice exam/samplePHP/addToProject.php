<?php

include('connection.php');
try {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $con = OpenConnection();
        $json = json_decode(file_get_contents('php://input'), true);


        $developers = $json['developers'];
        $projects = $json['projects'];


        for ($i = 0; $i < sizeof($projects); $i++) {
            $sql = "SELECT members FROM project WHERE name='$projects[$i]'";
            $query = $con->query($sql);

            if (mysqli_num_rows($query) == 0) {
                $query2 = "INSERT INTO sample.project(`projectManagerId`, `name`, `description`, `members`) VALUES (0,'$projects[$i]','','')";
                $con->query($query2);
            } else {
                if ($query) {
                    $row = mysqli_fetch_row($query);
                    $members = $row[0];
                    $members .= ",";
                    $members .= $developers[$i];

                    $query3 = "UPDATE sample.project SET members='$members' WHERE name='$projects[$i]'";
                    $con->query($query3);
                }
            }
        }
        header('HTTP/1.1 200 OK');

        CloseConnection($con);
        exit;
    }
} catch (Exception $e) {
    header('HTTP/1.1 500 INTERNAL_SERVER_ERROR');
    exit;
}