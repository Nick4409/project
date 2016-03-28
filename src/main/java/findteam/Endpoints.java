package findteam;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.util.ArrayList;

import javax.inject.Named;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(
    name = "endpoints",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class Endpoints {
	 
	@ApiMethod(name = "getMyInfo", path = "getMyInfo", httpMethod=HttpMethod.GET)
	  public HelloGreeting getMyInfo(User user) {
		String str="";
		if(user!=null)
			str += "User: "+user.getNickname()+"\n"+"Email: "+user.getEmail()+"\n"+"ID: "+user.getUserId();
		else str+="Login first";
	    HelloGreeting response = new HelloGreeting(str);
	    return response;
	  }
}
