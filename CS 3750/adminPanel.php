<?php include("includes/header.php"); ?>

<?php
	// kick you out if you're not logged in as an admin
	if(!isset($_SESSION['username']) || !isset($_SESSION['userRole']) || !($_SESSION['userRole'] > 0))
	{
		header("Location: scripts/logout.php");
	}
?>

<h2>Admin Panel</h2>

This is where we should be able to ban someone, or other admin level things.

<?php include("includes/footer.php"); ?>