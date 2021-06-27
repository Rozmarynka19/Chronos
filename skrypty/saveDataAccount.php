<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess");
}
else{
	echo("connection failed");
}

if($_POST){
	if(isset($_POST['User_ID'])){ $user_ID = $_POST['User_ID']; }
	if(isset($_POST['Bill_Account_Name'])){ $bill_name = $_POST['Bill_Account_Name']; }
	if(isset($_POST['Bill_Account_Number'])){ $bill_account_number = $_POST['Bill_Account_Number']; }
	
	$query="UPDATE User_Bill_Account_Information SET Bill_Account_Name = '$bill_name', Bill_Account_Number = '$bill_account_number' WHERE User_ID = $user_ID";
	$result = $conn->query($query);
}
else{
	echo("error in request");
}
?>