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

        //header('Content-Type: image/jpeg');
        imagejpeg($img, $theftId."/".sha1($pic).".jpg");
        imagedestroy($img);

        if(count($savedPics = dir_entries("./".$theftId."/")) >= 3) {

            // Instanciate the class (uses default options with the addition of width/height specified)
            $gif = new GifCreator(0, 2, array(-1, -1, -1), 600, 600);

            // Add each frame to the animation
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[0]), 200, true);
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[1]), 200, true);
            $gif->addFrame(file_get_contents($theftId."/".$savedPics[2]), 200, true);
            // Disposal set to 0 for this frame so that following frame becomes overlay
            //$gif->addFrame(file_get_contents('images/5.jpg'), 200, true, 0, 0, 0);
            // Overlay frame
            //$gif->addFrame(file_get_contents('images/6.gif'), 200, false, 150, 150, 2, array(255, 255, 255));

            // Output the animated gif
            //header('Content-type: image/gif');
            $fp = fopen($theftId."/theft.gif", 'w');
            fwrite($fp, $gif->getAnimation());
            fclose($fp);

        }
    }

?>
