document.addEventListener('DOMContentLoaded', function () {
	const navbar = document.getElementById('navs');
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

    if (isLoggedIn) {
        navbar.innerHTML = `
            <a id = "profile" >Profile</a> 
            <a href="index1.html" id = "logout">Logout</a>
        `;
        document.getElementById('logout').addEventListener('click', function () {
            localStorage.clear();
            window.location.href = "index1.html";
        });
        document.getElementById('profile').addEventListener('click', function () {
            showProfile();
        });
        
    } else {
        navbar.innerHTML = `
            <a href="login1.html">Login / Sign Up</a>
        `;
    }
    
    document.getElementById('close-details').addEventListener('click', function () {
		document.getElementById('details-container').style='visibility:hidden;';
    });
    document.getElementById('close-create').addEventListener('click', function () {
		document.getElementById('create-container').style='visibility:hidden;';
    });
    document.getElementById('close-profile').addEventListener('click', function () {
		document.getElementById('profile-container').style='visibility:hidden;';
    });
    
    document.getElementById('search-field').addEventListener('input', function (e) {
    	const query = e.target.value;
    	const sortBy = document.getElementById('sort-dropdown').value;
    	fetchStudySpots(query, sortBy);
	});
	
	document.getElementById('sort-dropdown').addEventListener('change', function (e) {
	    const sortBy = e.target.value;
	    fetchStudySpots(document.getElementById('search-field').value, sortBy);
	});
	
	// fetches the list of all study spots with a given name, sorted by newest or alphabetical
	function fetchStudySpots(query, sortBy = 'alphabetical') {
	    const params = new URLSearchParams({ query, sortBy });
	    fetch('/Spotted/Search?' + params.toString())
	        .then(response => {
            // check if the server responded with an error
            if (!response.ok) {
                throw new Error(`HTTP error ${response.status}: ${response.statusText}`);
            }
            
            // convert the response to json
            return response.json();
	        })
	        .then(data => {
	            // handle the parsed data
	            updateStudySpotResults(data);
	        })
	        .catch(error => {
	            console.error('Error fetching study spots:', error);
	        });
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
	
	function showProfile(){
		document.getElementById('profile-container').style='visibility: visible;';
		    const userId = localStorage.getItem('userId');
    
	    if (!userId) {
	        console.error('No user ID found in local storage.');
	        return;
	    }
	
	    // Construct the URL with the userId query parameter
	    const url = `/Spotted/UserProfile?userId=${encodeURIComponent(userId)}`;
	
	    // Fetch the user profile data
	    fetch(url)
	    .then(response => {
	        if (!response.ok) {
	            throw new Error('Failed to fetch user profile. Status: ' + response.status);
	        }
	        return response.json(); // Parse JSON data from response
	    })
	    .then(userProfile => {
	        console.log('User Profile:', userProfile);
	        // Perform actions with the user profile data
	    })
	    .catch(error => {
	        console.error('Error fetching user profile:', error);
	    });

	}
	
	
});


