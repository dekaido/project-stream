<?php
header("Content-Type: text/plain");
$dbc = new mysqli("localhost","php","PHP7@localhost","project_stream");
if($dbc->connect_errno){
	echo  $dbc->connect_error;
}
$songName = $_GET["name"];
if($songName == "all"){
	$result = $dbc->query("select * from songs;");
	while($row = mysqli_fetch_assoc($result)){
		foreach($row as $cname => $cvalue){
			print"$cname: $cvalue\t";
		}
		print "\r\n";
	}
}else{
	$result = $dbc->query("select * from songs where file = '$songName';");
	while($row = mysqli_fetch_assoc($result)){
		foreach($row as $cname => $cvalue){
			print"$cname: $cvalue\t";
		}
		print "\r\n";
	}
}
