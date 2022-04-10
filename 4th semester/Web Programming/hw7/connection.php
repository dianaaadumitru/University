<?php

function OpenConnection(): mysqli
{
    $host = "localhost";
    $username = "root";
    $password = "diana";
    $db = "phplab";

    return mysqli_connect($host, $username, $password, $db);
}

function CloseConnection(mysqli $con)
{
    $con->close();
}


