<?php
/*
	1. Post new item (List_Id, Item_Name, Item_Type) into database (items_list table).
	2. Get assigned Item_ID (search by List_Id, List_Name, Item_Type) - necessary to bind task details in another table.
		This query can return many records (the same item name in specific list), so get the last, newest one.
	3. Post new bill (Item_ID, Bill_Recipient, Bill_RecipientsBankAccount , Bill_TransferTitle , Bill_Amount , Bill_Desc, Bill_Deadline) (bill details particularly) into database (bills_list table).
*/
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	//[] = {listid, itemname, itemtype, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	if(isset($_POST['itemname'])){ $itemname = $_POST['itemname']; }
	if(isset($_POST['itemtype'])){ $itemtype = $_POST['itemtype']; }
	if(isset($_POST['billRecipient'])){ $billRecipient = $_POST['billRecipient']; }
	if(isset($_POST['billRecipientBankAccount'])){ $billRecipientBankAccount = $_POST['billRecipientBankAccount']; }
	if(isset($_POST['billTransferTitle'])){ $billTransferTitle = $_POST['billTransferTitle']; }
	if(isset($_POST['billAmount'])){ $billAmount = $_POST['billAmount']; }
	if(isset($_POST['billDesc'])){ $billDesc = $_POST['billDesc']; }
	if(isset($_POST['billDeadline'])){ $billDeadline = $_POST['billDeadline']; }
	
	
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
		
		$query="INSERT INTO bills_list (Item_ID, Bill_Recipient, Bill_RecipientsBankAccount , Bill_TransferTitle , Bill_Amount , Bill_Desc, Bill_Deadline) 
					VALUES ('$currentItem_ID', '$billRecipient', '$billRecipientBankAccount', '$billTransferTitle', '$billAmount', '$billDesc', '$billDeadline')";
		if(!mysqli_query($conn, $query)){
			echo("\nerror in adding new task");
			$query="DELETE FROM items_list WHERE Item_ID=".$currentItem_ID;
			$result = $conn->query($query);
			throw new \mysqli_sql_exception("exception msg");
		}
		//$conn->commit();
		echo("bill added successfully\n");
	} catch (mysqli_sql_exception $exception) {
		echo("error occured!");
		//$conn->rollback();
	}
}
else{
	echo("error in request");
}
?>
