<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed\n");
	exit(1);
}

if($_POST){
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }

	$conn->begin_transaction();

	try
	{
		$query = "SELECT Item_ID FROM items_list WHERE List_ID =".$listid;
		$result = $conn->query($query);
		if($result->num_rows != 0)
		{
			while($row = $result->fetch_assoc())
			{
				$query = "DELETE FROM tasks_list WHERE Item_ID = ".$row['Item_ID'];
				if(!mysqli_query($conn, $query)){
					// echo("error in deleting from tasks_list");
					throw new \mysqli_sql_exception("exception msg");
				}

				$query = "DELETE FROM bills_list WHERE Item_ID = ".$row['Item_ID'];
				if(!mysqli_query($conn, $query)){
					// echo("error in deleting from bills_list");
					throw new \mysqli_sql_exception("exception msg");
				}

				$query = "DELETE FROM items_list WHERE Item_ID = ".$row['Item_ID'];
				if(!mysqli_query($conn, $query)){
					// echo("error in deleting from items_list");
					throw new \mysqli_sql_exception("exception msg");
				}
			}
		}

		$query="DELETE FROM user_lists WHERE List_ID = ".$listid;
		if(!mysqli_query($conn, $query)){
			// echo("error in removing list from user_lists");
			throw new \mysqli_sql_exception("exception msg");
		}	

		$conn->commit();
		echo("list removed succesfully\n");
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
