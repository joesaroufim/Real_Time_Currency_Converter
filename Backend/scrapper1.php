<?php

header("Content-Type: text/plain");

include 'simple_html_dom.php';

//get html content from the site.
$dom = file_get_html('https://lirarate.org/');



?>