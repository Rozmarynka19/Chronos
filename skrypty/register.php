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
	if(isset($_POST['password'])){ $pass = $_POST['password']; }
	if(isset($_POST['email'])){ $email=$_POST['email']; }
	if(isset($_POST['phone'])){ $phone=$_POST['phone']; }
	$crypted = password_hash($pass, PASSWORD_DEFAULT);
	$query="INSERT INTO registered_users (User_Name, User_Password, User_Email, User_Phone) VALUES ('$login', '$crypted', '$email',  '$phone')";
	if(mysqli_query($conn, $query)){
		echo("\nregistered succesfully");
		$query = "SELECT * FROM registered_users WHERE `User_Name`='" . $login . "'";
		$result = $conn->query($query);
		$user = $result->fetch_assoc();
		$userData = "\n".$user['User_ID']."\n".$login."\n".$email."\n".$phone;
		echo($userData);
	}
	else{
		echo("error in registration");
	}	
}
else{
	echo("error in request");
}
?>