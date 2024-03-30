function signOutFromProfile(){
	funcLogout();
	fetchFile('login.html');
	setUpBanner();
}

function goBack(){
	fetchFile('home.html');
	showBackButton(false);
}

function getCookie(cookieName) {
	const name = cookieName + "=";
	const decodedCookie = decodeURIComponent(document.cookie);
	const cookieArray = decodedCookie.split(';');

	for (let i = 0; i < cookieArray.length; i++) {
		let cookie = cookieArray[i].trim();
		if (cookie.indexOf(name) === 0) {
			return cookie.substring(name.length, cookie.length);
		}
	}
	return "";
}

		
function displayUserData() {
	const usernameValue = getCookie("username");
	const emailValue = getCookie("email");
	document.querySelector('.username').textContent += usernameValue;
	document.querySelector('.email').textContent += emailValue;
}

window.onload = displayUserData;
