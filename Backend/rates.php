<?php

include ("db_info.php");

$query = $mysqli->prepare("SELECT lbp FROM rates");
$query->execute();

$array = $query->get_result();

$response = [];

while($rate = $array->fetch_assoc()){
    $response = $rate;
}

$json_response = json_encode($response);
echo $json_response;

?>