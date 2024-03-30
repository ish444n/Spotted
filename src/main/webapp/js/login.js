
function signIn(event) {
	event.preventDefault();

	if(funcLogin()){
		console.log("login works!");
		fetchFile('home.html');
		showBackButton(false);
		onBannerless = false;
		signedIn = true;
		setUpBanner();
		document.getElementById('message').style.display = "none";
	}
	else{
		console.log("login failed!");
		document.getElementById('message').style.display = "block";
	}
}

function displayResponse(response) {
	var responseContainer = document.getElementById('container');
    responseContainer.outerHTML = response;
}

function signUp() {
	fetchFile('signup.html');
	setUpBanner();
}

function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
	showBackButton(false);
    setUpBanner();
}


function loadPage() {
	document.getElementById('message').style.display = "none";
}