<!DOCTYPE html>

<script src="https://kit.fontawesome.com/c9c2cadf89.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer>
    function getpopular() {

        const popularity_list = new Map();

        // Set Map Values



        $.getJSON("getfiles.php", {}).then(function(result) {

            console.log("get");
            console.log(result);
            for (let i in result) { //alert(result[i]["filename"]);
                if (  popularity_list.get(result[i]["filename"]  ) )
                popularity_list.set(result[i]["filename"], popularity_list.get(result[i]["filename"]) + 1);
                else{
                    popularity_list.set(result[i]["filename"],  1);
                }
            }

            
            let x = 0;
            let mostpopular = "";  //key = name  value = numbers
            popularity_list.forEach(function(value,key ) {
                //console.log("value");
                //console.log(value);
                //console.log("key");
                console.log(key);
                if(x<value )
                { 
                    console.log("in if");
                    x = value;
                    mostpopular= key;
                }


            })
            console.log("mostpopular");
            console.log(mostpopular);
            //document.getElementById("#popular").innerHTML = mostpopular;


            let label = document.querySelector("label");
            label.innerText = "most popular: " + mostpopular;

        }, function(error) {
            alert("huh?")
        });
    }
</script>






<html>

<head>
    <title>Pagination</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>

<p style='text-align: center'>Files using pagination (5 on each page)</p>
<p id=popular></p>
<label></label>

<?php

include('getPagination.php');



function get_files()
{
    session_start();

    if (isset($_SESSION['id'])) {
        $userId = $_SESSION['id'];
    }
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
}

echo '<script type="text/javascript">',
'  getpopular();',
'</script>';



?>

<div class="container">
    <ul>
        <li><a href="?pageno=1"><button type="button" class="btn btn-secondary">First</button></a></li>

        <li>
            <a href="<?php if ($pageno > 1) {
                            echo "?pageno=" . ($pageno - 1);
                        } ?>"><button type="button" class="btn btn-secondary">Prev</button></a>
        </li>
        <li class="<?php if ($pageno >= $totalPages) {
                        echo 'disabled';
                    } ?>">
            <a href="<?php if ($pageno >= $totalPages) {
                            echo '#';
                        } else {
                            echo "?pageno=" . ($pageno + 1);
                        } ?>"><button type="button" class="btn btn-secondary">Next</button></a>
        </li>
        <li><a href="?pageno=<?php echo $totalPages; ?>"><button type="button" class="btn btn-secondary">Last</button></a></li>
    </ul>
</div>
</body>

</html>