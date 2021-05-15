<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed\n");
	exit(1);
}
if($_POST){
	if(isset($_POST['itemid'])){ $itemid = $_POST['itemid']; }
	
	$conn->begin_transaction();
	
	try
	{
		$query="DELETE FROM tasks_list WHERE Item_ID = ".$itemid;
		if(!mysqli_query($conn, $query)){
			//echo("error in removing from tasks_list");
			throw new \mysqli_sql_exception("exception msg");
		}	
		
		$query="DELETE FROM items_list WHERE Item_ID = ".$itemid;
		if(!mysqli_query($conn, $query)){
			//echo("error in removing from items_list");
			throw new \mysqli_sql_exception("exception msg");
		}	
	
		$conn->commit();
		echo("task removed successfully");
	} catch(mysqli_sql_exception $exception)
	{
		echo("error occured!");
		$conn->rollback();
	}

}
else{
	echo("error in request");
}
?>
