<?php

    ini_set('display_errors', 'On');
    ini_set('allow_url_fopen', 'On');
    error_reporting(E_ALL);

    $pics       = $_POST["pics"];
    $theftId    = $_POST["theftId"];
    mkdir($theftId);

    $i = 0;
    foreach($pics as $pic) {
        echo "Pic: ".$pic."<br/>";
        $thefile = base64_decode($pic);
        $img = imagecreatefromstring($thefile);

        //header('Content-Type: image/jpeg');
        imagejpeg($img, $theftId."/".$i.".jpg");
        imagedestroy($img);
        $i = $i + 1;
    }

?>
