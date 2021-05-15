<?php
/*
	1. Post new item (List_Id, Item_Name) into database (items_list table).
	2. Get assigned Item_ID (search by List_Id and List_Name) - necessary to bind task details in another table.
		This query can return many records (the same item name in specific list), so get the last, newest one.
	3. Post new task (Item_ID, Task_Deadline, Task_Desc, Task_Recurring, Task_Notification, Task_Priority) (tasks details particularly) into database (tasks_list table).
*/
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess");
}
else{
	echo("connection failed");
}
if($_POST){
	//[]= {listid, itemname, deadline, desc, recurring, notificationDate, piority}
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	if(isset($_POST['itemname'])){ $itemname = $_POST['itemname']; }
	if(isset($_POST['deadline'])){ $deadline = $_POST['deadline']; }
	if(isset($_POST['desc'])){ $desc = $_POST['desc']; }
	if(isset($_POST['recurring'])){ $recurring = $_POST['recurring']; }
	if(isset($_POST['notificationDate'])){ $notificationDate = $_POST['notificationDate']; }
	if(isset($_POST['piority'])){ $piority = $_POST['piority']; }
	
	
	$conn->begin_transaction();

	try 
	{
		$query="INSERT INTO items_list (List_Id, Item_Name) VALUES ('$listid', '$itemname')";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in adding new item");
			throw new \mysqli_sql_exception("exception msg");
		}
		
		$query="SELECT * FROM items_list WHERE List_Id='".$listid."' AND Item_Name='".$itemname."' ORDER BY Item_ID DESC LIMIT 1";
		$result = $conn->query($query);
		
		if($result->num_rows == 0){
			//echo("\nerror: something went wrong");
			throw new \mysqli_sql_exception("exception msg");
		}
		$currentItem_ID = ($result->fetch_assoc())['Item_ID'];
		
		$query="INSERT INTO tasks_list (Item_ID, Task_Deadline, Task_Desc, Task_Recurring, Task_Notification, Task_Priority) 
					VALUES ('$currentItem_ID', '$deadline', '$desc', '$recurring', '$notificationDate', '$piority')";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in adding new task");
			throw new \mysqli_sql_exception("exception msg");
		}
		echo("\ntask added successfully");
		$conn->commit();
	} catch (mysqli_sql_exception $exception) {
		$conn->rollback();
		echo("\nerror occured!");
	}
}
else{
	echo("error in request");
}
?>
