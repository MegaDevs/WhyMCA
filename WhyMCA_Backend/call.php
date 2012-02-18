<?php

    $TROPO_BASE_URL     = "https://api.tropo.com/1.0/sessions?action=create&token=";
    $TROPO_VOICE_TOKEN  = "c4564f8c3434484499a4380e2f0f4e131c6776963d237dfdc8587857609dbc6bf87b60d518f8801e1cd0d25c";
    $BASE_SENTENCE      = " è stato derubato!!!";

    $numbers    = $_POST["numbers"];
    $name       = $_POST["name"];

    foreach($numbers as $number) {
        $request = $TROPO_BASE_URL.$TROPO_VOICE_TOKEN."&sentence=".$name.$BASE_SENTENCE."&numberToDial=".$number;
        $result = fopen($request);
    }

?>
