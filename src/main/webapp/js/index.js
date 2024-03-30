
let signedIn = false;
let onBannerless = true;
let validUser = false;

window.onload = function (){	
	compileAllFiles();
	fetchFile('login.html');
	loadPage();
    setUpBanner();
    showBackButton(false);
    if(!getCookie('credits')){
		setCookie('credits', 5, 1);
	}
}

function goBack(){
}

function onQuit(){
	fetchFile('login.html');
	funcLogout();
 	setUpBanner();
 	validUser = false;
}

function onLogin(){
	fetchFile('login.html');
	funcLogout();
	setUpBanner();
}

function onHome(){
	fetchFile('home.html');
	showBackButton(false);
	setUpBanner();
}

function funcLogout(){
	signedIn = false;
	onBannerless = true;
}

function funcLogin(){
	var xhr = new XMLHttpRequest();
	var url = "../Login"; // Servlet URL
	
	// Collect form data
	var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
	var formData = new FormData();
	formData.append("username",username);
	formData.append("password",password);
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.status == 302) {
			//var response = JSON.parse(xhr.responseText);
			//displayResult(response);
			var redirectUrl = xhr.getResponseHeader("Location");
            window.location.href = redirectUrl;
                signedIn = true;
				onBannerless = false;
			//showBackButton(false);
			//setUpBanner();
		}
		if(xhr.status ==200 && xhr.readyState == 4){
			console.log(xhr.responseText);
			if(xhr.responseText.includes("false")){
				validUser= false;
			}
			else{
				validUser = true;
			}
		}
		
	};
	xhr.send();
	//fetchFile('results.html');
	
	return validUser;
}

function funcLoginGuest(){
	signedIn = false;
	onBannerless = false;
}

function funcSignUp() {
	var xhr = new XMLHttpRequest();
	var url = "../SignUp"; // Servlet URL
	
	// Collect form data
	var email = document.getElementById("email").value;
	var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
	var formData = new FormData();
	formData.append("email",email);
	formData.append("username",username);
	formData.append("password",password);
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.status == 302) {
			//var response = JSON.parse(xhr.responseText);
			//displayResult(response);
			var redirectUrl = xhr.getResponseHeader("Location");
            window.location.href = redirectUrl;
                signedIn = true;
				onBannerless = false;
			//showBackButton(false);
			//setUpBanner();
		}
	};
	xhr.send();

  
	signedIn = true;
	onBannerless = false;
}

function setUpBanner(){	
	if(onBannerless){
		document.getElementById("banner").style.display = "none";
	}
	else{
		document.getElementById("banner").style.display = "grid";
		if(signedIn){
			document.getElementById("quita").textContent = "Quit";			
		}
		else{
			document.getElementById("quita").textContent = "Login / Sign Up";
		}
	}
}

function showBackButton(boolean){
	if(boolean){
		document.getElementById("backa").style.display = "block";
	}
	else{
		document.getElementById("backa").style.display = "none";
	}
}

function loadPage() {
}

// Function to set a cookie
function setCookie(name, value, days) {
    const expires = new Date();
    expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/Emotify`;
}

// Function to get a cookie value
function getCookie(name) {
    const cookieArray = document.cookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        const cookiePair = cookieArray[i].split('=');
        if (cookiePair[0].trim() === name) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    return null;
}

function decrementCookie(name) {
    const cookieArray = document.cookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        const cookiePair = cookieArray[i].split('=');
        if (cookiePair[0].trim() === name) {
            let currentValue = parseInt(cookiePair[1]) || 0;
            if (currentValue > 0) {
                currentValue--;
                setCookie(name, currentValue, 1);
            }
        }
    }
}

async function fetchFile(file) {
  try {
    const response = await fetch(file);
    const html = await response.text();

    // Create a temporary container to parse the external content
    const tempContainer = document.createElement('div');
    tempContainer.innerHTML = html;

    // Extract the body content, styles, and scripts from the temporary container
    const externalBody = tempContainer.querySelector('#body');

    // Inject the external body content into the current document's body
    const currentBody = document.getElementById('body');
    currentBody.innerHTML = ''; // Clear existing content
    currentBody.appendChild(externalBody.cloneNode(true));
  } catch (error) {
    console.error('Error loading external content:', error.message);
  }
}



function compileAllFiles(){
	fetchJSCSS('home.html');
	fetchJSCSS('login.html');
	fetchJSCSS('signup.html');
	fetchJSCSS('profile.html');
	fetchJSCSS('create.html');
}

function fetchJSCSS(file){
	fetch(file)
    .then(response => response.text())
    .then(html => {
	  	const tempContainer = document.createElement('div');
	  	tempContainer.innerHTML = html;
	
		const externalStyles = tempContainer.querySelectorAll('link[rel="stylesheet"]');
      	const externalScripts = tempContainer.querySelectorAll('script');

      	externalStyles.forEach(style => document.head.appendChild(style.cloneNode(true)));

		externalScripts.forEach(script => {
        const newScript = document.createElement('script');
        newScript.src = script.src;
        newScript.defer = true; // Assuming you want to defer script execution
        document.body.appendChild(newScript);
      	});
	})
    .catch(error => console.error('Error loading external content:', error.message));
}
