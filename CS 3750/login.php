<?php include("includes/header.php"); ?>

<h2>Log In</h2>

<form id="loginForm" action="scripts/login.php" method="post">
	<ul id="labels">
		<li>Username:</li>
		<li>Password:</li>
	</ul>
	
	<ul id="fields">
		<li><input type="text" id="focusFirst" name="username" placeholder="username" /></li>
		<li><input type="password" name="password" placeholder="******" /></li>
	</ul>
	<div style="clear:both;"></div>
	<input style="margin-left:180px;" class="button" type="submit" value="Log in" />
	<a href="index.php"><input type="button" class="button" value="Cancel" /></a>
</form>

<script type="text/JavaScript">
	// put cursor in the right place when the page loads
	$(document).ready(function(){
		$("#focusFirst").focus();
	});
</script>

<?php include("includes/footer.php"); ?>