package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class Profile {
	// name of user
	String name;
	// email of user
	String email;
	//відвідані, невідвідані, та загальна квількість ігор на які користувач записаний
	int visitedGames;
	int unvisitedGames;
	int quantityOFGames;
	
	//в першій комірці зберізається кількість лайків для футбола в другій для волейболу і тд по ентіті спортс
	HashMap<String, ArrayList<String>> likeSkill;
 	HashMap<String, ArrayList<String>> dislikeSkill;
	private ArrayList<String> gamesKeysToAttend;
	@Id
	String userId;

	// constructor
	public Profile(String userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.visitedGames=0;
		this.unvisitedGames=0;
		this.quantityOFGames=0;
		this.likeSkill=new HashMap<String, ArrayList<String>>();
		likeSkill.put(Sports.football, new ArrayList<String>());
		likeSkill.put(Sports.basketball, new ArrayList<String>());
		likeSkill.put(Sports.volleyball, new ArrayList<String>());
		likeSkill.put(Sports.tennis, new ArrayList<String>());
		likeSkill.put(Sports.hockey, new ArrayList<String>());
		this.dislikeSkill=new HashMap<String, ArrayList<String>>();
		dislikeSkill.put(Sports.football, new ArrayList<String>());
		dislikeSkill.put(Sports.basketball, new ArrayList<String>());
		dislikeSkill.put(Sports.volleyball, new ArrayList<String>());
		dislikeSkill.put(Sports.tennis, new ArrayList<String>());
		dislikeSkill.put(Sports.hockey, new ArrayList<String>());
		this.gamesKeysToAttend = new ArrayList<String>();
	}
	
	public void update(HashMap<String, ArrayList<String>> likeSkill, HashMap<String, ArrayList<String>> dislikeSkill, int visitedGames, int unvisitedGames,int quantityOfGames){
		this.visitedGames=visitedGames;
		this.unvisitedGames=unvisitedGames;
		this.quantityOFGames=quantityOfGames;
		this.likeSkill=likeSkill;
		this.dislikeSkill=dislikeSkill;
	}
	
	// set user's new name
	public void setName(String name) {
		this.name = name;
	}

	// get user's name
	public String getName() {
		return name;
	}

	// set user's new email
	public void setEmail(String email) {
		this.email = email;
	}

	// get user's email
	public String getEmail() {
		return email;
	}

	// get user's id
	public String getId() {
		return userId;
	}

	public void addToGamesKeysToAttend(String gameKey){
		gamesKeysToAttend.add(gameKey);
		quantityOFGames++;
	}
	
	public void deleteFromGamesKeysToAttend(String gameKey){
		gamesKeysToAttend.remove(gameKey);
		quantityOFGames--;
	}
	
	public List<String> getGamesKeysToAttend(){
		List<String> res = new ArrayList<>();
		res.addAll(0, gamesKeysToAttend);
		return res;
	}
	
	//
	public void likeMySkill(String sport, String profileKey){
		likeSkill.get(sport).add(profileKey);
	}
	public void removeLikeMySkill(String sport, String profileKey){
		likeSkill.get(sport).remove(profileKey);
	}
	public HashMap<String, ArrayList<String>> getLikes() {
		return likeSkill;
	}
	public int getNumOfLikes(String sport){
		return likeSkill.get(sport).size();
	}
	
	
	public void dislikeMySkill(String sport, String profileKey){
		likeSkill.get(sport).add(profileKey);
	}
	public void removeDislikeMySkill(String sport, String profileKey){
		likeSkill.get(sport).remove(profileKey);
	}
	public HashMap<String, ArrayList<String>> getDislikes() {
		return dislikeSkill;
	}
	public int getNumOfDislikes(String sport){
		return dislikeSkill.get(sport).size();
	}
	public void setAttendance(){
		visitedGames++;
	}
	
	public void setNonAttendance(){
		unvisitedGames++;
	}
	
	private Profile() {}
	
	
}
