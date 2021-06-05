<?php
include 'databaseInfo.php';
$mysqli = new mysqli($servername, $mysql_user, $mysql_password, $dbname);

if($_POST){
	if(isset($_POST['userid']) && !empty($_POST['userid'])){ $userid = $_POST['userid']; }
	$query = "SELECT User_ID FROM registered_users where User_ID='$userid'";
	$result = $mysqli->query($query);
	if(mysqli_num_rows($result)<=0){
		echo("-1");
	}
	else {
		echo("0");
	}
}
else{
	echo("error in request");
}
?>