/**
 * Created by ostap on 03.04.2016.
 */
function initialize() {
    var mapProp = {
        center: new google.maps.LatLng(50.464379, 30.519131),
        zoom: 11
    };
    var html_element = document.getElementById("googleMap");
    var map = new google.maps.Map(html_element, mapProp);


    var point = new google.maps.LatLng(50.464379, 30.519131);
    var marker = new google.maps.Marker({
        position: point,
        map: map,
        draggable: true,
        title: "KMA"
    });


    google.maps.event.addListener(map, 'click', function (me) {
        var coordinates = me.latLng;
        geocodeLatLng(coordinates, function (err, adress) {
            if (!err) {
                console.log(adress);
            } else {
                console.log("There is no adress")
            }
        })
    });

    /*streetView*/
    /*var panorama = new google.maps.StreetViewPanorama(
        document.getElementById('pip-pano'), {
            position: point,
            pov: {
                heading: 200,
                pitch: 5
            }
        });
    map.setStreetView(panorama);*/

    var geocoder = new google.maps.Geocoder();

    document.getElementById('submit').addEventListener('click', function () {
        geocodeAddress(geocoder, map);
    });

    map.addListener('click', function(e) {
        placeMarkerAndPanTo(e.latLng, map);
    });

}


///////////////////////////////////////////////

function geocodeLatLng(latlng, callback){
    //Модуль за роботу з адресою
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({'location': latlng}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK && results[1]) {
            var adress = results[1].formatted_address;
            callback(null, adress);
        } else {
            callback(new Error("Can't find adress"));
        }
    });
}

function geocodeAddress(geocoder, resultsMap) {
    var address = document.getElementById('address').value;
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: resultsMap,
                position: results[0].geometry.location
                //title: results[0].geometry.location.description

            });
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}



function calculateRoute(A_latlng, B_latlng, callback) {
    var directionService = new google.maps.DirectionsService();
    directionService.route({
        origin: A_latlng,
        destination: B_latlng,
        travelMode: google.maps.TravelMode["DRIVING"]
    }, function(response, status) {
        if ( status == google.maps.DirectionsStatus.OK ) {
            var leg = response.routes[ 0 ].legs[ 0 ];
            callback(null, {
                duration: leg.duration
            });
        } else {
            callback(new Error("Can' not find direction"));
        }
    });
}

function placeMarkerAndPanTo(latLng, map) {
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        position: latLng,
        map: map
    });
    map.panTo(latLng);


    marker.addListener('click', function() {
        infowindow.setContent(marker.position.toString());
        infowindow.open(map, marker);

    });
}

google.maps.event.addDomListener(window, 'load', initialize);




