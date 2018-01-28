<?php
session_start();
	
	include("connection.php");
	include("../includes/functions.php");
	
	$firstName = 	strtolower($_POST['firstname']);
	$lastName = 	strtolower($_POST['lastname']);
	$phone = 		$_POST['phone'];
	$email = 		$_POST['email'];
	$username = 	strtolower($_POST['username']);
	
	$md5password =  $password = $_POST['password1'];
	$md5password =  md5($md5password);
	$md5password =  substr($md5password, 3, 15);
	
	$password2 = 	$_POST['password2'];
	
	unset($_SESSION['errors']); // clear and reset the $errors array
	$errors = array();
	
	
	// this should also be done in JavaScript so a page refresh is not required, this is in case JavaScript is turned off
	// validate inputs
	// name
	if(empty($firstName)){
		array_push($errors, "First name must not be empty");	
	}
	if(empty($lastName)){
		array_push($errors, "Last name must not be empty");
	}
	
	
	// phone
	if(empty($phone)){
		array_push($errors, "Phone must not be empty");
	}
	
	$charactersToRemove = array("-", "(", ")", "@", "!", "#", "$", "%", "*");
	$phone = str_replace($charactersToRemove, "", $phone);
	if(empty($phone)){
		array_push($errors, "Phone must contain numbers");
	}
	
	
	
	
	// email
	if(empty($email)){
		array_push($errors, "Email must not be empty");
	}
	if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
	  $emailErr = "Invalid email format"; 
	}
	
	
	
	// username
	if(empty($username)){
		array_push($errors, "Username must not be empty");
	}
	
	
	
	// password
	if(empty($password)){
		array_push($errors, "Password must not be empty");
	}
	if(empty($password2)){
		array_push($errors, "Retyped password must not be empty");
	}
	if(!valid_password($password)){
		array_push($errors, "Password does not match security rules");
	}
	if($password != $password2){
		array_push($errors, "Passwords do not match");
	}
	
	$_SESSION['errors'] = $errors;
	
	
	
	// if no errors
	if(empty($errors)){
		
		// check if username is already taken
		$sql = "SELECT * FROM Users WHERE userUsername = '$username'";
		$result = $mysqli->query($sql);
		
		if($result->num_rows == 0){
			// run query to insert data  
			$query = "INSERT INTO Users (userFirstName, userLastName, userPhoneNumber, userRole, userBanned, userPassword, userUsername, userEmail) 
			VALUES ('$firstName', '$lastName', '$phone', 0, 0, '$md5password', '$username', '$email');";
			
			if($result = $mysqli->query($query)){
				// redirect back when completed
				header("Location: ../signUpThankYou.php?username=" . $username . "&firstname=" . $firstName . "&lastname=" . $lastName);
			}
			else{
				echo "insert failed";
			}
		}
		else{
			echo "username already in use";
			echo "<br /><button style='padding:20px; margin:20px;' onclick=\"history.go(-1);\">Back</button>";
		}
		$mysqli->close();
	}
	else{ //errors
		header("Location: ../signUp.php?errors=true");
	}
?>