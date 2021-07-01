<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	if(isset($_POST['ids'])){ $list_ID = $_POST['ids']; }
	$query="SELECT items_list.Item_Name AS Name, tasks_list.Task_Deadline AS Deadline FROM tasks_list INNER JOIN items_list ON tasks_list.Item_ID=items_list.Item_ID WHERE items_list.List_ID = '".$list_ID."'";
	$result = $conn->query($query);
	foreach($result as $item) {
		echo $item['Name'].",";
		echo $item['Deadline']."\n";
	}
	$query="SELECT items_list.Item_Name AS Name, bills_list.Bill_Deadline AS Deadline FROM items_list INNER JOIN bills_list ON items_list.Item_ID=bills_list.Item_ID WHERE items_list.List_ID = '".$list_ID."'";
	$result = $conn->query($query);
	foreach($result as $item) {
		echo $item['Name'].",";
		echo $item['Deadline']."\n";
	}
	
}
else{
	echo("error in request");
}
?>