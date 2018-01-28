<?php
  session_start(); // allows $_SESSION information
  include("scripts/connection.php");
?>
<html>
<head>
  <?php include("includes/scripts.php"); ?>
</head>
<body>
	<div id="header">       <!-- is 100% width of the screen -->
		<div id="headerContainer">  <!-- can be shrunk to like 80% screen width and centered -->
			<ul id="UserAndLogInLogOutInHeader">
				<?php
				// show the correct elements depending on if logged in
				if(isset($_SESSION['username']))
				{
					?>
					<li><?php echo $_SESSION['username']; ?></li>
					<li><a href="scripts/logout.php">Log Out</a></li>
					<?php
					if(isset($_SESSION['userRole']) && $_SESSION['userRole'] > 0)
					{
						?>
						<li><a href="adminPanel.php">Admin Panel</a></li>
						<?php
					}
				}
				else
				{
					?>
					<li><a href="login.php">Log In</a></li>
					<li><a href="signUp.php">Sign Up</a></li>
					<?php
				}
				?>
			</ul>
			
			<div id="titleInHeader">
				<h1><a href="index.php">Auctions Near Me</a></h1>
			</div>
		</div>
	</div>
	
	<div id="wrapper">