<?php

    $pics       = $_POST["pics"];
    $theftId    = $_POST["theftId"];

    $i = 0;
    foreach($pics as $pic) {
        $thefile = base64_decode($pic);
        $img = imagecreatefromstring($thefile);

        header('Content-Type: image/jpeg');
        imagejpeg($image, $theftId."/".i.".jpg");
        imagedestroy($image);

        $i = $i + 1;
    }

?>
