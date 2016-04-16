
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
google.devrel.samples.hello.CLIENT_ID =
    '1001315827289-em9jhbuphg99hnk98un4heo3ffhdi83m.apps.googleusercontent.com';

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
  
  document.getElementById('getUserFromDatastore').onclick = function() {
	  google.devrel.samples.hello.getMeFromDatastore();
  }
  document.getElementById('sendFormButton').onclick = function() {
	  createGame();
  }
  document.getElementById('getGamesQueryButton').onclick = function() {
	 getGamesQueryButton();
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
	alert("End Date: "+endDate+"\n"+"Start Date: "+startDate);
	var request =  gapi.client.endpoints.createGame({'name': name, 'description':description, 'maxAttendees':attendees,
		'seatsAvailable':attendees, 'startDateStr':startDate, 'endDateStr':endDate, 'latitude':0, 'longitude':0, 'sport':sport});
	request.execute(alertInfo);
}



getGamesQueryButton = function(){
	var request = gapi.client.endpoints.getAllGames().execute(
		function(resp) {
			if (!resp.code) {
				resp.items = resp.items || [];
		        for (var i = 0; i < resp.items.length; i++) {
		        	print(resp.items[i]);
		        }
		        alert("END! Items: "+ resp.items.length);
		    }
			else{
				alert("Smth went wrong! resp.code: "+resp.code);
			}
		}
	);
}
print = function(game) {
	  var node = document.createElement("div");
	  var places=game.maxAttendees-game.seatsAvailable;
	  var textnode = document.createTextNode("Game name: "+game.name+"\n"+"Sport: "+game.sport+"\n"+"Game description: "+game.description+"\n"+
			  "Start date: "+game.startDateStr+"\n"+"End date: "+game.endDateStr+"\n"
			  +"Visitors: "+places+"/"+game.maxAttendees);
	  node.appendChild(textnode);                             
	  document.getElementById("gameNode").appendChild(node);
};

google.devrel.samples.hello.getUserInfo = function() {
	var request =  gapi.client.endpoints.getMyInfo();
	request.execute(alertInfo);
}
google.devrel.samples.hello.getMeFromDatastore = function() {
	var request =  gapi.client.endpoints.getMeFromDatastore();
	request.execute(alertInfo);
}
function alertInfo (response) {
	alert(response.message);	
}
