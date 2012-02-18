<?php

    ini_set('display_errors', 'On');
    ini_set('allow_url_fopen', 'On');
    error_reporting(E_ALL);

    require_once('gifcreator/GifCreator.php');

    function dir_entries($dirPath) {
        $entries = array();
        if ($handle = opendir($dirPath)) {
            while (false !== ($file = readdir($handle))) {
                if ($file != "." && $file != "..") {
                    $entries[] = $file;
                }
            }
            closedir($handle);
        }
        return $entries;
    }

    $pics       = $_POST["pics"];
    $theftId    = $_POST["theftId"];
    mkdir($theftId);

    foreach($pics as $pic) {
        echo "Pic: ".$pic."<br/>";
        $thefile = base64_decode($pic);
        $img = imagecreatefromstring($thefile);
        $imgRot = imagerotate($img, 270, 0);

        //header('Content-Type: image/jpeg');
        imagejpeg($imgRot, $theftId."/".sha1($pic).".jpg");
        imagedestroy($img); imagedestroy($imgRot);

        if(count($savedPics = dir_entries("./".$theftId."/")) >= 3) {

            // Instanciate the class (uses default options with the addition of width/height specified)
            $gif = new GifCreator(0, 2, array(-1, -1, -1), 320, 480);

            // Add each frame to the animation
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[0]), 150, true);
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[1]), 150, true);
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[2]), 150, true);

            $fp = fopen($theftId."/theft.gif", 'w');
            fwrite($fp, $gif->getAnimation());
            fclose($fp);

        }
    }

?>
