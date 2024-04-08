
function signIn(event) {
	event.preventDefault();

	// TODO: put username and password in the funcLogin params
	var username = null;
	var password = null;

	// make servlet request
	if(funcLogin(username, password)){
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

// send POST request to servlet
// returns true if login success, and false otherwise
function funcLogin(username, password) {
    // use fetch API to send a POST request to the servlet
    fetch('/SignIn', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `username=${username}&password=${password}`
    })
    .then(response => {
        if (response.ok) {
            // login was okay
            console.log("Login successful");
            return true;
        } else if (response.status === 401) {
            // login failed
            console.log("Login failed: invalid username or password");
            return false;
        } else {
            // something bad happened
            console.error("An unexpected error occurred");
            return false;
        }
    })
    .catch(error => {
        // something REALLY bad happened
        console.error("Error:", error);
    });
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