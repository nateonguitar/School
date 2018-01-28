<?php
	if($_GET)
	{
		$to = "nathanbrooks1@mail.weber.edu";

		$firstName   = strip_tags($_GET['firstName']);
		$lastName    = strip_tags($_GET['lastName']);
		$phoneNumber = strip_tags($_GET['phoneNumber']);
		$email       = strip_tags($_GET['email']);

		$subject = "CS 2350 Contact Form from home page $firstName $lastName";

		$body = $_GET['body'];

		$headers = "From: NoReply@natecontactform.com";
		mail($to, $subject, $body, $headers);

		echo "Thank you " . $firstName . " " . $lastName . ", your message has been sent";
	}
	else
	{
		echo "You shouldn't be on this page!";
	}
?>