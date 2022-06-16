<?php

header("Access-Control-Allow-Origin: *");

// Create connection
$conn = new mysqli("localhost", "root", "diana", "documents");

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

//$_GET - array of variables passed to the current script via the URL parameters
//$_POST - array of variables passed to the current script via the HTTP POST method

function get_documents()
{
    global $conn;
    $sql = "select d.name from documents.documents d";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        $documents = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $name = $row['name'];

            $d = array("name" => $name);
            $documents[] = $d;
        }
        echo json_encode($documents);
    }
}

function update_keyword()
{
    global $conn;
    $document = $_POST['select'];
    $keyboard_nr = $_POST['keynr'];
    $value = $_POST['newval'];

    print_r([$document, $keyboard_nr, $value]);

    $keyboard_nr = "keyboard" . $keyboard_nr;
    $sql = "update document set " . $keyboard_nr . "= ? where name = ?";

    print_r($sql);

    if ($stmt = mysqli_prepare($conn, $sql)) {
        // Bind variables to the prepared statement as parameters
        mysqli_stmt_bind_param($stmt, "ss", $value, $document);
        mysqli_stmt_execute($stmt);
        echo "<div>Keyword updated</div>";
            //header("location:index.php");
    }
    else
        echo "<div>Error prepare!</div>";
}

function get_doc_with_keyw()
{
    global $conn;

    $keywords = array($_POST['lines']);

    $sql = "select * from document";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        $documents = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $name = $row['name'];
            $k1 = $row['keyword1'];
            $k2 = $row['keyword2'];
            $k3 = $row['keyword3'];
            $k4 = $row['keyword4'];
            $k5 = $row['keyword5'];

            $count = 0;
            foreach ($keywords as $key) {
                if ($k1 == $key or $k2 == $key or $k3 == $key or $k4 == $key or $k5 == $key) {
                    $count += 1;
                }
                if ($count > 3) {
                    break;
                }
            }
            if ($count == 3) {
                $d = array("name" => $name, "keyword1" => $k1, "keyword2" => $k2, "keyword3" => $k3, "keyword4" => $k4, "keyword5" => $k5);
                $documents[] = $d;
            }
        }
        echo json_encode($documents);

    }
}

get_documents();

if (isset($_POST['callFunc1'])) {
    get_doc_with_keyw();
}

if(isset($_POST['submit'])) {
    $pet = $_POST['lines'];
    //$pet = array($_POST['pet1']);
    print_r($pet);
}


if (isset($_POST['update'])) {
    update_keyword();
    print_r("here");
}

?>