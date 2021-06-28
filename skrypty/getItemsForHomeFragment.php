<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	if(isset($_POST['listIDs'])){ $listIDs = $_POST['listIDs']; }
	$query="SELECT Item_ID FROM items_list WHERE List_ID IN(".$listIDs.");";
	$result = $conn->query($query);
	$itemIDs = "";
	$noOfItems = strval($result->num_rows);
	$counter = 0;
	
	// echo $noOfItems."\n";
	foreach($result as $item) {
		// echo $item['Item_ID']."\n";
		$itemIDs = $itemIDs . $item['Item_ID'];
		if($counter < $noOfItems - 1)
			$itemIDs = $itemIDs . ",";
		$counter = $counter + 1;
	}

	// echo $itemIDs."\n";

	//tasks details
	$query="SELECT tasks_list.Item_ID, items_list.Item_Name, tasks_list.Task_Deadline, tasks_list.Task_Priority FROM tasks_list INNER JOIN items_list ON items_list.Item_ID=tasks_list.Item_ID WHERE tasks_list.Item_ID IN (".$itemIDs.");";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['Item_ID']."\n";
		echo $item['Item_Name']."\n";
		echo $item['Task_Deadline']."\n";
		echo $item['Task_Priority']."\n";
	}	
	
	//bills details
	$query="SELECT bills_list.Item_ID, items_list.Item_Name, bills_list.Bill_Deadline FROM bills_list INNER JOIN items_list ON items_list.Item_ID=bills_list.Item_ID WHERE bills_list.Item_ID IN (".$itemIDs.");";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['Item_ID']."\n";
		echo $item['Item_Name']."\n";
		echo $item['Bill_Deadline']."\n";
	}	
}
else{
	echo("error in request");
}
?>
