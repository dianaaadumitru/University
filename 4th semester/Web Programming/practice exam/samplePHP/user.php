<?php
include ('connection.php');
session_start();
if (isset($_SESSION['username'])){
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

<div>
    <form action="user.php" method="post">
        <input id="view" type="submit" name="view" value="View all projects">
    </form>
    <br>
</div>

<?php
if (isset($_POST['view'])) {
    $conn = OpenConnection();
    $query = "SELECT * FROM project";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<p style='text-align: center'>All projects</p>";
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>Project manager id</th>";
        echo "<th>name</th>";
        echo "<th>description</th>";
        echo "<th>members</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['projectManagerId']."</th>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['description']."</th>";
            echo "<th>".$row['members']."</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "There are no projects available!";
    }

    CloseConnection($conn);
}
?>

<div>
    <form action="user.php" method="post">
        <input id="userProjects" type="submit" name="userProjects" value="Your projects">
    </form>
    <br>
</div>

<?php
if (isset($_POST['userProjects'])) {
    $conn = OpenConnection();
    $query = "SELECT * FROM project ";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>Project name</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            $members = $row['members'];
            if (strpos($members, $username) !== false) {
                echo "<tr>";
                echo "<th>" . $row['name'] . "</th>";
                echo "</tr>";
            }
        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "There are no projects available!";
    }

    CloseConnection($conn);
}
?>

<div>
    <button onclick="window.location.href = 'addToProject.html';" id="addToProject">add a developer to other projects</button>
    <br><br>
    <form action="user.php" method="post">
        <input id="viewDevelopers" type="submit" name="viewDevelopers" value="View all developers">
    </form>
    <br>
</div>

<?php
if (isset($_POST['viewDevelopers'])) {
    $conn = OpenConnection();
    $query = "SELECT * FROM softwaredeveloper";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result)) {
        echo "<table style='margin-left: auto;margin-right: auto;'>";
        echo "<tr>";
        echo "<th>ID</th>";
        echo "<th>name</th>";
        echo "<th>age</th>";
        echo "<th>skills</th>";
        echo "</tr>";

        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<th>".$row['id']."</th>";
            echo "<th>".$row['name']."</th>";
            echo "<th>".$row['age']."</th>";
            echo "<th>".$row['skills']."</th>";
            echo "</tr>";
        }
        echo "</table>";
        echo "</br>";
    }
    else {
        echo "There are no developers in db!";
    }

    CloseConnection($conn);
}
?>

<div>
    <label>Filter developers by a skill</label><br/><br/>
    <input id="skill" placeholder="skill" type="text"/><br/><br/>
    <input type="submit" id="filter" value="Filter"><br/><br/>
    <div id="getFiltered"></div>
</div>

<script src="https://kit.fontawesome.com/c9c2cadf89.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer>
    function buildTable(data) {
        let code_string="<table style='margin-left: auto;margin-right: auto;'>";
        for (let row in data) {
            console.log("row: ", data[row]);
            code_string += "<tr>";
            code_string += "<td>" + data[row] + "</td>"
            code_string += "</tr>"
        }
        code_string+="</table>";
        $("#getFiltered").html(code_string);
    }

    $("#filter").click(function() {
        console.log("got in")
        let developersWithSkill = []
        let givenSkill = document.getElementById("skill").value
        // console.log(givenSkill)

        $.getJSON("getDevelopers.php", {}).then(function(data) {
            // console.log(data)
            for(let i in data) {
                if (data[i]["skills"].includes(givenSkill)) {
                    developersWithSkill.push(data[i]["name"])
                }
            }
            document.getElementById("getFiltered").innerHTML = developersWithSkill
            }
        );
    })


</script>



<br/><br/>
<div><button type="button" onclick="location.href='./login.php'">Go back</button></div>