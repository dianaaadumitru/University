<?php

header("Access-Control-Allow-Origin: *");

// Create connection
$conn = new mysqli("localhost", "root", "", "exam7");

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

//$_GET - array of variables passed to the current script via the URL parameters
//$_POST - array of variables passed to the current script via the HTTP POST method


function get_websites()
{
    global $conn;
    $sql = "select w.url, count(d.webid) as documents from website w left join document d on d.webid=w.id group by d.webid;";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        $websites = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $url = $row['url'];
            $count = $row['documents'];

            $site = array("url" => $url, "documents" => $count);
            $websites[] = $site;
        }
        echo json_encode($websites);
    }
}

get_websites();
?>