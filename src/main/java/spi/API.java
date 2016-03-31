package spi;
import static service.OfyService.ofy;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import findteam.Constants;
import form.ProfileForm;
import domain.Profile;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.google.devrel.training.conference.service.OfyService;

@Api(name = "findteam", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, },
description = "API for FindTeam.")
public class API 
{
	
	private static String extractDefaultDisplayNameFromEmail(String email) {
		return email == null ? null : email.substring(0, email.indexOf("@"));
	}
	
	@ApiMethod(name = "saveProfile", path = "profile", httpMethod = HttpMethod.POST)
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
		if(profile == null){
		profile = new Profile(userId, name, email);
		}else{
			profile.update(name);
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
	
	
}
