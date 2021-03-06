
/**
 * @fileoverview
 * Provides methods for the Hello Endpoints sample UI and interaction with the
 * Hello Endpoints API.
 *
 * @author danielholevoet@google.com (Dan Holevoet)
 */

/** google global namespace for Google projects. */
var google = google || {};

/** devrel namespace for Google Developer Relations projects. */
google.devrel = google.devrel || {};

/** samples namespace for DevRel sample code. */
google.devrel.samples = google.devrel.samples || {};

/** hello namespace for this sample. */
google.devrel.samples.hello = google.devrel.samples.hello || {};

/**
 * Client ID of the application (from the APIs Console).
 * @type {string}
 */
google.devrel.samples.hello.CLIENT_ID ='1001315827289-em9jhbuphg99hnk98un4heo3ffhdi83m.apps.googleusercontent.com';



/**
 * Scopes used by the application.
 * @type {string}
 */
google.devrel.samples.hello.SCOPES =
    'https://www.googleapis.com/auth/userinfo.email';

/**
 * Whether or not the user is signed in.
 * @type {boolean}
 */
google.devrel.samples.hello.signedIn = false;

/**
 * Loads the application UI after the user has completed auth.
 */
google.devrel.samples.hello.userAuthed = function() {
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
    if (!resp.code) {
      google.devrel.samples.hello.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
      document.getElementById('userInfoButton').disabled = false;
    }
  });
};

/**
 * Handles the auth flow, with the given value for immediate mode.
 * @param {boolean} mode Whether or not to use immediate mode.
 * @param {Function} callback Callback to call on completion.
 */
google.devrel.samples.hello.signin = function(mode, callback) {
  gapi.auth.authorize({client_id: google.devrel.samples.hello.CLIENT_ID,
      scope: google.devrel.samples.hello.SCOPES, immediate: mode},
      callback);
};

/**
 * Presents the user with the authorization popup.
 */
google.devrel.samples.hello.auth = function() {
  if (!google.devrel.samples.hello.signedIn) {
    google.devrel.samples.hello.signin(false,
        google.devrel.samples.hello.userAuthed);
  } else {
    google.devrel.samples.hello.signedIn = false;
    document.getElementById('signinButton').innerHTML = 'Sign in';
    document.getElementById('userInfoButton').disabled = true;
  }
};





	





/**
 * Enables the button callbacks in the UI.
 */
google.devrel.samples.hello.enableButtons = function() {
    document.getElementById('signinButton').onclick = function() {
    google.devrel.samples.hello.auth();
    gapi.client.endpoints.aveProfile().execute();
  }
  document.getElementById('userInfoButton').onclick = function() {
	  google.devrel.samples.hello.getUserInfo();
  }
  

  document.getElementById('sendFormButton').onclick = function() {
	  createGame();
  }
  document.getElementById('getGamesQueryButton').onclick = function() {
	 getGamesQueryButton();
  }  
  document.getElementById('football').onclick = function() {
		 getFootballGamesQueryButton();
  } 
  document.getElementById('basketball').onclick = function() {
		 getBasketballGamesQueryButton();
  } 
  document.getElementById('volleyball').onclick = function() {
		 getVolleyballGamesQueryButton();
  } 
  document.getElementById('tennis').onclick = function() {
		 getTennisGamesQueryButton();
  } 
  document.getElementById('hockey').onclick = function() {
		 getHockeyGamesQueryButton();
  } 
};

/**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
 */
google.devrel.samples.hello.init = function(apiRoot) {
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {
      google.devrel.samples.hello.enableButtons();
      google.devrel.samples.hello.signin(true,
      google.devrel.samples.hello.userAuthed);
      getGamesQueryButton();
    }
  }

  apisToLoad = 2; // must match number of calls to gapi.client.load()
  gapi.client.load('endpoints', 'v1', callback, apiRoot);
  gapi.client.load('oauth2', 'v2', callback);
};

