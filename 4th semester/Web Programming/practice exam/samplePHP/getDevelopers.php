
<?php
include('connection.php');

session_start();

$conn = OpenConnection();
$sql = "SELECT * FROM softwaredeveloper";
$result = mysqli_query($conn, $sql);

if ($result) {
    // output data of each row
    $developers = array();
    while ($row = mysqli_fetch_assoc($result)) {

        $id = $row["id"];
        $name = $row["name"];
        $age = $row["age"];
        $skills = $row["skills"];

        $develolper = array(
            "id " => $id, "name" => $name, "age" => $age,
            "skills" => $skills
        );
        $developers[] = $develolper;
    }

    echo json_encode($developers);
}