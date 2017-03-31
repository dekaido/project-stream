<!DOCTYPE>
<html>

<head>
<title>Project Stream</title>
<style>
#signup {
position: absolute;
top: 60px;
font-family: "Arial", Arial, sans-serif;
}
</style>
</head>

<body>

<?php include "header.php";?>

<div id="signup">
<form action="welcome.php" method="post">
<br>
E-mail: <input type="text" name="email"><br>
Name: <input type="text" name="name"><br>
Password: <input type="password" name="password"><br>
<input type="submit">
</form>
</div>

</body>

</html>
