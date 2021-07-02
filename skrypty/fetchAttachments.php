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
	$query="SELECT * FROM attachments_list WHERE Item_ID='".$itemid."'";
	$result = $conn->query($query);
	
	foreach($result as $item) {
		echo $item['Attachment_Name']."\n";
	}	
}
else{
	echo("error in request");
}
?>
