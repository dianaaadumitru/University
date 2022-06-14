<?php

function OpenConnection(): mysqli
{
    $host = "localhost";
    $username = "root";
    $password = "diana";
    $db = "documents";

    return mysqli_connect($host, $username, $password, $db);
}

function CloseConnection(mysqli $con)
{
    $con->close();
}


