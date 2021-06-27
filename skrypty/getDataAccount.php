<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess\n");
}
else{
	echo("connection failed");
}

if($_POST){
	if(isset($_POST['User_ID'])){ $user_ID = $_POST['User_ID']; }
	$query="SELECT Bill_Account_Name, Bill_Account_Number FROM User_Bill_Account_Information WHERE User_ID = $user_ID";
	$result = $conn->query($query)->fetch_assoc();;
	echo $result['Bill_Account_Name']."\n";
	echo $result['Bill_Account_Number']."\n";
}
else{
	echo("error in request");
}
?>