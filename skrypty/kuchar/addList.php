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
	if(isset($_POST['userid'])){ $userid = $_POST['userid']; }
	if(isset($_POST['listname'])){ $listname = $_POST['listname']; }
	$query="INSERT INTO user_lists (User_ID, List_Name) VALUES ('$userid', '$listname')";
	if(mysqli_query($conn, $query)){
		echo("\nnew list added succesfully");
	}
	else{
		echo("error in adding new list");
	}	
}
else{
	echo("error in request");
}
?>
