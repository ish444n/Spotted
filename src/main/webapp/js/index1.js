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
    
    document.getElementById('close-details').addEventListener('click', function () {
		document.getElementById('details-container').style='visibility:hidden;';
    });
    
    document.getElementById('search-field').addEventListener('input', function (e) {
    	const query = e.target.value;
    	fetchStudySpots(query);
	});
	
	document.getElementById('sort-dropdown').addEventListener('change', function (e) {
	    const sortBy = e.target.value;
	    fetchStudySpots(document.getElementById('search-field').value, sortBy);
	});
	
	function fetchStudySpots(query, sortBy = 'alphabetical') {
	    const params = new URLSearchParams({ query, sortBy });
	    fetch('/studyspots?' + params.toString())
	        .then((response) => response.json())
	        .then((data) => {
	            updateStudySpotResults(data);
	        })
	        .catch((error) => console.error('Error fetching study spots:', error));
	}
	
	function updateStudySpotResults(studySpots) {
	    const container = document.getElementById('study-spot-results');
	    container.innerHTML = ''; // clear previous results
	
	    studySpots.forEach((spot) => {
	        const spotElement = document.createElement('div');
	        spotElement.innerHTML = `<hr><h3>${spot.name} - ${spot.rating}★</h3>`;
	        spotElement.style = 'margin-top:10px;';
	        
	        container.appendChild(spotElement);
	    });
	}

});


