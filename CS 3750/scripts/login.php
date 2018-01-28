<?php
session_start();
	
	include("connection.php");
	
	$username = strtolower($_POST['username']);
	
	$md5password = $password = $_POST['password'];
	$md5password = md5($md5password);
	$md5password = substr($md5password, 3, 15);
	
	// didn't fill out the form or is logged in already
	// redirect back to index
	if($password == "" || $username == "" || isset($_SESSION['username'])){
		header("Location: ../index.php");
		exit();
	}
	
	// get all rows that have that email/password
	// log them in
	$result = $mysqli->query(	
		"SELECT *
		FROM Users
		WHERE userUsername = '$username'
		AND userPassword   = '$md5password'"
	);
	
	if($result->num_rows == 1){
		$user = $result->fetch_object();
		$_SESSION['username'] = $user->userUsername;
		$_SESSION['userID']   = $user->userID;
		$_SESSION['userRole'] = $user->userRole;
		$result->close();
		header("Location: ../index.php");
		exit();
	}
	
	// bad login
	else{
		$mysqli->close();
		header("Location: ../index.php");
		exit();
	}
	
	// catch all
	$mysqli->close();
	header("Location: ../index.php");
	exit();

?>