createGame = function() {
	var name =document.getElementById("name").value;
	var description =document.getElementById("description").value;
    var sport=document.getElementById("sport").value;
	var startDate =$("#datetimepicker6").find("input").val();
    var endDate =$("#datetimepicker7").find("input").val();
	var attendees =document.getElementById("attendees").value;
	var marker = getMarker();
	alert(marker);
	var request =  gapi.client.endpoints.createGame({'name': name, 'description':description, 'maxAttendees':attendees,
		'seatsAvailable':attendees, 'startDateStr':startDate, 'endDateStr':endDate, 'marker':marker, 'sport':sport});
	request.execute(alertInfo);
}

getVolleyballGamesQueryButton= function(){
	var request = gapi.client.endpoints.getVolleyballGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
getTennisGamesQueryButton= function(){
	var request = gapi.client.endpoints.getTennisGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
getHockeyGamesQueryButton= function(){
	var request = gapi.client.endpoints.getHockeyGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}

getBasketballGamesQueryButton= function(){
	var request = gapi.client.endpoints.getBasketballGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
getBasketballGamesQueryButton= function(){
	var request = gapi.client.endpoints.getBasketballGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
getFootballGamesQueryButton= function(){
	var request = gapi.client.endpoints.getFootballGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
deleteCards = function(){
	var block = document.getElementById("gameNode");
	for(var i=0; i<currentlast; i++){
			block.removeChild(document.getElementById("number"+i));
	}
	if(block.hasChildNodes()){
		block.removeChild(document.getElementById("nextPage"));
	}
}

getGamesQueryButton= function(){
	var request = gapi.client.endpoints.getAllGames().execute(
		
		function(resp) {
			if (!resp.code) {
				page=1;
				list = resp.items || [];
				deleteCards();
				paging(list, page);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}


var list;
var page;
var currentlast;


paging = function(list, page){
	if(button!=null){
		var button = document.getElementById("nextPage");
		button.parentNode.removeNode(button);
	}
	var last = 5*page;
	var first = last-5;
	if(last>list.length) last=list.length;
	if(list.length>=first){
		for(var i=first; i<last; i++ ){
			print(list[i], i);
		}
	}
	printMarkers(gamesArray);
	currentlast=last;
	if(list.length>currentlast) printNextButton();
}


var gamesArray = new Array();
function addListeners(marker){
google.maps.event.addListener(marker, 'click', function() {
	document.location.href = marker.url;
});
}
function printMarkers(gamesArray){
	mymap= new google.maps.Map(document.getElementById('googleMapAll'), {
		 zoom: 12,
		 center:{lat: 50.46655, lng: 30.5181692}
	});
	for(var i=0; i<gamesArray.length; i++){
	
		var gameStringifiedAndEncoded = gamesArray[i]; 
		var gameStringified = decodeURIComponent(gameStringifiedAndEncoded);
		var game = JSON.parse(gameStringified);
		
		var myMarker = new google.maps.Marker({
			position: {lat: game.latitude, lng: game.longitude},
			map: mymap,
			title: game.name,
			url:"https://findteamtest.appspot.com/showgame.html?"+gameStringifiedAndEncoded
		});
		addListeners(myMarker);
	}
}

print = function(game, number){
	 var obj = {
		 id: game.id,
         name: game.name,
         sport:game.sport,
         description: game.description,
         startDateStr: game.startDateStr,
         endDateStr: game.endDateStr,
         latitude: game.latitude,
         longitude: game.longitude,
         seatsAvailable: game.seatsAvailable,
         maxAttendees: game.maxAttendees
     },
     objStringified = JSON.stringify(obj), 
     objStringifiedAndEncoded = encodeURIComponent(objStringified);
	
	gamesArray.push(objStringifiedAndEncoded);
	
	
	var wrapperNode = document.createElement("div");
	wrapperNode.setAttribute("class", "flip-card active-card");
	wrapperNode.setAttribute("id", "number"+number);
	//створення заголовку
	var gameNameNode = document.createElement("div");
	gameNameNode.setAttribute("class", "card label-info");
	//створення наповнення заголовку
	var header = document.createElement("text");
	header.setAttribute("class", "cardGameName");
	var name = document.createTextNode(game.name);
	header.appendChild(name);
	//власне заповнення заголовку іосновної ноди заголовком
	gameNameNode.appendChild(header);


	//лінк в нєкуда update в сторінку гри
	var action = document.createElement("a");
	var gameLink="https://findteamtest.appspot.com/showgame.html?"+objStringifiedAndEncoded;
	action.setAttribute("href", gameLink);
	action.setAttribute("class", "button button-linkbutton button-linkbutton-shadow");
	action.setAttribute("id", "card");
	var play= document.createElement("i");
	play.setAttribute("class","material-icons");
	
	action.appendChild(play)
	//опис
	var gameBodyNode = document.createElement("div");
	gameBodyNode.setAttribute("class", "well");

	var header = document.createElement("text");
	header.setAttribute("class", "cardGameDiscription");

	
	var descriptionDiv = document.createElement("div");
	descriptionDiv.setAttribute("class", "attr descriptionGameForm");
	descriptionDiv.innerHTML="<text class='attrName'>Опис: </text>";
	var description = document.createTextNode(game.description);
	descriptionDiv.appendChild(description);
	header.appendChild(descriptionDiv);
	
	var sportDiv = document.createElement("div");
	sportDiv.setAttribute("class", "attr sportGameForm");
	sportDiv.innerHTML="<text class='attrName'>Вид спорту: </text>";
	var sport = document.createTextNode(game.sport);
	sportDiv.appendChild(sport);
	header.appendChild(sportDiv);
	
	var visitorsDiv = document.createElement("div");
	visitorsDiv.setAttribute("class", "attr visitorsGameForm");
	visitorsDiv.innerHTML="<text class='attrName'>Кількість відвідувачів: </text>";
	var places = game.maxAttendees-game.seatsAvailable;
	var visitors = document.createTextNode(places+"/"+game.maxAttendees);
	visitorsDiv.appendChild(visitors);
	header.appendChild(visitorsDiv);
	
	var startDateDiv = document.createElement("div");
	startDateDiv.setAttribute("class", "attr startDateGameForm");
	startDateDiv.innerHTML="<text class='attrName'>Дата початку: </text>";
	var startDate = document.createTextNode(game.startDateStr);
	startDateDiv.appendChild(startDate);
	header.appendChild(startDateDiv);
	
	var endDateDiv = document.createElement("div");
	endDateDiv.setAttribute("class", "attr endDateGameForm");
	endDateDiv.innerHTML="<text class='attrName'>Дата завершення: </text>";
	var endDate = document.createTextNode(game.endDateStr);
	endDateDiv.appendChild(endDate);
	header.appendChild(endDateDiv);
	
	gameBodyNode.appendChild(header);

	wrapperNode.appendChild(gameNameNode);
	wrapperNode.appendChild(action); 
	wrapperNode.appendChild(gameBodyNode);


	document.getElementById("gameNode").appendChild(wrapperNode);
}

printNextButton = function(){
	var button = document.createElement("a");
	button.setAttribute("id", "nextPage");
	button.setAttribute("class", "btn btn-default btn-lg");
	button.setAttribute("href", "javascript:void(0)");
	var text = document.createTextNode("Більше");
	button.appendChild(text);
	document.getElementById("gameNode").appendChild(button);
	button.onclick=function(){
		page++;
		paging(list, page);
	};
}


google.devrel.samples.hello.getMeFromDatastore = function() {
	var request =  gapi.client.endpoints.getMeFromDatastore();
	request.execute(alertInfo);
}
function alertInfo (response) {
	alert(response.message);	
}

registerForGame = function(obj) {
	 	
	 	var request = gapi.client.endpoints.registerForGame({
	 		'id' : obj
	 	});
	 	request.execute(alertInfo);
	  }
	  
unregisterFromGame = function(obj) {
 	 	var id = obj.id;
	 	var request = gapi.client.endpoints.unregisterFromGame({
	 		'id' : id
	 	});
	 	request.execute(alertInfo);
}