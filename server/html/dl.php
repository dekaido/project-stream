<?php
$file_url = "mp3/".$_GET["name"];
header("Content-Type: application/octet-stream");
header("Content-Transfer-Encoding: Binary");
header("Content-disposition: attachement; filename=\"".basename($file_url)."\"");
readfile($file_url);
?>
