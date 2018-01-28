		var Zoomed = false;
		var Zoomed = false;
		var borderWidthSmall = "450px";
		var BorderWidthLarge = "675px";
	function toggleMenu()
	{
		if (Zoomed == true)
		{
			document.body.style.fontSize = "100%";
			for (var i=0; i<document.myForm.elements.length; i++)
			{
				document.myForm.elements[i].style.fontSize = "100%";
			}
			document.getElementById('border').style.width = borderWidthSmall;
			Zoomed = false;
		}
		else
		{
			document.body.style.fontSize = "150%";
			for (var a=0; a<document.myForm.elements.length; a++)
			{
				document.myForm.elements[a].style.fontSize = "110%";
			}
			document.getElementById('border').style.width = BorderWidthLarge;
			Zoomed = true;
		}
		
			// this worked, I just didn't feel like it did what the assignment wanted
		/* if (Zoomed == true)
		{
			document.body.style.zoom = "100%";
			Zoomed = false;
		}
		else
		{
			document.body.style.zoom = "150%";
			Zoomed = true;
		} */
	}

	function clearErrors()
	{
		document.getElementById('nameLabel').innerHTML = "";
		document.getElementById('phoneLabel').innerHTML = "";
		document.getElementById('radioLabel').innerHTML = "";
	}
	
		// calculates order
	function calculateOrder(form)
	{
		///////////////////////////////////////
		// check for pastatype               //
		///////////////////////////////////////
				// base price for pasta bowl
			var total = (750/100);
			
			var pastaTypeSelected = false;
			var pastaChosenValue = 0;
			
				// Iterate through the pasta types to check if one is selected.
			for (var j=0; j<form.pastatype.length; j++)
			{
					// Checking if at least one radio button is selected. Or not.
				if (form.pastatype[j].checked && pastaTypeSelected == false)
				{
					pastaTypeSelected = true;
					pastaChosenValue = form.pastatype[j].value;
					total += (pastaChosenValue / 100);
				}
			}
			
				// if the for loop ends without flipping PastaTypeSelected, it means you didn't check any
			if (!pastaTypeSelected)
			{
				document.getElementById('radioLabel').innerHTML = "You must select a pasta type";
				return false;
			}
			else
			{
				clearErrors();
			}
			
		///////////////////////////////////////
		// check for sauce                   //
		///////////////////////////////////////
		
		
			total += (form.sauce.value / 100);
			
			
		///////////////////////////////////////
		// check for extras                  //
		///////////////////////////////////////
		
			for (var i=0; i<form.extras.length; i++)
			{
				if (form.extras[i].checked)
				{
					total += (form.extras[i].value / 100);
				}
			}
		
		///////////////////////////////////////
		// validate text inputs              //
		///////////////////////////////////////
				
				var phoneNumber = form.phone.value;
				var phoneNumberSplit = phoneNumber.split("-");
				
				
				// make sure both forms aren't empty
			if (form.name.value == "" && form.phone.value == "")
			{
				document.getElementById('nameLabel').innerHTML = "You need to enter a name";
				document.getElementById('phoneLabel').innerHTML = "and a phone number";
				return false;
			}
			else
			{
				clearErrors();
			}
			
			
			
				// name validation
			if (form.name.value == "")
			{
				document.getElementById('nameLabel').innerHTML = "You need to enter a name";
				return false;
			}
			else if(isNaN(form.name.value) == false)
			{
				document.getElementById('nameLabel').innerHTML = "Your name cannot have numbers";
				return false;
			}
			else
			{
				clearErrors();
			}
				
				
				
				// phone validation
			if (form.phone.value == "")
			{
				document.getElementById('phoneLabel').innerHTML = "You need to enter a phone number";
				return false;
			}
			else if (phoneNumberSplit.length != 3)
			{
				document.getElementById('phoneLabel').innerHTML = "You need to put in a valid phone number\n###-###-####";
				return false;
			}
			else if (phoneNumberSplit[0].length != 3)
			{
				document.getElementById('phoneLabel').innerHTML = "Phone Number:\nYour first section must be 3 characters long\n(___)-###-####";
				return false;
			}
			else if (phoneNumberSplit[1].length != 3)
			{
				document.getElementById('phoneLabel').innerHTML = "Phone Number:\nYour second section must be 3 characters long\n###-(___)-####";
				return false;
			}
			else if (phoneNumberSplit[2].length != 4)
			{
				document.getElementById('phoneLabel').innerHTML = "Phone Number:\nYour third section must be 4 characters long\n###-###-(____)";
				return false;
			}
			else
			{
				clearErrors();
			}
			
			
			
				// phone is ONLY numbers
			for (var i=0; i<3; i++)
			{
				if (isNaN(phoneNumberSplit[i]) == true)	{
					document.getElementById('phoneLabel').innerHTML = "Your phone number must be only NUMBERS";
					return false;
					break;
				}
				else
				{
					clearErrors();
				}
			}



		
		///////////////////////////////////////
		// display the results               //
		///////////////////////////////////////

				clearErrors();
				document.getElementById("totalcost").innerHTML = " $" + total.toFixed(2);
				// reset value to base so you can use the form again without reloading
				total = 7.5;

		return false;
	}