
function backToSignIn() {
	fetchFile('login.html');
	setUpBanner();
}

function attemptSignUp() {
	funcSignUp();
	fetchFile('home.html');
	showBackButton(false);
	setUpBanner();
}

// sends post request to servlet, returning true if the sign up was successful, and false otherwise
async function funcSignUp(username, password, email) {
    // use fetch API to send a POST request to the servlet
    try{
		const response = fetch('/SignUp', {
	        method: 'POST',
	        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	        body: `username=${username}&password=${password}&email=${email}`
	    });
	    
        if (response.ok) {
            // sign up was okay
            console.log("Sign Up successful");
            return true;
        } else if (response.status === 401) {
            // sign up failed
            console.log("SignUp Failed: Username or Email already exists");
		} else if(response.status === 500) {
			// sign up error
			console.log("SignUp Failed: Servlet could not create new user.")
		} else {
            // something bad happened
            console.error("An unexpected error occurred");
        }
   	} catch(error) {
	console.error("Error:", error);
    }
    
    return false;
}


function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
	showBackButton(false);
    setUpBanner();
}

