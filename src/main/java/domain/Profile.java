package domain;

import java.util.ArrayList;
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
	int[] sportSkill;
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
		this.sportSkill=new int[5];
		this.gamesKeysToAttend = new ArrayList<String>();
	}
	
	public void update(int[] sportSkill, int visitedGames, int unvisitedGames,int quantityOfGames){
		this.visitedGames=visitedGames;
		this.unvisitedGames=unvisitedGames;
		this.quantityOFGames=quantityOfGames;
		this.sportSkill=sportSkill.clone();
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
	public void likeMySkill(Sports sport){
		sportSkill[sport.ordinal()]++;
	}
	
	public int[] getSportSkills() {
		return sportSkill;
	}
	public void setAttendance(){
		visitedGames++;
	}
	
	public void setNonAttendance(){
		unvisitedGames++;
	}
	
	private Profile() {}
	
	
}
