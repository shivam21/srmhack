<?php
require "dbconnect.php";
$size=$_POST['size'];
$crops=$_POST['crops'];
$coverpic=$_POST['coverpic'];
$userid=$_POST['userid'];
$conn->query("UPDATE user SET size=$size,crops='$crops',coverpic='$coverpic' WHERE userid=$userid");
?>