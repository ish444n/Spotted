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
        document.getElementById('save-details').addEventListener('click', function () {
            bookmarkSpot();
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
	
	function bookmarkSpot(LocationID) {
		const userID = localStorage.getItem("userId");
		const spot = fetchStudySpots(document.getElementById("details-header-name").value, "alphabetical")
		
		// Construct the request options
		const requestOptions = {
		  method: 'POST',
		  headers: {
		    'Content-Type': 'application/x-www-form-urlencoded',
		  },
		  body: `userID=${userID}&LocationID=${LocationID}`
		};
		
		// Define the URL to the servlet
		const url = '/Spotted/AddBookmark';
		
		// Make the fetch request to the servlet
		fetch(url, requestOptions)
		  .then(response => {
		    if (!response.ok) {
				alert("Failed to save!");
		      throw new Error('Network response was not ok');
		    }
		    return response.text();
		  })
		  .then(result => {
			alert("Saved Successfully!");
		    console.log('Response from server:', result);
		  })
		  .catch(error => {
		    console.error('Failed to fetch:', error);
		  });

	}

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
	        spotElement.innerHTML = `<hr><h3 id="$listing-{spot.name}">${spot.name} - ${spot.rating}★</h3>`;
	        spotElement.style = 'margin-top:10px;';

	        spotElement.addEventListener('click', function () {
            	displayDetails(spot);
        	});

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

	    const url = `/Spotted/UserProfile?userId=${encodeURIComponent(userId)}`;

	    fetch(url)
	    .then(response => {
	        if (!response.ok) {
	            throw new Error('Failed to fetch user profile. Status: ' + response.status);
	        }
	        return response.json();
	    })
	    .then(userProfile => {
	        document.getElementById("profile-name").textContent='Hi, '+userProfile.username + '!';
	        let bookDiv = document.getElementById("profile-bookmarks");
	        for(spot in userProfile.bookmarkedSpots){
				let temp = document.createElement("div");
				temp.appendChild(document.createElement("hr"));
				let bookbody = document.createElement("p");
				bookbody.onclick=displayDetails(event, spot);
				temp.appendChild(bookbody);
				temp.appendChild(document.createElement("hr"));
				bookDiv.appendChild(temp);
			}

	    })
	    .catch(error => {
	        console.error('Error fetching user profile:', error);
	    });

	}

	async function getImage(imageID) {
		const url = new URL('http://localhost:8080/Spotted/Image');
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

	async function displayDetails(spot) {
		console.log(spot);
		// fill the header
		document.getElementById("details-header-name").innerHTML = spot.Name;

		document.getElementById("details-header-name").innerHTML = spot.name;
		
		// fill picture
		const imagePath = await getImage(spot.ImagesID);
		if(imagePath) {
			document.getElementById("details-pictures").innerHTML = `
				<img src="${imagePath}" alt="Image of ${spot.Name}">
				`;
		}

		// set description
		document.getElementById("details-description").innerText = spot.Description;

		document.getElementById("details-description").innerText = spot.description;
		
		// fill the specs
		const labelDiv = document.getElementById("details-specs-c1");
		const specsDiv = document.getElementById("details-specs-c2");
		const specsJson = spot.specs;
		
		specsDiv.innerHTML = ``;
		
		for(let spec in specsJson) {
			// create child
			let paragraph = document.createElement("p");
			paragraph.innerText = spot.specs[`${spec.innerText}`];

			
			if(specsJson[spec] === "true") {
				paragraph.innerText = "Yes";
			} else if (specsJson[spec] === "false") {
				paragraph.innerText = "No";
			} else {
				paragraph.innerText = specsJson[spec];
			}

			// add to specs
			specsDiv.appendChild(paragraph);
		}

		const reviewsJson = spot.reviews;
		const reviews = document.getElementById("details-reviews-data");
		for(let review in reviewsJson) {
			const reviewP = document.createElement("p");
			reviewP.innerText = review.details;
			reviews.appendChild(review);
		}
		
		document.getElementById('save-details').addEventListener('click', function () {
            bookmarkSpot(spot.LocationID);
        });

		// make the page visible
		document.getElementById('details-container').style='visibility:visible;';
		
	}
});