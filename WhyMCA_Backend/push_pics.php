<?php

    ini_set('display_errors', 'On');
    ini_set('allow_url_fopen', 'On');
    error_reporting(E_ALL);

    function count_dir_entries($dirPath) {
        $count = 0;
        if ($handle = opendir($dirPath)) {
            while (false !== ($file = readdir($handle))) {
                if ($file != "." && $file != "..") {
                    $count = $count + 1;
                }
            }
            closedir($handle);
        }
        return $count;
    }

    $pics       = $_POST["pics"];
    $theftId    = $_POST["theftId"];
    mkdir($theftId);

    foreach($pics as $pic) {
        echo "Pic: ".$pic."<br/>";
        $thefile = base64_decode($pic);
        $img = imagecreatefromstring($thefile);

        //header('Content-Type: image/jpeg');
        imagejpeg($img, $theftId."/".sha1($pic).".jpg");
        imagedestroy($img);
        count_dir_entries("./".$theftId."/");
    }

?>
