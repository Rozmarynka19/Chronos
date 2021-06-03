<?php
include 'databaseInfo.php';
$mysqli = new mysqli($servername, $mysql_user, $mysql_password, $dbname);

if($_POST){
	if(isset($_POST['login']) && !empty($_POST['login'])){ $login = $_POST['login']; }
	$query = "SELECT User_Name FROM registered_users where User_Name='$login'";
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