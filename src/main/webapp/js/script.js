/**
 * Created by ostap on 16.04.2016.
 */
function initMap() {
    var map = new google.maps.Map(document.getElementById('googleMap'), {
        center: {lat: 50.464379, lng: 30.519131},
        zoom: 13
    });

    ////GEOLOCATING
    var input = document.getElementById('pac-input');

    var autocomplete = new google.maps.places.Autocomplete(input);
    autocomplete.bindTo('bounds', map);

    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        map: map
    });
    marker.addListener('click', function() {
        infowindow.open(map, marker);
    });

    autocomplete.addListener('place_changed', function() {
        infowindow.close();
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            return;
        }

        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }


        marker.setPlace({
            placeId: place.place_id,
            location: place.geometry.location
        });
        marker.setVisible(true);

        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' +
            '<br>' +
            place.formatted_address);
        infowindow.open(map, marker);
    });


    ///MARKER
    map.addListener('click', function(e) {
        placeMarkerAndPanTo(e.latLng, map);

    });
}




////

var marker;
var markersArray = [];
function placeMarkerAndPanTo(latLng, map) {

    var infowindow = new google.maps.InfoWindow();

    if (marker && marker.setMap) {
        marker.setMap(null);
    }

    marker=new google.maps.Marker({
        position: latLng,
        map: map
    });

    map.panTo(latLng);
    markersArray.push(marker);
    marker.addListener('click', function() {
        infowindow.setContent(marker.position.toString());
        infowindow.open(map, marker);

    });


    markersArray.push(marker);

}

//ФУНКЦІЯ, ЩО ВИДАЛЯЄ ВСІ МАРКЕРИ
function clear() {
    markersArray.forEach(function (marker) {
        marker.setMap(null);
    });
    markersArray = [];
}

function getMarkersLat(){
    return  marker.position.lat;
}
function getMarkersLng(){
    return  marker.position.lng;
}