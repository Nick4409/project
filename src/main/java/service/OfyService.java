package service;
import domain.Game;
import domain.Profile;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService
{
	static 
	{
        factory().register(Profile.class);
        factory().register(Game.class);
    }
	
	public static Objectify ofy() 
	{
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory() 
	{
        return ObjectifyService.factory();
    }
}