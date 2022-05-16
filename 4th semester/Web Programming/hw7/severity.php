<?php
include ('connection.php');
include ('LogReport.php');
try{
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $con = OpenConnection();
        $severity = json_decode(file_get_contents('php://input'), true)['severity'];

        if($severity !== false && $severity !== "choose severity"){
            $sql = sprintf("SELECT * FROM phplab.logreport WHERE severity = '%s'", $severity);
        }
        else{
            $sql = "SELECT * FROM phplab.logreport";
        }

        $result_set = $con->query($sql);
        $rows = array();
        while ($row = mysqli_fetch_array($result_set)) {
            $rows[] = new LogReport($row['id'],$row['userID'],$row['message'],$row['type'],$row['severity'],$row['posted_on']);

        }
        header('HTTP/1.1 200 OK');
        echo json_encode($rows);
        CloseConnection($con);
        exit;
    }
} catch (Exception $e) {
    header('HTTP/1.1 500 INTERNAL_SERVER_ERROR');
    exit;
}