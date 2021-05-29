<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	//[] = {itemid, itemname, billRecipient, billRecipientBankAccount,
	// billTransferTitle, billAmount, billDesc, billDeadline}
	if(isset($_POST['itemid'])){ $itemid = $_POST['itemid']; }
	if(isset($_POST['itemname'])){ $itemname = $_POST['itemname']; }
	if(isset($_POST['billRecipient'])){ $billRecipient = $_POST['billRecipient']; }
	if(isset($_POST['billRecipientBankAccount'])){ $billRecipientBankAccount = $_POST['billRecipientBankAccount']; }
	if(isset($_POST['billTransferTitle'])){ $billTransferTitle = $_POST['billTransferTitle']; }
	if(isset($_POST['billAmount'])){ $billAmount = $_POST['billAmount']; }
	if(isset($_POST['billDesc'])){ $billDesc = $_POST['billDesc']; }
	if(isset($_POST['billDeadline'])){ $billDeadline = $_POST['billDeadline']; }
	
	
	$conn->begin_transaction();

	try 
	{
		$query="UPDATE items_list SET Item_Name = '$itemname' WHERE items_list.Item_ID = $itemid;";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in updating name of item");
			throw new \mysqli_sql_exception("exception msg");
		}

		$query="UPDATE bills_list SET Bill_Recipient = '$billRecipient',
										Bill_RecipientsBankAccount = '$billRecipientBankAccount', 
										Bill_TransferTitle = '$billTransferTitle', 
										Bill_Amount = '$billAmount', 
										Bill_Desc = '$billDesc',
										Bill_Deadline = '$billDeadline' WHERE bills_list.Item_ID = $itemid;";
		if(!mysqli_query($conn, $query)){
			//echo("\nerror in updating bills details");
			throw new \mysqli_sql_exception("exception msg");
		}

		$conn->commit();
		echo("bill updated successfully\n");
	} catch (mysqli_sql_exception $exception) {
		echo("error occured!");
		$conn->rollback();
	}
}
else{
	echo("error in request");
}
?>
