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
	        spotElement.innerHTML = `<hr><h3 onclick="display-Details(event, ${spot})">${spot.name} - ${spot.rating}★</h3>`;
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
	
		async function getImage(imageID) {
		const url = new URL('/Spotted/Image');
	    url.searchParams.append('ImageID', imageID);
	
	    try {
	        const response = await fetch(url);
	        if (response.ok) {
	            return await response.text();
	        } else if (response.status === 302) {
	            throw new Error('Image not found!');
	        } else {
	            throw new Error('An error occurred while fetching the image path');
	        }
	    } catch (error) {
	        console.error('Error:', error.message);
	        return null;
	    }
	}
	
	async function displayDetails(event, spot) {
		event.preventDefault();
		
		// fill the header
		document.getElementById("details-header-name").innerHTML = spot.Name;
		
		// fill picture
		const imagePath = await getImage(spot.ImagesID);
		if(imagePath) {
			document.getElementById("details-pictures").innerHTML = `
				<img src="${imagePath}" alt="Image of ${spot.Name}">
				`;
		}
		
		// set description
		document.getElementById("details-description").innerText = spot.Description;
		
		// fill the specs
		const labelDiv = document.getElementById("details-specs-c1");
		const specsDiv = document.getElementById("details-specs-c2");
		specsDiv.innerHTML = ``;
		for(let spec in labelDiv) {
			// create child
			let paragraph = document.createElement("p");
			paragraph.innerText = spot.specs[`${spec.innerText}`];
			
			// add to specs
			specsDiv.appendChild(paragraph);
		}
		
		const reviews = spot.reviews;
		document.getElementById("details-reviews-data") = reviews;
		for(review in reviews) {
			const review = document.createElement("p");
			review.innerText = review.details;
			reviews.appendChild(review);
		}
		
		// make the page visible
		document.getElementById('profile-container').style='visibility:visible;';
	}
});