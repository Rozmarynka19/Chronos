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
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	if(isset($_POST['newlistname'])){ $newlistname = $_POST['newlistname']; }
	$query="UPDATE user_lists SET List_Name = '$newlistname' WHERE user_lists.List_ID = $listid;";
	if(mysqli_query($conn, $query)){
		echo("\nnew list updated succesfully");
	}
	else{
		echo("error in updating list");
	}	
}
else{
	echo("error in request");
}
?>
