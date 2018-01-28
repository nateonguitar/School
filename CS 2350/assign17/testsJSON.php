<?php
$id_testname = $_POST['testname'];

 


if ($id_testname=="css")
{
echo "[{
    \"testname\": \"CS 2350 Fernandez CSS\",
    \"date\": \"Wed Oct 03 2012\",
	\"timeelapsed\": \"2 minutes\"
    },
    {
    \"testname\": \"CS 2350 Fernandez CSS\",
    \"date\": \"Wed Nov 03 2012\",
	\"timeelapsed\": \"4 minutes\"
    }]";
}
else if ($id_testname=="xhtml")
{
echo "[{
    \"testname\": \"CS 2350 Fernandez XHTML\",
    \"date\": \"Thur Oct 07 2012\",
	\"timeelapsed\": \"6 minutes\"
    },{
    \"testname\": \"CS 2350 Fernandez XHTML\",
    \"date\": \"Sun Oct 09 2012\",
	\"timeelapsed\": \"7 minutes\"
    },
    {
    \"testname\": \"CS 2350 Fernandez XHTML\",
    \"date\": \"Fri Dec 03 2012\",
	\"timeelapsed\": \"8 minutes\"
    }]";


}
?>