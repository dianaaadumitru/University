<?php
include ('connection.php');
session_start();
if (isset($_SESSION['id'])){
    $username = $_SESSION['username'];
}
else {
    header('Location: login.php');
    die();
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome user</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div>
    <h3>Welcome <?php echo $username; ?> </h3>
</div>

<?php
$conn = OpenConnection();

$sql = "SELECT DISTINCT name FROM channels.persons";
$result_set = $conn->query($sql);
$rows = array();
while ($row = mysqli_fetch_array($result_set, MYSQLI_NUM)) {
    $rows[] = $row[0];
}
?>

<div>
    <form action="user.php" method="post">
        <label>Display all channels owned by a person</label> <br/><br/>
        <select name="owner"> <br/><br/>
            <option selected disabled>choose owner</option>
            <?php
            foreach ($rows as $row) {
                echo "<option>$row</option>";
            }
            ?>
        </select>
        <input id="view" type="submit" name="view" value="View channels">
    </form>
    <br>
</div>

<?php
if (isset($_POST['view'])) {
    $conn = OpenConnection();
    $owner = $_POST['owner'];

    $sql = "SELECT id FROM channels.persons where name='$owner'";
    $result_set = $conn->query($sql);
    $rows = array();
    while ($row = mysqli_fetch_array($result_set, MYSQLI_NUM)) {
        $rows[] = $row[0];
    }


    $query = "SELECT * FROM channels.channels WHERE ownerid='$rows[0]'";

    $result = mysqli_query($conn, $query);


    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>$owner's channels</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>name</th>";
        echo "<th>description</th>";
        echo "<th>subscribers</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['description']."</th>";
            echo "<th>".$row['subscribers']."</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "<div>This user doesn't have any channels!</div>";
    }
}

?>

<?php
$conn = OpenConnection();

$sql = "SELECT DISTINCT name FROM channels.channels";
$result_set = $conn->query($sql);
$nameChannels = array();
while ($row = mysqli_fetch_array($result_set, MYSQLI_NUM)) {
    $nameChannels[] = $row[0];
}
?>

<div>
    <form action="user.php" method="post">
        <input id="subscriptions" type="submit" name="subscriptions" value="See your subscriptions"><br/>
    </form>
</div>

<?php
if (isset($_POST['subscriptions'])) {
    $conn = OpenConnection();
    $query = "SELECT name, description FROM channels.channels WHERE subscribers LIKE '%$username%'";

    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>Your subscriptions</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>Name</th>";
        echo "<th>Description</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['description']."</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "<div><br/>You don't have any subscriptions!</div>";
    }

    CloseConnection($conn);
}
?>

<div>
    <br/>
    <form action="user.php" method="post">
        <label>Select a channel</label> <br/><br/>
        <select name="channel"> <br/><br/>
            <option selected disabled>choose channel</option>
            <?php
            foreach ($nameChannels as $row) {
                echo "<option>$row</option>";
            }
            ?>
        </select>
        <input id="subscribe" type="submit" name="subscribe" value="Subscribe to a channel"><br/>
    </form>
</div>

<?php
if (isset($_POST['subscribe'])) {
    $conn = OpenConnection();
    $channel = $_POST['channel'];

    $sql = "SELECT subscribers FROM channels.channels WHERE name='$channel'";

    $result_set = $conn->query($sql);
    $subscribers = array();
    while ($row = mysqli_fetch_array($result_set, MYSQLI_NUM)) {
        $subscribers[] = $row[0];
    }

    $arraySubscribers = explode(",", $subscribers[0]);
//    echo sizeof($arraySubscribers);

    for($i = 0; $i < sizeof($arraySubscribers); $i++) {
//        echo strpos($arraySubscribers[$i], $username);
        if (strpos($arraySubscribers[$i], $username) != "") {
            $getDate = explode("-", $arraySubscribers[$i]);
            echo "<br/><div>You are subscribed to $channel since $getDate[1]</div>";
            return;
        }
    }

    $currentDate = date("d.m.Y");

    $newSubscriber = ",";
    $newSubscriber.= $username;
    $newSubscriber.= "-";
    $newSubscriber.= "$currentDate";

    $subscribers[0].=$newSubscriber;
//    echo "channel subscribers: ", $subscribers[0];

    $sql = "UPDATE channels.channels SET subscribers='$subscribers[0]' WHERE name='$channel'";
    $result_set = $conn->query($sql);
    echo "<br/><div>You successfully subscribed to $channel</div>";
}
?>


<br/><br/>
<div><button type="button" onclick="location.href='./login.php'">Go back</button></div>
