<?php

    ini_set('display_errors', 'On');
    ini_set('allow_url_fopen', 'On');
    error_reporting(E_ALL);

    function get($url) {
        $options = array(
            'http' => array(
                'protocol_version' => '1.0',
                'method' => 'GET'
            )
        );
        $context = stream_context_create($options);
        $resp = file_get_contents($url, false, $context);

    }

    $TROPO_BASE_URL     = "https://api.tropo.com/1.0/sessions?action=create&token=";
    $TROPO_VOICE_TOKEN  = "c4564f8c3434484499a4380e2f0f4e131c6776963d237dfdc8587857609dbc6bf87b60d518f8801e1cd0d25c";
    $BASE_SENTENCE      = " è stato derubato!!!";

    $numbers    = $_GET["numbers"];
    $name       = $_GET["name"];
    echo "Name ".$name."<br/>";

    foreach($numbers as $number) {
        echo "Number ".$number."<br/>";
        $request = $TROPO_BASE_URL.$TROPO_VOICE_TOKEN."&sentence=".urlencode($name).urlencode($BASE_SENTENCE)."&numberToDial=".urlencode($number);
        get($request);
    }

    echo "<br/><br/>";
?>
