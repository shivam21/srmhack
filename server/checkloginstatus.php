<?php
require "dbconnect.php";
$phone=$_POST['phone'];
$pass=$_POST['pass'];
$userpass=md5($pass);
$data=$conn->query("SELECT userid FROM user WHERE phone='$phone' AND $pass='$userpass'");
if ($data!=null&&$data->num_rows>0) {
	$row=$data->fetch_assoc();
	echo $row['userid'];
}else{
echo "0";
}
?>