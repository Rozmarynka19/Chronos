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
	$query="INSERT INTO registered_users (User_Name, User_Password, User_Email, User_Phone, Is_Verified) VALUES ('$login', '$crypted', '$email',  '$phone', '0')";
	if(mysqli_query($conn, $query)){
		echo("\nregistered succesfully");
		$query = "SELECT * FROM registered_users WHERE `User_Name`='" . $login . "'";
		$result = $conn->query($query);
		$user = $result->fetch_assoc();
		$userData = "\n".$user['User_ID']."\n".$login."\n".$email."\n".$phone;

		$userID = $user['User_ID'];
		$sql_new_lists="INSERT INTO user_lists(User_ID, List_Name) VALUES ($userID, 'Dom'),($userID, 'Praca'),($userID, 'Rachunki')";
		$result_new_lists = $conn->query($sql_new_lists);
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
