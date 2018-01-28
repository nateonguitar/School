function showLoginForm(){
			$("#loginFormWholePageContainer").css({"display":"block"});
			$("#loginFormWholePageContainer").animate({"opacity":1});
			document.getElementById("loginFormEmailTextBox").focus();
		}
		
		function hideLoginForm(){
			$("#loginFormWholePageContainer").css({"display":"none", "opacity":0});
		}