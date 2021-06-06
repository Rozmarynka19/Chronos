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
		if(isset($_POST['is_verified'])){ $is_verified = $_POST['is_verified']; }
		if(isset($_POST['code'])){ $code = $_POST['code']; }
		if(isset($_POST['login'])){ $login = $_POST['login']; }
		
		$query = "SELECT User_Name FROM registered_users where User_Name='$login'";

		
		
		$query="UPDATE registered_users SET Is_Verified = '$is_verified', Verification_Code = ' ' WHERE User_Name = '$login'";
		if(mysqli_query($conn, $query)){
			echo("\nver succ");
		}
		else{
			echo("\nverification failed");
		}
			
		
	
	}
?>
