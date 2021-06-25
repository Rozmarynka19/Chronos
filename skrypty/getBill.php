<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess\n");
}
else{
	echo("connection failed\n");
}
if($_POST){
	if(isset($_POST['itemid'])){ $itemid = $_POST['itemid']; }
	$query="SELECT * FROM bills_list WHERE Item_ID='".$itemid."'";
	$result = $conn->query($query)->fetch_assoc();

	echo $result['Bill_Recipient']."\n";
	echo $result['Bill_RecipientsBankAccount']."\n";
	echo $result['Bill_TransferTitle']."\n";
	echo $result['Bill_Amount']."\n";
	echo $result['Bill_Desc']."\n";
	echo $result['Bill_Deadline']."\n";
}
else{
	echo("error in request");
}
?>
