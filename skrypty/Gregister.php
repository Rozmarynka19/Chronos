<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess");
}
else{
	echo("connection failed");
}
if($_POST){
	if(isset($_POST['login'])){ $login = $_POST['login']; }
	if(isset($_POST['email'])){ $email=$_POST['email']; }
	//$crypted = password_hash($pass, PASSWORD_DEFAULT);
	$mysqli = new mysqli($servername, $mysql_user, $mysql_password, $dbname);
	$query = "SELECT User_Name FROM registered_users where External_User_Name='$login'";
	//$result = mysqli_query($conn, $query);
	$result = $mysqli->query($query);
	if(mysqli_num_rows($result)<=0){
		$query="INSERT INTO registered_users (External_User_Name, External_User_Email, Is_Verified) VALUES ('$login', '$email', '1')";
	if(mysqli_query($conn, $query)){
		echo("\nregistered succesfully");
		$query = "SELECT * FROM registered_users WHERE `External_User_Name`='" . $login . "'";
		$result = $conn->query($query);
		$user = $result->fetch_assoc();
		$userData = "\n".$user['User_ID']."\n".$login."\n".$email;
		echo($userData);
	}
	else{
		echo("error in registration");
	}	
	}
	else{
		$query = "SELECT * FROM registered_users WHERE `External_User_Name`='" . $login . "'";
		$result = $conn->query($query);
		$user = $result->fetch_assoc();
		$userData = "\n".$user['User_ID']."\n".$login."\n".$email;
		echo($userData);
	}
	
}
else{
	echo("error in request");
}
?>
