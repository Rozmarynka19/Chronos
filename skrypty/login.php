<?php
include 'databaseInfo.php';
$mysqli = new mysqli($servername, $mysql_user, $mysql_password, $dbname);
//$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
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

$query = "SELECT 'Login' FROM user where Login='$login'";
//$result = mysqli_query($conn, $query);
$result = $mysqli->query($query);
if(mysqli_num_rows($result)<=0){
	echo("no such user");
}
else{
	try {
		
		$query = "SELECT * FROM `user` WHERE `Login`='" . $login . "'";
		$result2 = $mysqli->query($query);
		//if($result2->num_rows > 0) {
			//	while($results = $result2->fetch_assoc()) {
				//	
			//	}
		//}
		$user = $result2->fetch_assoc();
		
		if(isset($user['Password']))
		{
			 if(password_verify($password, $user['Password'])){
			echo("\nlogin sucesfull");
			$userData = "\n".$user['Login']."\n".$user['Email']."\n".$user['Phone'];
			echo($userData);
			}
			else{
				echo("\nlogin failed");
			}
		}
		else
		{
		   echo("setting value failed\n");
		}
	} catch(Exception $e) {
		echo $e->getMessage();
		echo $e->getError();
		echo 'service unavailable';
	}
	
	
}


?>