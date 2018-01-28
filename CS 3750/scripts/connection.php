<?php 
	// uncomment this if you want to see extra detailed database error reporting
	// not for the production version of the app
	//error_reporting(E_ALL & ~E_NOTICE); 
	$username = "CS3750AuctionApp";
	$password = "ARh4dL9J6tuL5ALz";
	$database = "CS3750AuctionApp";
	
	$mysqli = new mysqli("localhost", $username, $password, $database);
	if ($mysqli->connect_errno) {
		printf("Connect failed: %s\n", $mysqli->connect_error);
		exit();
	}
?>
