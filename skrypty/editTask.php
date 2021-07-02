<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	//[]= {itemid, itemname, itemtype, deadline, desc, recurring, notificationDate, piority, subtasks, attachments}
	if(isset($_POST['itemid'])){ $itemid = $_POST['itemid']; }
	if(isset($_POST['itemname'])){ $itemname = $_POST['itemname']; }
	if(isset($_POST['itemtype'])){ $itemtype = $_POST['itemtype']; }
	if(isset($_POST['deadline'])){ $deadline = $_POST['deadline']; }
	if(isset($_POST['desc'])){ $desc = $_POST['desc']; }
	if(isset($_POST['recurring'])){ $recurring = $_POST['recurring']; }
	if(isset($_POST['notificationDate'])){ $notificationDate = $_POST['notificationDate']; }
	if(isset($_POST['piority'])){ $piority = $_POST['piority']; }
	if(isset($_POST['subtasks'])){ $subtasks = $_POST['subtasks']; }
	if(isset($_POST['attachments'])){ $attachments = $_POST['attachments']; }
	
	
	$conn->begin_transaction();

	try 
	{
		$query="UPDATE items_list SET Item_Name = '$itemname' WHERE items_list.Item_ID = $itemid;";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in updating name of item");
			throw new \mysqli_sql_exception("exception msg");
		}

		$query="UPDATE tasks_list SET Task_Deadline = '$deadline',
										Task_Desc = '$desc', 
										Task_Recurring = '$recurring', 
										Task_Notification = '$notificationDate', 
										Task_Priority = '$piority' WHERE tasks_list.Item_ID = $itemid;";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in updating tasks details");
			throw new \mysqli_sql_exception("exception msg");
		}

		$query="DELETE FROM subtasks WHERE Item_ID='".$itemid."'";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in deleting subtasks");
			throw new \mysqli_sql_exception("exception msg");
		}

		$subtasksArray = explode(",", $subtasks);
		$subtasksArrayLength = count($subtasksArray);

		for ($i = 0; $i < $subtasksArrayLength; $i++) {
			$query="INSERT INTO subtasks (Item_ID, Subtask_Name) 
						VALUES ('$itemid', '".$subtasksArray[$i]."');";

			if(!mysqli_query($conn, $query)){
				echo("\nerror in adding subtask");
				$query="DELETE FROM subtasks WHERE Item_ID=".$itemid;
				$result = $conn->query($query);
				throw new \mysqli_sql_exception("exception msg");
			}
		} 

		$query="DELETE FROM attachments_list WHERE Item_ID='".$itemid."'";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in deleting subtasks");
			throw new \mysqli_sql_exception("exception msg");
		}

		$attArray = explode(",", $attachments);
		$attArrayLength = count($attArray);

		for ($i = 0; $i < $attArrayLength; $i++) {
			$query="INSERT INTO attachments_list (Item_ID, Attachment_Name) 
						VALUES ('$itemid', '".$attArray[$i]."');";

			if(!mysqli_query($conn, $query)){
				echo("\nerror in adding subtask");
				$query="DELETE FROM attachments_list WHERE Item_ID=".$itemid;
				$result = $conn->query($query);
				$query="DELETE FROM subtasks WHERE Item_ID=".$itemid;
				$result = $conn->query($query);
				throw new \mysqli_sql_exception("exception msg");
			}
		} 

		$conn->commit();
		echo("task updated successfully\n");
	} catch (mysqli_sql_exception $exception) {
		echo("error occured!");
		$conn->rollback();
	}
}
else{
	echo("error in request");
}
?>
