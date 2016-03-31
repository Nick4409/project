package domain;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
//Profile of our user
public class Profile
{
	//name of user
	String name;
	//email of user
	String email;
	//assessment for attendance
	int attend;
	//the number of assessments for attendance;
	int numOfAttandAssess;
	//list of sports attended by user
	ArrayList<Sports> sports;
	//user's average rating of skills for each kind of sport
	ArrayList<Integer> skill;
	//the numbers of assessment for skills
	ArrayList<Integer> numOfSkillAssess;
	//the userId is to be used in the Entity's key
	@Id 
	String userId;
	
	
	//constructor
	public Profile(String userId, String name, String email)
	{
		this.userId  = userId;
		this.name = name;
		this.email = email;
		sports = new ArrayList<Sports>();
		skill = new ArrayList<Integer>();
		numOfSkillAssess = new ArrayList<Integer>();
		attend = -1;
		numOfAttandAssess = 0;
	}
	
	public void update(String name)
	{
		this.name = name;
	}
	
	//set user's new name
	public void setName(String name)
	{
		this.name = name;
	}
	//get user's name
	public String getName()
	{
		return name;
	}
	
	
	//set user's new email
	public void setEmail(String email)
	{
		this.email = email;
	}
	//get user's email
	public String getEmail()
	{
		return email;
	}	
	
	
	//get user's id
	public String getId()
	{
		return userId;
	}
	
	
	//Set new sport user visited
	public void setSport(Sports sport)
	{
		sports.add(sport);
	}
	
	
	//Get all sports user visited
	public ArrayList<Sports> getAllSports()
	{
		return sports;
	}
	
	
	//Set one skill assessment for certain sport
	public void setSkill(Sports sport, int assessment)
	{
		int index = sports.indexOf(sport);
		int newRating = (skill.get(index)*numOfSkillAssess.get(index) + assessment)/numOfSkillAssess.get(index)+1;
		skill.set(index, newRating);
		numOfSkillAssess.set(index, numOfSkillAssess.get(index)+1);
	}
	
	
	//get skill for certain sport
	public int getSkill(Sports sport)
	{	
		return skill.get(sports.indexOf(sport));
	}
	
	
	//set one attendance assessment
	public void setAttend(int assessment)
	{
		attend = attend*numOfAttandAssess + assessment/numOfAttandAssess+1;
		numOfAttandAssess++;
	}
	
	
	//get attendance assessment
	public int getAttend()
	{
		return attend;
	}
}
