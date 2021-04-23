<?php
$servername = "localhost";
$mysql_user = "root";
$mysql_password = "";
$dbname="chronos";
$conn = mysql_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess");
}
else{
	echo("connection failed");
}
if($_SERVER['REQUEST_METHOD']=='POST'){
	$user=$_POST['username'];
	$pass=$_POST['password'];
	//$add=$_POST['address'];
	$email=$_POST['email'];
	$name=$_POST['name'];
	$surname=$_POST['surname'];
	$age=$_POST['age'];
	$query="INSERT INTO 'user'('Login', 'Password', 'Email', 'Name', 'Surname', 'Age') VALUES ('$user', '$pass', '$email', '$name', '$surname', '$age')"
	if(mysqli_query($conn, $query)){
		echo("registered succesfully");
	}
	else{
		echo("error in registration");
	}	
}
else{
	echo("error in request");
}
?>
