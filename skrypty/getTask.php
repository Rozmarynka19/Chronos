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
	if(isset($_POST['itemid'])){ $itemid = $_POST['itemid']; }
	$query="SELECT * FROM tasks_list WHERE Item_ID='".$itemid."'";
	$result = $conn->query($query)->fetch_assoc();

	echo $result['Task_Deadline']."\n";
	echo $result['Task_Desc']."\n";
	echo $result['Task_Recurring']."\n";
	echo $result['Task_Notification']."\n";
	echo $result['Task_Priority']."\n";
}
else{
	echo("error in request");
}
?>
