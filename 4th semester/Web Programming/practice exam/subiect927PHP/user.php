<!DOCTYPE html>
<html>
<head>
    <title>Welcome user</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>

<div>
    <form action="user.php" method="post">
        <label>Add a keyword record</label><br/><br/>
        <input type="text" name="keyAdd" placeholder="key">
        <input type="text" name="valueAdd" placeholder="value">
        <input id="addKeyword" type="submit" name="addKeyword" value="Add">
    </form>
    <br>
</div>


<?php
include ('connection.php');
session_start();

if (isset($_POST['addKeyword'])) {
    $conn = OpenConnection();
    $key = $_POST['keyAdd'];
    $value = $_POST['valueAdd'];

    if($key == '' || $value == '') {
        echo "<div>All fields must be completed</div>";
    } else {
        $query = "INSERT INTO keyword(keyword, value) VALUES (?,?)";
        if($stmt = mysqli_prepare($conn, $query)){
            mysqli_stmt_bind_param($stmt, "ss", $key, $value);
            mysqli_stmt_execute($stmt);
            echo "<div>Keyword added</div>";
        } else {
            echo "<div>error</div>";
        }
    }
}
?>

<div>
    <br/>
    <p>Search for documents based on title: </p>
    <form action="user.php" method="post">
        <input type="text" name="titleSearch" placeholder="title">
        <input id="search" type="submit" name="search" value="search">
    </form>
</div>

<?php
if (isset($_POST['search'])) {
    $conn = OpenConnection();
    $title = $_POST['titleSearch'];

    if ($title == '') {
        echo "<div>All fields must be completed</div>";
    } else {
        $partOf = "'%";
        $partOf .= $title;
        $partOf .= "%'";
        $query = "SELECT * FROM document WHERE title LIKE '%$title%'";

        $result = mysqli_query($conn, $query);


        if (mysqli_num_rows($result)) {
            echo "<table style='margin-left: auto;margin-right: auto;'>";
            echo "<tr>";
            echo "<th>ID</th>";
            echo "<th>title</th>";
            echo "<th>listOfTemplates</th>";
            echo "</tr>";

            $index = 0;
            while ($row = mysqli_fetch_array($result)) {
                if ($index % 2 == 0) {
                    echo "<tr style='color: red'>";
                    echo "<th>" . $row['id'] . "</th>";
                    echo "<th>" . $row['title'] . "</th>";
                    echo "<th>" . $row['listOfTemplates'] . "</th>";
                    echo "</tr>";

                } else {
                    echo "<tr>";
                    echo "<th>" . $row['id'] . "</th>";
                    echo "<th>" . $row['title'] . "</th>";
                    echo "<th>" . $row['listOfTemplates'] . "</th>";
                    echo "</tr>";
                }
                $index += 1;
            }
            echo "</table>";
            echo "</br>";
        }
        else {
            echo "<div>no documents!</div>";
        }
    }
}
?>

