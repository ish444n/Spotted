document.addEventListener('DOMContentLoaded', function () {
	const navbar = document.getElementById('navs');
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

    if (isLoggedIn) {
        navbar.innerHTML = `
            <a href="index1.html">Home</a>
        `;
        document.getElementById('logout').addEventListener('click', function () {
            localStorage.clear();
            window.location.href = "index.html";
        });
    } else {
        navbar.innerHTML = `
            <a href="index1.html">Home</a>
        `;
    }

    const loginForm = document.getElementById('login-form');
    const signupForm = document.getElementById('signup-form');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        performLogin();
    });

    signupForm.addEventListener('submit', function(event) {
        event.preventDefault();
        performSignup();
    });
	
	// handles the sign in button press
	async function performLogin() {
	    const username = document.getElementById('login-username').value.trim();
	    const password = document.getElementById('login-password').value;
	
		// sends the request to servlet - UserID is null if login failed
		// server errors handled in here
		const userID = await funcLogin(username, password);
		console.log(`userid is ${userID}`)
		
		// handle failed login
		if (userID == null) {
			return;
		}
		
		// handle login success & redirect home
		localStorage.setItem('isLoggedIn', 'true'); 
	    localStorage.setItem('userId', userID); 
	    window.location.href = "index1.html";
	}
		
	// front end interaction function that makes a request to verify user in database
	async function funcLogin(username, password) {
	    console.log("Attempting to log user " + username + " in with password " + password);
	
		// send POST request to servlet
		// returns UserID if login success, and null otherwise
	    try {
	        const response = await fetch('/Spotted/SignIn', {
	            method: 'POST',
	            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	            body: `username=${username}&password=${password}`
	        });
	        
			// login was successful
	        if (response.ok) {
	            const userID = await response.text(); // wait for server to fill request
	            return userID;
	        } 
	        // login was not successful
	        else if (response.status == 401) {
	            alert("Login failed: invalid username or password");
	            return null;
	        }
	        // login was REALLY not successful
	        else {
				alert("Server error on login!");
		        return null;
			}
	    } catch (error) {
	        alert("Server error on login!");
	        console.error("Error:", error);
	        return null;
	    }
	}

	// sends signup post request to servlet, logging in if the sign up was successful, and null otherwise
	async function performSignup() {
		// grab the form values
		const email = document.getElementById("signup-email").value;
		const password = document.getElementById("signup-password").value;
		const username = document.getElementById("signup-username").value;
		
		// check if params exist
		if(!(username && password && email)) {
			return null;
		}
		
		// get the sign in request
	    const success = 
	    	await fetch('/Spotted/SignUp', {
		        method: 'POST',
		        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		        body: `username=${username}&password=${password}&email=${email}`
		    })
		    .then(response => {
				// login was successful, so redirect user & update state
		        if (response.ok) {
		            console.log("Sign Up successful");
		            return true;
		
		        } else if (response.status == 401) {
		            alert("Sign Up failed: Username or Email already in use.");
		            return false;
		        } else {
					alert("SignUp failed: Internal Server Error.");
					return false;
				}
		    })
		    .catch(error => {
		        console.error("Error:", error);
		        return false;
		    });
		    
		// handle failed signup (do nothing)
		if(!success) {
			return;
		}
	
		// attempt to log the user is using the new credentials
		const userID = await funcLogin(username, password);
		console.log(`userid is ${userID}`)
		
		
		// handle failed login
		if (userID == null) {
			return;
		}
		
		
		// handle login success & redirect home
		localStorage.setItem('isLoggedIn', 'true'); 
	    localStorage.setItem('userId', userID); 
	    window.location.href = "index1.html";
	}
	
});



