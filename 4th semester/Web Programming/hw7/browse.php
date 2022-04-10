<!DOCTYPE html>
<html>
<head>
    <title>All Reports</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<!--<body>-->
<!--<p style='text-align: center'>All reports</p>-->
<?php //include('getAllReports.php'); ?>
<!--<br>-->

<p style='text-align: center'>Reports using pagination (4 on each page)</p>

<?php include('getPagination.php'); ?>
<div>
    <ul>
        <li><a href="?pageno=1"><button type="button">First</button></a></li>

        <li>
            <a href="<?php if($pageno > 1){ echo "?pageno=".($pageno - 1); } ?>"><button type="button">Prev</button></a>
        </li>
        <li class="<?php if($pageno >= $totalPages){ echo 'disabled'; } ?>">
            <a href="<?php if($pageno >= $totalPages){ echo '#'; } else { echo "?pageno=".($pageno + 1); } ?>"><button type="button">Next</button></a>
        </li>
        <li><a href="?pageno=<?php echo $totalPages; ?>"><button type="button">Last</button></a></li>
    </ul>
</div>
</body>
</html>
