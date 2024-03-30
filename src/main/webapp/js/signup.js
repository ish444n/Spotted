
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

function asGuest() {
	funcLoginGuest();
	fetchFile('home.html');
	showBackButton(false);
    setUpBanner();
}
