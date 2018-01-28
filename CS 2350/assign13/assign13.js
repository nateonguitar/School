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
				alert("You need to select a pasta type");
				return false;
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
		// display the results               //
		///////////////////////////////////////
		
			document.getElementById("totalcost").innerHTML = "Total bill: $" + total.toFixed(2);
		
		// reset value to base so you can use the form again without reloading
		total = 7.5;

		return false;
	}