package findteam;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.annotation.Entity;


import domain.Game;
import domain.Profile;
import form.GameForm;

import form.ProfileForm;

import static service.OfyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

	private static Profile getProfileFromUser(User user) {
		Profile profile = ofy().load().key(Key.create(Profile.class, user.getUserId())).now();
		if (profile == null) {
			String email = user.getEmail();
			profile = new Profile(user.getUserId(), extractDefaultDisplayNameFromEmail(email), email);
		}
		return profile;
	}

	@ApiMethod(name = "createGame", path = "createGame", httpMethod = HttpMethod.POST)
	public Game createGame(final User user, final GameForm gameForm) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		final String userId = user.getUserId();
		Key<Profile> profileKey = Key.create(Profile.class, userId);

		final Key<Game> gameKey = ofy().factory().allocateId(profileKey, Game.class);

		final long gameId = gameKey.getId();

		final Queue queue = QueueFactory.getDefaultQueue();
		Game game = ofy().transact(new Work<Game>() {
			@Override
			public Game run() {

				Profile profile = getProfileFromUser(user);

				Game game = new Game(gameId, userId, gameForm);

				ofy().save().entities(profile, game);

				queue.add(ofy().getTransaction(), TaskOptions.Builder.withUrl("/game_created_email")
						.param("email", profile.getEmail()).param("gameInfo", game.toString()));
				return game;
			}
		});
		return game;
	}

	@ApiMethod(name = "getAllGames", path = "getAllGames", httpMethod = HttpMethod.POST)
	public List queryGames() {
		List<Game> result = ofy().load().type(Game.class)

				.list();
		return result;
	}

	@ApiMethod(name = "getFootballGames", path = "getFootballGames", httpMethod = HttpMethod.POST)
	public List getFootballGames() {
		Date d = new Date();
		List<Game> result = ofy().load().type(Game.class)
				.filter("startDate >", d.getTime())
				.filter("sport", "Футбол")
				.order("startDate")
				.list();
		return result;
	}


	
	
	

	public static class WrappedBoolean {

		private final Boolean result;
		private final String reason;

		public WrappedBoolean(Boolean result) {
			this.result = result;
			this.reason = "";
		}

		public WrappedBoolean(Boolean result, String reason) {
			this.result = result;
			this.reason = reason;
		}

		public Boolean getResult() {
			return result;
		}

		public String getReason() {
			return reason;
		}
	}

	@ApiMethod(name = "registerForGame", path = "game/{websafeGameKey}/registration", httpMethod = HttpMethod.POST)

	public WrappedBoolean registerForGame(final User user, @Named("websafeGameKey") final String websafeGameKey)
			throws UnauthorizedException, NotFoundException, ForbiddenException, ConflictException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		WrappedBoolean result = ofy().transact(new Work<WrappedBoolean>() {
			@Override
			public WrappedBoolean run() {
				try {
					Key<Game> gameKey = Key.create(websafeGameKey);

					Game game = ofy().load().key(gameKey).now();

					if (game == null) {
						return new WrappedBoolean(false, "No Game found with key: " + websafeGameKey);
					}

					Profile profile = getProfile(user);

					if (profile.getGamesKeysToAttend().contains(websafeGameKey)) {
						return new WrappedBoolean(false, "Already registered");
					} else if (game.getSeatsAvailable() <= 0) {
						return new WrappedBoolean(false, "No seats available");
					} else {
						profile.addToGamesKeysToAttend(websafeGameKey);

						game.bookSeats(1);

						ofy().save().entities(profile, game).now();

						Queue queue = QueueFactory.getDefaultQueue();
						queue.add(ofy().getTransaction(), TaskOptions.Builder.withUrl("/register_for_game_email")
								.param("email", profile.getEmail()).param("gameInfo", game.toString()));

						return new WrappedBoolean(true, "Registration successful");
					}
				} catch (Exception e) {
					return new WrappedBoolean(false, "Unknown exception");
				}
			}

		});
		if (!result.getResult()) {
			if (result.getReason().contains("No Game found with key")) {
				throw new NotFoundException(result.getReason());
			} else if (result.getReason() == "Already registered") {
				throw new ConflictException("You have already registered");
			} else if (result.getReason() == "No seats available") {
				throw new ConflictException("There are no seats available");
			} else {
				throw new ForbiddenException("Unknown exception");
			}
		}
		return result;
	}

	@ApiMethod(name = "getGamesToAttend", path = "getGamesToAttend", httpMethod = HttpMethod.GET)
	public Collection<Game> getGamesToAttend(final User user) throws UnauthorizedException, NotFoundException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		Profile profile = getProfileFromUser(user);
		if (profile == null) {
			throw new NotFoundException("Profile doesn't exist.");
		}

		List<String> keyStringsToAttend = profile.getGamesKeysToAttend();

		List<Key<Game>> keysListToAttend = new ArrayList<>();
		for (String keyString : keyStringsToAttend) {
			keysListToAttend.add(Key.<Game> create(keyString));
		}
		return ofy().load().keys(keysListToAttend).values();
	}

	@ApiMethod(name = "unregisterFromGame", path = "game/{websafeGameKey}/registration", httpMethod = HttpMethod.DELETE)
	public WrappedBoolean unregisterFromGame(final User user, @Named("websafeGameKey") final String websafeGameKey)
			throws UnauthorizedException, NotFoundException, ForbiddenException, ConflictException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		WrappedBoolean result = ofy().transact(new Work<WrappedBoolean>() {
			@Override
			public WrappedBoolean run() {
				try {

					Key<Game> gameKey = Key.create(websafeGameKey);

					Game game = ofy().load().key(gameKey).now();

					if (game == null) {
						return new WrappedBoolean(false, "No game found with key: " + websafeGameKey);
					}

					Profile profile = getProfile(user);

					profile.deleteFromGamesKeysToAttend(websafeGameKey);

					game.giveBackSeats(1);

					ofy().save().entities(profile, game).now();
					return new WrappedBoolean(true, "Unregistration successful");
				} catch (Exception e) {
					return new WrappedBoolean(false, "Unknown exception");
				}
			}

		});
		if (!result.getResult()) {
			if (result.getReason().contains("No Game found with key")) {
				throw new NotFoundException(result.getReason());
			} else {
				throw new ForbiddenException("Unknown exception");
			}
		}
		return result;
	}

	//Чуваки, це чиїх рук справа? перейменували хоча б
	@ApiMethod(name = "getConferencesCreated", path = "getConferencesCreated", httpMethod = HttpMethod.POST)
	public List<Game> getGamesCreated(final User user) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		String userId = user.getUserId();
		Key<Profile> userKey = Key.create(Profile.class, userId);
		return ofy().load().type(Game.class).ancestor(userKey).order("name").list();
	}
	
	

}