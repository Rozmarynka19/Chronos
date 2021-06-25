<?php
include 'databaseInfo.php';
$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if(!$conn){
	echo("connection failed");
	exit(1);
}
if($_POST){
	if(isset($_POST['listid'])){ $listid = $_POST['listid']; }
	$query="SELECT * FROM items_list WHERE List_ID='".$listid."'";
	$result = $conn->query($query);
	
	echo strval($result->num_rows)."\n";
	foreach($result as $item) {
		echo $item['Item_ID']."\n";
		echo $item['Item_Name']."\n";
		echo $item['Item_Type']."\n";
	}	
}
else{
	echo("error in request");
}
?>
