document.addEventListener('DOMContentLoaded', function () {
	const navbar = document.getElementById('navs');
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

    if (isLoggedIn) {
        navbar.innerHTML = `
            <a href="index1.html" id = "logout">Logout</a>
        `;
        document.getElementById('logout').addEventListener('click', function () {
            localStorage.clear();
            window.location.href = "index1.html";
        });
    } else {
        navbar.innerHTML = `
            <a href="login1.html">Login / Sign Up</a>
        `;
    }
});


