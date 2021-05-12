<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess\n");
}
else{
	echo("connection failed\n");
}
if($_POST){
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	$query="DELETE FROM user_lists WHERE List_ID = ".$listid;
	if(mysqli_query($conn, $query)){
		echo("list removed succesfully");
	}
	else{
		echo("error in removing list");
	}	
}
else{
	echo("error in request");
}
?>
