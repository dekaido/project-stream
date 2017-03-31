<!DOCTYPE html>


<html>
<head><title> Project Stream </title></head>
<body>
<?php
	include 'header.php';

	$email = $_POST["email"];
	$password = $_POST["password"];

	$dbc = new mysqli("localhost","php","PHP7@localhost","project_stream");

	$email = $dbc->real_escape_string($email);

	$sql = "select password from login where email='$email';";

	$que = $dbc->query($sql);

	$hash = $que->fetch_assoc()["password"];
	//$hash = $que->fetch_assoc();

	if( password_verify($password, $hash))
		print "true";
	else
		print "false";


?>

</body>

</html>
