package findteam;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

import domain.Game;
import domain.Profile;
import form.GameForm;
import form.ProfileForm;

import static service.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(name = "endpoints", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class Endpoints {

	@ApiMethod(name = "getMyInfo", path = "getMyInfo", httpMethod = HttpMethod.GET)
	public HelloGreeting getMyInfo(User user) {
		String str = "";
		if (user != null)
			str += "User: " + user.getNickname() + "\n" + "Email: " + user.getEmail() + "\n" + "ID: "
					+ user.getUserId();
		else
			str += "Login first";
		HelloGreeting response = new HelloGreeting(str);
		return response;
	}

	@ApiMethod(name = "getMeFromDatastore", path = "getMeFromDatastore", httpMethod = HttpMethod.GET)
	public HelloGreeting getMeFromDatastore(User user) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		String userId = user.getUserId();
		Key key = Key.create(Profile.class, userId);
		Profile profile = (Profile) ofy().load().key(key).now();
		String str = "";
		if (profile != null)
			str += "FROM DATASTORE" + "\n" + "User: " + profile.getName() + "\n" + "Email: " + profile.getEmail() + "\n"
					+ "ID: " + profile.getId();
		else
			str += "Nothing to show";
		HelloGreeting response = new HelloGreeting(str);
		return response;
	}

	private static String extractDefaultDisplayNameFromEmail(String email) {
		return email == null ? null : email.substring(0, email.indexOf("@"));
	}

	@ApiMethod(name = "saveProfile", path = "saveProfile", httpMethod = HttpMethod.POST)
	public Profile saveProfile(User user, ProfileForm profileForm) throws UnauthorizedException {

		String userId = null;
		String email = null;
		String name;

		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		if (user.getEmail() != null) {
			email = user.getEmail();
		}

		if (user.getUserId() != null) {
			userId = user.getUserId();
		}

		if (profileForm.getName() != null) {
			name = profileForm.getName();
		} else {
			name = extractDefaultDisplayNameFromEmail(email);
		}

		Profile profile = getProfile(user);
		if (profile == null) {
			profile = new Profile(userId, name, email);
		} else {
			int quantityOfGames = profileForm.getQuantityOfGames();
			int visitedGames = profileForm.getVisitedGames();
			int unvisitedGames = profileForm.getUnvisitedGames();
			int[] sportSkill = profileForm.getSportSkill();
			profile.update(sportSkill, visitedGames, unvisitedGames, quantityOfGames);
		}
		ofy().save().entity(profile).now();
		return profile;
	}

	@ApiMethod(name = "getProfile", path = "profile", httpMethod = HttpMethod.GET)
	public Profile getProfile(final User user) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		String userId = user.getUserId();
		Key key = Key.create(Profile.class, userId);
		Profile profile = (Profile) ofy().load().key(key).now();
		return profile;
	}

	@ApiMethod(name = "createGame", path = "createGame", httpMethod = HttpMethod.POST)
	public Game createGame(final User user, final GameForm gameForm) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		String userId = user.getUserId();
		Key<Profile> profileKey = Key.create(Profile.class, userId);

		final Key<Game> gameKey = ofy().factory().allocateId(profileKey, Game.class);

		final long gameId = gameKey.getId();

		Profile profile = getProfile(user);
		if (profile == null) {
			String email = user.getEmail();
			profile = new Profile(user.getUserId(), extractDefaultDisplayNameFromEmail(email), email);

		}

		Game game = new Game(gameId, userId, gameForm);
		ofy().save().entities(profile, game);
		// тут робиш відправку мейла
		return game;
	}

	@ApiMethod(name = "getAllGames", path = "getAllGames", httpMethod = HttpMethod.POST)
	public List queryGames() {
		List<Game> result =ofy().load().type(Game.class).list();
		return result;
	}
	
	
	

}
