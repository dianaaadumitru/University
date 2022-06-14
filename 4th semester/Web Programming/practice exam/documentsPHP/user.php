<!DOCTYPE html>
<html>
<head>
    <title>Welcome user</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>

<div>
    <form action="user.php" method="post">
        <input id="view" type="submit" name="view" value="View all documents">
    </form>
    <br>
</div>


<?php
include ('connection.php');
session_start();

if (isset($_POST['view'])) {
    $conn = OpenConnection();

    $query = "SELECT * FROM documents.documents";

    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>All documents</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>name</th>";
        echo "<th>keyword1</th>";
        echo "<th>keyword2</th>";
        echo "<th>keyword3</th>";
        echo "<th>keyword4</th>";
        echo "<th>keyword5</th>";

        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>" . $row['id'] . "</th>";
            echo "<th>" . $row['name'] . "</th>";
            echo "<th>" . $row['keyward1'] . "</th>";
            echo "<th>" . $row['keyward2'] . "</th>";
            echo "<th>" . $row['keyward3'] . "</th>";
            echo "<th>" . $row['keyward4'] . "</th>";
            echo "<th>" . $row['keyward5'] . "</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    } else {
        echo "<div>There are no documents in the db!</div>";
    }

    CloseConnection($conn);
}
?>

<div>
    <br/><br/>
    <form action="user.php" method="post">
        <!--        <input type="text" name="idAdd" placeholder="id">-->
        <label>Update a document's keywords</label><br/><br/>
        <input type="text" name="documentId" placeholder="documentId">
        <select name="keywordField">
            <option selected disabled>choose keyword field</option>
            <option>keyward1</option>
            <option>keyward2</option>
            <option>keyward3</option>
            <option>keyward4</option>
            <option>keyward5</option>
        </select>
        <input type="text" name="keyword" placeholder="keyword">
        <input id="update" type="submit" name="update" value="Update keyword">
    </form>
    <br>
</div>

<?php
if (isset($_POST['update'])) {
    $conn = OpenConnection();
    $documentId = $_POST['documentId'];
    $keywordField = $_POST['keywordField'];
    $keyword = $_POST['keyword'];

//    echo "  : ", $documentId, " ", $keywordField, " ", $keyword;

    $idExists = "SELECT id FROM documents.documents";

    $res = mysqli_query($conn, $idExists);
    $idValues = Array();

    while ( $row = mysqli_fetch_assoc($res) ) {

        $idValues[] = $row['id'];

    }

    if ($documentId == '' || in_array($documentId, $idValues) != 1) {
        echo "<div>Invalid ID</div>";
    } else if ($keywordField === '' || $keyword === '') {
        echo "<div>All fields must be completed</div>";
    } else {
        $query = "UPDATE documents.documents SET $keywordField=? WHERE id=?";

        if($stmt = mysqli_prepare($conn, $query)){
            mysqli_stmt_bind_param($stmt, "si", $keyword, $documentId);
            mysqli_stmt_execute($stmt);
            echo "<div>Keyword updated</div>";
        } else {
            echo "<div>error</div>";
        }
    }

    CloseConnection($conn);
}

?>


<div>
    <form action="user.php" method="post">
        <input id="websites" type="submit" name="websites" value="View all websites">
    </form>
    <br>
</div>


<?php

if (isset($_POST['websites'])) {
    $conn = OpenConnection();

    $query = "SELECT * FROM documents.websites";

    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>All websites</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>URL</th>";
        echo "<th>nr of documents</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            $id = $row['id'];
            $count = "SELECT count(*) from documents.documents where idWebsite='$id'";
            $result_set = $conn->query($count);
            $nrDocuments = array();
            while ($nrDoc = mysqli_fetch_array($result_set, MYSQLI_NUM)) {
                $nrDocuments[] = $nrDoc[0];
            }
            echo "<tr>";
            echo "<th>" . $row['url'] . "</th>";
            echo "<th>" . $nrDocuments[0]. "</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    } else {
        echo "<div>There are no websites in the db!</div>";
    }

    CloseConnection($conn);
}
?>

<div>
    <br/>
    <form action="user.php" method="post">
        <label>Input 3 keywords</label><br/><br/>
        <input type="text" name="keywords" placeholder="keywords">
        <input id="match" type="submit" name="match" value="Search">
    </form>
    <br>
</div>

<?php
if (isset($_POST['match'])) {
    $conn = OpenConnection();
    $keywords = $_POST['keywords'];

    echo "<div>$keywords</div>";

    $arrayKeywords = explode(" ", $keywords);

    if(sizeof($arrayKeywords))

//    print_r($arrayKeywords);

    $query = "SELECT * FROM documents.documents";

    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>All documents</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>name</th>";
        echo "<th>keyword1</th>";
        echo "<th>keyword2</th>";
        echo "<th>keyword3</th>";
        echo "<th>keyword4</th>";
        echo "<th>keyword5</th>";

        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            $nrOfMatches = 0;
            $k1 = $row['keyward1'];
            $k2 = $row['keyward2'];
            $k3 = $row['keyward3'];
            $k4 = $row['keyward4'];
            $k5 = $row['keyward5'];

            for($i = 0; $i < sizeof($arrayKeywords); $i++){
                if($arrayKeywords[$i] == $k1 || $arrayKeywords[$i] == $k2 || $arrayKeywords[$i] == $k3 || $arrayKeywords[$i] == $k4 || $arrayKeywords[$i] == $k5) {
                    $nrOfMatches += 1;
                }
            }

            if ($nrOfMatches == 3) {
                echo "<tr>";
                echo "<th>" . $row['id'] . "</th>";
                echo "<th>" . $row['name'] . "</th>";
                echo "<th>" . $row['keyward1'] . "</th>";
                echo "<th>" . $row['keyward2'] . "</th>";
                echo "<th>" . $row['keyward3'] . "</th>";
                echo "<th>" . $row['keyward4'] . "</th>";
                echo "<th>" . $row['keyward5'] . "</th>";
                echo "</tr>";
            }
        }
        echo "</table>";
        echo "</br>";
    }



    CloseConnection($conn);
}

?>

