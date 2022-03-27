<?php

require_once 'simple_html_dom.php';

//get html content from the site.
$dom = file_get_html('https://lirarate.org/', false);

$answer = array();

if (!empty($dom)) {
    $divClass = $rate = $i = 0;

    foreach($dom->find('.wp-block-columns') as $divClass) {

        foreach($divClass->find('.wp-block-column') as $wp_block_column ) {
            $answer[$i]['rate'] = trim($wp_block_column->plaintext);
            }
     $i++; 
    }
}
print_r($answer); 
exit;

?>