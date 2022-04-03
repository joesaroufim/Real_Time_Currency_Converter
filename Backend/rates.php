<?php

include ("db_info.php");


$rate = $_POST["rate"];
$conversion_type = $_POST["conversion"];
$lbp = $_POST["lbp"];
$usd = $_POST["usd"];

$json_response = json_encode($response);
echo($json_response);

?>