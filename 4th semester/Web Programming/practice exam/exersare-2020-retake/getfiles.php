
<?php
include('connect.php'); 

session_start();
    if (isset($_SESSION['id']))
    { $userId = $_SESSION['id'];}
    global $conn;
    $sql = "SELECT * FROM files WHERE userID='$userId'";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        // output data of each row
        $users = array();
        while ($row = mysqli_fetch_assoc($result)) {
     
            $bid = $row["id"];
            $title = $row["userid"];
            $author = $row["filename"];
            $pages = $row["filepath"];
            $genre = $row["size"];

            $user = array(
                "id " => $bid, "userid" => $title, "filename" => $author,
                "filepath" => $pages, "size" => $genre
            );
            $users[] = $user;
        }
        
        echo json_encode($users);
    }