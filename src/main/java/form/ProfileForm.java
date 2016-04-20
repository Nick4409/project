package form;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileForm {
	private String name;
	private int visitedGames;
	private int unvisitedGames;
	private int quantityOFGames;
	
	private HashMap<String, ArrayList<String>> likeSkill = new HashMap<String, ArrayList<String>>();
	private HashMap<String, ArrayList<String>> dislikeSkill = new HashMap<String, ArrayList<String>>();

	//Ця штука просто має бути
	private ProfileForm () {}
	
	public ProfileForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getVisitedGames(){
		return visitedGames;
	}
	
	public int getUnvisitedGames(){
		return unvisitedGames;
	}
	
	public int getQuantityOfGames(){
		return quantityOFGames;
	}
	
	public int getLatitude(){
		return unvisitedGames;
	}
	
	public int getLongitude(){
		return quantityOFGames;
	}
	
	public HashMap<String, ArrayList<String>> getLikes(){
		return likeSkill;
	}
	
	public HashMap<String, ArrayList<String>> getDislikes(){
		return dislikeSkill;
	}
	
}
