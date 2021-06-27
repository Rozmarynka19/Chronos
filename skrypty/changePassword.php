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
if(isset($_POST['password_new']) && !empty($_POST['password_new'])){ $password_new = $_POST['password_new']; }


if(!isset($_POST['login'])) {
    throw new Exception('nie ma usera');
}

if(!isset($_POST['password'])){
    throw new Exception('haslo nie ustawione');
}

if(!isset($_POST['password_new'])){
    throw new Exception('nowe haslo nie ustawione');
}

$query = "SELECT User_Name FROM registered_users where User_Name='$login'";

$result = $mysqli->query($query);

if(mysqli_num_rows($result)>=1){
	try {
		
		$query = "SELECT * FROM `registered_users` WHERE `User_Name`='" . $login . "'";
		$result2 = $mysqli->query($query);
		
		$user = $result2->fetch_assoc();
		
		if(password_verify($password, $user['User_Password'])){
			echo("\nverified");
            $crypted = password_hash($password_new, PASSWORD_DEFAULT);
            $query = "UPDATE registered_users SET User_Password = '$crypted' WHERE `User_Name`='" . $login . "'";
            $result = $mysqli->query($query);
		}
	} catch(Exception $e) {
		echo $e->getMessage();
		echo $e->getError();
		echo 'service unavailable';
	}
}
?>