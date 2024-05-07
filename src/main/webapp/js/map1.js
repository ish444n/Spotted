let map;
let openInfoWindow = null; // currently open InfoWindow
let currLat;
let currLong;


async function initMap() {
  const position = { lat: 34.022, lng: -118.285 };
  //@ts-ignore
  const { Map } = await google.maps.importLibrary("maps");

    map = new Map(document.getElementById("map"), {
    	zoom: 17,
    	center: position,
	});
	map.setOptions({ styles: styles});
	
    map.addListener('click', function(e) {
	    if (openInfoWindow) {
	        openInfoWindow.close();
	    }
	    var lat = e.latLng.lat();
	    var lng = e.latLng.lng();
	    console.log('Latitude: ' + lat + ', Longitude: ' + lng);
	
	    var infoWindow = new google.maps.InfoWindow({
	        content: `<p onclick='createStudySpot(${lat},${lng})'>Add Study Spot</p>`
	    });
	    infoWindow.setPosition(e.latLng);
	    infoWindow.open(map);
	    openInfoWindow = infoWindow;

	});
}

function createStudySpot(lat, long){
	document.getElementById('create-container').style = 'visibility: visible;';
	currLat= lat;
	currLong = long;
}

function submitCreate(event) {
	event.preventDefault();
	
	const form = document.getElementById('create-form');
    const formData = new FormData(form); 

    appendCheckboxToFormData(formData, 'waterFountains');
    appendCheckboxToFormData(formData, 'restrooms');
    appendCheckboxToFormData(formData, 'microwaves');
    appendCheckboxToFormData(formData, 'refrigerators');
    appendCheckboxToFormData(formData, 'outlets');
    appendCheckboxToFormData(formData, 'ac');
    appendCheckboxToFormData(formData, 'wifi');
    
    console.log(formData);

    fetch('upload', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        console.log('Image upload successful:', data);
    })
    .catch(error => {
        console.error('Error uploading image:', error);
    });
    
    
    //another fetch that sends form data to add spot servlet


}

// helper function
function appendCheckboxToFormData(formData, checkboxName) {
	const checkbox = document.getElementById('create-' + checkboxName);
    formData.append(checkboxName, checkbox.checked ? 'yes' : 'no');
}
  
  
const styles = [
	  {
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#ebe3cd"
	      }
	    ]
	  },
	  {
	    "elementType": "geometry.fill",
	    "stylers": [
	      {
	        "color": "#d2c17f"
	      },
	      {
	        "weight": 1.5
	      }
	    ]
	  },
	  {
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#523735"
	      }
	    ]
	  },
	  {
	    "elementType": "labels.text.stroke",
	    "stylers": [
	      {
	        "color": "#f5f1e6"
	      }
	    ]
	  },
	  {
	    "featureType": "administrative",
	    "elementType": "geometry.stroke",
	    "stylers": [
	      {
	        "color": "#c9b2a6"
	      }
	    ]
	  },
	  {
	    "featureType": "administrative.land_parcel",
	    "elementType": "geometry.stroke",
	    "stylers": [
	      {
	        "color": "#dcd2be"
	      }
	    ]
	  },
	  {
	    "featureType": "administrative.land_parcel",
	    "elementType": "labels",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "administrative.land_parcel",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#ae9e90"
	      }
	    ]
	  },
	  {
	    "featureType": "landscape",
	    "elementType": "geometry.fill",
	    "stylers": [
	      {
	        "color": "#bf271d"
	      }
	    ]
	  },
	  {
	    "featureType": "landscape.natural",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#dfd2ae"
	      }
	    ]
	  },
	  {
	    "featureType": "poi",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#dfd2ae"
	      }
	    ]
	  },
	  {
	    "featureType": "poi",
	    "elementType": "labels",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "poi",
	    "elementType": "labels.text",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "poi",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#93817c"
	      }
	    ]
	  },
	  {
	    "featureType": "poi.business",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "poi.park",
	    "elementType": "geometry.fill",
	    "stylers": [
	      {
	        "color": "#a5b076"
	      }
	    ]
	  },
	  {
	    "featureType": "poi.park",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#447530"
	      }
	    ]
	  },
	  {
	    "featureType": "poi.school",
	    "elementType": "labels",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "road",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#f5f1e6"
	      }
	    ]
	  },
	  {
	    "featureType": "road",
	    "elementType": "labels.icon",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "road.arterial",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#fdfcf8"
	      }
	    ]
	  },
	  {
	    "featureType": "road.highway",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#f8c967"
	      }
	    ]
	  },
	  {
	    "featureType": "road.highway",
	    "elementType": "geometry.stroke",
	    "stylers": [
	      {
	        "color": "#e9bc62"
	      }
	    ]
	  },
	  {
	    "featureType": "road.highway.controlled_access",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#e98d58"
	      }
	    ]
	  },
	  {
	    "featureType": "road.highway.controlled_access",
	    "elementType": "geometry.stroke",
	    "stylers": [
	      {
	        "color": "#db8555"
	      }
	    ]
	  },
	  {
	    "featureType": "road.local",
	    "elementType": "labels",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "road.local",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#806b63"
	      }
	    ]
	  },
	  {
	    "featureType": "transit",
	    "stylers": [
	      {
	        "visibility": "off"
	      }
	    ]
	  },
	  {
	    "featureType": "transit.line",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#dfd2ae"
	      }
	    ]
	  },
	  {
	    "featureType": "transit.line",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#8f7d77"
	      }
	    ]
	  },
	  {
	    "featureType": "transit.line",
	    "elementType": "labels.text.stroke",
	    "stylers": [
	      {
	        "color": "#ebe3cd"
	      }
	    ]
	  },
	  {
	    "featureType": "transit.station",
	    "elementType": "geometry",
	    "stylers": [
	      {
	        "color": "#dfd2ae"
	      }
	    ]
	  },
	  {
	    "featureType": "water",
	    "elementType": "geometry.fill",
	    "stylers": [
	      {
	        "color": "#b9d3c2"
	      }
	    ]
	  },
	  {
	    "featureType": "water",
	    "elementType": "labels.text.fill",
	    "stylers": [
	      {
	        "color": "#92998d"
	      }
	    ]
	  },
	];


window.initMap = initMap;

initMap();