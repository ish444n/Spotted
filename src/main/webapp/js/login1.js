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

	function performLogin() {
	    const username = document.getElementById('login-username').value.trim();
	    const password = document.getElementById('login-password').value;
	
	    fetch('/Spotted/LoginServlet', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify({ username, password })
	    })
	    .then(response => {
	        if (response.ok) return response.json();
	        else {
				alert('Invalid Credentials');
				throw new Error('Login failed');
			}
	    })
	    .then(data => {
	        if (data > 0) {
	            localStorage.setItem('isLoggedIn', 'true'); 
	            localStorage.setItem('userId', data); 
	            window.location.href = "index1.html";
	        } else {
	            throw new Error('User ID not received');
	        }
	    })
	    .catch(error => {
	        console.error('Login error:', error);
	    });
	}
	
	function performSignup() {
	    const email = document.getElementById('signup-email').value.trim();
	    const username = document.getElementById('signup-username').value.trim();
	    const password = document.getElementById('signup-password').value;
	    const confirmPassword = document.getElementById('signup-confirm-password').value;
	    
	    if (password !== confirmPassword) {
	        alert("Passwords do not match.");
	        return;
	    }
	
	    fetch('/Spotted/RegisterServlet', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify({ email, username, password })
	    })
	    .then(response => response.json())
	    .then(data => {
			if(data > 0){
				localStorage.setItem('isLoggedIn', 'true'); 
	            localStorage.setItem('userId', data); 
	            window.location.href = "index1.html"; 
			} else {
				alert(data);
			}
	    })
	    .catch(error => {
	        console.error('Signup error:', error);
	    });
	}
});



