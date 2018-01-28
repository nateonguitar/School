<?php include("includes/header.php"); ?>

<?php
	$firstName = $_GET['firstname'];
	$lastName = $_GET['lastname'];
	$username = $_GET['username'];
?>

<h2>Account Created</h2>

Thank you, <span class="bold"><?php echo ucfirst($firstName) . " " . ucfirst($lastName); ?></span>, your account has been created under the username: <span class="bold"><?php echo $username; ?></span>

<?php include("includes/footer.php"); ?>