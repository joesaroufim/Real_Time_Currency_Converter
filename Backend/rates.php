<?php

include ("db_info.php");


$rate = $_POST["rate"];
$conversion_type = $_POST["conversion"];
$lbp = $_POST["lbp"];
$usd = $_POST["usd"];

$date = date('d-m-y h:i:s'); 

$json_response = json_encode($response);
echo($json_response);

$query = $mysqli->prepare("INSERT INTO rates (rate, lbp, usd, conversion_type,date) VALUES (?, ?, ?, ?,?)");
$query->bind_param("sssss", $rate, $lbp, $usd, $conversion_type,$date);
$query->execute();

?>