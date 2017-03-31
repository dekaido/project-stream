<!DOCTYPE html>
<html>

<head><title>Project Stream</title></head>

<?php

include 'header.php';

$name = $_POST["name"];
$email = $_POST["email"];
$password = $_POST["password"];
$dbc = new mysqli("localhost","php","PHP7@localhost","project_stream");
echo "2";
$name = $dbc->real_escape_string($name);
$email = $dbc->real_escape_string($email);
$crypt = password_hash($password, PASSWORD_DEFAULT);
if($dbc->connect_errno){
	echo "<br><br><br>Connection failed: ".$dbc->connect_error;
	exit;
}
echo "3";
//$dbc->beginTransaction();
$dbc->autocommit(true);
echo "4";
$sql = "INSERT INTO login (email, password, name) values('$email','$crypt','$name');";
$dbc->query($sql);
echo "5";
echo $dbc->error;
//$dbc->commit();
echo "6";
$dbc->close();
echo "7";
?>

</html>
