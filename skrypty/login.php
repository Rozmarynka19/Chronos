<?php
include 'databaseInfo.php';
$mysqli = new mysqli($servername, $mysql_user, $mysql_password, $dbname);

if($mysqli){
	echo("connection sucess");
}
else{
	echo("connection failed");
}

if(isset($_POST['login']) && !empty($_POST['login'])){ $login = $_POST['login']; }
if(isset($_POST['password']) && !empty($_POST['password'])){ $password = $_POST['password']; }

 if(!isset($_POST['login'])) {
   throw new Exception('nie ma usera');
}

$query = "SELECT User_Name FROM registered_users where User_Name='$login'";

$result = $mysqli->query($query);
if(mysqli_num_rows($result)<=0){
	echo("no such user");
}
else{
	try {
		
		$query = "SELECT * FROM `registered_users` WHERE `User_Name`='" . $login . "'";
		$result2 = $mysqli->query($query);
		
		$user = $result2->fetch_assoc();
		
		if(password_verify($password, $user['User_Password'])){
			echo("\nlogin sucesfull");
			$userData = "\n".$user['User_ID']."\n".$user['User_Name']."\n".$user['User_Email']."\n".$user['User_Phone']."\n".$user['Is_Verified'];
			echo($userData);
		}
		else{
			echo("\nlogin failed");
		}
	} catch(Exception $e) {
		echo $e->getMessage();
		echo $e->getError();
		echo 'service unavailable';
	}
	
	
}
?>