<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	if(isset($_POST['ids'])){ $ids = $_POST['ids']; }
	//tasks details
	$query="SELECT Item_ID, Task_Deadline, Task_Priority FROM tasks_list WHERE Item_ID IN (".$ids.");";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['Item_ID']."\n";
		echo $item['Task_Deadline']."\n";
		echo $item['Task_Priority']."\n";
	}	
	
	//bills details
	$query="SELECT Item_ID, Bill_Deadline FROM bills_list WHERE Item_ID IN (".$ids.");";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['Item_ID']."\n";
		echo $item['Bill_Deadline']."\n";
	}	
}
else{
	echo("error in request");
}
?>
