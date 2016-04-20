function inflateGame (game) {
	var wrapper = document.createElement("div");
	wrapper.setAttribute("id", "wrapper");
	wrapper.setAttribute("class", "wrapper");
	
	var nameDiv = document.createElement("div");
	nameDiv.setAttribute("class", "nameGameForm");
	var name = document.createTextNode(game.name);
	nameDiv.appendChild(name);
	wrapper.appendChild(nameDiv);
	
	//wrapper.appendChild(document.createElement("br"));
	
	var descriptionDiv = document.createElement("div");
	descriptionDiv.setAttribute("class", "descriptionGameForm");
	var description = document.createTextNode(game.name);
	descriptionDiv.appendChild(description);
	wrapper.appendChild(descriptionDiv);
	
	var sportDiv = document.createElement("div");
	sportDiv.setAttribute("class", "sportGameForm");
	var sport = document.createTextNode(game.sport);
	sportDiv.appendChild(sport);
	wrapper.appendChild(sportDiv);
	
	var visitorsDiv = document.createElement("div");
	visitorsDiv.setAttribute("class", "visitorsGameForm");
	var places = game.maxAttendees-game.seatsAvailable;
	var visitors = document.createTextNode(places+"/"+game.maxAttendees);
	visitorsDiv.appendChild(visitors);
	wrapper.appendChild(visitorsDiv);
	
	var startDateDiv = document.createElement("div");
	startDateDiv.setAttribute("class", "startDateGameForm");
	var startDate = document.createTextNode(game.startDateStr);
	startDateDiv.appendChild(startDate);
	wrapper.appendChild(startDateDiv);
	
	var endDateDiv = document.createElement("div");
	endDateDiv.setAttribute("class", "endDateGameForm");
	var endDate = document.createTextNode(game.endDateStr);
	endDateDiv.appendChild(endDate);
	wrapper.appendChild(endDateDiv);
	
	
	
	document.body.appendChild(wrapper);
}