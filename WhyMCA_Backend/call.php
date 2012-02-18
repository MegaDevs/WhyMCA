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
    $TROPO_SMS_TOKEN    = "3f310f0c41148c469a608cc051d582e2d54dc8cd3ba45a946704a80be4e48e118eb4d9f23df7794584eda57e";
    $BASE_SENTENCE      = "Sta avvenendo un furto a casa di ";

    $voiceNumbers   = $_GET["voiceNumbers"];
    $smsNumbers     = $_GET["smsNumbers"];
    $name           = $_GET["name"];

    echo "Name ".$name."<br/>";

    foreach($voiceNumbers as $number) {
        echo "Number ".$number."<br/>";
        $request = $TROPO_BASE_URL.$TROPO_VOICE_TOKEN."&sentence=".urlencode($BASE_SENTENCE).urlencode($name)."!!"."&numberToDial=".urlencode($number);
        get($request);
    }

    foreach($smsNumbers as $number) {
        echo "Number ".$number."<br/>";
        $request = $TROPO_BASE_URL.$TROPO_SMS_TOKEN."&msg=".urlencode($BASE_SENTENCE).urlencode($name)."!!"."&numberToDial=".urlencode($number);
        get($request);
    }

    echo "<br/><br/>";
?>
