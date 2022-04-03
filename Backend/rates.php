<?php

include ("db_info.php");


$x = $_POST["data"];
$y = "hello";
$z = "helloo";


$response = $x.$y;

$json_response = json_encode($response);
echo($json_response);

$query = $mysqli->prepare("INSERT INTO rates (date, lbp, usd) VALUES (?, ?, ?)");
$query->bind_param("sss", $y, $z, $x);
$query->execute();

?>