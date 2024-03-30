function submitSearchForm(event) {
	
	event.preventDefault();
	var xhr = new XMLHttpRequest();
	var url = "../Api"; // Servlet URL
	
	// Collect form data
	var formData = new FormData(document.getElementById('Form'));
	
	// Build the query string
	var queryString = new URLSearchParams(formData).toString();
	
	xhr.open("POST", url + "?" +  queryString, true);
		
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		console.log(xhr.status);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var response = JSON.parse(xhr.responseText);
			displayResult(response, queryString);
		}
	};
	xhr.send();
	
	//fetchFile('results.html');

}
function displayResult(data, prompt) {
	
	document.getElementById("createForm").style.display="none";
	document.getElementById("you-searched").style.display = "flex";
	document.getElementById("output").style.display="flex";
	document.getElementById("ys-prompt").textContent =prompt.substring(7);
            
    const scrollContainer = document.getElementById('output');

    // Add items to the container
    for (let i = 0; i < data.songids.length; i++) {
         const iframe = document.createElement('iframe');
	    iframe.id = 'spotifyFrame';
	    iframe.src = 'https://open.spotify.com/embed/track/' + data.songids[i] + '?utm_source=generator&theme=0';
	    iframe.width = '100%';
	    iframe.allowFullscreen = true;
	    iframe.allow = 'autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture';
	    iframe.loading = 'eager';
	    iframe.style.border = 'none';

      
      scrollContainer.appendChild(iframe);
  	}
}

function goBack(){
	fetchFile('home.html');
}

function updateClickCount() {
	    const clickButton = document.getElementById('searchbtn');
	    const counterElement = document.getElementById('create-message');
	
	    let remainingCredits = parseInt(getCookie('credits')) || 0;
		if(!signedIn){
			counterElement.textContent = `You have ${remainingCredits} credits. Please sign up!`;
		}
		else{
			counterElement.textContent = `Thank you for signing up!`;
		}
		
	    clickButton.addEventListener('click', decrement);
}


function decrement() {
	let remainingCredits = parseInt(getCookie('credits')) || 0;
	if(!signedIn && remainingCredits>0)
	{
    	const counterElement = document.getElementById('create-message');
		let remainingCredits = parseInt(getCookie('credits')) || 0;
	    counterElement.textContent = `You have ${remainingCredits} credits. Please sign up!`;
	    decrementCookie('credits');
	}
}


function loadPage(){
	if(signedIn){
		document.getElementById("create-message").textContent = "Thank you for signing up!";
	}
	else {
		let remainingCredits = parseInt(getCookie('credits')) || 0;
		document.getElementById("create-message").textContent = "You have "+remainingCredits+" credits left. Please sign up!";
		if(remainingCredits < 1)
		{
			document.getElementById("searchbtn").onclick="";
			document.getElementById("searchbtn").style.backgroundColor="grey";
		}
	}
	updateClickCount();
}