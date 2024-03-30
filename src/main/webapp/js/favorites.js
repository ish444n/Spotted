function goBack(){
	fetchFile('home.html');
	showBackButton(false);
}

function remove(item){
	
}

function loadPage() {
    // Number of items to add
    const itemCount = 10;

    // Reference to the scrollable container
    const scrollContainer = document.getElementById('scrollable-favorites');

    // Add items to the container
    for (let i = 0; i < itemCount; i++) {
         const iframe = document.createElement('iframe');
	    iframe.id = 'spotifyFrame';
	    iframe.src = 'https://open.spotify.com/embed/track/3pHIFV3HjCUiHkwL74Xr99?utm_source=generator&theme=0';
	    iframe.width = '100%';
	    iframe.height = '152';
	    iframe.frameBorder = '0';
	    iframe.allowFullscreen = true;
	    iframe.allow = 'autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture';
	    iframe.loading = 'lazy';

      
      scrollContainer.appendChild(item);
  	}
}


