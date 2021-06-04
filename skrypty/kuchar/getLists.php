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
	if(isset($_POST['userid'])){ $userid = $_POST['userid']; }
	$query="SELECT * FROM user_lists WHERE User_ID='".$userid."'";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['List_ID']."\n";
		echo $item['List_Name']."\n";
	}	
}
else{
	echo("error in request");
}
?>
