<?php include("includes/header.php"); ?>

<h2>Sign Up</h2>

<?php
	// if the "errors" session variable has been set AND the variable has info in it
	if(isset($_GET['errors']) && $_GET['errors'] == true)
	{
		if(isset($_SESSION['errors']) && !empty($_SESSION['errors']))
		{
			echo "<ul style=\"color:red;\">";
			foreach ($_SESSION['errors'] as $error) {
				echo "<li>" . $error . "</li>";
			}
			echo "</ul>";
		}
	}
?>

<form id="signUpForm" action="scripts/createNewUser.php" method="post">
	<ul id="labels">
		<li>First Name:</li>
		<li>Last Name:</li>
		<li>Phone:</li>
		<li>Email:</li>
		<li>Username:</li>
		<li><span id="passwordRulesHover" class="tip" style="cursor:pointer;">Password rules</span> Password:</li>
		<li>Re-enter Password:</li>
	</ul>
	
	<ul id="fields">
		<li><input type="text"     name="firstname" placeholder="First Name"  id="focusFirst" /></li>
		<li><input type="text"     name="lastname"  placeholder="Last Name"           /></li>
		<li><input type="text"     name="phone"     placeholder="888-555-1234"        /></li>
		<li><input type="text"     name="email"     placeholder="example@example.com" /></li>
		<li><input type="text"     name="username"  placeholder="Username"            /></li>
		<li><input type="password" name="password1" placeholder="Password"            /></li>
		<li><input type="password" name="password2" placeholder="Re-enter Password"   /></li>
	</ul>
	
	
	
	<script type="text/JavaScript">
		// put cursor in the right place when the page loads
		$(document).ready(function(){
			$("#focusFirst").focus();
		});
		
		$("#passwordRulesHover").click(function(){
			$("#passwordRules").css({"display": "block"});
		});
	</script>
	
	<div style="clear:both;"></div>
	<input style="margin-left:180px;" class="button" type="submit" value="Create Account" />
	<a href="index.php"><input type="button" class="button" value="Cancel" /></a>
	<div id="passwordRules" class="tip">
		Password must be at least 8 characters long and contain:<br />
		* at least 1 upper case letter<br />
		* at least 1 lower case letter<br />
		* at least 1 special character<br />
		* at least 1 number
	</div>
	
</form>

<?php include("includes/footer.php"); ?>