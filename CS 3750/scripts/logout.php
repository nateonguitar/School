<?php
	session_start();
	$_SESSION['admin'] = "n";
	unset($_SESSION['admin']);
	unset($_SESSION['username']);
	session_destroy();
	header("Location: ../index.php");
?>