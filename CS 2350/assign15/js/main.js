var transitionTime = 40;

divsOpacity = 0;
divsMaxOpacity = 9;
enterImageOpacity = 9;

var errors = false;

	function onloadOpacities()
	{
		document.getElementById('enterImage').style.opacity = 1;
		document.getElementById('homePageWelcome').style.opacity = 1;
		document.getElementById('footerBox').style.opacity = 1;
	}

////////////////////////////////////////////////
// Changing opacities                         //
////////////////////////////////////////////////
	function hideMainPageForRedirect(link)
	{
		divsOpacity--;
		
		document.getElementById('topBox').style.opacity = divsOpacity / 10;
		document.getElementById('wrapper').style.opacity = divsOpacity / 10;
		
		if(divsOpacity != 0)
		{
			setTimeout("hideMainPageForRedirect()", transitionTime);
		}
		else if (divsOpacity == 0)
		{
			window.location = link;
		}
	}

	function hideEnterImage()
	{
		document.getElementById('enterImage').style.opacity = enterImageOpacity / 10;
		document.getElementById('homePageWelcome').style.opacity = enterImageOpacity / 10;
		document.getElementById('footerBox').style.opacity = enterImageOpacity / 10;
		
		enterImageOpacity--;
		
		if(enterImageOpacity > 0)
		{
			setTimeout("hideEnterImage()", transitionTime);
		}
		else if (enterImageOpacity == 0)
		{
			window.location = 'home.html';
		}
	}

	function showDivs()
	{
		divsOpacity += 1;
		
		document.getElementById('topBox').style.opacity = divsOpacity / 10;
		document.getElementById('wrapper').style.opacity = divsOpacity / 10;
		
		if(divsOpacity < divsMaxOpacity)
		{
			setTimeout("showDivs()", transitionTime);
		}
	}

/////////////////////////////////////////////
// Validation                              //
/////////////////////////////////////////////

	function validate(form)
	{
		var phone = form.phoneNumber.value;
		var phoneNumberSplit = phone.split("-");
		
		var at = "@";
		var dot = ".";
		var space = " ";
		var indexOfAt = form.email.value.indexOf(at);
		var indexOfDot = form.email.value.indexOf(dot);
		var indexOfSpace = form.email.value.indexOf(space);
		
		
			// first name validation
		if (!form.firstName.value)
		{
			document.getElementById('errorsBox').innerHTML = "You need to enter a First Name";
			errors = true;
			return false;
		}
		else if(isNaN(form.firstName.value) == false)
		{
			document.getElementById('errorsBox').innerHTML = "Your First Name cannot have numbers";
			return false;
		}
		
		
			// last name validation
		else if (!form.lastName.value)
		{
			document.getElementById('errorsBox').innerHTML = "You need to enter a Last Name";
			return false;
		}
		else if(isNaN(form.lastName.value) == false)
		{
			document.getElementById('errorsBox').innerHTML = "Your Last Name cannot have numbers";
			
			return false;
		}
		
			
			// phone validation
		else if(!form.phoneNumber.value)
		{
			document.getElementById('errorsBox').innerHTML = "You need to enter a Phone Number";
			return false;
		}
		else if (phoneNumberSplit.length != 3)
		{
			document.getElementById('errorsBox').innerHTML = "You need to put in a valid phone number<br />###-###-####";
			return false;
		}
		else if (phoneNumberSplit[0].length != 3)
		{
			document.getElementById('errorsBox').innerHTML = "Phone Number:\nYour first section must be 3 characters long<br />(___)-###-####";
			return false;
		}
		else if (phoneNumberSplit[1].length != 3)
		{
			document.getElementById('errorsBox').innerHTML = "Phone Number:\nYour second section must be 3 characters long<br />###-(___)-####";
			return false;
		}
		else if (phoneNumberSplit[2].length != 4)
		{
			document.getElementById('errorsBox').innerHTML = "Phone Number:\nYour third section must be 4 characters long<br />###-###-(____)";
			return false;
		}
		else if(form.phoneNumber.value)
		{
				// phone is ONLY numbers
			for (var i=0; i<3; i++)
			{
				if (isNaN(phoneNumberSplit[i]) == true)	{
					document.getElementById('errorsBox').innerHTML = "Your phone number must be only NUMBERS";
					return false;
				}
			}
			
			
			
					// email validation
			if(!form.email.value)
			{
				document.getElementById('errorsBox').innerHTML = "You need to enter an Email";
				return false;
			}
				// if @ is not present
			else if (indexOfAt == -1)
			{
			   document.getElementById('errorsBox').innerHTML = "Invalid E-mail ID:<br />No @ symbol";
			   return false;
			}
				// if . isn't present
			else if (indexOfDot == -1)
			{
				document.getElementById('errorsBox').innerHTML = "Invalid E-mail ID<br />No . symbol";
				return false;
			}
				// make sure there are no spaces
			else if(indexOfSpace != -1)
			{
				document.getElementById('errorsBox').innerHTML = "Invalid E-mail ID <br />cannot have spaces";
			}
			else if(errors == true)
			{
				return false;
			}
		}
		else
		{
			clearErrors();
		}
	}

	function clearErrors()
	{
		document.getElementById('errorsBox').innerHTML = " ";
	}
		
	function showMenuImg(frame)
	{
		document.getElementById(frame).style.display = "block";
	}
	function hideMenuImg(frame)
	{
		document.getElementById(frame).style.display = "none";
	}