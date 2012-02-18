<?php

    if (!function_exists('json_encode'))
    {
       function json_encode($a=false)
       {
           if (is_null($a)) return 'null';
           if ($a === false) return 'false';
           if ($a === true) return 'true';
           if (is_scalar($a))
           {
               if (is_float($a))
               {
                   // Always use "." for floats.
                   return floatval(str_replace(",", ".", strval($a)));
               }

               if (is_string($a))
               {
                   static $jsonReplaces = array(array("\\", "/", "\n", "\t", "\r", "\b", "\f", '"'), array('\\\\', '\\/', '\\n', '\\t', '\\r', '\\b', '\\f', '\"'));
                   return '"' . str_replace($jsonReplaces[0], $jsonReplaces[1], $a) . '"';
               }
               else
               return $a;
           }
           $isList = true;
           for ($i = 0, reset($a); $i < count($a); $i++, next($a))
           {
               if (key($a) !== $i)
               {
                   $isList = false;
                   break;
               }
           }
           $result = array();
           if ($isList)
           {
               foreach ($a as $v) $result[] = json_encode($v);
               return '[' . join(',', $result) . ']';
           }
           else
           {
               foreach ($a as $k => $v) $result[] = json_encode($k).':'.json_encode($v);
               return '{' . join(',', $result) . '}';
           }
       }
    }

    $theftId = $_GET["theftId"];

    // TODO: Check who tries directory traversal
    $picUrls = array();
    if ($handle = opendir("./".$theftId."/")) {
        /* This is the correct way to loop over the directory. */
        while ( false !== ($entry = readdir($handle))) {
            if ($entry !== ".." && $entry !== ".") {
                $picUrls[] = $entry;
            }
        }

        closedir($handle);
    }
    echo json_encode($picUrls);

?>
