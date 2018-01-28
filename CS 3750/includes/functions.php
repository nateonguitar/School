<?php
	// used to check if a person entered a strong password
	function valid_password($candidate) {
		$r1='/[A-Z]/';  					// Uppercase
		$r2='/[a-z]/';  					// Lowercase
		$r3='/[!@#$%^&*()\-_=+{};:,<.>]/';  // 'special char'
		$r4='/[0-9]/';  					// numbers
		
		// need to have an upper case
		if(preg_match_all($r1,$candidate, $o)<1) return FALSE;

		// need to have a lower case
		if(preg_match_all($r2,$candidate, $o)<1) return FALSE;

		// need to have at least 1 special character
		if(preg_match_all($r3,$candidate, $o)<1) return FALSE;
		
		// need to have at least 1 number
		if(preg_match_all($r4,$candidate, $o)<1) return FALSE;

		// needs to be at least 8 characters long
		if(strlen($candidate)<8) return FALSE;

		return TRUE;
	}
?>