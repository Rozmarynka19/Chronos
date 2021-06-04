<?php

include 'databaseInfo.php';

function generateRandomString($length = 4) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

$conn = mysqli_connect($servername, $mysql_user, $mysql_password, $dbname);
if($conn){
	echo("connection sucess");
	
	if($_POST){
		if(isset($_POST['email'])){ $email = $_POST['email']; }
		if(isset($_POST['login'])){ $login = $_POST['login']; }
		if(isset($_POST['id'])){ $id = $_POST['id']; }
		$to = $email;
		$subject = "Weryfikacja konta Chronos";

		$user = $login;
		$key = generateRandomString(4);

		$message = "
Witaj $user, dziękujemy za zajerestrowanie się w naszej aplikacji Chronos.\n
Aby Zacząć korzystać z aplikacji musisz najpierw zweryfikować swoje konto.\n
Aby to zrobić przepisz poniższy kod do pola w oknie weryfikacji.\n
$key\n\n
Dziękujemy\n
Zespół AlgoLearn
";


		$headers = "MIME-Version: 1.0" . "\r\n";
		$headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

		$headers .= 'From: <Chronos@TeamAlgoLearn.hub.pl>' . "\r\n";

		$test = mail($to,$subject,$message,$headers);
		//echo($test);
		$query="UPDATE registered_users SET Verification_Code = '$key' WHERE User_ID = '$id'";
		if(mysqli_query($conn, $query)){
			echo("\ncode succ");
			echo("\n$key");
		}
		
	}
}
else{
	echo("connection failed");
}

?>
