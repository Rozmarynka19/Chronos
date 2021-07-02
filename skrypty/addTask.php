<?php
/*
	1. Post new item (List_Id, Item_Name, Item_Type) into database (items_list table).
	2. Get assigned Item_ID (search by List_Id, List_Name, Item_Type) - necessary to bind task details in another table.
		This query can return many records (the same item name in specific list), so get the last, newest one.
	3. Post new task (Item_ID, Task_Deadline, Task_Desc, Task_Recurring, Task_Notification, Task_Priority) (tasks details particularly) into database (tasks_list table).
*/
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	//[]= {listid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority, subtasks, attachments}
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	if(isset($_POST['itemname'])){ $itemname = $_POST['itemname']; }
	if(isset($_POST['itemtype'])){ $itemtype = $_POST['itemtype']; }
	if(isset($_POST['deadline'])){ $deadline = $_POST['deadline']; }
	if(isset($_POST['desc'])){ $desc = $_POST['desc']; }
	if(isset($_POST['recurring'])){ $recurring = $_POST['recurring']; }
	if(isset($_POST['notificationDate'])){ $notificationDate = $_POST['notificationDate']; }
	if(isset($_POST['piority'])){ $piority = $_POST['piority']; }
	if(isset($_POST['subtasks'])){ $subtasks = $_POST['subtasks']; }
	if(isset($_POST['attachments'])){ $attachments = $_POST['attachments']; }
	
	
	//$conn->begin_transaction();

	try 
	{
		$query="INSERT INTO items_list (List_Id, Item_Name, Item_Type) VALUES ('$listid', '$itemname', '$itemtype')";
		if(!mysqli_query($conn, $query)){
			echo("\nerror in adding new item");
			throw new \mysqli_sql_exception("exception msg");
		}
		
		$query="SELECT * FROM items_list WHERE List_Id='".$listid."' AND Item_Name='".$itemname."'AND Item_Type='".$itemtype."' ORDER BY Item_ID DESC LIMIT 1";
		$result = $conn->query($query);
		
		if($result->num_rows == 0){
			echo("\nerror: something went wrong");
			throw new \mysqli_sql_exception("exception msg");
		}
		$currentItem_ID = ($result->fetch_assoc())['Item_ID'];
		
		$query="INSERT INTO tasks_list (Item_ID, Task_Deadline, Task_Desc, Task_Recurring, Task_Notification, Task_Priority) 
					VALUES ('$currentItem_ID', '$deadline', '$desc', '$recurring', '$notificationDate', '$piority')";
		if(!mysqli_query($conn, $query)){
			echo("\nerror in adding new task");
			$query="DELETE FROM items_list WHERE Item_ID=".$currentItem_ID;
			$result = $conn->query($query);
			throw new \mysqli_sql_exception("exception msg");
		}

		$subtasksArray = explode(",", $subtasks);
		$subtasksArrayLength = count($subtasksArray);

		for ($i = 0; $i < $subtasksArrayLength; $i++) {
			$query="INSERT INTO subtasks (Item_ID, Subtask_Name) 
						VALUES ('$currentItem_ID', '".$subtasksArray[$i]."');";

			if(!mysqli_query($conn, $query)){
				echo("\nerror in adding new task");
				$query="DELETE FROM subtasks WHERE Item_ID=".$currentItem_ID;
				$result = $conn->query($query);
				$query="DELETE FROM items_list WHERE Item_ID=".$currentItem_ID;
				$result = $conn->query($query);
				throw new \mysqli_sql_exception("exception msg");
			}
		} 

		$attArray = explode(",", $attachments);
		$attArrayLength = count($attArray);

		for ($i = 0; $i < $attArrayLength; $i++) {
			$query="INSERT INTO attachments_list (Item_ID, Attachment_Name) 
						VALUES ('$currentItem_ID', '".$attArray[$i]."');";

			if(!mysqli_query($conn, $query)){
				echo("\nerror in adding new task");
				$query="DELETE FROM attachments_list WHERE Item_ID=".$currentItem_ID;
				$result = $conn->query($query);
				$query="DELETE FROM subtasks WHERE Item_ID=".$currentItem_ID;
				$result = $conn->query($query);
				$query="DELETE FROM items_list WHERE Item_ID=".$currentItem_ID;
				$result = $conn->query($query);
				throw new \mysqli_sql_exception("exception msg");
			}
		} 

		
		//$conn->commit();
		echo("task added successfully\n");
	} catch (mysqli_sql_exception $exception) {
		//$conn->rollback();
		echo("error occured!");
	}
}
else{
	echo("error in request");
}
?>
