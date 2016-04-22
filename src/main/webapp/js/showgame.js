
function inflateGame(game) {
	var wrapper = document.createElement("div");
	wrapper.setAttribute("id", "wrapper");
	wrapper.setAttribute("class", "wrapper col-sm-11 col-md-5 card");

	var nameDiv = document.createElement("div");
	nameDiv.setAttribute("class", "nameGameForm label-info");
	var name = document.createTextNode(game.name);
	nameDiv.appendChild(name);
	wrapper.appendChild(nameDiv);

	// wrapper.appendChild(document.createElement("br"));

	var descriptionDiv = document.createElement("div");
	descriptionDiv.setAttribute("class", "attr descriptionGameForm");
	descriptionDiv.innerHTML = "<text class='attrName'>Опис: </text>";
	var description = document.createTextNode(game.description);
	descriptionDiv.appendChild(description);
	wrapper.appendChild(descriptionDiv);

	var sportDiv = document.createElement("div");
	sportDiv.setAttribute("class", "attr sportGameForm");
	sportDiv.innerHTML = "<text class='attrName'>Вид спорту: </text>";
	var sport = document.createTextNode(game.sport);
	sportDiv.appendChild(sport);
	wrapper.appendChild(sportDiv);

	var visitorsDiv = document.createElement("div");
	visitorsDiv.setAttribute("class", "attr visitorsGameForm");
	visitorsDiv.innerHTML = "<text class='attrName'>Кількість відвідувачів: </text>";
	var places = game.maxAttendees - game.seatsAvailable;
	var visitors = document.createTextNode(places + "/" + game.maxAttendees);
	visitorsDiv.appendChild(visitors);
	wrapper.appendChild(visitorsDiv);

	var startDateDiv = document.createElement("div");
	startDateDiv.setAttribute("class", "attr startDateGameForm");
	startDateDiv.innerHTML = "<text class='attrName'>Дата початку: </text>";
	var startDate = document.createTextNode(game.startDateStr);
	startDateDiv.appendChild(startDate);
	wrapper.appendChild(startDateDiv);

	var endDateDiv = document.createElement("div");
	endDateDiv.setAttribute("class", "attr endDateGameForm");
	endDateDiv.innerHTML = "<text class='attrName'>Дата завершення: </text>";
	var endDate = document.createTextNode(game.endDateStr);
	endDateDiv.appendChild(endDate);
	wrapper.appendChild(endDateDiv);

	document.body.appendChild(wrapper);
}


function onloadFunc(){
	var myLatLng = {lat: obj.latitude, lng: obj.longitude};
		var thismap = new google.maps.Map(document.getElementById('someid'), {
		    zoom: 14,
		    center: myLatLng
		});

		var marker = new google.maps.Marker({
			position: myLatLng,
			map: thismap,
			title: obj.name
		});
}




