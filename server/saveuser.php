<?php
require "dbconnect.php";
$username=$_POST['username'];
$userphone=$_POST['userphone'];
$userpass=md5($_POST['userpass']);
$userpin=$_POST['userpin'];
$userstate=$_POST['userstate'];
$userdistrict=$_POST['userdistrict'];
$usertehsil=$_POST['usertehsil'];
$uservillage=$_POST['uservillage'];
$ismale=$_POST['ismale'];
$isfarmer=$_POST['isfarmer'];
$data=$conn->query("SELECT userid FROM user WHERE phone='$userphone' AND pass='$userpass'");
if ($data!=null&&$row=$data->fetch_assoc()) {
	echo "0";
}else{
	$conn->query("INSERT INTO user(name,phone,pass,pin,state,district,tehsil,village,ismale,isfarmer)VALUES('$username','$userphone','$userpass','$userpin','$userstate','$userdistrict','$usertehsil','$uservillage',$ismale,$isfarmer)");
	echo $conn->insert_id;
}
?>